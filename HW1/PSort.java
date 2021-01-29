//UT-EID=


import java.util.*;
import java.util.concurrent.*;

public class PSort{
    public static void parallelSort(int[] A, int begin, int end){
    // TODO: Implement your parallel sort function 
    
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
        QuickSort(){}
        protected void compute(){

        }
    }
}
