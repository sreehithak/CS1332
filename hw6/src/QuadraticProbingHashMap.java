import java.util.List;
import java.util.NoSuchElementException;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

/**
 * Your implementation of a QuadraticProbingHashMap.
 *
 * @author Sreehitha Kalagara
 * @version 1.0
 * @userid skalagara6
 * @GTID 903782097
 *
 * Collaborators: N/A
 *
 * Resources: N/A
 */
public class QuadraticProbingHashMap<K, V> {

    /**
     * The initial capacity of the QuadraticProbingHashMap when created with the
     * default constructor.
     * <p>
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /**
     * The max load factor of the QuadraticProbingHashMap
     * <p>
     * DO NOT MODIFY THIS VARIABLE!
     */
    private static final double MAX_LOAD_FACTOR = 0.67;

    // Do not add new instance variables or modify existing ones.
    private QuadraticProbingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new QuadraticProbingHashMap.
     * <p>
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     * <p>
     * Use constructor chaining.
     */
    public QuadraticProbingHashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Constructs a new QuadraticProbingHashMap.
     * <p>
     * The backing array should have an initial capacity of initialCapacity.
     * <p>
     * You may assume initialCapacity will always be positive.
     *
     * @param initialCapacity the initial capacity of the backing array
     */
    public QuadraticProbingHashMap(int initialCapacity) {
        table = (QuadraticProbingMapEntry<K, V>[]) (new QuadraticProbingMapEntry[initialCapacity]);
        size = 0;
    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     * <p>
     * In the case of a collision, use quadratic probing as your resolution
     * strategy.
     * <p>
     * Before actually adding any data to the HashMap, you should check to
     * see if the array would violate the max load factor if the data was
     * added. For example, let's say the array is of length 5 and the current
     * size is 3 (LF = 0.6). For this example, assume that no elements are
     * removed in between steps. If another entry is attempted to be added,
     * before doing anything else, you should check whether (3 + 1) / 5 = 0.8
     * is larger than the max LF. It is, so you would trigger a resize before
     * you even attempt to add the data or figure out if it's a duplicate. Be
     * careful to consider the differences between integer and double
     * division when calculating load factor.
     * <p>
     * You must also resize when there are not any valid spots to add a
     * (key, value) pair in the HashMap after checking table.length spots.
     * There is more information regarding this case in the assignment PDF.
     * <p>
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     * <p>
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key   the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("The key is null");
        } else if (value == null) {
            throw new IllegalArgumentException("The value is null");
        }

        double loadFactor = (size + 1.0) / table.length;
        if (loadFactor > MAX_LOAD_FACTOR) {
            int length = 2 * table.length + 1;
            resizeBackingTable(length);
        }

        int index = Math.abs(key.hashCode() % table.length);
        int delIndex = -1;
        int delTime = 0;
        int k = 1;
        for (int i = 0; i < table.length; i++) {
            if (table[index] == null) {
                if (delTime != 0) {
                    break;
                }
                table[index] = new QuadraticProbingMapEntry<>(key, value);
                size++;
                return null;
            } else if (table[index].getKey().equals(key) && !(table[index].isRemoved())) {
                V temp = table[index].getValue();
                table[index].setValue(value);
                return temp;
            } else if (table[index].isRemoved() && delTime == 0) {
                delIndex = index;
                delTime++;
                if (table[index].getKey().equals(key)) {
                    break;
                }
            } else if (table[index].getKey().equals(key) && table[index].isRemoved()) {
                break;
            } else {
                index = (index + k * k) % table.length;
                k++;
            }
        }

        if (delIndex != -1) {
            table[delIndex] = new QuadraticProbingMapEntry<>(key, value);
        } else {
            int length = 2 * table.length + 1;
            resizeBackingTable(length);
            put(key, value);
        }
        size++;
        return null;
    }

    /**
     * Removes the entry with a matching key from map by marking the entry as
     * removed.
     *
     * @param key the key to remove
     * @return the value previously associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key is null");
        }

        int index = Math.abs(key.hashCode() % table.length);
        int i = 0;

        while (i < table.length) {
            if (table[index] != null) {
                if (table[index].getKey().equals(key) && !table[index].isRemoved()) {
                    table[index].setRemoved(true);
                    size--;
                    return table[index].getValue();
                }
            } else {
                break;
            }
            index = Math.abs((index + i * i) % table.length);
            i++;
        }

        throw new NoSuchElementException("The key isn't in the hash map!");
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for in the map
     * @return the value associated with the given key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key is not in the map
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key is null");
        }

        int index = Math.abs(key.hashCode() % table.length);
        int i = 0;

        while (i < table.length) {
            if (table[index] != null) {
                if (table[index].getKey().equals(key) && !table[index].isRemoved()) {
                    return table[index].getValue();
                }
            } else {
                break;
            }
            // Calculate the next index using quadratic probing
            index = Math.abs((index + i * i) % table.length);
            i++;
        }

        throw new NoSuchElementException("The key isn't in the hash map!");
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for in the map
     * @return true if the key is contained within the map, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key is null");
        }

        int index = Math.abs(key.hashCode() % table.length);
        int i = 0;

        while (i < table.length) {
            if (table[index] != null) {
                if (table[index].getKey().equals(key) && !(table[index].isRemoved())) {
                    return true;
                }
            } else {
                break;
            }
            // Calculate the next index using quadratic probing
            index = Math.abs((index + i * i) % table.length);
            i++;
        }

        return false;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     *
     * Use java.util.HashSet.
     *
     * @return the set of keys in this map
     */
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        int keySize = 0;
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !(table[i].isRemoved())) {
                keySet.add(table[i].getKey());
                keySize++;
            } else if (keySize == size) {
                break;
            } else {
                int index = Math.abs(i * i) % table.length;
            }
        }
        return keySet;
    }

    /**
     * Returns a List view of the values contained in this map.
     *
     * Use java.util.ArrayList or java.util.LinkedList.
     *
     * You should iterate over the table in order of increasing index and add
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        List<V> valueList = new ArrayList<>();
        int valueSize = 0;
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !(table[i].isRemoved())) {
                valueList.add(table[i].getValue());
                valueSize++;
            } else if (valueSize == size) {
                break;
            }
        }
        return valueList;
    }

    /**
     * Resize the backing table to length.
     *
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     *
     * Note: This method does not have to handle the case where the new length
     * results in collisions that cannot be resolved without resizing again. It
     * also does not have to handle the case where size = 0, and length = 0 is
     * passed into the function.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     *
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't explicitly check for
     * duplicates.
     *
     * Hint: You cannot just simply copy the entries over to the new array.
     *
     * @param length new length of the backing table
     * @throws java.lang.IllegalArgumentException if length is less than the
     * number of items in the hash map
     */
    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException("The length is less than the "
                    + "number of items in the hash map");
        }

        QuadraticProbingMapEntry<K, V>[] tempTable = new QuadraticProbingMapEntry[length];
        int tableSize = 0;

        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !(table[i].isRemoved())) {
                K key = table[i].getKey();
                int index = Math.abs(key.hashCode() % tempTable.length);
                int j = 0;

                while (j < tempTable.length) {
                    int newIndex = (index + j * j) % tempTable.length;
                    if (tempTable[newIndex] == null) {
                        tempTable[newIndex] = table[i];
                        tableSize++;
                        break;
                    }
                    j++;
                }
            } else if (tableSize == size) {
                break;
            }
        }
        table = tempTable;
    }

    /**
     * Clears the map.
     *
     * Resets the table to a new array of the INITIAL_CAPACITY and resets the
     * size.
     *
     * Must be O(1).
     */
    public void clear() {
        size = 0;
        table = new QuadraticProbingMapEntry[INITIAL_CAPACITY];
    }

    /**
     * Returns the table of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the table of the map
     */
    public QuadraticProbingMapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the map
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
