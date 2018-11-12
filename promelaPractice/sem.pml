byte sem = 0;
byte x = 0;

inline acquire(sem) {
    atomic {
        (sem>0);
        sem--;
    }
}

inline release(sem) {
    sem++;
}

active proctype P() {
    printf("Releasing a permit from P\n");
    release(sem);
} 

active proctype Q() {
    acquire(sem);
    printf("Permit acquired by Q\n");
}