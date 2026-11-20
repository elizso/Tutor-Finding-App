package org.tutorg.data;

/**
 * @author Sharaf Zaman (u7559204) , Samuel Seymour (u6959744)
 * Adapted from COMP2100 Lab 04
 * @param <T> a type for Node type, this class should implement Comparable.
 * AI Tools aided in docstrings
 */

public class AVLTree<T extends Comparable<T>> extends BinarySearchTree<T> {
    /*
        As a result of inheritance by using 'extends BinarySearchTree<T>,
        all class fields within BinarySearchTree are also present here.
        So while not explicitly written here, this class has:
            - value
            - leftNode
            - rightNode
     */

    public AVLTree(T value) {
        super(value);
        // Set left and right children to be of EmptyAVL as opposed to EmptyBST.
        this.leftNode = new EmptyAVL<>();
        this.rightNode = new EmptyAVL<>();
    }

    public AVLTree(T value, Tree<T> leftNode, Tree<T> rightNode) {
        super(value, leftNode, rightNode);
    }

    /**
     * @return balance factor of the current node.
     */
    public int getBalanceFactor() {

        return leftNode.getHeight() - rightNode.getHeight();
    }

    @Override
    public AVLTree<T> insert(T element) {
        // Ensure input is not null.
        if (element == null)
            throw new IllegalArgumentException("Input cannot be null");

        AVLTree<T> tree = null;
        // The number is greater
        if (element.compareTo(value) > 0) {
            tree = new AVLTree<T>(value, leftNode, rightNode.insert(element));
        } else if (element.compareTo(value) < 0) {
            tree = new AVLTree<T>(value, leftNode.insert(element), rightNode);
        } else {
            // Don't care if they're the same
            return  this;
        }

        int balanceFactor = tree.getBalanceFactor();
        if (balanceFactor >= -1 && balanceFactor <= 1) {
            return tree;
        } else {
            // we need rebalancing
            if (balanceFactor > 1) {
                if (balanceFactor == 2 && ((AVLTree<T>)tree.leftNode).getBalanceFactor() == -1)  {
                    tree.leftNode = ((AVLTree<T>) tree.leftNode).leftRotate();
                    return tree.rightRotate();
                }
                // thing is heavy on the left
                return tree.rightRotate();
            } else {
                if (balanceFactor == -2 && ((AVLTree<T>)tree.rightNode).getBalanceFactor() == 1)  {
                    tree.rightNode = ((AVLTree<T>) tree.rightNode).rightRotate();
                    return tree.leftRotate();
                }
                // thing is heavy on the right
                return tree.leftRotate();
            }
        }
    }

    /**
     * Conducts a left rotation on the current node.
     *
     * @return the new 'current' or 'top' node after rotation.
     */
    public AVLTree<T> leftRotate() {

        Tree<T> newRoot = this.rightNode;
        Tree<T> oldRoot = this;

        newRoot.leftNode = new AVLTree<>(oldRoot.value, oldRoot.leftNode, newRoot.leftNode);
        if (!(newRoot.rightNode instanceof AVLTree.EmptyAVL)) {
            newRoot.rightNode = new AVLTree<>(newRoot.rightNode.value, newRoot.rightNode.leftNode, newRoot.rightNode.rightNode);
        }

        return (AVLTree<T>) newRoot;
    }

    /**
     * Conducts a right rotation on the current node.
     *
     * @return the new 'current' or 'top' node after rotation.
     */
    public AVLTree<T> rightRotate() {
        Tree<T> leftNodeOfNewRoot = this.leftNode.leftNode;
        Tree<T> newRoot = this.leftNode;
        Tree<T> oldRoot = this;
        Tree<T> leftOfOldRoot = newRoot.rightNode;

        newRoot.rightNode = new AVLTree<>(oldRoot.value, leftOfOldRoot, oldRoot.rightNode);
        if (!(leftNodeOfNewRoot instanceof AVLTree.EmptyAVL)) {
            newRoot.leftNode = new AVLTree<>(leftNodeOfNewRoot.value, leftNodeOfNewRoot.leftNode, leftNodeOfNewRoot.rightNode);
        }

        return (AVLTree<T>) newRoot; // Change to return something different
    }

    public static class EmptyAVL<T extends Comparable<T>> extends EmptyTree<T> {
        @Override
        public Tree<T> insert(T element) {
            // The creation of a new Tree, hence, return tree.
            return new AVLTree<T>(element);
        }
    }
}
