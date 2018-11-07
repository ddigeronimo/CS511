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

% Creates a watcher and spawns it's sensors
createWatcher(Watchers, Current) ->
    error(notImplemented).

% Spawns sensors
createSensors(0, _Current, Pids) ->
    % 0 sensors left, return pid list
    Pids;
createSensors(N, Current, Pids) ->
    % Create a new sensor using spawn_monitor
    {Pid, _} = spawn_monitor(sensor, sensor, [self(), Current]),
    % Recurse and add the newest pid and it's number to the pid list
    createSensors(N-1, Current+1, lists:append(Pids, [{Current, Pid}])).


% Creates N sensors and Num_watchers watchers 
setup_loop(N, Num_watchers) ->
    error(doThis).


% Main logic of watcher, takes in ID of watcher and list of Pids created by createWatcher()
watcherMain(ID, Pids) ->
    receive
	{ID, "anamalous_reading"} ->

    end,
    watcherMain(ID,_).


    
 
