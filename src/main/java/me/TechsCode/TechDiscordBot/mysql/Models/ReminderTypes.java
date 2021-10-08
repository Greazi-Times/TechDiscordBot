package me.TechsCode.TechDiscordBot.mysql.Models;

public enum ReminderTypes {

    CHANNEL(0),
    DMs(1);

    private final int i;

    ReminderTypes(int i) {
        this.i = i;
    }

    public int getI() {
        return i;
    }
}
