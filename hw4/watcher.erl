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
    % Create watchers first (watch), then sensors, even though the watchers need a list of sensors
    error(doThis).


% Main logic of watcher, takes in ID of watcher and list of Pids created by createWatcher()
watch(ID, Pids) ->
    receive
	{'DOWN', _, process, Pid2, Reason} ->
	    % TODO: Iterate through pids, find Pid2, remove it, replace it
	    % TODO: Implement replacement sensor
	    io:fwrite("Sensor ~p crashed, because of ~p.~n", [Pid2, Reason]);
	    % Print list of Pids
	{SID, Measurement} ->
	    % Print "Sensor _ reported measurement: Measurement"
	    io:fwrite("Sensor ~p reported measurement: ~p ~n", [SID, Measurement])
    end,
    watch(_, _).


    
 
