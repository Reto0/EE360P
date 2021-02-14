/*
 * rt25884
 * trm2796
 */

public class MonitorCyclicBarrier {
	private int parties;
    private int count = 0;
	public MonitorCyclicBarrier(int parties) {
        this.parties = parties;
	}
	
	public synchronized int await() throws InterruptedException {
        count++;
        int index = parties - count;
        if(count != parties)
            wait();
        else{            
            count = 0;
            notifyAll();
        }
        return index;
	}
}