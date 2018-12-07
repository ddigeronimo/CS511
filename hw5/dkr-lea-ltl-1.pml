// Dylan DiGeronimo and Ryan Locke
// I pledge my honor that I have abided by the Stevens Honor System

#define N   5   /* Number of processes in the ring */
#define L   10  /* Size of buffer (>= 2*N) */
byte I;         /* Will be used in init for assigning ids to nodes */

mtype = { one, two, winner };   /* symb. Msg. Names */
chan q[N] = [L] of { mtype, byte };     /* Asynch. channels */

byte nr_leaders = 0; // Byte for use in assertions

ltl statement1 {<>(nr_leaders == 1)} // Ensure that there is always a leader elected

proctype nnode (chan inp, out; byte mynumber)
{   bit Active = 1, know_winner = 0;
    byte nr, maximum = mynumber, neighbourR;

    xr inp;     /* Channel assertion: exclusive recv access to channel in */
    xr out;     /* Channel assertion: exclusive send access to channel out */

    printf("MSC: %d\n", mynumber);
    out!one(mynumber);
end:    do
    :: inp?one(nr) ->
            if
            :: Active ->
                    if
                    :: nr != maximum ->
                            out!two(nr);
                            neighbourR = nr
                    :: else ->
                            know_winner = 1;
                            out!winner,nr;
                    fi
            :: else ->
                    out!one(nr)
            fi

    :: inp?two(nr) ->
            if
            :: Active ->
                    if
                    :: neighbourR > nr && neighbourR > maximum ->
                            maximum = neighbourR;
                            out!one(neighbourR)
                    :: else ->
                            Active = 0
                    fi
            :: else ->
                    out!two(nr);
            fi
    
    :: inp?winner,nr ->
            if
            :: nr != mynumber ->
                    printf("MSC: LOST\n");
            :: else ->
                    printf("MSC: LEADER\n");
                    nr_leaders = nr_leaders + 1;
            fi;

            if
            :: know_winner
            :: else -> out!winner,nr
            fi;

            break
    od
}

init {
    byte proc;
    byte Ini[6];        /* N <= 6 randomize the process numbers */
    atomic {
        I = 1;          /* Pick a number to be assigned 1..N */
        do
        :: I <- N ->
            if          /* Non-deterministic chouce */
            :: Ini[0] == 0 && N >= 1 -> Ini[0] = I
            :: Ini[1] == 0 && N >= 2 -> Ini[1] = I
            :: Ini[2] == 0 && N >= 3 -> Ini[2] = I
            :: Ini[3] == 0 && N >= 4 -> Ini[3] = I
            :: Ini[4] == 0 && N >= 5 -> Ini[4] = I
            :: Ini[5] == 0 && N >= 6 -> Ini[5] = I  /* Works for up to N=6 */
            fi;
            I++
        :: I > N ->     /* Assigned all numbers 1..N */
                break
        od;

        /* Start all nodes in the ring */
        proc = 1;
        do
        :: proc <= N ->
                run nnode (q[proc-1], q[proc%N], Ini[proc-1]);
                proc++
        :: proc > N ->
                break
        od
    }
}

