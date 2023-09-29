package com.bbw.pokemon.service;

import java.util.Arrays;
import java.util.List;

public class FightCommandWords {
    private List<String> validCommands = Arrays.asList("attack", "heal", "item");

    public boolean isCommand(String commandWord) {
        return validCommands.stream()
                .filter(c -> c.equals(commandWord))
                .count()>0;
    }

    public String showAll() {
        return String.join(" ", validCommands);
    }
}
