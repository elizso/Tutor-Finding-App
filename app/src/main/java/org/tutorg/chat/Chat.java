package org.tutorg.chat;



/**
 * Chat class for saving the name, chatID and latest message
 * @author Eliz So (u7489812)
 */
public class Chat {

    private final String name;

    private final String chatID;
    private String message;

    public Chat(String name, String chatID, String message){
        this.name = name;
        this.chatID = chatID;
        this.message = message;
    }

    public Chat(String name, String chatID){
        this.name = name;
        this.chatID = chatID;
        this.message = " ";
    }

    public String getName() {
        return name;
    }

    public String getChatID() {
        return chatID;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


    public boolean equals(Chat chat) {
        return chatID.equals(chat.getChatID());
    }
}
