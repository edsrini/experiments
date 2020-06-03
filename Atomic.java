/**
Locking is a very expensive operation, so we can use atomic variable and perform compare & swap operation
**/
public class Atomic {
    private final AtomicInteger counter = new AtomicInteger(0);
     
    public int getValue() {
        return counter.get();
    }
    public void increment() {
        //Loops until counter is successfully updated.
        while(true) {
            int old = getValue();
            int new = existingValue + 1;
            
            //Compare and Swap
            if(counter.compareAndSet(old, new)) {
                return;
            }
        }
    }
}

