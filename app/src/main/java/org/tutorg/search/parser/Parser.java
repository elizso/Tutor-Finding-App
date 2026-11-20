package org.tutorg.search.parser;


import org.tutorg.search.tokenizer.Token;
import org.tutorg.search.tokenizer.Tokenizer;

import java.util.List;

public class Parser {
    /**
     * The following exception should be thrown if the parse is faced with series of tokens that do not
     * correlate with any possible production rule.
     * @author Name: Samuel Macauley UID:u7486259
     * @annotations Scaffold for Parser taken from lab 5.
     */
    public static class IllegalProductionException extends IllegalArgumentException {
        public IllegalProductionException(String errorMessage) {
            super(errorMessage);
        }
    }

    // The tokenizer (class field) this parser will use.
    Tokenizer tokenizer;

    // The entryResults this parser will use.
    EntryResults entryResults;

    /**
     * Creates a Parser, setting the tokenizer and entryResults, then runs parseEntry.
     * @param tokenizer The tokenizer.
     * @param entryResults The entryResults.
     */
    public Parser(Tokenizer tokenizer, EntryResults entryResults) {
        this.tokenizer = tokenizer;
        this.entryResults = entryResults;
        parseEntry();
    }

    /**
     * Adheres to the grammar rule:
     * <entry>   ::=  <mandatoryEntry> | <mandatoryEntry> <space> <optionalEntry>
     *
     */
    private void parseEntry(){
        parseMandatoryEntry();

        // Deals with the space between the mandatoryEntry and the optionalEntry.
        if (tokenizer.hasNext()) {
            if (tokenizer.current().getType().equals(Token.Type.SPACE)) {
                tokenizer.next();
                // Deal with optionalEntry.
                if (tokenizer.hasNext()) {
                    parseOptionalEntry();
                    if (tokenizer.hasNext())
                        // Additional entries given after all possible valid entries have been parsed.
                        throw new IllegalProductionException("Invalid Entry: Additional entries given that can't be taken.");
                } else
                    throw new IllegalProductionException("Invalid Entry: Space given after subject level with no following entries.");
                // Extra token after mandatory entry that is not SPACE.
            } else throw new IllegalProductionException("Invalid Entry: Space not given between mandatory entry and optional entry.");
        }
    }


    /**
     * Adheres to the grammar rule:
     * <mandatoryEntry>   ::=  <string> <space> <string>
     */
    private void parseMandatoryEntry(){
        // Checks if the entry is not empty.
        if (tokenizer.hasNext()) {
            // Deal with the discipline;
            if (tokenizer.current().getType().equals(Token.Type.STRING)) {
                entryResults.setDiscipline(tokenizer.current().getToken());
                tokenizer.next();
                // Deal with the space and the subject level.
                if (checkSpaceString()) {
                    entryResults.setSubjectLevel(tokenizer.current().getToken());
                    tokenizer.next();
                } else throw new IllegalProductionException("Invalid Entry: No subject level specified.");
            }
        }else throw new IllegalProductionException("Invalid Entry: No discipline specified.");
    }

    /**
     * Adheres to the grammar rule:
     * <optionalEntry>   ::=  <WITH> <space> <string> | <WITH> <space> <string> <space> <additionalEntryAfterPerson>
     *                        | <IN> <space> <string> | <IN> <space> <string> <space> <additionalEntryAfterLocation
     *                        | <ON> <space> <days>   | <ON> <space> <days> <space> <additionalEntryAfterDays>
     */
    private void parseOptionalEntry() {
        // Deals with optional entry starting with WITH.
        if (checkIfPerson()) {
            // Deal with the space between person and additionalEntryAfterPerson.
            if (tokenizer.hasNext() && tokenizer.current().getType().equals(Token.Type.SPACE)) {
                tokenizer.next();
                // Deal with additionalEntryAfterPerson.
                if (tokenizer.hasNext()) {
                    parseAdditionalEntryAfterPerson();
                } else throw new IllegalProductionException("Invalid Entry: Space given after person but no additional entries given.");
            }
        }

        // Deals with optional entry starting with IN.
        else if (checkIfLocation()) {
            // Deal with the space between location and additionalEntryAfterLocation.
            if (tokenizer.hasNext() && tokenizer.current().getType().equals(Token.Type.SPACE)){
                tokenizer.next();
                // Deal with additionalEntryAfterLocation.
                if (tokenizer.hasNext()) {
                    parseAdditionalEntryAfterLocation();
                } else throw new IllegalProductionException("Invalid Entry: Space given after location but no additional entries given.");
            }
        }

        // Deals with optional entry starting with ON.
        else if (checkIfDays()) {
            // Deal with the space between days and additionalEntryAfterDays.
            if (tokenizer.hasNext() && tokenizer.current().getType().equals(Token.Type.SPACE)){
                tokenizer.next();
                // Deal with additionalEntryAfterDays.
                if (tokenizer.hasNext()) {
                    parseAdditionalEntryAfterDays();
                } else throw new IllegalProductionException("Invalid Entry: Space given after days but no additional entries given.");
            }

        } else throw new IllegalProductionException("Invalid Entry: Inappropriate optional entry given.");
    }



    /**
     * Adheres to the grammar rule:
     * <additionalEntryAfterPerson>   ::=  <IN> <space> <string> | <ON> <space> <days>
     *                                      |  <IN> <space> <string> <space> <ON> <space> <days>
     *                                      |  <ON> <space> <days> <space> <IN> <space> <string>
     */
    private void parseAdditionalEntryAfterPerson() {
        // Deals with additional entry starting with IN.
        if (checkIfLocation()) {
            // Deal with the space between location and ON.
            if (tokenizer.hasNext() && tokenizer.current().getType().equals(Token.Type.SPACE)) {
                tokenizer.next();
                // Deals the extra additional entry starting with ON.
                if (!(tokenizer.hasNext() && checkIfDays())) {
                    throw new IllegalProductionException("Invalid Entry: Space given after location but no days given.");
                }
            }
        }


        // Deals with additional entry starting with ON.
        else if (checkIfDays()) {
            // Deal with the space between days and IN.
            if (tokenizer.hasNext() && tokenizer.current().getType().equals(Token.Type.SPACE)) {
                tokenizer.next();
                // Deals the extra additional entry starting with IN.
                if (!(tokenizer.hasNext() && checkIfLocation())) {
                    throw new IllegalProductionException("Invalid Entry: Space given after days but no location given.");
                    }
                }
            }
    }


    /**
     * Adheres to the grammar rule:
     * <additionalEntryAfterLocation>   ::=  <WITH> <space> <string> | <ON> <space> <days>
     *                                      |  <WITH> <space> <string> <space> <ON> <space> <days>
     *                                      |  <ON> <space> <days> <space> <WITH> <space> <string>
     */
    private void parseAdditionalEntryAfterLocation(){
        // Deals with additional entry starting with WITH.
        if (checkIfPerson()) {
            // Deal with the space between person and ON.
            if (tokenizer.hasNext() && tokenizer.current().getType().equals(Token.Type.SPACE)) {
                tokenizer.next();
                // Deals the extra additional entry starting with ON.
                if (!(tokenizer.hasNext() && checkIfDays())) {
                    throw new IllegalProductionException("Invalid Entry: Space given after person but no days given.");
                }
            }
        }


        // Deals with additional entry starting with ON.
        else if (checkIfDays()) {
            // Deal with the space between days and WITH.
            if (tokenizer.hasNext() && tokenizer.current().getType().equals(Token.Type.SPACE)) {
                tokenizer.next();
                // Deals the extra additional entry starting with WITH.
                if (!(tokenizer.hasNext() && checkIfPerson())) {
                    throw new IllegalProductionException("Invalid Entry: Space given after days but no person given.");
                }
            }
        }
    }

    /**
     * Adheres to the grammar rule:
     * <additionalEntryAfterDays>   ::=  <WITH> <space> <string> | <IN> <space> <string>
     *                                  |  <WITH> <space> <string> <space> <IN> <space> <string>
     *                                  |  <IN> <space> <string> <space> <WITH> <space> <string>
     */
    private void parseAdditionalEntryAfterDays(){
        // Deals with additional entry starting with WITH.
        if (checkIfPerson()) {
            // Deal with the space between person and IN.
            if (tokenizer.hasNext() && tokenizer.current().getType().equals(Token.Type.SPACE)) {
                tokenizer.next();
                // Deals the extra additional entry starting with IN.
                if (!(tokenizer.hasNext() && checkIfLocation())) {
                    throw new IllegalProductionException("Invalid Entry: Space given after person but no location given.");
                }
            }
        }


        // Deals with additional entry starting with IN.
        else if (checkIfLocation()) {
            // Deal with the space between location and WITH.
            if (tokenizer.hasNext() && tokenizer.current().getType().equals(Token.Type.SPACE)) {
                tokenizer.next();
                // Deals the extra additional entry starting with WITH.
                if (!(tokenizer.hasNext() && checkIfPerson())) {
                    throw new IllegalProductionException("Invalid Entry: Space given after location but no person given.");
                }
            }
        }
    }

    /**
     * Adheres to the grammar rule:
     * <days>   ::=  <string> | <string> <comma> <space> <days>
     */
    private void parseDays(){
        // Deals with day.
        if (tokenizer.current().getType().equals(Token.Type.STRING)){
            entryResults.addDay(tokenizer.current().getToken());
            tokenizer.next();
            // Deals with comma after day.
            if (tokenizer.hasNext() && tokenizer.current().getType().equals(Token.Type.COMMA)){
                tokenizer.next();
                // Deals with space between comma and additional days.
                if (tokenizer.hasNext() && tokenizer.current().getType().equals(Token.Type.SPACE)){
                    tokenizer.next();
                    // Deals with the additional days.
                    if (tokenizer.hasNext()){
                        parseDays();
                    } else throw new IllegalProductionException("Invalid Entry: Comma given but no extra days specified.");
                } else throw new IllegalProductionException("Invalid Entry: Comma given but no extra days specified.");
            }
        } else throw new IllegalProductionException("Invalid Entry: Space given but no day specified.");
    }

    /**
     * Checks if there is a space then a string.
     * @return type: boolean.
     */
    private boolean checkSpaceString() {
        // Checks if there is a space.
        if (tokenizer.hasNext() && tokenizer.current().getType().equals(Token.Type.SPACE)) {
            tokenizer.next();
            // Checks if there is a string.
            return tokenizer.hasNext() && tokenizer.current().getType().equals(Token.Type.STRING);
        }
        return false;
    }


    /**
     * Checks if the next tokens are <IN> <space> <string>. If so deals with them appropriately and returns true.
     * @return type: boolean.
     */
    private boolean checkIfLocation(){
        // Deals with entry starting with IN.
        if (tokenizer.current().getType().equals(Token.Type.IN)) {
            tokenizer.next();
            // Deal with the space and the location.
            if (checkSpaceString()) {
                entryResults.setLocation(tokenizer.current().getToken());
                tokenizer.next();
                return true;
            } else throw new IllegalProductionException("Invalid Entry: IN given but no location specified.");
        }
        return false;
    }


    /**
     * Checks if the next tokens are <WITH> <space> <string>. If so deals with them appropriately and returns true.
     * @return type: boolean.
     */
    private boolean checkIfPerson(){
        // Deals with entry starting with WITH.
        if (tokenizer.current().getType().equals(Token.Type.WITH)) {
            tokenizer.next();
            // Deal with the space and the person.
            if (checkSpaceString()) {
                entryResults.setPerson(tokenizer.current().getToken());
                tokenizer.next();
                return true;
            } else throw new IllegalProductionException("Invalid Entry: WITH given but no person specified.");
        }
        return false;
    }

    /**
     * Checks if the next tokens are <ON> <space> <days>. If so deals with them appropriately and returns true.
     * @return type: boolean.
     */
    private boolean checkIfDays(){
        // Deals with entry starting with ON.
        if (tokenizer.current().getType().equals(Token.Type.ON)) {
            tokenizer.next();
            // Deal with the space between ON and days.
            if (tokenizer.hasNext() && tokenizer.current().getType().equals(Token.Type.SPACE)) {
                tokenizer.next();
                // Deal with days.
                if (tokenizer.hasNext()){
                    parseDays();
                    return true;
                } else throw new IllegalProductionException("Invalid Entry: ON given but no days specified.");

            } else throw new IllegalProductionException("Invalid Entry: ON given but no days specified.");
        }
        return false;
    }

}
