package me.TechsCode.TechDiscordBot.mysql.Models;

public class DbMarket {

    private final int id;
    private final String name;

    public DbMarket(int id, String Name) {
        this.id = id;
        this.name = Name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
