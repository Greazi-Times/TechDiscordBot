package me.TechsCode.TechDiscordBot.mysql.Models;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import me.TechsCode.TechDiscordBot.spigotmc.data.Resource;
import me.TechsCode.TechDiscordBot.spigotmc.data.Time;

import java.util.Objects;

public class DbUpdate {

    private final int id, resourceId;
    private final String version;
    private final long date;

    public DbUpdate(int id, int resourceId, String version, long date) {
        this.id = id;
        this.resourceId = resourceId;
        this.version = version;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getResourceId() {
        return resourceId;
    }

    public String getVersion() {
        return version;
    }

    public long getDate() {
        return date;
    }

}