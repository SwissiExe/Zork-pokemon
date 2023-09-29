package com.bbw.pokemon;

import com.bbw.pokemon.service.CommandWords;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Parser {

    private CommandWords validCommandWords;
    private InputStream inputStream;

    public Parser(InputStream inputStream) {
        this.inputStream = inputStream;
        this.validCommandWords = new CommandWords();
    }


    public Command getCommand() {
        String inputLine;
        String word1;
        String word2;

        System.out.print("> ");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.inputStream));
        try {
            inputLine = bufferedReader.readLine();

            String[] tokens = inputLine.split(" ");
            switch(tokens.length) {
                case 2:
                    if (validCommandWords.isCommand(tokens[0])) {
                        return new Command(tokens[0], tokens[1]);
                    } else {
                        // TODO: refactor this
                        return new Command(null, tokens[1]);
                    }
                case 1:
                    if (validCommandWords.isCommand(tokens[0])) {
                        return new Command(tokens[0]);
                    } else {
                        // TODO: refactor this and that
                        return new Command(null);
                    }
                default:
                    // TODO: handle this error with an exception and non
                    break;
            }
        } catch (java.io.IOException exc) {
            System.out.println("There was an error during reading: " + exc.getMessage());
        }
        // TODO: handle error
        return new Command(null);
    }

    public FightCommands getFightCommands() {
        String inputLine;
        String word1;
        String word2;
        System.out.println("attack {attackname}");
        System.out.println("heal, items");
        System.out.println(" ");
        System.out.print("> ");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.inputStream));
        try {
            inputLine = bufferedReader.readLine();

            String[] tokens = inputLine.split(" ");
            switch(tokens.length) {
                case 2:
                    if (validCommandWords.isCommand(tokens[0])) {
                        return new FightCommands(tokens[0], tokens[1]);
                    } else {
                        // TODO: refactor this
                        return new FightCommands(null, tokens[1]);
                    }
                case 1:
                    if (validCommandWords.isCommand(tokens[0])) {
                        return new FightCommands(tokens[0]);
                    } else {
                        // TODO: refactor this and that
                        return new FightCommands(null);
                    }
                default:
                    System.out.println("dieser befehl exestiert nid");
                    break;
            }
        } catch (java.io.IOException exc) {
            System.out.println("There was an error during reading: " + exc.getMessage());
        }
        // TODO: handle error
        return new FightCommands(null);
    }
    public String showCommands() {
        return validCommandWords.showAll();
    }
}
