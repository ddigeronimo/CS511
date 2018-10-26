%% Exercise implementing a music crtiic system.

-module(cr).
-compile(export_all).

start() ->
    spawn(?MODULE, restarter, []).

restarter() ->
    process_flag(trap_exit, true),
    Pid = spawn_link(?MODULE, critic, []),
    register(critic,Pid),
    receive
	{'EXIT', Pid, normal} -> % Not a crash
	    ok;
	{'EXIT', Pid, shutdown} -> % manually terminated
	    ok;
	{'EXIT', Pid, _} -> 
	    restarter()
    end.

judge(Pid , Band , Album) ->
    Pid = whereis(critic),
    Pid ! {self(), {Band , Album}},
    receive
	{Pid , Criticism} -> Criticism
    after 2000  ->
	    timeout
    end.

critic () ->
    receive
	{From , {"Rage  Against  the  Turing  Machine", "Unit  Testify"}} ->
	    From ! {self(), "They  are  great!"};
	{From , {"System  of a Downtime", "Memoize"}} ->
	    From ! {self(), "They’re not  Johnny  Crash  but  they’re good."};
	{From , {"Johnny  Crash", "The  Token  Ring of Fire"}} ->
	    From ! {self(), "Simply  incredible."};
	{From , {_Band , _Album}} ->
	    From ! {self(), "They  are  terrible!"}
    end ,
    critic ().

