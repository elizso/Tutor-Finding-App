package org.tutorg;

import org.junit.Assert;
import org.junit.Test;
import org.tutorg.data.AVLTree;

/**
 * Testing class that contains unit tests for the AVLTree.java class.
 *
 * @author Name: Samuel Seymour UID:u6959744
 *
 * Copied from COMP2100 Lab 4
 * Aided and adapted with ChatGPT
 */

public class AVLTreeTest {
    public AVLTreeTest() {
    }

    /**
     * Tests the immutability of the AVL tree.
     */
    @Test(timeout = 1000L)
    public void immutableTest1() {
        AVLTree<Integer> avl = new AVLTree(5);
        avl.insert(10);
        String expected = "{value=5, leftNode={}, rightNode={}}";
        Assert.assertEquals("\nAVL tree implementation is not immutable\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, expected, avl.toString());
    }

    /**
     * Tests the immutability of the AVL tree with multiple insertions.
     */
    @Test(timeout = 1000L)
    public void immutableTest2() {
        AVLTree<Integer> avl = new AVLTree(1);
        avl = avl.insert(15);
        avl = avl.insert(45);
        avl.insert(10);
        avl.insert(50);
        avl.insert(3);
        String expected = "{value=15, leftNode={value=1, leftNode={}, rightNode={}}, rightNode={value=45, leftNode={}, rightNode={}}}";
        Assert.assertEquals("\nAVL tree implementation is not immutable\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, expected, avl.toString());
    }

    /**
     * Tests the insertion of elements in order.
     */
    @Test(
            timeout = 1000L
    )
    public void insertInOrderTest() {
        AVLTree<Integer> avl = new AVLTree(5);
        avl = avl.insert(10);
        String expected = "{value=5, leftNode={}, rightNode={value=10, leftNode={}, rightNode={}}}";
        Assert.assertNotNull("\nInsertion does not properly position values\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, avl.rightNode.value);
        Assert.assertEquals("\nInsertion does not properly position values\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, 10L, (long)(Integer)avl.rightNode.value);
        avl = avl.insert(1);
        expected = "{value=5, leftNode={value=1, leftNode={}, rightNode={}}, rightNode={value=10, leftNode={}, rightNode={}}}";
        Assert.assertNotNull("\nInsertion does not properly position values\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, avl.leftNode.value);
        Assert.assertEquals("\nInsertion does not properly position values\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, 1L, (long)(Integer)avl.leftNode.value);
    }

    /**
     * Tests the insertion of duplicate elements.
     */
    @Test(timeout = 1000L)
    public void insertDuplicateTest() {
        AVLTree<Integer> avl = (new AVLTree(5)).insert(5);
        String expected = "{value=5, leftNode={}, rightNode={}}";
        Assert.assertEquals("\nInsertion does not properly position values\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, 0L, (long)avl.getHeight());
        Assert.assertNull("\nInsertion does not properly handle duplicates\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, avl.leftNode.value);
        Assert.assertNull("\nInsertion does not properly handle duplicates\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, avl.rightNode.value);
    }

    /**
     * Tests the left rotation operation of the AVL tree.
     */
    @Test(timeout = 1000L)
    public void leftRotateTest() {
        AVLTree<Integer> avl = (new AVLTree(5)).insert(8).insert(10);
        String expected = "{value=8, leftNode={value=5, leftNode={}, rightNode={}}, rightNode={value=10, leftNode={}, rightNode={}}}";
        Assert.assertNotNull("\nLeft rotation failed\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, avl.value);
        Assert.assertEquals("\nLeft rotation failed\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, 8L, (long)(Integer)avl.value);
        Assert.assertNotNull("\nLeft rotation failed\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, avl.leftNode.value);
        Assert.assertEquals("\nLeft rotation failed\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, 5L, (long)(Integer)avl.leftNode.value);
        Assert.assertNotNull("\nLeft rotation failed\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, avl.rightNode.value);
        Assert.assertEquals("\nLeft rotation failed\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, 10L, (long)(Integer)avl.rightNode.value);
    }

    /**
     * Tests the right rotation operation of the AVL tree.
     */
    @Test(timeout = 1000L)
    public void rightRotateTest() {
        AVLTree<Integer> avl = (new AVLTree(10)).insert(6).insert(3);
        String expected = "{value=6, leftNode={value=3, leftNode={}, rightNode={}}, rightNode={value=10, leftNode={}, rightNode={}}}";
        Assert.assertNotNull("\nRight rotation failed\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, avl.value);
        Assert.assertEquals("\nRight rotation failed\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, 6L, (long)(Integer)avl.value);
        Assert.assertNotNull("\nRight rotation failed\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, avl.leftNode.value);
        Assert.assertEquals("\nRight rotation failed\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, 3L, (long)(Integer)avl.leftNode.value);
        Assert.assertNotNull("\nRight rotation failed\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, avl.rightNode.value);
        Assert.assertEquals("\nRight rotation failed\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, 10L, (long)(Integer)avl.rightNode.value);
    }

    /**
     * Tests the balance factor calculation of the AVL tree.
     */
    @Test(timeout = 1000L)
    public void balanceFactorTest() {
        AVLTree<Integer> avl = (new AVLTree(5)).insert(10).insert(20);
        String expected = "{value=10, leftNode={value=5, leftNode={}, rightNode={}}, rightNode={value=20, leftNode={}, rightNode={}}}";
        Assert.assertEquals("\nInsertion does not properly balance tree (must left rotate)\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, 0L, (long)avl.getBalanceFactor());
        avl = avl.insert(22).insert(21);
        expected = "{value=10, leftNode={value=5, leftNode={}, rightNode={}}, rightNode={value=21, leftNode={value=20, leftNode={}, rightNode={}}, rightNode={value=22, leftNode={}, rightNode={}}}}";
        Assert.assertEquals("\nInsertion does not properly balance tree (must left, right, left rotate)\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, -1L, (long)avl.getBalanceFactor());
        avl = avl.insert(23);
        expected = "{value=21, leftNode={value=10, leftNode={value=5, leftNode={}, rightNode={}}, rightNode={value=20, leftNode={}, rightNode={}}}, rightNode={value=22, leftNode={}, rightNode={value=23, leftNode={}, rightNode={}}}}";
        Assert.assertEquals("\nInsertion does not properly balance tree (must left, right, left, left rotate)\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, 0L, (long)avl.getBalanceFactor());
        avl = (new AVLTree(10)).insert(5).insert(6).insert(4).insert(7).insert(2).insert(1).insert(0).insert(3);
        expected = "{value=6, leftNode={value=2, leftNode={value=1, leftNode={value=0, leftNode={}, rightNode={}}, rightNode={}}, rightNode={value=4, leftNode={value=3, leftNode={}, rightNode={}}, rightNode={value=5, leftNode={}, rightNode={}}}}, rightNode={value=10, leftNode={value=7, leftNode={}, rightNode={}}, rightNode={}}}";
        Assert.assertEquals("\nInsertion does not properly balance tree (must left, right, right, right, left, right rotate)\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, 1L, (long)avl.getBalanceFactor());
    }

    /**
     * Tests advanced rotations (double rotations) of the AVL tree.
     */
    @Test(timeout = 1000L)
    public void advancedRotationsTest() {
        AVLTree<Integer> avl = (new AVLTree(14)).insert(17).insert(11).insert(7).insert(53).insert(4).insert(13).insert(12).insert(8).insert(60).insert(19).insert(16).insert(20);
        String expected = "{value=14, leftNode={value=11, leftNode={value=7, leftNode={value=4, leftNode={}, rightNode={}}, rightNode={value=8, leftNode={}, rightNode={}}}, rightNode={value=12, leftNode={}, rightNode={value=13, leftNode={}, rightNode={}}}}, rightNode={value=19, leftNode={value=17, leftNode={value=16, leftNode={}, rightNode={}}, rightNode={}}, rightNode={value=53, leftNode={value=20, leftNode={}, rightNode={}}, rightNode={value=60, leftNode={}, rightNode={}}}}}";
        Assert.assertNotNull("\nInsertion cannot handle either right right, right left, left left or left right double rotations.\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, avl.value);
        Assert.assertNotNull("\nInsertion cannot handle either right right, right left, left left or left right double rotations.\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, avl.leftNode.value);
        Assert.assertNotNull("\nInsertion cannot handle either right right, right left, left left or left right double rotations.\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, avl.rightNode.value);
        Assert.assertEquals("\nInsertion cannot handle either right right, right left, left left or left right double rotations.\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, 0L, (long)avl.getBalanceFactor());
        Assert.assertEquals("\nInsertion cannot handle either right right, right left, left left or left right double rotations.\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, 14L, (long)(Integer)avl.value);
        Assert.assertEquals("\nInsertion cannot handle either right right, right left, left left or left right double rotations.\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, 11L, (long)(Integer)avl.leftNode.value);
        Assert.assertEquals("\nInsertion cannot handle either right right, right left, left left or left right double rotations.\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, 19L, (long)(Integer)avl.rightNode.value);
        avl = (new AVLTree(40)).insert(20).insert(10).insert(25).insert(30).insert(22).insert(50);
        expected = "{value=25, leftNode={value=20, leftNode={value=10, leftNode={}, rightNode={}}, rightNode={value=22, leftNode={}, rightNode={}}}, rightNode={value=40, leftNode={value=30, leftNode={}, rightNode={}}, rightNode={value=50, leftNode={}, rightNode={}}}}";
        Assert.assertNotNull("\nInsertion cannot handle either right right, right left, left left or left right double rotations.\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, avl.value);
        Assert.assertNotNull("\nInsertion cannot handle either right right, right left, left left or left right double rotations.\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, avl.leftNode.value);
        Assert.assertNotNull("\nInsertion cannot handle either right right, right left, left left or left right double rotations.\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, avl.rightNode.value);
        Assert.assertEquals("\nInsertion cannot handle either right right, right left, left left or left right double rotations.\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, 0L, (long)avl.getBalanceFactor());
        Assert.assertEquals("\nInsertion cannot handle either right right, right left, left left or left right double rotations.\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, 25L, (long)(Integer)avl.value);
        Assert.assertEquals("\nInsertion cannot handle either right right, right left, left left or left right double rotations.\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, 20L, (long)(Integer)avl.leftNode.value);
        Assert.assertEquals("\nInsertion cannot handle either right right, right left, left left or left right double rotations.\nYour AVL tree should look like: " + expected + "\nBut it actually looks like: " + avl, 40L, (long)(Integer)avl.rightNode.value);
    }
}
