-module(rw).
-compile(export_all).

reader(RW) ->
    Ref = make_ref(),
    RW ! {self(), Ref, start_read},
    receive
	{RW, Ref, ok} ->
	    read
    end,
    RW ! {self(), Ref, end_read}.

loop(_R, _W) ->
    error(notImplemented).
