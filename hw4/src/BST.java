import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     * <p>
     * This constructor should initialize an empty BST.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     * <p>
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     * <p>
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The collection cannot be null!");
        }
        for (T x : data) {
            if (x == null) {
                throw new IllegalArgumentException("The data in the collection cannot be null!");
            } else {
                add(x);
            }
        }
    }

    /**
     * Adds the data to the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * The data becomes a leaf in the tree.
     * <p>
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        }
        root = add(data, root);
    }

    /**
     * Add recursive helper method.
     *
     * @param data data to add to the bst
     * @param temp current node in traversal
     * @return root node to final bst
     */
    private BSTNode<T> add(T data, BSTNode<T> temp) {
        if (temp == null) {
            size++;
            return new BSTNode<>(data);
        } else if (data.compareTo(temp.getData()) < 0) {
            temp.setLeft(add(data, temp.getLeft()));
        } else if (data.compareTo(temp.getData()) > 0) {
            temp.setRight(add(data, temp.getRight()));
        }
        return temp;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     * <p>
     * This must be done recursively.
     * <p>
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        }
        BSTNode<T> removed = new BSTNode<>(null);
        root = remove(data, root, removed);
        if (removed.getData() == null) {
            throw new NoSuchElementException("Error, data is not found!");
        }
        size--;
        return removed.getData();
    }

    /**
     * private recursive helper method for remove
     * @param data to be removed from the BST
     * @param temp current node being traversed
     * @param removed node that is removed from the tree
     * @return parent node of the node to be removed
     */
    private BSTNode<T> remove(T data, BSTNode<T> temp, BSTNode<T> removed) {
        if (temp == null) {
            return null;
        }
        if (data.equals(temp.getData())) {
            if (removed.getData() == null) {
                removed.setData(temp.getData());
            }
            if (temp.getLeft() == null) {
                return temp.getRight();
            } else if (temp.getRight() == null) {
                return temp.getLeft();
            }
            temp.setData(successor(temp.getRight()));
            temp.setRight(remove(temp.getData(), temp.getRight(),
                    removed));
        }
        if (data.compareTo(temp.getData()) < 0) {
            temp.setLeft(remove(data, temp.getLeft(), removed));
        } else if (data.compareTo(temp.getData()) > 0) {
            temp.setRight(remove(data, temp.getRight(), removed));
        }
        return temp;
    }

    /**
     * Remove recursive helper method for pointer reinforcement.
     *
     * @param temp the node to recurse from
     * @return data contained in the successor node
     */
    private T successor(BSTNode<T> temp) {
        T data = temp.getData();
        while (temp.getLeft() != null) {
            data = temp.getLeft().getData();
            temp = temp.getLeft();
        }
        return data;
    }

    private T successor(BSTNode<T> temp) {
        T data = temp.getData();
        while (temp.getLeft() != null) {
            data = temp.getLeft().getData();
            temp = temp.getLeft();
        }
        return data;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     * <p>
     * This must be done recursively.
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot run get on null data.");
        }
        return get(root, data);
    }
    /**
     * recursive helper method for get method
     * @param temp current node being checked
     * @param data data being searched for
     * @return data of the node that contains the data passed in
     */
    private T get(BSTNode<T> temp, T data) {
        if (temp == null) {
            throw new NoSuchElementException("Data is not in the tree!");
        }
        if (data.equals(temp.getData())) {
            return temp.getData();
        } else if (data.compareTo(temp.getData()) < 0) {
            return get(temp.getLeft(), data);
        } else {
            return get(temp.getRight(), data);
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        try {
            get(data);
        } catch (NoSuchElementException x) {
            return false;
        }
        return true;
    }

    /**
     * Generate a pre-order traversal of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> aList = new ArrayList<>();
        preorder(root, aList);
        return aList;
    }

    /**
     * recursive helper method for preorder method
     * @param temp node currently being added
     * @param aList stores the data from BST
     */
    private void preorder(BSTNode<T> temp, List<T> aList) {
        if (temp == null) {
            return;
        }
        aList.add(temp.getData());
        preorder(temp.getLeft(), aList);
        preorder(temp.getRight(), aList);
    }
    /**
     * Generate an in-order traversal of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> aList = new ArrayList<>();
        inorder(aList, root);
        return aList;
    }
    /**
     * Recursive helper method for inorder
     * @param aList stores the data from the BST
     * @param temp current node being added
     */
    private void inorder(List<T> aList, BSTNode<T> temp) {
        if (temp != null) {
            inorder(aList, temp.getLeft());
            aList.add(temp.getData());
            inorder(aList, temp.getRight());
        }
    }
    /**
     * Generate a post-order traversal of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> aList = new ArrayList<>();
        return postorder(root, aList);
    }
    /**
     * Recursive helper method for inorder
     * @param aList stores the data from the BST
     * @param temp current node being added
     * @return the list that contains the nodes of the bst in postorder
     */
    private List<T> postorder(BSTNode<T> temp, List<T> aList) {
        if (temp == null) {
            return aList;
        } else {
            postorder(temp.getLeft(), aList);
            postorder(temp.getRight(), aList);
            aList.add(temp.getData());
        }
        return aList;
    }

    /**
     * Generate a level-order traversal of the tree.
     * <p>
     * This does not need to be done recursively.
     * <p>
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     * <p>
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> queue = new LinkedList<>();
        List<T> aList = new ArrayList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            BSTNode<T> temp = queue.poll();
            aList.add(temp.getData());
            if (temp.getLeft() != null) {
                queue.add(temp.getLeft());
            }
            if (temp.getRight() != null) {
                queue.add(temp.getRight());
            }
        }
        return aList;
    }

    /**
     * Returns the height of the root of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     * <p>
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0 || root == null) {
            return -1;
        } else {
            return height(root);
        }
    }

    /**
     * recursive helper method for height
     * @param temp node to find height from, the root
     * @return height of the tree
     */
    private int height(BSTNode<T> temp) {
        if (temp == null) {
            return -1;
        } else {
            return 1 + Math.max(height(temp.getLeft()),
                    height(temp.getRight()));
        }
    }

    /**
     * Clears the tree.
     * <p>
     * Clears all data and resets the size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     * <p>
     * This must be done recursively.
     * <p>
     * A good way to start is by finding the deepest common ancestor (DCA) of both data
     * and add it to the list. You will most likely have to split off and
     * traverse the tree for each piece of data adding to the list in such a
     * way that it will return the path in the correct order without requiring any
     * list manipulation later. One way to accomplish this (after adding the DCA
     * to the list) is to then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list.
     * <p>
     * Please note that there is no relationship between the data parameters
     * in that they may not belong to the same branch.
     * <p>
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use considering the Big-O efficiency of the list
     * operations.
     * <p>
     * This method only needs to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     * <p>
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     * <p>
     * Ex:
     * Given the following BST composed of Integers
     * 50
     * /        \
     * 25         75
     * /    \
     * 12    37
     * /  \    \
     * 11   15   40
     * /
     * 10
     * findPathBetween(10, 40) should return the list [10, 11, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     * <p>
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("One of the nodes has null data!");
        }
        List<T> pathBetween = new LinkedList<>();
        if (data1.equals(data2)) {
            pathBetween.add(data1);
        } else {
            BSTNode<T> dca = ancestor(data1, data2, root);
            pathBetween = legOne(data1, dca, pathBetween);
            pathBetween = legTwo(data2, dca, pathBetween);
        }
        return pathBetween;
    }
    /**
     * recursive helper method for findPathBetween that finds the first leg of the path
     * @param data the data to find the path from
     * @param temp the current node to recurse from
     * @param pathBetween the list containing the nodes that are in the path between the data passed in
     * @return the first leg of the path between the two nodes
     */
    private List<T> legOne(T data, BSTNode<T> temp, List<T> pathBetween) {
        if (temp != null) {
            if (data.compareTo(temp.getData()) == 0) {
                ((LinkedList<T>) pathBetween).addFirst(temp.getData());
                ((LinkedList<T>) pathBetween).removeLast();
                return pathBetween;
            } else if (data.compareTo(temp.getData()) > 0) {
                ((LinkedList<T>) pathBetween).addFirst(temp.getData());
                return legOne(data, temp.getRight(), pathBetween);
            } else {
                ((LinkedList<T>) pathBetween).addFirst(temp.getData());
                return legOne(data, temp.getLeft(), pathBetween);
            }
        }
        throw new NoSuchElementException("One of the nodes is not in the tree!");
    }

    /**
     * recursive helper method for findPathBetween that finds the second leg of the path
     * @param data the data to find the path to
     * @param temp the current node to recurse from
     * @param pathBetween the list containing the nodes that are in the path between the data passed in
     * @return the second leg of the path between the two nodes
     */
    private List<T> legTwo(T data, BSTNode<T> temp, List<T> pathBetween) {
        if (temp != null) {
            if (data.compareTo(temp.getData()) == 0) {
                ((LinkedList<T>) pathBetween).addLast(temp.getData());
                return pathBetween;
            } else if (data.compareTo(temp.getData()) > 0) {
                ((LinkedList<T>) pathBetween).addLast(temp.getData());
                return legTwo(data, temp.getRight(), pathBetween);
            } else {
                ((LinkedList<T>) pathBetween).addLast(temp.getData());
                return legTwo(data, temp.getLeft(), pathBetween);
            }
        }
        throw new NoSuchElementException("One of the nodes is not in the tree!");
    }

    /**
     *
     * @param data1 the data to find the path from
     * @param data2 the data to find the path to
     * @param temp the current node to recurse from
     * @return the common ancestor node of the nodes containing the data
     */
    private BSTNode<T> ancestor(T data1, T data2, BSTNode<T> temp) {
        if (data1.compareTo(data2) < 0) {
            if (temp.getData().compareTo(data1) >= 0 && temp.getData().compareTo(data2) <= 0) {
                return temp;
            } else if (temp.getData().compareTo(data1) < 0) {
                return ancestor(data1, data2, temp.getRight());
            } else {
                return ancestor(data1, data2, temp.getLeft());
            }
        } else {
            if (temp.getData().compareTo(data2) >= 0 && temp.getData().compareTo(data1) <= 0) {
                return temp;
            } else if (temp.getData().compareTo(data2) < 0) {
                return ancestor(data1, data2, temp.getRight());
            } else {
                return ancestor(data1, data2, temp.getLeft());
            }
        }
    }
    /**
     * Returns the root of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}

