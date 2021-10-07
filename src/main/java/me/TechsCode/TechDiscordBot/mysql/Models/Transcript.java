package me.TechsCode.TechDiscordBot.mysql.Models;

public class Transcript {

    private final int id;
    private final String value;

    public Transcript(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

}
