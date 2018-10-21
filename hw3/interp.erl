%% Dylan DiGeronimo and Ryan Locke
%% I pledge my honor that I have abided by the Stevens

-module(interp).
-export([scanAndParse/1,runFile/1,runStr/1]).
-include("types.hrl").

loop(InFile,Acc) ->
    case io:request(InFile,{get_until,prompt,lexer,token,[1]}) of
        {ok,Token,_EndLine} ->
            loop(InFile,Acc ++ [Token]);
        {error,token} ->
            exit(scanning_error);
        {eof,_} ->
            Acc
    end.

scanAndParse(FileName) ->
    {ok, InFile} = file:open(FileName, [read]),
    Acc = loop(InFile,[]),
    file:close(InFile),
    {Result, AST} = parser:parse(Acc),
    case Result of 
	ok -> AST;
	_ -> io:format("Parse error~n")
    end.


-spec runFile(string()) -> valType().
runFile(FileName) ->
    valueOf(scanAndParse(FileName),env:new()).

scanAndParseString(String) ->
    {_ResultL, TKs, _L} = lexer:string(String),
    parser:parse(TKs).

-spec runStr(string()) -> valType().
runStr(String) ->
    {Result, AST} = scanAndParseString(String),
    case Result  of 
    	ok -> valueOf(AST,env:new());
    	_ -> io:format("Parse error~n")
    end.


-spec numVal2Num(numValType()) -> integer().
numVal2Num({num, N}) ->
    N.

-spec boolVal2Bool(boolValType()) -> boolean().
boolVal2Bool({bool, B}) ->
    B.

-spec valueOf(expType(),envType()) -> valType().

% For numVal, return it
valueOf({num, N}, _Env) ->
    {num, N};

% For boolVal, return it as well
valueOf({bool, B}, _Env) ->
    {bool, B};

% For numExp, return the numVal
valueOf({numExp, {num, _, N}}, _Env) ->
    {num, N};

% For idExp, look up the item x in the environment
valueOf({idExp, {id, _, X}}, Env) ->
    env:lookup(Env, X);

% For diffExp, calculate and convert using numVal2Num
valueOf({diffExp, X, Y}, Env) ->
    Diff = numVal2Num(valueOf(X, Env)) - numVal2Num(valueOf(Y, Env)),
    {num, Diff};

% plusExp follows the same pattern with addition
valueOf({plusExp, X, Y}, Env) ->
    Sum = numVal2Num(valueOf(X, Env)) + numVal2Num(valueOf(Y, Env)),
    {num, Sum};

% For isZeroExp, evaluate if the value is 0  
valueOf({isZeroExp, X}, Env) ->
    Truth = (numVal2Num(valueOf(X, Env)) == 0),
    {bool, Truth};

% For ifThenElseExp, evaluate the condition. If true, then get valueOf x. Otherwise, get valueOf y. Uses pattern matching.
valueOf({ifThenElseExp, Condition, X, Y}, Env) ->
    if Condition == true ->
	    valueOf(X, Env);
       Condition == false ->
	    valueOf(Y, Env);
       % Convert condition to bool and reevaluate if it isn't a bool originally
       true -> 
	    valueOf({ifThenElseExp, boolVal2Bool(valueOf(Condition, Env)), X, Y}, Env)
    end;

% For letExp, you need to store the id in the environment
valueOf({letExp, IdVal, ValExp, InExp}, Env) ->
    {id, _, Id} = IdVal,
    valueOf(InExp, env:add(Env, Id, valueOf(ValExp, Env)));

% For procExp, you set the id and evaluate the proc
valueOf({procExp, IdVal, Exp}, Env) ->
    {id, _, Id} = IdVal,
    {proc, Id, Exp, Env};

% For appExp, you need to apply the given proc to the given value, returning the result through a letExp
valueOf({appExp, ProcId, ValExp}, Env) ->
    {proc, ProcExpId, ProcExp, ProcEnv} = valueOf(ProcId, Env),
    valueOf({letExp, {id, 1, ProcExpId}, valueOf(ValExp, Env), ProcExp}, ProcEnv).


    
    





