-module(bar).
-compile(export_all).

start(W, M) ->
    S=spawn(?MODULE, server, [0,0]),
    [spawn(?MODULE, woman, [S]) || _ <- lists:seq(1, W)],
    [spawn(?MODULE, man, [S]) || _ <- lists:seq(1, M)].

woman(S) -> % Reference to Pid of server
    Ref = make_ref(),
    S ! (self(), W, Ref).

man(S) -> % Reference to Pid of server
    error(not_implemented).

server(Woman, Man) -> % "Counters" of Women and Men
    error(not_implemented).
