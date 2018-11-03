%% Erlang code modelling a timer that sends "ticks" to different processes at a specified interval

-module(timer).
-compile(export_all).

start() ->
    L = [spawn(?MODULE, client, []) || _ <- lists:seq(1,10)],
    spawn(?MODULE, timer, [100,L]).

timer(Frequency, L) ->
    timer:sleep(Frequency),
    Ref = make_ref(),
    L ! {self(), Ref, tick},

client() ->
    pass.
