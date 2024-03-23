import java.util.NoSuchElementException;
/**
 * Your implementation of a LinkedDeque.
 *
 * @author Sreehitha Kalagara
 * @version 1.0
 * @userid skalagara6
 * @GTID 903782097
 * @param <T> object of generic type T
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class LinkedDeque<T> {

    // Do not add new instance variables or modify existing ones.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the front of the deque.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        }
        if (head == null) {
            head = new LinkedNode<T>(data);
            tail = head;
        } else {
            LinkedNode<T> added = new LinkedNode<T>(data, null, head);
            head.setPrevious(added);
            head = added;
        }
        size++;
    }

    /**
     * Adds the element to the back of the deque.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        } else if (head == null) {
            head = new LinkedNode<T>(data, null, null);
            tail = head;
        } else {
            LinkedNode<T> added = new LinkedNode<T>(data, tail, null);
            tail.setNext(added);
            tail = added;
        }
        size++;
    }

    /**
     * Removes and returns the first element of the deque.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("There deque is empty!");
        } else if (size == 1) {
            LinkedNode<T> toRemove = head;
            head = null;
            tail = null;
            size = 0;
            return toRemove.getData();
        } else {
            LinkedNode<T> toRemove = head;
            LinkedNode<T> newHead = head.getNext();
            newHead.setPrevious(null);
            head = newHead;
            size--;
            return toRemove.getData();
        }
    }

    /**
     * Removes and returns the last element of the deque.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("There deque is empty!");
        } else if (size == 1) {
            LinkedNode<T> toRemove = head;
            head = null;
            tail = null;
            size = 0;
            return toRemove.getData();
        } else {
            LinkedNode<T> toRemove = tail;
            LinkedNode<T> newTail = tail.getPrevious();
            newTail.setNext(null);
            tail = newTail;
            size--;
            return toRemove.getData();
        }
    }

    /**
     * Returns the first data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getFirst() {
        if (size == 0) {
            throw new NoSuchElementException("The deque is empty!");
        }
        return head.getData();
    }

    /**
     * Returns the last data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getLast() {
        if (size == 0) {
            throw new NoSuchElementException("The deque is empty!");
        }
        return tail.getData();
    }

    /**
     * Returns the head node of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY THIS METHOD!
        return head;
    }

    /**
     * Returns the tail node of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY THIS METHOD!
        return tail;
    }

    /**
     * Returns the size of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the deque
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
