/* Example of absence deadlock in Promela */

bool wantP = false;
bool wantQ = false;

active proctype P() {

  do
    ::
    wantP = true;
    !wantQ;
    /* Critical Section */
    wantP = false;
  od
}

active proctype Q() {

  do
    ::
    wantQ = true;
    !wantP;
    /* Critical Section */
    wantQ = false;
  od
       
}