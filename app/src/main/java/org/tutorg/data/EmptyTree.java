package org.tutorg.data;

import org.tutorg.data.Tree;

/**
 * Empty Tree is a subclass of Tree
 * @author Sharaf Zaman (u7559204) Samuel Seymour (u6959744)
 * @param <T> a type for Node type, this class should implement Comparable.
 * Adapted from COMP2100 Tutorial 4
 *           AI Tools aided in docstrings
 */

public abstract class EmptyTree<T extends Comparable<T>> extends Tree<T> {
    // Will need to be implemented by the subclass inheriting this class.
    public abstract Tree<T> insert(T element);

    @Override
    public T min() {
        // No minimum.
        return null;
    }

    @Override
    public T max() {
        // No maximum.
        return null;
    }

    @Override
    public Tree<T> find(T element) {
        // Was unable to find the item. Hence, return null.
        return null;
    }

    @Override
    public int getHeight() {
        /*
         return -1 as this is a leaf node.
         -1 instead of 0 as this is inline with our definition of height as 'the number of edges between
             the current node and the leaf node'. Furthermore, returning 0 will not cause rotations where they
             should occur.
         */
        return -1;
    }

    @Override
    public String toString() {
        return "{}";
    }

    /*
    @Override
    public String display(int tabs) {
        return "null";
    }

     */
}
