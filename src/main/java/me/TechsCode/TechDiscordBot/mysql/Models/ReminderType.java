package me.TechsCode.TechDiscordBot.mysql.Models;

public enum ReminderType {

    CHANNEL(0),
    DMs(1);

    private final int index;

    ReminderType(int i) {
        this.index = i;
    }

    public int getIndex() {
        return index;
    }
}
