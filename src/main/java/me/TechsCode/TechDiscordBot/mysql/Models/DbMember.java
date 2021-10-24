package me.TechsCode.TechDiscordBot.mysql.Models;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import me.TechsCode.TechDiscordBot.mysql.Models.Lists.PunishmentList;
import me.TechsCode.TechDiscordBot.mysql.Models.Lists.ReminderList;
import me.TechsCode.TechDiscordBot.mysql.Models.Lists.ReviewList;
import me.TechsCode.TechDiscordBot.mysql.Models.Lists.SubVerificationList;
import me.TechsCode.TechDiscordBot.objects.RoleManager;
import net.dv8tion.jda.api.entities.Member;

public class DbMember {
    private final int id;
    private final String discordID, name;
    private final long joined;
    private final boolean staff;

    public DbMember(int id, String DiscordID, String Name, long Joined, boolean Staff) {
        this.id = id;
        this.discordID = DiscordID;
        this.name = Name;
        this.joined = Joined;
        this.staff = Staff;
    }

    public int getId() {
        return id;
    }

    public String getDiscordId() {
        return discordID;
    }

    public Member getDiscordMember() {
        return TechDiscordBot.getGuild().getMemberById(discordID);
    }

    public String getName() {
        return name;
    }

    public long getJoined() {
        return joined;
    }

    public boolean isStaff() {
        return staff;
    }

    public RoleManager getRoleManager(){
        return new RoleManager(this);
    }

    public void save(){
        TechDiscordBot.getStorage().saveMember(this);
    }

    public void delete(){
        TechDiscordBot.getStorage().deleteMember(this);
    }

    public ReviewList getReviews(){
        return TechDiscordBot.getStorage().retrieveMemberReviews(this);
    }

    public PunishmentList getPunishments(){
        return TechDiscordBot.getStorage().retrieveMemberPunishments(this);
    }

    public VerficationQ getVerificationQ(){
        return TechDiscordBot.getStorage().retrieveMemberVerficationQ(this);
    }

    public DbVerfication getVerification(){
        return TechDiscordBot.getStorage().retrieveMemberVerfication(this);
    }

    public DbVerfication newVerification(String PayerId){
        DbVerfication dbVerfication = new DbVerfication(this, PayerId);
        dbVerfication.save();
        return dbVerfication;
    }

    public ReminderList getReminders(){
        return TechDiscordBot.getStorage().retrieveMemberReminders(this);
    }

    public SubVerificationList getSubVerifications(){
        return TechDiscordBot.getStorage().retrieveMemberSubVerfications(this);
    }
}
