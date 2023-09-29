package com.bbw.pokemon;

public class FightCommands {
    private String commandWord;
    private String secondWord;

    public FightCommands(String FightCommands) {
        this(FightCommands, null);
    }
    public FightCommands(String FightCommandsWords, String secondWord) {
        this.commandWord = FightCommandsWords;
        this.secondWord = secondWord;
    }

    public String getCommandWord() {
        return commandWord;
    }
    public String getSecondWord() {
        return secondWord;
    }
    public boolean isUnknown() {
        return (commandWord == null);
    }
    public boolean hasSecondWord() {
        return (secondWord != null);
    }
}
