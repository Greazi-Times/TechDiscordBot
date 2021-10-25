package me.TechsCode.TechDiscordBot.mysql.Models;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import me.TechsCode.TechDiscordBot.mysql.Models.DbUpdate;
import me.TechsCode.TechDiscordBot.mysql.Models.Lists.UpdateList;
import net.dv8tion.jda.api.entities.Role;

import java.util.Set;

public class Resource {

    private final int id;
    private final String name;
    private final int spigotId;
    private final long discordRoleId;

    public Resource(int id, String Name, int SpigotId, long discordRoleId) {
        this.id = id;
        this.name = Name;
        this.spigotId = SpigotId;
        this.discordRoleId= discordRoleId;
    }

    public Resource(String Name, int SpigotId, long discordRoleId) {
        this.id = 0;
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
