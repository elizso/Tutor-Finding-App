package org.tutorg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.tutorg.data.EmptyTree;
import org.tutorg.data.Tree;

/**
 * Testing class that contains unit tests for the EmptyTree class.
 *
 * @author Name: Samuel Seymour UID:u6959744
 * Aided and adapted with ChatGPT
 */

public class EmptyTreeTest {
    /**
     * Custom subclass to test EmptyTree.
     *
     * This class is a custom subclass of EmptyTree that overrides the insert method for testing purposes.
     */
    private static class TestEmptyTree<T extends Comparable<T>> extends EmptyTree<T> {
        @Override
        public Tree<T> insert(T element) {
            // Implement insert method for testing
            return null;
        }
    }

    /**
     * Tests the min method of the EmptyTree class.
     *
     * This method tests the min method of the EmptyTree class by creating an instance of TestEmptyTree
     * and asserting that the returned minimum value is null, as an empty tree has no minimum element.
     */
    @Test(timeout=1000)
    public void testMin() {
        TestEmptyTree<String> emptyTree = new TestEmptyTree<>();
        assertNull(emptyTree.min()); // The minimum of an empty tree should be null
    }

    /**
     * Tests the max method of the EmptyTree class.
     *
     * This method tests the max method of the EmptyTree class by creating an instance of TestEmptyTree
     * and asserting that the returned maximum value is null, as an empty tree has no maximum element.
     */
    @Test(timeout = 1000)
    public void testMax() {
        TestEmptyTree<Double> emptyTree = new TestEmptyTree<>();
        assertNull(emptyTree.max()); // The maximum of an empty tree should be null
    }

    /**
     * Tests the find method of the EmptyTree class.
     *
     * This method tests the find method of the EmptyTree class by creating an instance of TestEmptyTree
     * and asserting that the returned value is null when trying to find an element in an empty tree.
     */
    @Test(timeout = 1000)
    public void testFind() {
        TestEmptyTree<Character> emptyTree = new TestEmptyTree<>();
        assertNull(emptyTree.find('A')); // Finding an element in an empty tree should return null
    }

    /**
     * Tests the getHeight method of the EmptyTree class.
     *
     * This method tests the getHeight method of the EmptyTree class by creating an instance of TestEmptyTree
     * and asserting that the returned height is -1, as an empty tree has a height of -1.
     */
    @Test(timeout = 1000)
    public void testGetHeight() {
        TestEmptyTree<Integer> emptyTree = new TestEmptyTree<>();
        assertEquals(-1, emptyTree.getHeight()); // The height of an empty tree should be -1
    }

    /**
     * Tests the toString method of the EmptyTree class.
     *
     * This method tests the toString method of the EmptyTree class by creating an instance of TestEmptyTree
     * and asserting that the returned string representation is "{}", as an empty tree is represented as "{}".
     */
    @Test(timeout = 1000)
    public void testToString() {
        TestEmptyTree<Integer> emptyTree = new TestEmptyTree<>();
        assertEquals("{}", emptyTree.toString()); // The string representation of an empty tree should be "{}"
    }
}
