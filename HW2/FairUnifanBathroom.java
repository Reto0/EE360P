// trm2796
// rt25884

public class FairUnifanBathroom {   
	private int status; //nobody status 0, ut status 1, ou status 2
    private int capacity = 4;
    private int people = 0;
    private int ticketNumber = 0;
    private int nextUp = 1;
    
    private synchronized int getTickerNumber(){
        ticketNumber++;
        return ticketNumber;
    }
    public synchronized void enterBathroomUT() throws InterruptedException {
    // Called when a UT fan wants to enter bathroom	
        int ticket = getTickerNumber();
        while((status == 2 || (status == 1 && people == capacity)) && (nextUp == ticket)){
            wait();
        }
        status = 1;
        nextUp++;
        people++;
        notifyAll();
    }
	
	public synchronized void enterBathroomOU() throws InterruptedException {
    // Called when a OU fan wants to enter bathroom
        int ticket = getTickerNumber();
        while((status == 1 || (status == 2 && people == capacity)) && (nextUp == ticket)){
            wait();
        }
        status = 2;
        nextUp++;
        people++;
        notifyAll();
	}
	
	public synchronized void leaveBathroomUT() {
    // Called when a UT fan wants to leave bathroom
        people--;
        if(people == 0)
            status = 0;
        notifyAll();
	}

	public synchronized void leaveBathroomOU() {
    // Called when a OU fan wants to leave bathroom
        people--;
        if(people == 0)
            status = 0;
        notifyAll();
	}
}
	