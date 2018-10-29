%% Erlang implementation of the bar problem.

-module(bar).
-compile(export_all).

start(W, M) ->
    S=spawn(?MODULE, server, [0,0]),
    [spawn(?MODULE, woman, [S]) || _ <- lists:seq(1, W)],
    [spawn(?MODULE, man, [S]) || _ <- lists:seq(1, M)].

woman(S) -> % Reference to Pid of server
    S ! {self(), woman}.

man(S) -> % Reference to Pid of server
    Ref = make_ref(),
    S ! {self(), Ref, woman},
    receive
	{S, Ref, ok} ->
	    ok
    end.

server(Women) -> % "Counter" of Women
    receive
	{From, woman} ->
	    From ! {self(), ok},
	    server(Women+1);
	{From, Ref, man} when Women > 1 ->
	    From ! {self(), Ref, ok},
	    server(Women-2)
    end.
    
