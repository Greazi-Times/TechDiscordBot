package me.TechsCode.TechDiscordBot.mysql.Models;

import me.TechsCode.TechDiscordBot.TechDiscordBot;

public class DbUpdate {

    private final int id, resourceId;
    private final String name;
    private final long date, updateId;

    public DbUpdate(int id, int resourceId, String name, long date, long updateId) {
        this.id = id;
        this.resourceId = resourceId;
        this.name = name;
        this.date = date;
        this.updateId = updateId;
    }

    public DbUpdate(Resource resource, String name, long date, long updateId) {
        this.id = TechDiscordBot.getStorage().getAvailableId(TechDiscordBot.getStorage().UPDATES_TABLE);
        this.resourceId = resource.getId();
        this.name = name;
        this.date = date;
        this.updateId = updateId;
    }

    public int getId() {
        return id;
    }

    public int getResourceId() {
        return resourceId;
    }

    public String getName() {
        return name;
    }

    public long getUpdateId() {
        return updateId;
    }

    public long getDate() {
        return date;
    }

    public void save(){
        TechDiscordBot.getStorage().saveUpdate(this);
    }

    public Resource getResource(){
        return TechDiscordBot.getStorage().retrieveResourceById(resourceId);
    }

    public void delete(){
        TechDiscordBot.getStorage().deleteUpdate(this);
    }

}