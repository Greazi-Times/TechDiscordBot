package me.TechsCode.TechDiscordBot.mysql.Models;

import me.TechsCode.TechDiscordBot.TechDiscordBot;

public class DbMarket {

    private final int id;
    private final String name;

    public DbMarket(int id, String Name) {
        this.id = id;
        this.name = Name;
    }

    public DbMarket(String Name) {
        this.id = TechDiscordBot.getStorage().getAvailableId(TechDiscordBot.getStorage().MARKETS_TABLE);
        this.name = Name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void save(){
        TechDiscordBot.getStorage().saveMarket(this);
    }

    public void delete(){
        TechDiscordBot.getStorage().deleteMarket(this);
    }

}
