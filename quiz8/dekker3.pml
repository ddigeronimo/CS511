// Dylan DiGeronimo and Anthony Duca
// I pledge my honor that I have abided by the Stevens Honor System

byte turn;
bool flags[3];

proctype P() {
    byte myId = _pid-1;
    /* Complete this */
    byte left;
    if 
        :: myId == 0 ->
            left = 2;
        :: else ->
            left = (myId - 1) % 3;  
    fi
    byte right = (myId + 1) % 3;
    flags[myId] = true;
    do
        :: (flags[left] == false && flags[right] == false) ->
            break;
        :: (flags[left] || flags[right]) ->
            if
                ::  turn == left -> 
                        flags[myId] = false;
                        (turn == myId);
                        flags[myId] = true;
                :: turn != left ->
                        break;
            fi
    od
    // Critical section
    turn = right;
    flags[myId] = false;
            


}

init {
    turn = 0;
    byte i;
    for (i:0..2) {
        flags[i] = false;
    }
    atomic {
        for (i:0..2) {
            run P();
        }
    }
}