package me.TechsCode.TechDiscordBot.mysql.Models;

public class Review {

    private final int id, memberId, resourceId;
    private final long date;

    public Review(int id, int memberId, int resourceId, long date) {
        this.id = id;
        this.memberId = memberId;
        this.resourceId = resourceId;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getMemberId() {
        return memberId;
    }

    public long getDate() {
        return date;
    }

    public int getResourceId() {
        return resourceId;
    }
}
