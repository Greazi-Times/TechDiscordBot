package me.TechsCode.TechDiscordBot.mysql.Models;

import me.TechsCode.TechDiscordBot.TechDiscordBot;

public class SubVerfication {

    private final int id, subMemberId, verificationId;

    public SubVerfication(int id, int subMemberId, int verificationId) {
        this.id = id;
        this.subMemberId = subMemberId;
        this.verificationId = verificationId;
    }

    public SubVerfication(DbMember subMember, DbVerfication verification) {
        this.id = 0;
        this.subMemberId = subMember.getId();
        this.verificationId = verification.getId();
    }

    public int getId() {
        return id;
    }

    public int getSubMemberId() {
        return subMemberId;
    }

    public DbMember getSubMember(){
        return TechDiscordBot.getStorage().retrieveMemberById(subMemberId);
    }

    public int getVerificationId() {
        return verificationId;
    }

    public DbVerfication getVerification(){
        return TechDiscordBot.getStorage().retrieveVerficationById(verificationId);
    }
}
