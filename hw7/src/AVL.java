import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
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
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        }
        for (T item : data) {
            if (item == null) {
                throw new IllegalArgumentException("Cannot add null elements to AVL!");
            }
            add(item);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data to AVL.");
        }
        root = addHelp(data, root);
    }

    /**
     * Recursive helper method for add
     * @param data the data to add
     * @param curr the current node to recurse
     * @return node with the data to add
     */
    private AVLNode<T> addHelp(T data, AVLNode<T> curr) {
        if (curr == null) {
            size++;
            return new AVLNode<>(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(addHelp(data, curr.getLeft()));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(addHelp(data, curr.getRight()));
        }
        recalc(curr);
        if ((curr.getBalanceFactor() > 1) || (curr.getBalanceFactor() < -1)) {
            curr = balance(curr);
        }
        return curr;
    }

    /**
     * Recursive helper method to recalculate height and balance factor of each node.
     * @param curr node being updated
     */
    private void recalc(AVLNode<T> curr) {
        int leftHeight = 0;
        int rightHeight = 0;
        if (curr.getLeft() == null) {
            leftHeight = -1;
        } else {
            leftHeight = curr.getLeft().getHeight();
        }
        if (curr.getRight() == null) {
            rightHeight = -1;
        } else {
            rightHeight = curr.getRight().getHeight();
        }
        curr.setHeight(Math.max(leftHeight, rightHeight) + 1);
        curr.setBalanceFactor(leftHeight - rightHeight);
    }

    /**
     * Recursive helper method to balance the tree
     * @param curr the node to balance
     * @return the root of the balanced subtree
     */
    private AVLNode<T> balance(AVLNode<T> curr) {
        AVLNode<T> balancedNode = curr;
        if (curr.getBalanceFactor() > 0) {
            if (curr.getLeft().getBalanceFactor() < 0) {
                curr.setLeft(rotateLeft(curr.getLeft()));
                balancedNode = rotateRight(curr);
            } else {
                balancedNode = rotateRight(curr);
            }
        } else if (curr.getBalanceFactor() < 0) {
            if (curr.getRight().getBalanceFactor() > 0) {
                curr.setRight(rotateRight(curr.getRight()));
                balancedNode = rotateLeft(curr);
            } else {
                balancedNode = rotateLeft(curr);
            }
        }
        return balancedNode;
    }
    /**
     * Recursive helper method to perform a right rotation on a subtree
     * @param curr root of the subtree being rotated
     * @return root of the rotated subtree
     */
    private AVLNode<T> rotateRight(AVLNode<T> curr) {
        AVLNode<T> left = curr.getLeft();
        curr.setLeft(left.getRight());
        left.setRight(curr);
        recalc(curr);
        recalc(left);
        return left;
    }
    /**
     * Recursive helper method to perform a left rotation on a subtree
     * @param curr root of the subtree being rotated
     * @return root of the rotated subtree
     */
    private AVLNode<T> rotateLeft(AVLNode<T> curr) {
        AVLNode<T> right = curr.getRight();
        curr.setRight(right.getLeft());
        right.setLeft(curr);
        recalc(curr);
        recalc(right);
        return right;
    }
    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data.");
        }
        AVLNode<T> removeRef = new AVLNode<>(null);
        root = removeHelper(data, root, removeRef);
        size--;
        return removeRef.getData();
    }

    /**
     * recursively traverses the BST to find the node containing the data
     * @param data data to remove
     * @param curr node to check for data
     * @param removed reference to dummy node w/ removed data
     * @return node containing data
     */
    private AVLNode<T> removeHelper(T data, AVLNode<T> curr, AVLNode<T> removed) {
        if (curr == null) {
            throw new NoSuchElementException(data + " was not "
                    + "found in the tree.");
        }
        if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(removeHelper(data, curr.getLeft(), removed));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(removeHelper(data, curr.getRight(), removed));
        } else {
            if (removed.getData() == null) {
                removed.setData(curr.getData());
            }
            if (curr.getLeft() == null) {
                return curr.getRight();
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            }
            curr.setData(pred(curr.getLeft()));
            curr.setLeft(removeHelper(curr.getData(), curr.getLeft(), removed));
        }
        recalc(curr);
        if (Math.abs(curr.getBalanceFactor()) > 1) {
            curr = balance(curr);
        }
        return curr;
    }

    /**
     * Recursive helper method to find the predecessor node
     * @param curr the current node being traversed
     * @return the predecessor node
     */
    private T pred(AVLNode<T> curr) {
        if (curr.getRight() == null) {
            return curr.getData();
        } else {
            return pred(curr.getRight());
        }
    }


    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        }
        return getHelp(root, data).getData();
    }

    /**
     * Recursive helper method for the get method
     * @param curr current node being traversed
     * @param data data to find in the tree
     * @return the data in the tree that is found
     */
    private AVLNode<T> getHelp(AVLNode<T> curr, T data) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not in the tree!");
        }
        if (data.equals(curr.getData())) {
            return curr;
        } else if (data.compareTo(curr.getData()) < 0) {
            return getHelp(curr.getLeft(), data);
        } else {
            return getHelp(curr.getRight(), data);
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        }
        return containsHelper(root, data);
    }

    /**
     * Recursive helper method for the contains method
     * @param curr current node being recursed
     * @param data to search for
     * @return whether the AVL contains the data
     */
    private boolean containsHelper(AVLNode<T> curr, T data) {
        if (curr == null) {
            return false;
        } else if (curr.getData().equals(data)) {
            return true;
        } else {
            if (data.compareTo(curr.getData()) > 0) {
                return containsHelper(curr.getRight(), data);
            } else {
                return containsHelper(curr.getLeft(), data);
            }
        }
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return root.getHeight();
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * The predecessor is the largest node that is smaller than the current data.
     *
     * Should be recursive.
     *
     * This method should retrieve (but not remove) the predecessor of the data
     * passed in. There are 2 cases to consider:
     * 1: The left subtree is non-empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 2: The left subtree is empty. In this case, the predecessor is the lowest
     * ancestor of the node containing data whose right child is also
     * an ancestor of data.
     *
     * This should NOT be used in the remove method.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *     76
     *   /    \
     * 34      90
     *  \    /
     *  40  81
     * predecessor(76) should return 40
     * predecessor(81) should return 76
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T predecessor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data.");
        } else if (!contains(data)) {
            throw new NoSuchElementException("Data not found in tree");
        }
        AVLNode<T> node = getHelp(root, data);
        if (node.getLeft() == null) {
            return moreHelp(root, node).getData();
        } else {
            return predecessorHelp(node.getLeft()).getData();
        }
    }

    /**
     * Recursive helper method to find the node containing data
     * @param curr The current node being compared
     * @return the node containing the data to find
     */
    private AVLNode<T> predecessorHelp(AVLNode<T> curr)  {
        if (curr.getRight() == null) {
            return curr;
        } else {
            return predecessorHelp(curr.getRight());
        }
    }

    /**
     * Recursive helper method to find the predecessor data
     * @param curr current node being traversed
     * @param node the node to find the predecessor of
     * @return data of the predecessor node
     */
    private AVLNode<T> moreHelp(AVLNode<T> curr, AVLNode<T> node) {
        if (curr.getRight() == node) {
            return curr;
        } else if (curr.getRight() != null) {
            if (curr.getRight().getLeft() == node) {
                return curr;
            }
        }
        if (curr.getData().compareTo(node.getData()) < 0) {
            return moreHelp(curr.getRight(), node);
        } else if (curr.getData().compareTo(node.getData()) > 0) {
            return moreHelp(curr.getLeft(), node);
        }
        return null;
    }
    /**
     * Returns the data in the deepest node. If there is more than one node
     * with the same deepest depth, return the rightmost (i.e. largest) node with
     * the deepest depth.
     *
     * Should be recursive.
     *
     * Must run in O(log n) for all cases.
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      3
     *        \
     *         1
     * Max Deepest Node:
     * 1 because it is the deepest node
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      4
     *        \    /
     *         1  3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        if (size == 0) {
            return null;
        }
        return deepestNodeHelper(root);
    }

    /**
     * Recursive  method for finding the max deepest node in an AVL
     * @param curr the root of the subtree
     * @return data of the deepest node
     */
    private T deepestNodeHelper(AVLNode<T> curr) {
        if (curr.getLeft() == null && curr.getRight() == null) {
            return curr.getData();
        } else {
            int left;
            if (curr.getLeft() == null) {
                left = -1;
            } else {
                left = curr.getLeft().getHeight();
            }
            int right;
            if (curr.getLeft() == null) {
                right = -1;
            } else {
                right = curr.getRight().getHeight();
            }
            if (left > right) {
                return deepestNodeHelper(curr.getLeft());
            } else {
                return deepestNodeHelper(curr.getRight());
            }
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
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
