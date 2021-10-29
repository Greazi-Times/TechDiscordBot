package me.TechsCode.TechDiscordBot.mysql.Models;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import me.TechsCode.TechDiscordBot.mysql.Models.Lists.UpdateList;
import net.dv8tion.jda.api.entities.Role;

public class Resource {

    private final int id;
    private int spigotId;
    private String name;
    private long discordRoleId;

    public Resource(int id, String Name, int SpigotId, long discordRoleId) {
        this.id = id;
        this.name = Name;
        this.spigotId = SpigotId;
        this.discordRoleId= discordRoleId;
    }

    public Resource(String Name, int SpigotId, long discordRoleId) {
        this.id = TechDiscordBot.getStorage().getAvailableId(TechDiscordBot.getStorage().RESOURCES_TABLE);
        this.name = Name;
        this.spigotId = SpigotId;
        this.discordRoleId= discordRoleId;
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

    public long getDiscordRoleId() {
        return discordRoleId;
    }

    public Role getDiscordRole(){
        return TechDiscordBot.getJDA().getRoleById(discordRoleId);
    }

    public void setName(String Name){
        this.name = Name;
    }

    public void setSpigotId(int SpigotId){
        this.spigotId = SpigotId;
    }

    public void setDiscordRoleId(long discordRoleId){
        this.discordRoleId = discordRoleId;
    }

    public void save(){
        TechDiscordBot.getStorage().saveResource(this);
    }

    public void delete(){
        TechDiscordBot.getStorage().deleteResource(this);
    }

    public UpdateList getUpdates(){
        return TechDiscordBot.getStorage().retrieveResourceUpdates(this);
    }

    public void addUpdate(String version, long date, long updateId){
        TechDiscordBot.getStorage().saveUpdate(new DbUpdate(this, version, date, updateId));
    }

}
