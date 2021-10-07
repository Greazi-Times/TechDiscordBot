package me.TechsCode.TechDiscordBot.mysql.Models;

public class Resource {

    private final int id;
    private final String name;
    private final int spigotId;

    public Resource(int id, String Name, int SpigotId) {
        this.id = id;
        this.name = Name;
        this.spigotId = SpigotId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSpigotId() {
        return spigotId;
    }
}
