package org.tutorg.search.tokenizer;

/**
 * Tokenizer class that allows for the tokenization of Strings.
 * @author Name: Samuel Macauley UID:u7486259
 * @annotations Scaffold for Tokenizer taken from lab 5.
 */
public class Tokenizer {
    public String buffer;          // String to be transformed into tokens each time next() is called.
    private Token currentToken;     // The current token. The next token is extracted when next() is called.


    /**
     * Tokenizer class constructor
     * The constructor extracts the first token and save it to currentToken
     */
    public Tokenizer(String text) {
        buffer = text;          // save input text (string)
        next();                 // extracts the first token.
    }

    /**
     * This function will find and extract a next token from buffer and
     * save the token to currentToken.
     */
    public void next() {

        if (buffer.isEmpty()) {
            System.out.println("1");
            currentToken = null;    // if there's no string left, set currentToken null and return
            return;
        }

        char firstChar = buffer.charAt(0);

        // IN tokenizing.
        if (firstChar == 'I' && buffer.length() > 1 && buffer.charAt(1) == 'N'){
            currentToken = new Token(buffer.substring(0, 2), Token.Type.IN);
        }
        // ON tokenizing.
        else if (firstChar == 'O' && buffer.length() > 1 && buffer.charAt(1) == 'N'){
            currentToken = new Token(buffer.substring(0, 2), Token.Type.ON);
        }
        // Person tokenizing.
        else if (firstChar == 'W' && buffer.length() > 3 && buffer.startsWith("ITH", 1)){
            currentToken = new Token(buffer.substring(0, 4), Token.Type.WITH);
        }
        // Space tokenizing.
        else if (firstChar == ' '){
            currentToken = new Token(" ", Token.Type.SPACE);
        }
        // Comma tokenizing.
        else if (firstChar == ','){
            currentToken = new Token(",", Token.Type.COMMA);
        }
        // String tokenizing.
        else {
            int currentCharIndex = 0;
            // Finds all the characters of the STRING.
            while (currentCharIndex < buffer.length() && buffer.charAt(currentCharIndex) != ' ' && buffer.charAt(currentCharIndex) != ','){
                currentCharIndex ++;
                currentToken = new Token(buffer.substring(0,currentCharIndex), Token.Type.STRING);
            }

        }
        // Remove the extracted token from buffer
        int tokenLen = currentToken.getToken().length();
        buffer = buffer.substring(tokenLen);
    }

    /**
     * Returns the current token extracted by next()
     * @return type: Token
     */
    public Token current() {
        return currentToken;
    }

    /**
     * Check whether tokenizer still has tokens left
     * @return type: boolean
     */
    public boolean hasNext() {
        return currentToken != null;
    }

}
