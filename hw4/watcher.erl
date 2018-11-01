%% Models watcher processes
-module(watcher).
-author("Dylan DiGeronimo and Ryan Locke")
-compile(export_all).

start() ->
    {ok, [N]} = io:fread("enter number of sensors> ", "~d"),
    if N =< 1 ->
	    io:fwrite("setup: range must be at least 2~n", []) ;
       true ->
	    Num_watchers = 1 + (N div 10),
	    setup_loop(N, Num_watchers)
end.

% Creates N sensors and Num_watchers watchers 
setup_loop(N, Num_watchers) ->
    % Should I spawn all of the processes through recursion?
    error(notImplemented).

 
