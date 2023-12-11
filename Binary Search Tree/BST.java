import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Your implementation of a BST.
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /*
     * Do not add a constructor.
     */

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The new data should become a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Should be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data The data to add to the tree.
     * @throws java.lang.IllegalArgumentException If data is null.
     */
    public void add(T data) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (data == null){
            throw new IllegalArgumentException("Error,data is null");
        } else {
            root = addHelper(root, data);
        }
    }

    private BSTNode<T> addHelper(BSTNode<T> node, T data){
        if (node == null){
            size++;
            return new BSTNode(data);

        } else {
            int result = data.compareTo(node.getData());

            if (result == 0){
                return node;
            } else if (result > 0){
                node.setRight(addHelper(node.getRight(), data));
                return node;
            } else{
                node.setLeft(addHelper(node.getLeft(), data));
                return node;
            }
        }
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the SUCCESSOR to
     * replace the data. You should use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do NOT return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data The data to remove.
     * @return The data that was removed.
     * @throws java.lang.IllegalArgumentException If data is null.
     * @throws java.util.NoSuchElementException   If the data is not in the tree.
     */
    public T remove(T data) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (data == null){
            throw new IllegalArgumentException("Error,data is null");
        } else {

            search(root, data);
            
            root = removeHelper(root, data);

            return data;
        }
    }

    private BSTNode<T> removeHelper(BSTNode<T> node, T data){

        if (Objects.isNull(node)){ 

            return null;
        } else {

            if (node.getData().equals(data)){

                //If the node is with only one child or no child
                
                if (Objects.isNull(node.getLeft())) {
                    size--;
                    return node.getRight();           

                } else if (Objects.isNull(node.getRight())){
                    size--;
                    return node.getLeft();
                
                // If the node has two children

                } else {
                    // find successor & delete it
                    node.setData(getSuccessor(node.getRight()));
                    node.setRight(removeHelper(node.getRight(), node.getData()));
                }

            } else if (data.compareTo(node.getData()) > 0){
                    node.setRight(removeHelper(node.getRight(), data));

            } else if (data.compareTo(node.getData()) < 0){
                    node.setLeft(removeHelper(node.getLeft(), data));
                }  
            return node;
        } 
    }

    private void search(BSTNode<T> node, T data){
        if (Objects.isNull(node)){
            throw new NoSuchElementException("data not in the tree");
        } else {
            if (node.getData().equals(data)){
                return;
            } else if (data.compareTo(node.getData()) > 0){
                search(node.getRight(),data);
            } else if (data.compareTo(node.getData()) < 0){
                search(node.getLeft(),data);
            } 
        }
    }

    private T getSuccessor(BSTNode<T> currentNode){
        while (currentNode.getLeft() != null){
            currentNode = currentNode.getLeft();
        }
        return currentNode.getData();
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }


}
