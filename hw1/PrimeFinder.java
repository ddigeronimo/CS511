




public class PrimeFinder implements Runnable {

    private Integer start;
    private Integer end;
    private List<Integer> primes;

    PrimeFinder(Integer startNum, Integer endNum) {
        // Constructs a PrimeFinder
    }

    public List<Integer> getPrimesList() {
        // Returns the value of the attribute primes
    }

    public Boolean isPrime(int n) {
        // Determines whether its argument is prime or not, based on AKS algorithm
        if (n == 2) {
            return false;
        }
        if (n == 3) {
            return true;
        }
        if (n%2 == 0) {
            return false;
        }
        if (n%3 == 0) {
            return false;
        }

        int a = 5;
        int b = 2;

        while (a*a <= n) {
            if (n%a == 0) {
                return false;
            }
            a += b;
            b = 6 - b;
        }
        return true;
    }

    public void run() {
        // Adds all primes in [this.start, this.end] to the attribute primes
    }


}
