package org.tutorg.chat;

/**
 * The Message class saves each of the message before passing to the MessageAdapter.
 * It saves the sender and the message.
 * @author Eliz So (u7489812)
 */
public class Message {

    private String sender;
    private String message;

    public Message(String sender, String message) {

        this.sender = sender;
        this.message = message;
    }


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
