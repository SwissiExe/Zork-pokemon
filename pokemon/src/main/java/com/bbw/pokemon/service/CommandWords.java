package com.bbw.pokemon.service;

import java.util.Arrays;
import java.util.List;

public class CommandWords {
    private List<String> validCommands = Arrays.asList("go", "quit", "help","item","removeitem", "attack", "map", "items", "select", "pinfo", "bag", "location");

    public boolean isCommand(String commandWord) {
        return validCommands.stream()
                .filter(c -> c.equals(commandWord))
                .count()>0;
    }

    public String showAll() {
        return String.join(" ", validCommands);
    }
}
