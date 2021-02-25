import java.util.concurrent.atomic.AtomicInteger;

// trm2796
// rt25884

public class FairUnifanBathroom {   
	private AtomicInteger status = new AtomicInteger(); //nobody status 0, ut status 1, ou status 2
    private int capacity = 4;
    private AtomicInteger people = new AtomicInteger();
    private int ticketNumber = 0;
    private int nextUp = 1;
    
    private synchronized int getTickerNumber(){
        ticketNumber++;
        return ticketNumber;
    }

    public synchronized void enterBathroomUT() throws InterruptedException {
    // Called when a UT fan wants to enter bathroom	
        int ticket = getTickerNumber();
// System.out.println("WUT Ticket: " + ticket + " is waiting to enter bathroom UT");     //for custom test 
        while((status.get() == 2 || (status.get() == 1 && people.get() == capacity)) || (nextUp != ticket)){
            wait();
        }
// System.out.println("EUT Ticket: " + ticket + " is entering bathroom UT");     //for custom test 
        status.set(1);
        nextUp++;
        people.getAndIncrement();
        notifyAll();
    }
	
	public synchronized void enterBathroomOU() throws InterruptedException {
    // Called when a OU fan wants to enter bathroom
        int ticket = getTickerNumber();
// System.out.println("WOU Ticket: " + ticket + " is waiting to enter bathroom OU");     //for custom test 
        while((status.get() == 1 || (status.get() == 2 && people.get() == capacity)) || (nextUp != ticket)){
            wait();
        }
// System.out.println("EOU Ticket: " + ticket + " is entering bathroom OU");     //for custom test 
        status.set(2);
        nextUp++;
        people.getAndIncrement();
        notifyAll();
	}
	
	public synchronized void leaveBathroomUT() {
    // Called when a UT fan wants to leave bathroom
// System.out.println("Someone has left bathroom UT");     //for custom test 
        people.getAndDecrement();
        if(people.get() == 0)
            status.set(0);
        notifyAll();
	}

	public synchronized void leaveBathroomOU() {
    // Called when a OU fan wants to leave bathroom
// System.out.println("Someone has left bathroom OU");     //for custom test 
        people.getAndDecrement();
        if(people.get() == 0)
            status.set(0);
        notifyAll();
	}
}
	