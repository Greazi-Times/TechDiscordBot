package me.TechsCode.TechDiscordBot.mysql.Models;

public class SubVerfication {

    private final int id, subMembersId, verificationId;

    public SubVerfication(int id, int subMembersId, int verificationId) {
        this.id = id;
        this.subMembersId = subMembersId;
        this.verificationId = verificationId;
    }

    public int getId() {
        return id;
    }

    public int getSubMembersId() {
        return subMembersId;
    }

    public int getVerificationId() {
        return verificationId;
    }
}
