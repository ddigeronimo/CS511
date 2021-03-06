%% Basic Erlang echo server

-module(es).
-compile(export_all).


echo() ->
    receive
	{From,Msg} ->
	       From!{self(),Msg},
	       echo();
	stop ->
	       stop
end.


client(S,M) ->
    S!{self(),M},
    io:format("Client ~w sent ~w~n",[self(),M]),
    receive
	{S,Msg} ->
	    io:format("Client ~w got back 1~w~n",[self(), Msg])				 
end.


start() ->    
    S=spawn(?MODULE,echo,[]),
    spawn(?MODULE,client,[S,1]),
    spawn(?MODULE,client,[S,2]).
