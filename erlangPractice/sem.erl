-module(sem).
-compile(export_all).

make_semaphore(Permits) ->
    spawn(?MODULE, semaphore, [Permits]).

% Complete

% Define semaphore
semaphore(0) ->
    receive
	{From, release} ->
	    From!{self(),ok},
	    semaphore(1)
    end;
semaphore(N) ->
    receive
	{From, acquire} ->
	    From!{self(),ok},
	    semaphore(N-1);
	{From, release} ->
	    From!{self(),ok},
	    semaphore(N+1)
    end.

client1(S) ->
    io:format("A~n"),
    S!{self(), acquire},
    receive
	{From, ok}->
	    ok
    end,
    io:format("C~n").

client2(S) ->
    io:format("B~n"),
    S!{self(), acquire},
    receive
	{From, ok}->
	    ok
    end.

test() ->
    S=make_semaphore(0),
    spawn(?MODULE, client1, [S]),
    spawn(?MODULE, client2, [S]).
