import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
 *
 * @author Sreehitha Kalagara
 * @version 1.0
 * @userid skalagara6
 * @GTID 903782097
 *
 * Collaborators: N/A
 *
 * Resources: None
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     * To initialize the backing array, create a Comparable array and then cast
     * it to a T array.
     */
    public MinHeap() {
        backingArray = (T[]) (new Comparable[INITIAL_CAPACITY]);
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * size of the passed in ArrayList (not INITIAL_CAPACITY). Index 0 should
     * remain empty, indices 1 to n should contain the data in proper order, and
     * the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The list should not be null!");
        }
        size = data.size();
        int capacity = 1 + 2 * size;
        backingArray = (T[]) new Comparable[capacity];
        for (int i = 0; i < size; i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("The data to be added should not be null!");
            } else {
                backingArray[i + 1] = data.get(i);
            }
        }
        for (int i = size / 2; i >= 1; i--) {
            heap(i);
        }
    }

    /**
     * Recursive helper method to remove from a heap and create a heap
     * @param num the index that heap() is being called on
     */
    //heapDown
    private void heap(int num) {
        int l = num * 2;
        int r = l + 1;
        int temp = num;
        if (l <= size && backingArray[l].compareTo(backingArray[temp]) < 0) {
            temp = l;
        }
        if (r <= size && backingArray[r].compareTo(backingArray[temp]) < 0) {
            temp = r;
        }
        if (temp <= size && backingArray[temp].compareTo(backingArray[num]) < 0) {
            T tmp = backingArray[num];
            backingArray[num] = backingArray[temp];
            backingArray[temp] = tmp;
            heap(temp);
            heap(num);
        }
    }


    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding. You can
     * assume that no duplicate data will be passed in.
     * 
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data should not be null!");
        } else if (size == backingArray.length - 1) {
            T[] arr = (T[]) new Comparable[2 * backingArray.length];
            for (int i = 1; i < size + 1; i++) {
                arr[i] = backingArray[i];
            }
            backingArray = arr;
            backingArray[size + 1] = data;
            up(++size);
        } else {
            backingArray[size + 1] = data;
            up(++size);
        }
    }

    /**
     * Helper method to add to a heap.
     * @param num index that up() is being called on
     * */
    //heap up
    private void up(int num) {
        while (num > 1) {
            int x = num / 2;
            if (backingArray[num].compareTo(backingArray[x]) < 0) {
                T temp = backingArray[x];
                backingArray[x] = backingArray[num];
                backingArray[num] = temp;
            }
            num = x;
        }
    }
    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after removing.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("The heap is empty!");
        } else {
            T removed = backingArray[1];
            backingArray[1] = backingArray[size];
            backingArray[size--] = null;
            heap(1);
            return removed;
        }
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            throw new NoSuchElementException("The heap is empty!");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) (new Comparable[INITIAL_CAPACITY]);
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
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
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
