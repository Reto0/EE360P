import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

// trm2796
// rt25884

public class PriorityQueue {
    Node head = null;
    ReentrantLock lock = new ReentrantLock();
    ReentrantLock lock2 = new ReentrantLock();
    AtomicInteger spaceAvail;
    Condition notEmpty = lock.newCondition();
    Condition notFull = lock.newCondition();
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
        Node current = head;
        int position = 1;
        try {
            //return -1 if already present in list
            if(search(name) != -1){
                return -1;
            }
            while(spaceAvail.get() == 0)
				try {
                    lock.lock();
					notFull.await();
                    lock.unlock();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            //insert
            Node newNode = new Node(name, priority);
        
            lock.lock();    //lock needed for if list is empty and two threads trying to create a node at head
            current = head;
            if(head == null){
                head = newNode;
                lock.unlock();
                position = 0;
                spaceAvail.decrementAndGet();
            }   
            //inserting at the beginning head when head is not null
            else if(head.priority < priority){
                newNode.next = head;
                head = newNode;
                position = 0;
                spaceAvail.decrementAndGet();
                lock.unlock();
            }
            //insert at not head
            else{
                lock.unlock();
                current.lock.lock();
                while(current.next != null){
                    if(current.priority >= priority && current.next.priority < priority)
                        break;
                    //do something
                    current.next.lock.lock();
                    current.lock.unlock();
                    current = current.next;
                    position++;
                }
                //current priority >= priority && current.next.priority < priority || null
                //current has location we are inserting at
                //inserting 
                newNode.next = current.next;
                current.next = newNode;
                spaceAvail.decrementAndGet();
                current.lock.unlock();
            }
            lock.lock();
            notEmpty.signalAll();
            lock.unlock();
            return position;
        }
        finally{
            //guarantee locks are unlocked
            if(lock.isHeldByCurrentThread())
                lock.unlock();
            if(current != null)
                if(current.lock.isHeldByCurrentThread())
                    current.lock.unlock();
        }
	}

	public int search(String name) {
        // Returns the position of the name in the list;
        // otherwise, returns -1 if the name is not found.
        
        //iterate till null
        //if found ret position else ret -1
        //no locks needed because we want the position when this is called
        
        Node current = head;
        // try{
            if(current == null)
                return -1;
            // current.lock.lock();
            // System.out.println("Searching for: " + name + " current head is: " + head.name);
            int position = 0;
            while(current.next != null){
                if(current.name.equals(name)){
                    return position;
                }
                // current.next.lock.lock();
                // current.lock.unlock();
                current = current.next;
                position++;
            }
            if(current.name.equals(name))
                return position;
            return -1;
        // }
        // finally{
        //     if(current != null)
        //         current.lock.unlock();
        // }
	}

	public String getFirst() {
        // Retrieves and removes the name with the highest priority in the list,
        // or blocks the thread if the list is empty.
        Node temp = new Node("",9);
        try{
            lock.lock();
            while(spaceAvail.get() == maxSize){
                
                try {
					notEmpty.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
            }
            
            head.lock.lock();
            temp = head;
            String name = head.name;
            head.lock.unlock();
            head = head.next;
            spaceAvail.incrementAndGet();
            notFull.signalAll();
            lock.unlock();
            return name;
        }
        finally{
            // if(lock2.isHeldByCurrentThread())
            //     lock2.unlock();
            if(lock.isHeldByCurrentThread())
                lock.unlock();
            if(temp.lock.isHeldByCurrentThread())
                temp.lock.unlock();
        }
	}

    

    public class Node{
        int priority;
        String name;
        ReentrantLock lock = new ReentrantLock();
        Node next = null;
        Node(String name, int priority){
            this.priority = priority;
            this.name = name;
        }
    }
}


