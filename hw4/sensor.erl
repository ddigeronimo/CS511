%% Models a sensor process
-module(sensor).
-author("Dylan DiGeronimo and Ryan Locke")
-compile(export_all).

sensor(ID, Watcher) ->
    Ref = make_ref(),
    Measurement = rand:uniform(11),
    if Measurement == 11 ->
	    % report "anomalous_reading"
	    % Watcher needs to be replaced by the Pid of the sensor's watcher
	    Watcher ! {self(), Ref, "anamalous_reading"},
	    exit(sensor); % Should be changed to catch, but this works for now
       Measurement < 11 ->
	    % report to watcher
	    % Watcher needs to be replaced by the Pid of the sensor's watcher
	    % Check if the below tuple is correct
	    Watcher ! {self(), Ref, {ID, Measurement}},
	    Sleep_time = rand:uniform(10000),
	    timer:sleep(Sleep_time)
	    % How do we recurse?
    end.

