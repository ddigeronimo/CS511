% Dylan DiGeronimo and Ryan Locke
% I pledge my honor that I have abided by the Stevens Honor System

-module(gg).
-compile(export_all).

% start process that creates initial server
start() ->
    spawn(fun server/0).

% Server that gets Pid of client, creates a servlet for each one to play with, and then passes the servlet Pid
server() ->
    receive
	PidC ->
	    PidC ! spawn(fun servlet/0)
    end.    

% Servlet process that plays the game once with a client and then closes
servlet() ->
    receive
	{Pid, Ref, Number}->
	    Randumb = rand:uniform(10),
	    if Randumb == Number ->
		    io:format("gotIt");
	       Randumb /= Number ->
		    io:format("tryAgain")		    
	    end
    end.
	 
% Client that passes its Pid to the server, gets a servlet to play with, and then plays with it
client(S) ->
    Randumb2 = rand:uniform(10),
    Ref = make_ref(),
    S ! self(),
    receive 
	PidS ->
	    PidS ! {self(), Ref, Randumb2}
    end.
