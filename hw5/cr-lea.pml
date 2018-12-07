// Dylan DiGeronimo and Ryan Locke
// I pledge my honor that I have abided by the Stevens Honor System

#define N   5   /* Number of processes in the ring */
#define L   10  /* Size of buffer (>= 2*N) */
byte I;         /* Will be used in init for assigning ids to nodes */

mtype = { one, leader };   /* symb. Msg. Names */
chan q[N] = [L] of { mtype, byte };     /* Asynch. channels */

proctype nnode (chan inp, out; byte mynumber)
{   
    byte nr;

    xr inp;     /* Channel assertion: exclusive recv access to channel in */
    xr out;     /* Channel assertion: exclusive send access to channel out */

    printf("MSC: %d\n", mynumber);
    out!one(mynumber);
end:    do
    // Choose a leader with this process
    :: inp?one(nr) ->
        if
        :: nr == mynumber -> // You've been elected
                out!leader(mynumber);
                printf("MSC: Leader: %d\n", mynumber);
                break
        :: nr > mynumber -> 
                out!one(nr)
        :: nr < mynumber ->
                skip
        fi
    // Alert everyone and end choice with this process
    :: inp?leader(nr) ->
        if  
        :: nr != mynumber ->
                printf("MSC: Node %d confirms Node %d is leader\n", mynumber, nr);
                out!leader(nr)
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
        :: I <= N ->
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