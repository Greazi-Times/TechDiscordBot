package me.TechsCode.TechDiscordBot.mysql.Models;

public class ResourceUpdate {

    private final int id, resourceId;
    private final String version;
    private final long date;

    public ResourceUpdate(int id, int ResourceId, String Version, long Date) {
        this.id = id;
        this.resourceId = ResourceId;
        this.version = Version;
        this.date = Date;
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
