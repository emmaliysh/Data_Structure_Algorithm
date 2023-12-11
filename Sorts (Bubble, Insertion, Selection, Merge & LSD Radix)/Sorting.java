import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Your implementation of various iterative sorting algorithms.
 */
public class Sorting {

    

    

    /**
     * Implement bubble sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of: O(n^2)
     * And a best case running time of: O(n)
     *
     * NOTE: You should implement bubble sort with the last swap optimization.
     *
     * You may assume that the passed in array and comparator
     * are both valid and will never be null.
     *
     * @param <T>        Data type to sort.
     * @param arr        The array that must be sorted after the method runs.
     * @param comparator The Comparator used to compare the data in arr.
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!

        int stopIndex = arr.length - 1;
        

        // optimization 1
        // stop the outer loop once no swap is founded in the inner loop (which means the sort is done!)
        boolean swapsMade = true; 
        

        while (stopIndex != 0 && swapsMade){

            swapsMade = false;
            int i = 0;

            // optimization 2
            // narrow the scanning scope by marking down the previous last swapped index & updating the stopIndex with it
            int lastSwapped = 0;

            while (i < stopIndex){

                if (comparator.compare(arr[i], arr[i+1]) > 0){
                    T temp = arr[i];
                    arr[i] = arr[i+1];
                    arr[i+1] = temp;

                    swapsMade = true; // swap identified 
                    lastSwapped = i;
    
                }
                i ++;
            }

            stopIndex = lastSwapped;

        }
    }

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of: O(n^2)
     * And a best case running time of: O(n^2)
     *
     * You may assume that the passed in array and comparator
     * are both valid and will never be null.
     *
     * @param <T>        Data type to sort.
     * @param arr        The array that must be sorted after the method runs.
     * @param comparator The Comparator used to compare the data in arr.
     */

    // search for the max & put it in the end of the unsorted array 
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!

        for (int i = arr.length-1; i >= 1; i--){

            T max = arr[0];
            int maxIndex = 0;

            for (int j = 1; j <= i; j++){
                if (comparator.compare(arr[j], max) > 0){
                    max = arr[j];
                    maxIndex = j;
                    
                } 
            }
            arr[maxIndex] = arr[i];
            arr[i] = max;
            
        }
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of: O(n^2)
     * And a best case running time of: O(n)
     *
     * You may assume that the passed in array and comparator
     * are both valid and will never be null.
     *
     * @param <T>        Data type to sort.
     * @param arr        The array that must be sorted after the method runs.
     * @param comparator The Comparator used to compare the data in arr.
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        for (int i = 1; i < arr.length; i++){

            for(int j = i; j > 0; j--){

                if (comparator.compare(arr[j], arr[j-1]) < 0){
                    T temp = arr[j];
                    arr[j] = arr[j-1];
                    arr[j-1] = temp;

                } else {
                    break;
                }
            }
        }

    }

    // *******************************************************************************************************************************************
    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of: O(n log n)
     * And a best case running time of: O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: You may need to create a helper method that merges two arrays
     * back into the original T[] array. If two data are equal when merging,
     * think about which subarray you should pull from first.
     *
     * You may assume that the passed in array and comparator are both valid
     * and will not be null.
     *
     * @param <T>        Data type to sort.
     * @param arr        The array to be sorted.
     * @param comparator The Comparator used to compare the data in arr.
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
    

        if (arr.length > 1){

            // divide the current array into 2 'equal' halves
        int length = arr.length;
        int midIndex = length / 2;

        T[] leftArr = (T[]) new Object[midIndex];
        T[] rightArr = (T[]) new Object[length - midIndex];

        for (int i = 0; i < midIndex; i++){
            leftArr[i] = arr[i];
        }

        for (int j = 0; j < (length - midIndex); j++){
            rightArr[j] = arr[midIndex + j];
        }

        mergeSort(leftArr, comparator);
        mergeSort(rightArr, comparator);

        // compare & merge

        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < leftArr.length && rightIndex < rightArr.length){
            if (comparator.compare(leftArr[leftIndex], rightArr[rightIndex]) <= 0){
                arr[leftIndex + rightIndex] = leftArr[leftIndex];
                leftIndex++;

            } else{
                arr[leftIndex + rightIndex] = rightArr[rightIndex];
                rightIndex++;

            }
        }

        while (leftIndex < leftArr.length){
            arr[leftIndex + rightIndex] = leftArr[leftIndex];
            leftIndex++;

        }

        while(rightIndex < rightArr.length){
            arr[leftIndex + rightIndex] = rightArr[rightIndex];
            rightIndex++;

        }

            
        }

        








    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of: O(kn)
     * And a best case running time of: O(kn)
     *
     * Feel free to make an initial O(n) passthrough of the array to
     * determine k, the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * You may use an ArrayList or LinkedList if you wish, but it should only
     * be used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with merge sort. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * You may assume that the passed in array is valid and will not be null.
     *
     * @param arr The array to be sorted.
     */
    public static void lsdRadixSort(int[] arr) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!

        if (arr == null) {
            throw new IllegalArgumentException("Error,arr is null");
        }
        
        long max = 0;
        long absValue = 0; 

        for (int i = 0; i < arr.length; i++){
            absValue = Math.abs((long)arr[i]);

            if (absValue > max) {
                max = absValue;
            }
        }
        
 
        // find out the length of max (which decides how many loops for later sorting)
        String maxStr = Long.toString(max);
        int maxLength = maxStr.length();

        // arrange the elements in arr into ArrayList

        List<Integer>[] buckets = new ArrayList[19];

        int valuePlace = 1;

        for (int i = 0; i < maxLength; i++){

            for (int num: arr){

                // find out the place value (be careful with negative elements)

                int index = (num/valuePlace) % 10 + 9;

                if (buckets[index] == null){
                    buckets[index] = new ArrayList<Integer>();
        
                } 
            
                buckets[index].add(num);
                
            }


            int index = 0;
            for (int j = 0; j < buckets.length; j++){
                if (buckets[j] != null){
                    for (Integer newNum: buckets[j]){
                        arr[index] = newNum;
                        index++;

                    }
                    buckets[j].clear();
                }
            }

            valuePlace *= 10;
        }



    }





    public static void main(String[] args) {

        Integer[] a = {-2, -111, 1, 3, -9, -214, -83648} ;
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
          }

        System.out.println("********");

        Comparator<Integer> comparator = new Comparator<>(){

            public int compare(Integer b, Integer c){
                return (b-c); 
            }
        };

        mergeSort(a, comparator);

        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
          }




    }
}