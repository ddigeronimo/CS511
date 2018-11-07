byte counter = 0;
byte finished = 0;

active [2] proctype P() {
       byte i, temp;
       for(i:1..10) {
       temp = counter;
       counter = temp+1;
       }
       finished = finished+1;

}

init {
     (finished == 2);
     printf("%d\n", counter);
     assert(counter>2);
}