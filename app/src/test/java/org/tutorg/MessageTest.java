package org.tutorg;

import org.junit.Assert;
import org.junit.Test;
import org.tutorg.chat.Message;

/**
 * Testing class that contains unit tests for the Message class.
 *
 * @author Name: Samuel Seymour UID:u6959744
 * Aided and adapted with ChatGPT
 */
public class MessageTest {

    /**
     * Tests the getSender() method of the Message class.
     *
     * This method tests the getSender() method of the Message class by creating an instance of Message
     * with a sender and message, and then asserts that the retrieved sender is correct.
     */
    @Test(timeout = 1000)
    public void testGetSender() {
        String sender = "John";
        String message = "Hello";
        Message msg = new Message(sender, message);

        Assert.assertEquals(sender, msg.getSender());
    }

    /**
     * Tests the setSender() method of the Message class.
     *
     * This method tests the setSender() method of the Message class by creating an instance of Message
     * with a sender and message, setting a new sender, and then asserts that the updated sender is correct.
     */
    @Test(timeout = 1000)
    public void testSetSender() {
        String sender = "John";
        String newSender = "Alice";
        String message = "Hello";
        Message msg = new Message(sender, message);
        msg.setSender(newSender);

        Assert.assertEquals(newSender, msg.getSender());
    }

    /**
     * Tests the getMessage() method of the Message class.
     *
     * This method tests the getMessage() method of the Message class by creating an instance of Message
     * with a sender and message, and then asserts that the retrieved message is correct.
     */
    @Test(timeout = 1000)
    public void testGetMessage() {
        String sender = "John";
        String message = "Hello";
        Message msg = new Message(sender, message);

        Assert.assertEquals(message, msg.getMessage());
    }

    /**
     * Tests the setMessage() method of the Message class.
     *
     * This method tests the setMessage() method of the Message class by creating an instance of Message
     * with a sender and message, setting a new message, and then asserts that the updated message is correct.
     */
    @Test(timeout = 1000)
    public void testSetMessage() {
        String sender = "John";
        String message = "Hello";
        String newMessage = "How are you?";
        Message msg = new Message(sender, message);
        msg.setMessage(newMessage);

        Assert.assertEquals(newMessage, msg.getMessage());
    }
}
