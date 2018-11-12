%% Models a sensor process
-module(sensor).
-author("Dylan DiGeronimo and Ryan Locke").

sensor(ID, Watcher) ->
    Measurement = rand:uniform(11),
    if Measurement == 11 ->
	    % report "anomalous_reading" to watcher
	    exit({ID, anomalous_reading});
       Measurement < 11 ->
	    % report measurement to watcher
	    Watcher ! {ID, Measurement}
    end, 
    timer:sleep(rand:uniform(10000)),
    sensor(ID, Watcher).
