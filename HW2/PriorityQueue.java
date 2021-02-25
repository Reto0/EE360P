import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.net.ssl.HandshakeCompletedListener;

// trm2796
// rt25884

public class PriorityQueue {
    Node head = null;
    ReentrantLock lock = new ReentrantLock();
    AtomicInteger spaceAvail;
    Condition PQEmpty = lock.newCondition();
    Condition PQFull = lock.newCondition();
    int maxSize;
    
	public PriorityQueue(int maxSize) {
        // Creates a Priority queue with maximum allowed size as capacity
        spaceAvail = new AtomicInteger(maxSize);
        this.maxSize = maxSize;
	}

	public int add(String name, int priority) {
        // Adds the name with its priority to this queue.
        // Returns the current position in the list where the name was inserted;
        // otherwise, returns -1 if the name is already present in the list.
        // This method blocks when the list is full.
        try{
            lock.lock();
            while(spaceAvail.get() == 0)
                PQFull.await();
            //insert
            PQEmpty.signalAll();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        finally{
            lock.unlock();
        }

        return -1;
	}

	public int search(String name) {
        // Returns the position of the name in the list;
        // otherwise, returns -1 if the name is not found.
        try{
            lock.lock();
            //iterate till null
            //if found ret position else ret -1
            return -1;
        }
        finally{
            lock.unlock();
        }
            
	}

	public String getFirst() {
        // Retrieves and removes the name with the highest priority in the list,
        // or blocks the thread if the list is empty.
        try{
            lock.lock();
            while(spaceAvail.get() == maxSize){
                PQEmpty.await();
            }
            //pop?
            PQFull.signalAll();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        finally{
            lock.unlock();
        }
	}

    public class Node{
        int priority;
        String name;
        Node(String name, int priority){
            this.priority = priority;
            this.name = name;
        }
    }
}


