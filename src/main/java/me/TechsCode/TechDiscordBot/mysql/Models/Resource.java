package me.TechsCode.TechDiscordBot.mysql.Models;

import me.TechsCode.TechDiscordBot.TechDiscordBot;

import java.util.Set;

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

    public void save(){
        TechDiscordBot.getStorage().saveResource(this);
    }

    public void delete(){
        TechDiscordBot.getStorage().deleteResource(this);
    }

    public Set<DbUpdate> getUpdates(){
        return TechDiscordBot.getStorage().retrieveResourceUpdates(this);
    }

    public void addUpdate(String version, long date){
        TechDiscordBot.getStorage().saveUpdate(new DbUpdate(this, version, date));
    }

}
