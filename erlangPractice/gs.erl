-module(gs).
-compile(export_all).

start(InitialState, F) ->
    spawn(?MODULE, server_loop, [InitialState, F]).

server_loop(State, F) ->
    receive
	{From, Ref, Data, request} ->
	    case catch(F(State,Data)) of
		{'EXIT', Reason} ->
		    From ! {self(), Ref, Reason},
		    server_loop(State,F);
		{NewState, Result} ->
		    From ! {self(), Ref, Result},
		    server_loop(NewState, F)
	    end;
	{From, Ref, G, update} -> % hot swapping
	    From ! {self(), Ref, ok},
	    server_loop(State, G);
	{From, Ref, inspect} ->
	    From ! {self(), Ref, State},
	    server_loop(State, F);
	{stop} ->
	    stop
    end.
