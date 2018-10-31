%% Erlang implementation of the bar problem.

-module(bar).
-compile(export_all).

start(W, M) ->
    S=spawn(?MODULE, server, [0,0]),
    [spawn(?MODULE, woman, [S]) || _ <- lists:seq(1, W)],
    [spawn(?MODULE, man, [S]) || _ <- lists:seq(1, M)],
    [spawn(?MODULE, itGotLate, [1000,S])].

itGotLate(Time, S) ->
    timer:sleep(Time),
    Ref = make_ref(),
    S ! {self(), Ref, itGotLate},
    receive
	{S, Ref, ok} ->
	    done
    end.

woman(S) -> % Reference to Pid of server
    S ! {self(), woman}.

man(S) -> % Reference to Pid of server
    Ref = make_ref(),
    S ! {self(), Ref, woman},
    receive
	{S, Ref, ok} ->
	    ok
    end.

answer_pending_men_requests() ->
    receive
	{From,Ref,man} ->
	    From!{self(),Ref,ok},
	    answer_pending_men_requests()
    after 0 ->
	    ok
end.

server(Women, false) -> % It is not late
    receive
	{From, woman} ->
	    From ! {self(), ok},
	    server(Women+1, false);
	{From, Ref, man} when Women > 1 ->
	    From ! {self(), Ref, ok},
	    server(Women-2, false);
	{From, Ref, itGotLate} ->
	    From ! {self(), Ref, ok}, %% reply to the itGotLate thread
	    answer_pending_men_requests(),
	    server(true)
    end.

server(true) -> % It is late
    receive
	{_From, woman} ->
	    server(true);
	{From, Ref, man} ->
	    From ! {self(), Ref, ok},
	    server(true)
    end.
    
