%% Exercise implementing a music critic system.

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
    Ref = make_ref(),
    Pid ! {self(), Ref, {Band , Album}},
    receive
	{Ref, Criticism} -> 
	    Criticism
    end.

critic() ->
    receive
	{From, Ref, {"Rage  Against  the  Turing  Machine", "Unit  Testify"}} ->
	    From ! {Ref, "They  are  great!"};
	{From, Ref, {"System  of a Downtime", "Memoize"}} ->
	    From ! {Ref, "They’re not  Johnny  Crash  but  they’re good."};
	{From, Ref, {"Johnny  Crash", "The  Token  Ring of Fire"}} ->
	    From ! {Ref, "Simply  incredible."};
	{From, Ref, {_Band , _Album}} ->
	    From ! {Ref, "They  are  terrible!"}
    end ,
    critic ().

