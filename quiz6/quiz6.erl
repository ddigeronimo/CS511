-module(quiz6).
-compile(export_all).
-author("Dylan DiGeronimo and Ryan Locke").

-type btree() :: {empty}
		 | {node, integer, btree(), btree()}.


-spec hasNoNodes(btree()) -> atom().

hasNoNodes({empty}) ->
    true;
hasNoNodes({node, integer, empty, empty}) ->
    true;
hasNoNodes(_) ->
    false.

-spec isComplete(btree()) -> atom().

%% Partially correct solution using no queue

isComplete({empty}) -> 
    true;
isComplete({node, integer, empty, empty}) ->
    true;
isComplete({node, integer, B1, empty}) ->
    hasNoNodes(B1);
isComplete({node, integer, B1, B2}) ->
    isComplete(B1) and isComplete(B2);
isComplete(_) ->
    false.

%% Incomplete attempt at using queue

-spec q0() -> queue.

q0() ->
    queue:new().

-spec isCompleteQ(btree(), queue) -> atom().

isCompleteQ({empty}, q0) ->
    true;
isCompleteQ({node, integer, empty, empty}, q0) ->
    true;
isCompleteQ({node, integer, B1, empty}, q0) ->
    queue:in(B1, q0);
isCompleteQ({node, integer, B1, B2}, q0) ->
    isCompleteQ(B1, q0) and isCompleteQ(B2, q0);
isCompleteQ(_,q) ->
    false.


    
