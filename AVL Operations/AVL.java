import java.util.NoSuchElementException;
import java.util.Objects;


/**
 * Your implementation of an AVL.
 */
public class AVL<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private AVLNode<T> root;
    private int size;

    /*
     * Do not add a constructor.
     */
    public AVL() {

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
     * necessary. This is as simple as calling the balance() method on the
     * current node, before returning it (assuming that your balance method
     * is written correctly from part 1 of this assignment).
     *
     * @param data The data to add.
     * @throws java.lang.IllegalArgumentException If data is null.
     */
    public void add(T data) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)
        if (data == null){
            throw new IllegalArgumentException("data is null");
        }
        root = addHelper(root, data);

    }

    private AVLNode<T> addHelper (AVLNode<T> current, T data){
        AVLNode<T> newNode = new AVLNode<T>(data);

        if (Objects.isNull(current)){
            current = newNode;
            size ++;

        } else {
            if (current.getData().compareTo(data) > 0){
                current.setLeft(addHelper(current.getLeft(),data));
                current = balance(current);

            } else if (current.getData().compareTo(data) < 0) {
                current.setRight(addHelper(current.getRight(),data));
                current = balance(current);

            }
        }
        return current;
    }



    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     *    simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     *    replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     *    replace the data, NOT predecessor. As a reminder, rotations can occur
     *    after removing the successor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary. This is as simple as calling the balance() method on the
     * current node, before returning it (assuming that your balance method
     * is written correctly from part 1 of this assignment).
     *
     * Do NOT return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data The data to remove.
     * @return The data that was removed.
     * @throws java.lang.IllegalArgumentException If the data is null.
     * @throws java.util.NoSuchElementException   If the data is not found.
     */
    public T remove(T data) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (data == null){
            throw new IllegalArgumentException("data is null");
        }

        AVLNode<T> temp = new AVLNode<T>(null);

        search(root, data, temp);

        root = removeHelper(root, data);

        return temp.getData();

    }


    private AVLNode<T> removeHelper(AVLNode<T> current, T data){

        if (Objects.isNull(current)){
            return current;

        } else if (current.getData().compareTo(data) > 0){
            current.setLeft(removeHelper(current.getLeft(),data));
            current = balance(current);

        } else if (current.getData().compareTo(data) < 0) {
            current.setRight(removeHelper(current.getRight(),data));
            current = balance(current);
              
        } else {
            
            if (Objects.isNull(current.getLeft())){
                size --;
                current = balance(current);
                return current.getRight();
       
            } else if (Objects.isNull(current.getRight())){
                size --;
                return current.getLeft();
      
            } else {
                // find the successor to replace
                current.setData(searchSuccessor(current.getRight()));
                current.setRight(removeHelper(current.getRight(), current.getData()));
                current = balance(current);
                return current;
                }
                
        }
        current = balance(current);
        return current;

    }

    private T searchSuccessor(AVLNode<T> node){

        while(!Objects.isNull(node.getLeft())){  
            node = node.getLeft();

        }
        return node.getData();
    }

    private AVLNode<T> search(AVLNode<T> current, T data, AVLNode<T> temp){


        if (Objects.isNull(current)){
            throw new NoSuchElementException("data is not found");
        }
        
        if (current.getData().compareTo(data) > 0){
            search(current.getLeft(),data, temp);

        } else if (current.getData().compareTo(data) < 0) {
            search(current.getRight(),data, temp);
              
        } else{
            temp.setData(data);
        }
        
        return temp;
    }
    

    /**
     * Updates the height and balance factor of a node using its
     * setter methods.
     *
     * Recall that a null node has a height of -1. If a node is not
     * null, then the height of that node will be its height instance
     * data. The height of a node is the max of its left child's height
     * and right child's height, plus one.
     *
     * The balance factor of a node is the height of its left child
     * minus the height of its right child.
     *
     * This method should run in O(1).
     * You may assume that the passed in node is not null.
     *
     * This method should only be called in rotateLeft(), rotateRight(),
     * and balance().
     *
     * @param currentNode The node to update the height and balance factor of.
     */
    private void updateHeightAndBF(AVLNode<T> currentNode) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (currentNode.getLeft() != null && currentNode.getRight() != null){
            currentNode.setHeight(Math.max(currentNode.getLeft().getHeight(), currentNode.getRight().getHeight())+1);
            currentNode.setBalanceFactor(currentNode.getLeft().getHeight() - currentNode.getRight().getHeight());

        } else if (currentNode.getLeft() == null && currentNode.getRight() == null){
            currentNode.setHeight(0);
            currentNode.setBalanceFactor(0);

        } else if (currentNode.getRight() == null){
            currentNode.setHeight(currentNode.getLeft().getHeight()+1);
            currentNode.setBalanceFactor(currentNode.getLeft().getHeight()+1);// a null node has a height of -1

        } else {
            currentNode.setHeight(currentNode.getRight().getHeight()+1);
            currentNode.setBalanceFactor(-1 - currentNode.getRight().getHeight()); // a null node has a height of -1
        }
    }

    /**
     * Method that rotates a current node to the left. After saving the
     * current's right node to a variable, the right node's left subtree will
     * become the current node's right subtree. The current node will become
     * the right node's left subtree.
     *
     * Don't forget to recalculate the height and balance factor of all
     * affected nodes, using updateHeightAndBF().
     *
     * This method should run in O(1).
     *
     * You may assume that the passed in node is not null and that the subtree
     * starting at that node is right heavy. Therefore, you do not need to
     * perform any preliminary checks, rather, you can immediately perform a
     * left rotation on the passed in node and return the new root of the subtree.
     *
     * This method should only be called in balance().
     *
     * @param currentNode The current node under inspection that will rotate.
     * @return The parent of the node passed in (after the rotation).
     */
    private AVLNode<T> rotateLeft(AVLNode<T> currentNode) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        AVLNode<T> newParent = currentNode.getRight(); // find the new parent node
    
        currentNode.setRight(newParent.getLeft());
        
        newParent.setLeft(currentNode);

        updateHeightAndBF(currentNode);
        updateHeightAndBF(newParent);

        return newParent;

    }

    /**
     * Method that rotates a current node to the right. After saving the
     * current's left node to a variable, the left node's right subtree will
     * become the current node's left subtree. The current node will become
     * the left node's right subtree.
     *
     * Don't forget to recalculate the height and balance factor of all
     * affected nodes, using updateHeightAndBF().
     *
     * This method should run in O(1).
     *
     * You may assume that the passed in node is not null and that the subtree
     * starting at that node is left heavy. Therefore, you do not need to perform
     * any preliminary checks, rather, you can immediately perform a right
     * rotation on the passed in node and return the new root of the subtree.
     *
     * This method should only be called in balance().
     *
     * @param currentNode The current node under inspection that will rotate.
     * @return The parent of the node passed in (after the rotation).
     */
    private AVLNode<T> rotateRight(AVLNode<T> currentNode) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        AVLNode<T> newParent = currentNode.getLeft(); // find the new parent node

        currentNode.setLeft(newParent.getRight()); 
        
        newParent.setRight(currentNode);

        updateHeightAndBF(currentNode);
        updateHeightAndBF(newParent);

        return newParent;

    }

    /**
     * Method that balances out the tree starting at the node passed in.
     * This method should be called in your add() and remove() methods to
     * facilitate rebalancing your tree after an operation.
     *
     * The height and balance factor of the current node is first recalculated.
     * Based on the balance factor, a no rotation, a single rotation, or a
     * double rotation takes place. The current node is returned.
     *
     * You may assume that the passed in node is not null. Therefore, you do
     * not need to perform any preliminary checks, rather, you can immediately
     * check to see if any rotations need to be performed.
     *
     * This method should run in O(1).
     *
     * @param currentNode The current node under inspection.
     * @return The AVLNode that the caller should return.
     */
    private AVLNode<T> balance(AVLNode<T> currentNode) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!

        /* First, we update the height and balance factor of the current node. */
        updateHeightAndBF(currentNode);

        if ( currentNode.getBalanceFactor() < -1) {
            if ( currentNode.getRight().getBalanceFactor() > 0 ) {
                currentNode.setRight(rotateRight(currentNode.getRight()));
            }
            currentNode = rotateLeft(currentNode);
            
        } else if (currentNode.getBalanceFactor() > 1) {
            if ( currentNode.getLeft().getBalanceFactor() < 0 ) {
                currentNode.setLeft(rotateLeft(currentNode.getLeft()));
            }
            currentNode = rotateRight(currentNode);
        }
      
        return currentNode;
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The root of the tree.
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
     * @return The size of the tree.
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    // Print the tree
    private void show(AVLNode<T> node){

        if (node.getLeft() != null){
            System.out.println(node.getLeft().getData());
            show(node.getLeft());
        }
    
        if (node.getRight() != null){
            System.out.println(node.getRight().getData());
            show(node.getRight());
        }
    
      }
      
    
    public static void main(String[] args) {
        AVL a = new AVL();
        a.add(7);
        a.add(4);
        a.add(15);
        a.add(1);
        a.add(6);
        a.add(10);
        a.add(18);
        a.add(0);
        a.add(3);
        a.add(5);
        a.add(8);
        a.add(13);
        a.add(16);
        a.add(19);
        a.add(2);
        a.add(9);
        a.add(11);
        a.add(14);
        a.add(17);
        a.add(12);

 
       
        


        

        System.out.println("root: " + a.getRoot().getData());
        a.show(a.getRoot());
        System.out.println("########");
     
        System.out.println("the removed data is " + a.remove(7));
        
        System.out.println("root: " + a.getRoot().getData());
        a.show(a.getRoot());
        
      }
    

    
}