import java.util.Arrays;

public class MergeTest {
  public static void main (String[] args) {
    int[] A1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,20,30,40,50,60,70,80,90,100,1000,2000,3000,4000,5000,100000};
    
    int[] A2 = {-5, 1, 3, 5, 7, 9,4000};
    verifyMerge(A2, A1);
    
    // int[] A3 = {13, 59, 24, 18, 33, 20, 11, 11, 13, 50, 10999, 97};
    // verifyMerge(A3);
  }

  static void verifyMerge(int[] A, int[] B) {
    int[] C = new int[A.length+B.length];

    System.out.println("Verify Merge for array A: ");
    printArray(A);
    System.out.println("Verify Merge for array B: ");
    printArray(B);
    System.out.println("Verify Merge for array C: ");
    PMerge.parallelMerge(A,B,C,60);
    
    printArray(C);

  }

  public static void printArray(int[] A) {
    for (int i = 0; i < A.length; i++) {
      if (i != A.length - 1) {
        System.out.print(A[i] + " ");
      } else {
        System.out.print(A[i]);
      }
    }
    System.out.println();
  }
}