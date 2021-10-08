package me.TechsCode.TechDiscordBot.mysql.Models;

import me.TechsCode.TechDiscordBot.TechDiscordBot;

public class SubVerfication {

    private final int id, subMembersId, verificationId;

    public SubVerfication(int id, int subMemberId, int verificationId) {
        this.id = id;
        this.subMembersId = subMemberId;
        this.verificationId = verificationId;
    }

    public SubVerfication(DbMember subMember, Verfication verification) {
        this.id = 0;
        this.subMembersId = subMember.getId();
        this.verificationId = verification.getId();
    }

    public int getId() {
        return id;
    }

    public int getSubMembersId() {
        return subMembersId;
    }

    public DbMember getSubMember(){
        return TechDiscordBot.getStorage().retrieveMemberById(subMembersId);
    }

    public int getVerificationId() {
        return verificationId;
    }

    public Verfication getVerification(){
        return TechDiscordBot.getStorage().retrieveVerficationById(verificationId);
    }
}
