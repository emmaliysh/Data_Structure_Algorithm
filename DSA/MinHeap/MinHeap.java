import java.util.NoSuchElementException;


/**
 * Your implementation of a MinHeap.
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

     /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * This is the constructor that constructs a new MinHeap.
     *
     * Recall that Java does not allow for regular generic array creation,
     * so instead we cast a Comparable[] to a T[] to get the generic typing.
     */
    public MinHeap() {
        //DO NOT MODIFY THIS METHOD!
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     *
     * Method should run in amortized O(log n) time.
     *
     * @param data The data to add.
     * @throws java.lang.IllegalArgumentException If the data is null.
     */
    public void add(T data) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (data == null){
            throw new IllegalArgumentException("data is null");

        } else {
            if (size == 0){
                backingArray[1] = data;
                size ++;
                return;
            }

            if (size + 1 >= backingArray.length){
                T[] newBackingArray = (T[]) new Comparable[backingArray.length * 2];
                for (int i = 1; i < size + 1; i ++){
                    newBackingArray[i] = backingArray[i];
                }
                
                backingArray = newBackingArray;
            }

            
            backingArray[size + 1] = data;

            int currentIndex = size + 1;
            int referenceIndex = currentIndex / 2;

            while (data.compareTo(backingArray[referenceIndex]) < 0){
                if (currentIndex == 1){
                    break;
                }

                T temp = backingArray[referenceIndex];
                backingArray[referenceIndex] = data;
                backingArray[currentIndex] = temp;

                //prep for next comparison
                currentIndex = referenceIndex;
                referenceIndex /= 2;

                if (currentIndex == 1){
                    break;
                }
               
            }
            
            size ++;

        }
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     *
     * Method should run in O(log n) time.
     *
     * @return The data that was removed.
     * @throws java.util.NoSuchElementException If the heap is empty.
     */
    public T remove() {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (size == 0){
            throw new NoSuchElementException("the heap is empty");
        } else {
            T min = backingArray[1];
        
            backingArray[1] = backingArray[size];
            backingArray[size] = null;

            size --;
            
            int currentIndex = 1;
            int referenceIndex = 10;

            T temp;

            while ((currentIndex * 2) <= size) {
                // choose left or right node to swap
                if (backingArray[currentIndex * 2 + 1] != null){
                    if (backingArray[currentIndex * 2 + 1].compareTo(backingArray[currentIndex * 2]) > 0){
                        referenceIndex = currentIndex * 2;
                    } else {
                        referenceIndex = currentIndex * 2 + 1;
                    }
                } else {
                    referenceIndex = currentIndex * 2;
                }

                if (backingArray[currentIndex].compareTo(backingArray[referenceIndex]) > 0 ){
                    temp = backingArray[referenceIndex];
                    backingArray[referenceIndex] = backingArray[currentIndex];
                    backingArray[currentIndex] = temp;

                }
                currentIndex = referenceIndex;

            }

            return min;
        }
    }

    

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    public void show(){
        for (int i=0; i < backingArray.length; i++){
            System.out.println(backingArray[i]);
        }
    }

    public static void main(String[] args) {
        MinHeap<Integer> a = new MinHeap<>();
        a.add(7);
        a.add(1);
        a.add(4);
        a.add(8);
        a.add(5);
        a.add(2);
        a.add(6);
        a.add(3);
        a.add(9);
        
        a.show();

        
        
        

    }




}