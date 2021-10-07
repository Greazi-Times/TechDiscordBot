package me.TechsCode.TechDiscordBot.mysql.Models;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
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

    public Member getDiscordMember() {
        return TechDiscordBot.getGuild().getMemberById(discordID);
    }

    public String getDiscordId() {
        return discordID;
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
}
