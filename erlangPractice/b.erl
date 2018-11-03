%% Barrier syncronization exercise

-module(b).
-compile(export_all).

startb(N) ->
    Pid = spawn(fun() -> coordinator(N, N, []) end), %% we utilize a copy of N to serve as a decrementing counter
    register(coordinator,Pid).

start() ->
    startb(3),
    spawn(?MODULE, client,["a~n","b~n"]),
    spawn(?MODULE, client,["c~n","d~n"]),
    spawn(?MODULE, client,["e~n","f~n"]).

coordinator(0, N, L) -> % All threads in, notify
    % Notify all in list
    [From ! {self(), Ref, ok} || {From, Ref} <- L],
    % Restart coordinator w/ original parameters
    coordinator(N,N,[]);

coordinator(M, N, L) when M > 0 -> % Waiting for more threads
    receive
	{From, Ref, arrived} ->
	    % Recursively call coordinator, decrementing the remaining number of threads and adding the current one to the list
	     coordinator(M-1, N, [ {From, Ref} | L])
    end.

client(Str1, Str2) ->
    io:format(Str1, []),
    Ref = make_ref(),
    C = whereis(coordinator),
    % Signal to coordinator that a thread has arrived 
    coordinator ! {self(), Ref, arrived},
    % Get the all clear from coordinator to print the next string
    receive
	{C, Ref, ok} ->
	    ok
    end,
    io:format(Str2, []).
    
