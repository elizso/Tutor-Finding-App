package org.tutorg;

import org.junit.Assert;
import org.junit.Test;
import org.tutorg.chat.Chat;

/**
 * Testing class that contains unit tests for the Chat class.
 *
 * @author Name: Samuel Seymour UID:u6959744
 * Aided and adapted with ChatGPT
 */
public class ChatTest {

    /**
     * Tests the getName method of the Chat class.
     *
     * This method tests the getName method of the Chat class by creating a Chat instance
     * and asserting that the returned name matches the expected name.
     */
    @Test
    public void testGetName() {
        String name = "John";
        String chatID = "123";
        String message = "Hello";
        Chat chat = new Chat(name, chatID, message);

        Assert.assertEquals(name, chat.getName());
    }

    /**
     * Tests the getChatID method of the Chat class.
     *
     * This method tests the getChatID method of the Chat class by creating a Chat instance
     * and asserting that the returned chat ID matches the expected chat ID.
     */
    @Test
    public void testGetChatID() {
        String name = "John";
        String chatID = "123";
        String message = "Hello";
        Chat chat = new Chat(name, chatID, message);

        Assert.assertEquals(chatID, chat.getChatID());
    }

    /**
     * Tests the getMessage method of the Chat class.
     *
     * This method tests the getMessage method of the Chat class by creating a Chat instance
     * and asserting that the returned message matches the expected message.
     */
    @Test
    public void testGetMessage() {
        String name = "John";
        String chatID = "123";
        String message = "Hello";
        Chat chat = new Chat(name, chatID, message);

        Assert.assertEquals(message, chat.getMessage());
    }

    /**
     * Tests the setMessage method of the Chat class.
     *
     * This method tests the setMessage method of the Chat class by creating a Chat instance,
     * setting a new message, and asserting that the returned message matches the new message.
     */
    @Test
    public void testSetMessage() {
        String name = "John";
        String chatID = "123";
        String message = "Hello";
        Chat chat = new Chat(name, chatID);
        chat.setMessage(message);

        Assert.assertEquals(message, chat.getMessage());
    }

    /**
     * Tests the equals method of the Chat class.
     *
     * This method tests the equals method of the Chat class by creating Chat instances with
     * the same and different properties, and asserting the expected results of the equality
     * comparisons.
     */
    @Test
    public void testEquals() {
        String name = "John";
        String chatID1 = "123";
        String chatID2 = "456";
        String message = "Hello";
        Chat chat1 = new Chat(name, chatID1, message);
        Chat chat2 = new Chat(name, chatID1, message);
        Chat chat3 = new Chat(name, chatID2, message);

        Assert.assertTrue(chat1.equals(chat2));
        Assert.assertFalse(chat1.equals(chat3));
    }
}
