/*
 * rt25884
 * trm2796
 */
import java.util.concurrent.Semaphore; // for implementation using Semaphores

public class CyclicBarrier {
	private Semaphore barrierLock = new Semaphore(0);
    private Semaphore mutex = new Semaphore(1);
    private Semaphore limit;
    private int count = 0;
    private int parties;
	public CyclicBarrier(int parties) {
        this.limit = new Semaphore(parties);
        this.parties = parties;
	}
	
	public int await() throws InterruptedException {
        mutex.acquire();
        limit.acquire();
        
        count++;
        int index = parties-count;
        if(count != parties){
            mutex.release();
            barrierLock.acquire();
        }
        else{
            barrierLock.release(parties-1);
            count = 0;
            limit.release(parties);
            mutex.release();
        }
	    return index;
	}
}