%% Models a sensor process
-module(sensor).
-export([sensor/2]).
-author("Dylan DiGeronimo and Ryan Locke").
% I pledge my honor that I have abided by the Stevens Honor System.

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
