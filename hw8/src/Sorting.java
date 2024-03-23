import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.LinkedList;
import java.util.PriorityQueue;
/**
 * Your implementation of various sorting algorithms.
 *
 * @author Sreehitha Kala
 * @version 1.0
 * @userid skalagara6
 * @GTID 903782097
 *
 * Collaborators: N/A
 *
 * Resources: N/A
 */
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("The array should not be null!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("The comparator should not be null.");
        }
        for (int n = 1; n < arr.length; n++) {
            int i = n;
            while (i > 0 && comparator.compare(arr[i], arr[i - 1]) < 0) {
                T temp = arr[i];
                arr[i] = arr[i - 1];
                arr[i - 1] = temp;
                i--;
            }
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("The array should not be null!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("The comparator should not be null.");
        }
        boolean num = true;
        int x = 0;
        int y = arr.length - 1;
        while (x < y && num) {
            num = false;
            int tempEnd = y - 1;
            for (int i = x; i < y; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    num = true;
                    tempEnd = i;
                }
            }
            y = tempEnd;
            if (num) {
                num = false;
                int k = x + 1;
                for (int j = y; j > x; j--) {
                    if (comparator.compare(arr[j], arr[j - 1]) < 0) {
                        T temp2 = arr[j];
                        arr[j] = arr[j - 1];
                        arr[j - 1] = temp2;
                        num = true;
                        k = j;
                    }
                }
                x = k;
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("The array should not be null!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("The comparator should not be null.");
        }
        if (arr.length != 0 && arr.length != 1) {
            int l = arr.length;
            int m = l / 2;
            T[] left = (T[]) new Object[m];
            T[] right = (T[]) new Object[l - m];
            for (int i = 0, j = 0; i < l; i++) {
                if (i < m) {
                    left[i] = arr[i];
                } else {
                    right[j] = arr[i];
                    j++;
                }
            }
            mergeSort(left, comparator);
            mergeSort(right, comparator);
            int i = 0;
            int j = 0;
            while (i < left.length && j < right.length) {
                if (comparator.compare(left[i], right[j]) <= 0) {
                    arr[i + j] = left[i++];
                } else {
                    arr[i + j] = right[j++];
                }
            }
            while (j < right.length) {
                arr[i + j] = right[j++];
            }
            while (i < left.length) {
                arr[i + j] = left[i++];
            }
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator, Random rand) {
        if (arr == null) {
            throw new IllegalArgumentException("The array is null.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("the comparator is null");
        }
        if (rand == null) {
            throw new IllegalArgumentException("The rand is null");
        }

        int end = arr.length - 1;
        int start = 0;

        quickSortHelper(arr, comparator, rand, start, end);
    }

    /**
     * Helper method for quickSort
     * @param arr the array being sorted
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @param start the start of the array
     * @param end the end of the array
     * @param <T> the generic type
     */
    private static <T> void quickSortHelper(T[] arr, Comparator<T> comparator, Random rand, int start, int end) {
        if (end - start < 1) {
            return;
        }
        int index = rand.nextInt(end - start + 1) + start;
        T data = arr[index];
        arr[index] = arr[start];
        arr[start] = data;
        int i = start + 1;
        int j = end;
        while (j - i >= 0) {
            while (j - i >= 0 && comparator.compare(arr[i], data) <= 0) {
                i++;
            }
            while (j - i >= 0 && comparator.compare(arr[j], data) >= 0) {
                j--;
            }
            if (j - i >= 0) {
                T temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }

        }
        T temp = arr[start];
        arr[start] = arr[j];
        arr[j] = temp;
        quickSortHelper(arr, comparator, rand, start, j - 1);
        quickSortHelper(arr, comparator, rand, j + 1, end);
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("The array should not be null!");
        }
        LinkedList<Integer>[] buckets = (LinkedList<Integer>[]) new LinkedList[19];
        int div = 1;
        boolean condition = true;
        while (condition) {
            condition = false;
            for (int num : arr) {
                int bucket = num / div;
                if (bucket / 10 != 0) {
                    condition = true;
                }
                if (buckets[bucket % 10 + 9] == null) {
                    buckets[bucket % 10 + 9] = new LinkedList<Integer>();
                }
                buckets[bucket % 10 + 9].add(num);
            }
            int x = 0;
            for (LinkedList<Integer> bucket : buckets) {
                if (bucket != null) {
                    for (int num : bucket) {
                        arr[x++] = num;
                    }
                    bucket.clear();
                }
            }
            div *= 10;
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data should not be null!");
        }
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        queue.addAll(data);
        int[] arr = new int[data.size()];
        for (int i = 0; i < data.size(); i++) {
            arr[i] = queue.remove();
        }
        return arr;
    }
}
