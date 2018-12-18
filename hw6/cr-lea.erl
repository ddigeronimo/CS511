%% Dylan DiGeronimo and Ryan Locke
%% I pledge my honor that I have abided by the Stevens Honor System

-module('cr-lea').
-compile([export_all]).

%% Startup function that begins the process with the requested number of nodes
start(N) ->
    %% Create nodes with randomized ID's from 1 to N
    Node_list = [spawn(?MODULE, node, [ID]) || ID <- shuffle(lists:seq(1, N))],
    %% Duplicate Node_list shifted over by 1 so that nodes can access their neighbors
    Neighbor_list = shift(Node_list, []),
    %% Inform each node who its neighbor is and to talk to it
    [Node ! {neighbor, Neighbor} || {Node, Neighbor} <- lists:zip(Node_list, Neighbor_list)],
    done.

%% Helper function that shifts elements in a list by one into a new list
shift([Last_elem], New_list) ->
    [Last_elem]++New_list;
shift([H|T], New_list) ->
    shift( T , New_list++[H]).

%% Helper function to randomly shuffle an array
%% Found at https://www.rosettacode.org/wiki/Knuth_shuffle
shuffle(Inputs) ->
    N = erlang:length(Inputs),
    {[], Acc} = lists:foldl(fun random_move/2, {Inputs, []}, lists:reverse(lists:seq(1, N))),
    Acc.
random_move(N, {Inputs, Acc}) ->
    Item = lists:nth(rand:uniform(N), Inputs),
    {lists:delete(Item, Inputs), [Item | Acc]}.

%% Node module to spawn, signals neighbor and then begins server
node(ID) ->
    receive
	%% Receive signal from neighbor
	{neighbor, Neighbor} ->
	    %% Send its neighbor its ID
	    Neighbor ! {id, ID},
	    %% Begin the election server for the node
	    election(ID, Neighbor)
    end.

%% Server function to handle majority of node behavior
election(ID, Neighbor) ->
    receive
	{id, ID} ->
	    %% Received your own ID, pass on that the election is over
	    Neighbor ! {stop, ID},
	    election(ID, Neighbor);
	{id, Neighbor_ID} when ID < Neighbor_ID ->
	    %% You won! Pass the neighbor their own ID to stop
	    Neighbor ! {id, Neighbor_ID},
	    election(ID, Neighbor);
	{id, Neighbor_ID} when ID > Neighbor_ID ->
	    %% This case should not happen so  you should do nothing
	    election(ID, Neighbor);
	{stop, Winning_ID} ->
	    %% The election is over, tell your neighbor to stop and then alert
	    Neighbor ! {stop, Winning_ID},
	    io:format("Node: ~p reporting Node ~p is the leader~n", [ID, Winning_ID]),
	    ok
    end.
