// Java pseudocode emulating a coffee machine with two dispensers -- will not run

Semaphore buttons = new Semaphore(2);
Semaphore employeeCanRefill = new Semaphore(1); 
int amtOfCustomers = 0;

thread customer {
       amtOfCustomers++;
       buttons.acquire();
       getCoffee();
       buttons.release();
       amtOfCustomers--;

}

thread employee {

    while(true) {

	mutex.acquire();
	repeat (2) { buttons.acquire(); }
	mutex.release();
	
	fill();

	repeat(2) { buttons.release(); }


    }
}

