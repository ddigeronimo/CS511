byte women = 0;
byte men = 0;
byte sem = 0;
byte mutex = 1;

inline acquire(sem) {
    skip;
    mylabel:
    atomic {
        (sem>0);
        sem--;
    }
}

inline release(sem) {
    sem++;
}

active [20] proctype Man {
    acquire(mutex);
    acquire(sem);
    acquire(sem);
    men++;
    release(mutex);
    assert (men*2 <= women);
}

active [20] proctype Woman {
    acquire(mutex);
    women++;
    release(sem);
    release(sem);
    release(mutex);
}

init {
    
}