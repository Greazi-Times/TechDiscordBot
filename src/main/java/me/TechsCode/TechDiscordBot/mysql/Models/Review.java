package me.TechsCode.TechDiscordBot.mysql.Models;

import me.TechsCode.TechDiscordBot.TechDiscordBot;

public class Review {

    private final int id;
    private int memberId;
    private int reviewID;
    private int resourceId;
    private long date;

    public Review(int id, int memberId, int reviewID, int resourceId, long date) {
        this.id = id;
        this.memberId = memberId;
        this.reviewID = reviewID;
        this.resourceId = resourceId;
        this.date = date;
    }

    public Review(DbMember member, Resource resource, int reviewID, long date) {
        this.id = TechDiscordBot.getStorage().getAvailableId(TechDiscordBot.getStorage().REVIEWS_TABLE);
        this.memberId = member.getId();
        this.resourceId = resource.getId();
        this.reviewID = reviewID;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getMemberId() {
        return memberId;
    }

    public DbMember getMember(){
        return TechDiscordBot.getStorage().retrieveMemberById(memberId);
    }

    public long getDate() {
        return date;
    }

    public int getResourceId() {
        return resourceId;
    }

    public Resource getResource(){
        return TechDiscordBot.getStorage().retrieveResourceById(resourceId);
    }

    public void setMemberId(DbMember member){
        this.memberId = member.getId();
    }

    public void setResourceId(Resource resource){
        this.resourceId = resource.getId();
    }

    public void setReviewID(int reviewID){
        this.reviewID = reviewID;
    }

    public void setDate(long date){
        this.date = date;
    }

    public void save(){
        TechDiscordBot.getStorage().saveReview(this);
    }

    public void delete(){
        TechDiscordBot.getStorage().deleteReview(this);
    }

}
