import java.util.NoSuchElementException;
/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @author Sreehitha Kalagara
 * @version 1.0
 * @userid skalagara6
 * @GTID 903782097
 *
 * Collaborators: N/A
 * Resources: N/A
 * 
 *
 * DoublyLinkedList class that creates a linked list object.
 * @param <T> parameter of a generic type T that represents the data in the node
 *
 */
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;
    // Do not add a constructor.
    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) throws IndexOutOfBoundsException, IllegalArgumentException {
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index cannot be negative!");
        } else if (index > size) {
            throw new IndexOutOfBoundsException("The index cannot be larger than " + size + ", the size of the list!");
        } else if (data == null) {
            throw new IllegalArgumentException("The data cannot be null!");
        } else if (isEmpty()) {
            head = new DoublyLinkedListNode<T>(data);
            tail = head;
        } else if (index == 0) {
            DoublyLinkedListNode<T> oldHead = head;
            head = new DoublyLinkedListNode<T>(data);
            head.setNext(oldHead);
            oldHead.setPrevious(head);
        } else if (index == size) {
            DoublyLinkedListNode<T> oldTail = tail;
            tail = new DoublyLinkedListNode<T>(data);
            tail.setPrevious(oldTail);
            oldTail.setNext(tail);
        } else if (index <= size / 2) {
            DoublyLinkedListNode<T> prev = head;
            DoublyLinkedListNode<T> nex;
            DoublyLinkedListNode<T> toAdd = new DoublyLinkedListNode<T>(data);
            for (int i = 0; i < index - 1; i++) {
                prev = prev.getNext();
            }
            nex = prev.getNext();
            prev.setNext(toAdd);
            nex.setPrevious(toAdd);
            toAdd.setPrevious(prev);
            toAdd.setNext(nex);
        } else {
            DoublyLinkedListNode<T> nex = tail;
            DoublyLinkedListNode<T> prev;
            DoublyLinkedListNode<T> toAdd = new DoublyLinkedListNode<T>(data);
            for (int i = size - 1; i > index; i--) {
                nex = nex.getPrevious();
            }
            prev = nex.getPrevious();
            nex.setPrevious(toAdd);
            prev.setNext(toAdd);
            toAdd.setPrevious(prev);
            toAdd.setNext(nex);
        }
        size++;
    }
    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException("The data cannot be null!");
        }
        addAtIndex(0, data);
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) throws IllegalArgumentException {
        addAtIndex(size, data);
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) throws IndexOutOfBoundsException {
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index cannot be negative!");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("The index has to be less than " + size + ", the size of the list!");
        } else if (size == 1) {
            DoublyLinkedListNode<T> toReturn = head;
            clear();
            size = 0;
            return toReturn.getData();
        } else if (index == 0) {
            DoublyLinkedListNode<T> toReturn = head;
            head = head.getNext();
            head.setPrevious(null);
            size--;
            return toReturn.getData();
        } else if (index == size - 1) {
            DoublyLinkedListNode<T> toReturn = tail;
            tail = tail.getPrevious();
            tail.setNext(null);
            size--;
            return toReturn.getData();
        } else if (index <= size / 2) {
            DoublyLinkedListNode<T> prev = head;
            DoublyLinkedListNode<T> nex;
            for (int i = 0; i < index - 1; i++) {
                prev = prev.getNext();
            }
            DoublyLinkedListNode<T> toReturn = prev.getNext();
            nex = toReturn.getNext();
            prev.setNext(nex);
            nex.setPrevious(prev);
            size--;
            return toReturn.getData();
        } else {
            DoublyLinkedListNode<T> nex = tail;
            DoublyLinkedListNode<T> prev;
            for (int i = size - 1; i > index + 1; i--) {
                nex = nex.getPrevious();
            }
            DoublyLinkedListNode<T> toReturn = nex.getPrevious();
            prev = toReturn.getPrevious();
            nex.setPrevious(prev);
            prev.setNext(nex);
            size--;
            return toReturn.getData();
        }
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() throws NoSuchElementException {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty!");
        }
        return removeAtIndex(0);
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty!");
        }
        return removeAtIndex(size - 1);
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index cannot be negative!");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("The index cannot be greater than the size of the list!");
        } else if (index <= size / 2) {
            DoublyLinkedListNode<T> toReturn = head;
            for (int i = 0; i < index; i++) {
                toReturn = toReturn.getNext();
            }
            return toReturn.getData();
        } else {
            DoublyLinkedListNode<T> toReturn = tail;
            for (int i = size; i > index + 1; i--) {
                toReturn = toReturn.getPrevious();
            }
            return toReturn.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) throws IllegalArgumentException, NoSuchElementException {
        if (data == null) {
            throw new IllegalArgumentException("The data cannot be null!");
        } else {
            DoublyLinkedListNode<T> toCheck = tail;
            for (int i = size - 1; i >= 0; i--) {
                if (toCheck.getData().equals(data)) {
                    return removeAtIndex(i);
                }
                toCheck = toCheck.getPrevious();
            }
        }
        throw new NoSuchElementException("The data was not found in the list!");
    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        T[] arr = (T[]) new Object[size];
        DoublyLinkedListNode<T> node = head;
        for (int i = 0; i < size; i++) {
            arr[i] = node.getData();
            node = node.getNext();
        }
        return arr;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
