%% Models a sensor process
-module(sensor).
-author("Dylan DiGeronimo and Ryan Locke")
-compile(export_all).

sensor(ID, Watcher) ->
    Measurement = rand:uniform(11),
    if Measurement == 11 ->
	    % report "anomalous_reading" to watcher
	    Watcher ! {ID, "anomalous_reading"},
	    exit(anomalous_reading);
       Measurement < 11 ->
	    % report measurement to watcher
	    Watcher ! {ID, Measurement}
		end, 
    Sleep_time = rand:uniform(10000),
    timer:sleep(Sleep_time),
    sensor(ID, Watcher).
	  
