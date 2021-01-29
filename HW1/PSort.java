//UT-EID= rt25884   trm2796


import java.util.*;
import java.util.concurrent.*;

public class PSort{
    public static void parallelSort(int[] A, int begin, int end){
        int processors = Runtime.getRuntime().availableProcessors();
        QuickSort qs = new QuickSort(A, begin, end);
        ForkJoinPool pool = new ForkJoinPool(processors);
        pool.invoke(qs);
    }

    public static void insertionSort(int[] A, int begin, int end){
        for(int i = begin+1; i < end; i++){
            int n = A[i];
            int j;
            for(j = i-1; j >= begin && A[j] > n; j--)
                A[j+1] = A[j];
            A[j+1] = n;
        }
    }

    public static class QuickSort extends RecursiveAction{

        int[] A;
        int begin;
        int end;
        QuickSort(int[] A, int begin, int end){
            this.A = A;
            this.begin = begin;
            this.end = end;
        }

        @Override
        protected void compute(){
            if(end-begin < 16)
                insertionSort(A, begin, end);
            else{
                int pivot = partition(A, begin, end);
                QuickSort q1 = new QuickSort(A, begin, pivot);
                q1.fork();
                QuickSort q2 = new QuickSort(A, pivot, end);
                q2.compute();
                q1.join();
            }
        }

        protected static int partition(int[] A, int begin, int end){
            int pivot = A[end-1];
            int i = begin-1;
            for(int j = begin; j <= end; j++){
                if(A[j] < pivot){
                    i++;
                    int temp = A[i];
                    A[i] = A[j];
                    A[j] = temp;
                }
            }
            int temp = A[i+1];
            A[i+1] = A[end-1];
            A[end-1] = temp;
            return i+1;
        }
    }
}
