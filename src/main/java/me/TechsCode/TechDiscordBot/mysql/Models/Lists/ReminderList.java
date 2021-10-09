package me.TechsCode.TechDiscordBot.mysql.Models.Lists;

import me.TechsCode.TechDiscordBot.mysql.Models.DbMember;
import me.TechsCode.TechDiscordBot.mysql.Models.Reminder;
import me.TechsCode.TechDiscordBot.mysql.Models.ReminderType;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class ReminderList extends ArrayList<Reminder> {

    public ReminderList(int initialCapacity) {
        super(initialCapacity);
    }

    public ReminderList() {}

    public ReminderList(Collection<? extends Reminder> c) {
        super(c);
    }

    public ReminderList id(int id){
        return stream().filter(reminder -> reminder.getId() == id).collect(Collectors.toCollection(ReminderList::new));
    }

    public ReminderList memberId(int memberId){
        return stream().filter(reminder -> reminder.getMemberId() == memberId).collect(Collectors.toCollection(ReminderList::new));
    }

    public ReminderList member(DbMember member){
        return stream().filter(reminder -> reminder.getMember().equals(member)).collect(Collectors.toCollection(ReminderList::new));
    }

    public ReminderList channelId(String channelId){
        return stream().filter(reminder -> reminder.getChannelId().equals(channelId)).collect(Collectors.toCollection(ReminderList::new));
    }

    public ReminderList channel(TextChannel channel){
        return stream().filter(reminder -> reminder.getChannel().equals(channel)).collect(Collectors.toCollection(ReminderList::new));
    }

    public ReminderList typeIndex(int typeIndex){
        return stream().filter(reminder -> reminder.getType().getIndex() == typeIndex).collect(Collectors.toCollection(ReminderList::new));
    }

    public ReminderList type(ReminderType type){
        return stream().filter(reminder -> reminder.getType().equals(type)).collect(Collectors.toCollection(ReminderList::new));
    }

    public ReminderList timeEquels(long time){
        return stream().filter(review -> review.getTime() == time).collect(Collectors.toCollection(ReminderList::new));
    }

    public ReminderList timeGreater(long time){
        return stream().filter(review -> review.getTime() >= time).collect(Collectors.toCollection(ReminderList::new));
    }

    public ReminderList timeLess(long time){
        return stream().filter(review -> review.getTime() <= time).collect(Collectors.toCollection(ReminderList::new));
    }

}