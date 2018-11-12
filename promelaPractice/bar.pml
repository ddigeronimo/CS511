byte women = 0;
byte men = 0;
byte sem = 0;

inline acquire(sem) {
    atomic {
        (sem>0);
        sem--;
    }
}

inline release(sem) {
    sem++;
}


init {
    
}