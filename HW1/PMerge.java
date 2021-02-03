//UT-EID= rt25884   trm2796

import java.util.*;
import java.util.concurrent.*;

import javax.lang.model.util.ElementScanner14;


public class PMerge{
  public static int[] cArray;

  public static void parallelMerge(int[] A, int[] B, int[]C, int numThreads){
      ExecutorService pool = Executors.newFixedThreadPool(numThreads);
      cArray = C;
    for(int i = 0;i < A.length; i++){
        Runnable ra = new search(A[i], B, true, i);
        pool.execute(ra);
    }
    for(int i = 0;i < B.length; i++){
        Runnable rb = new search(B[i], A, false, i);
        pool.execute(rb);
    }
    pool.shutdown();
    while(!pool.isTerminated()){}
    C = cArray;
  }
  public static class search implements Runnable{
      private int num;
      private int[] arr;
      private boolean Aarray;
      private int index;
      public search(int num, int[] arr, boolean Aarray, int index){
        this.num = num;
        this.arr = arr;
        this.Aarray = Aarray;
        this.index = index;
      }
      public void run(){
        int l = 0;
        int r = arr.length-1;
        while(l <= r){
            int mid = l + (r-l)/2;
            if(num == arr[mid]){
                if(Aarray)
                    mid++;
                cArray[index+mid] = num;
                // System.out.println("inserting " + num + "at index: " + index+mid);
                return;
            }
            else if ( num < arr[mid])
                r = mid - 1;
            else
                l = mid + 1;
        }
        cArray[index+l] = num;
        // System.out.println("inserting " + num + "at index: " + (index+l));
      }
  }
}