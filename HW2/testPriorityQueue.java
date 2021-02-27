import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class testPriorityQueue implements Runnable {
    Random rand = new Random();
    final PriorityQueue pq;
    final static AtomicInteger ct = new AtomicInteger();
    final static int SIZE = 100;

    public testPriorityQueue(PriorityQueue pq) {
		this.pq = pq;
	}

    public void run() {
        try {
            String name = String.valueOf(ct.getAndIncrement());
            pq.add(name, rand.nextInt(10));
            Thread.sleep(1000);
            while(pq.search(name) != 0){
                Thread.sleep(20);
            }
            System.out.println(String.valueOf(pq.getFirst()) + " has returned");
		} 
        // try {
        //     int i = ct.getAndIncrement();
        //     String name = String.valueOf(i);
        //     if(i == 10){
        //         System.out.println("The return of search is: " + pq.search("1000"));
        //     }
        //     if(i < SIZE/3){
        //         System.out.println(String.valueOf(pq.getFirst()) + " has returned");
        //         pq.add(name, rand.nextInt(10));
        //     }
        //     else{
        //         pq.add(name, rand.nextInt(10));
        //         Thread.sleep(1000);
        //         System.out.println(String.valueOf(pq.getFirst()) + " has returned");
        //     }
		// } 
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

    public static void main(String[] args) {
        Random rand = new Random();
		PriorityQueue pq = new PriorityQueue(21); //pick q size
		Thread[] t = new Thread[SIZE];
		
		for (int i = 0; i < SIZE; ++i) {
			t[i] = new Thread(new testPriorityQueue(pq));
		}
		for (int i = 0; i < SIZE; ++i) {
			t[i].start();
            try {
				Thread.sleep(rand.nextInt(50));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        for(int i = 0; i < SIZE; ++i){
            try {
				t[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
}
