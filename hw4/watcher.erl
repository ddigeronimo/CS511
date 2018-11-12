%% Models watcher processes
-module(watcher).
-export([start/0]).
-author("Dylan DiGeronimo and Ryan Locke").

-spec start() -> no_return().
start() ->
    {ok, [N]} = io:fread("enter number of sensors> ", "~d"),
    if N =< 1 ->
	    io:fwrite("setup: range must be at least 2~n", []) ;
       true ->
	    Num_watchers = 1 + (N div 10),
	    setup_loop(N, Num_watchers, 0)
end.

-spec setup_loop(N :: integer(), Num_watchers :: integer(), Cur :: integer()) -> pid().
% Creates N sensors and Num_watchers watchers 
setup_loop(N, 1, Cur) ->
    % Associate the last N sensors + sensor id's with a watcher
    spawn_monitor(watcher, create_watcher, [lists:seq(Cur, Cur+N), []]);
setup_loop(N, Num_watchers, Cur) ->
    % N is the total number of sensors
    % Num_watchers is the number of watchers we need to spawn
    % we need to accumulate 10 spawned sensors before can pass them off to a watcher.
    % a sensor requires the pid of its watcher
    % Cur is the id of the latest sensor created, the 3rd argument in setup_loop
    spawn_monitor(watcher, create_watcher, [lists:seq(Cur, Cur+10), []]),
    % N = total number of sensors input by user.
    % sub 10 everytime because it is sent 10 sensor_ids every time
    % recursive call with parameters modified as described above here
    setup_loop(N-10, Num_watchers-1, Cur+11).

-spec create_watcher(S_id_List :: list(), Sensor_List :: list()) -> pid().
%creates a single watcher after accumulating <=10 sensors into a list
create_watcher([], Sensor_List) ->
    io:fwrite("Watcher starting: ~w~n", [Sensor_List]),
    % Initiates receiving from a watcher
    watcher:watch(Sensor_List); %TODO: watch also needs to take in ID
create_watcher(S_id_List, Sensor_List) ->
    % Get id for new sensor
    Cur = hd(S_id_List),
    T = tl(S_id_List),
    % Spawn a new sensor, passing in self() as the watcher pid,
    {S_Pid, _} = spawn_monitor(sensor, sensor, self()), 
    % Because in the base case of fn, we have a call to an actual watcher
    % Generate next sensor, accumulate new sensor object into list
    create_watcher(T, Sensor_List ++ [{Cur, S_Pid}]).

% Main logic of watcher, takes in ID of watcher and list of Pids created by createWatcher()
watch(ID, Pids) ->
    receive
	{'DOWN', _, process, Pid2, {SID, Reason}} ->
	    io:fwrite("Sensor ~p died, reason: ~p.~n", [SID, Reason]),
	    io:fwrite("Replacing sensor ~p with replacement sensor: ~n", [SID]),
	    % Implement replacement sensor
	    {S_Pid, _} = spawn_monitor(sensor, sensor, self()),
	    % Create new pidlist with replacement snesor's pid
	    NewPids = lists:keyreplace(Pid2, 1, Pids, {SID, S_Pid}),
	    % Print list of Pids
	    io:fwrite("~p ~n", [Pids]),
	    watch(ID, NewPids);
	{SID, Measurement} ->
	    io:fwrite("Sensor ~p reported measurement: ~p ~n", [SID, Measurement])
    end,
    watch(ID, Pids). 

%%% OVERALL NOTES
% N is how many sensors the user requested to make
% we compute how many watchers we will need, at <= 10 sensors
% per watcher.
% Then, we make a call to setup_loop which collects sensor_ids to send off
% to create_watcher in batches of 10, except for the last which will send
% <= 10 sensor_ids
% A typical argument set to create_watcher is shown below,
% where the first argument is the sensor id's we will make, given by
% setup_loop at lists:seq(begin, end) and the second argument is an empty
% list into which we accumulate the list of {Sensor_id, Sensor_pid} tuples
% to send to a single watcher
% [[0,1,2,3,4,5,6,7,8,9],[]) //line 30 -> line 38
% [[1,2,3,4,5,6,7,8,9],[{0,<0.69.0>}]] //line 43 gets current and pid
% [[2,3,4,5,6,7,8,9], [{0,<0.69.0}, {1,<0.79.2>}, ]]
% ...
% [[], [{0,<0.69.0>},...,{9,<0.102.2>}]]-
% after this, a call to watcher:watch is made with the list on the right.
% the entire process above is repeated `Num_watchers` times.
% the next call to create_watcher made by setup loop would look like:
% [[10,11,12,13,14,15,16,17,18,19],[]],
% after which we would perform the same procedure
% which is computed in the start() function when it said 1 + (N div 10)
% it stops making calls to create watcher when there is only one watcher
% left to create
% This "edge" case is caught by setup_loop, which instead of
% sending a batch of 10, sends just however many were remaining at the end
