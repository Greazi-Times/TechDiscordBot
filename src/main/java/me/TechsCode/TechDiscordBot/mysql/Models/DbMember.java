package me.TechsCode.TechDiscordBot.mysql.Models;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import net.dv8tion.jda.api.entities.Member;

import java.util.Set;

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

    public void save(){
        TechDiscordBot.getStorage().saveMember(this);
    }

    public void delete(){
        TechDiscordBot.getStorage().deleteMember(this);
    }

    public Set<Review> getReviews(){
        return TechDiscordBot.getStorage().retrieveMemberReviews(this);
    }

    public Set<Punishment> getPunishments(){
        return TechDiscordBot.getStorage().retrieveMemberPunishments(this);
    }

    public VerficationQ getVerificationQ(){
        return TechDiscordBot.getStorage().retrieveMemberVerficationQ(this);
    }

    public Verfication getVerification(){
        return TechDiscordBot.getStorage().retrieveMemberVerfication(this);
    }

    public Set<Reminder> getReminders(){
        return TechDiscordBot.getStorage().retrieveMemberReminders(this);
    }

    public Set<SubVerfication> getSubVerifications(){
        return TechDiscordBot.getStorage().retrieveMemberSubVerfications(this);
    }
}
