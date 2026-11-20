package org.tutorg.search.tokenizer;


/**
 * Token class to save extracted token from tokenizer.
 * Each token has its surface form saved in token
 * and type saved in type which is one of the predefined type in Type enum.
 * The following are the different types of tokens:
 * STRING
 * IN
 * ON
 * WITH
 * SPACE
 * COMMA
 * @author Name: Samuel Macauley UID:u7486259
 * @annotations Scaffold for Token taken from lab 5.
 */
public class Token {
    public enum Type {STRING, IN, ON, WITH, SPACE, COMMA}



    private final String token; // Token representation in String form.
    private final Type type;    // Type of the token.

    public Token(String token, Type type) {
        this.token = token;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        if (type == Type.STRING){
            return "STRING(" +token+")";
        } else {
            return type + "";
        }
    }
}


