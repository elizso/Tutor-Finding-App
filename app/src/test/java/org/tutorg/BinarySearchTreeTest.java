package org.tutorg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.tutorg.data.BinarySearchTree;

/**
 * Testing class that contains unit tests for the BinarySearchTree class.
 *
 * @author Name: Samuel Seymour UID:u6959744
 * Aided and adapted with ChatGPT
 */

public class BinarySearchTreeTest {

    /**
     * Tests the insert method of the BinarySearchTree class.
     *
     * This method tests the insert method of the BinarySearchTree class by creating a binary
     * search tree and inserting elements into it. It asserts that the tree structure is
     * maintained correctly after each insertion and checks if the inserted elements can be
     * found in the tree.
     */
    @Test(timeout = 1000)
    public void testInsert() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>(5);
        tree = tree.insert(3);
        tree = tree.insert(7);
        tree = tree.insert(4);
        tree = tree.insert(1);

        assertEquals(Integer.valueOf(5), tree.value);
        assertEquals(Integer.valueOf(3), tree.leftNode.value);
        assertEquals(Integer.valueOf(1), tree.leftNode.leftNode.value);
        assertEquals(Integer.valueOf(4), tree.leftNode.rightNode.value);
        assertEquals(Integer.valueOf(7), tree.rightNode.value);

        assertNotNull(tree.find(5));
        assertNotNull(tree.find(3));
        assertNotNull(tree.find(7));
        assertNotNull(tree.find(4));
        assertNotNull(tree.find(1));
        assertNull(tree.find(2)); // Element not present in the tree
    }

    /**
     * Tests the min method of the BinarySearchTree class.
     *
     * This method tests the min method of the BinarySearchTree class by creating a binary
     * search tree and inserting elements into it. It asserts that the minimum value in the tree
     * is correctly identified.
     */
    @Test(timeout = 1000)
    public void testMin() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>(5);
        tree = tree.insert(3);
        tree = tree.insert(7);
        tree = tree.insert(4);
        tree = tree.insert(1);

        assertEquals(Integer.valueOf(1), tree.min());
    }

    /**
     * Tests the max method of the BinarySearchTree class.
     *
     * This method tests the max method of the BinarySearchTree class by creating a binary
     * search tree and inserting elements into it. It asserts that the maximum value in the tree
     * is correctly identified.
     */
    @Test(timeout = 1000)
    public void testMax() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>(5);
        tree = tree.insert(3);
        tree = tree.insert(7);
        tree = tree.insert(4);
        tree = tree.insert(1);

        assertEquals(Integer.valueOf(7), tree.max());
    }

    /**
     * Tests the find method of the BinarySearchTree class.
     *
     * This method tests the find method of the BinarySearchTree class by creating a binary
     * search tree and inserting elements into it. It asserts that the inserted elements can be
     * found in the tree and that a non-existing element is not found.
     */
    @Test(timeout = 1000)
    public void testFind() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>(5);
        tree = tree.insert(3);
        tree = tree.insert(7);
        tree = tree.insert(4);
        tree = tree.insert(1);

        assertNotNull(tree.find(5));
        assertNotNull(tree.find(3));
        assertNotNull(tree.find(7));
        assertNotNull(tree.find(4));
        assertNotNull(tree.find(1));
        assertNull(tree.find(2));
    }
}

