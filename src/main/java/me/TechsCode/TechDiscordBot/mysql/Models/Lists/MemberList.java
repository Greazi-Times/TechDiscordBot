package me.TechsCode.TechDiscordBot.mysql.Models.Lists;

import me.TechsCode.TechDiscordBot.mysql.Models.DbMember;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MemberList extends ArrayList<DbMember> {

    public MemberList(int initialCapacity) {
        super(initialCapacity);
    }

    public MemberList() {}

    public MemberList(Collection<? extends DbMember> c) {
        super(c);
    }

    public MemberList id(int id){
        return stream().filter(market -> market.getId() == id).collect(Collectors.toCollection(MemberList::new));
    }

    public MemberList discordId(String discordId){
        return stream().filter(member -> member.getDiscordId().equalsIgnoreCase(discordId)).collect(Collectors.toCollection(MemberList::new));
    }

    public MemberList name(String name){
        return stream().filter(member -> member.getName().equalsIgnoreCase(name)).collect(Collectors.toCollection(MemberList::new));
    }

    public MemberList joinedEquels(long joined){
        return stream().filter(member -> member.getJoined() == joined).collect(Collectors.toCollection(MemberList::new));
    }

    public MemberList joinedGreater(long joined){
        return stream().filter(member -> member.getJoined() >= joined).collect(Collectors.toCollection(MemberList::new));
    }

    public MemberList joinedLess(long joined){
        return stream().filter(member -> member.getJoined() <= joined).collect(Collectors.toCollection(MemberList::new));
    }

    public MemberList staff(){
        return stream().filter(DbMember::isStaff).collect(Collectors.toCollection(MemberList::new));
    }

}