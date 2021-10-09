package me.TechsCode.TechDiscordBot.mysql.Models.Lists;

import me.TechsCode.TechDiscordBot.mysql.Models.DbMember;
import me.TechsCode.TechDiscordBot.mysql.Models.Punishment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class PunishmentList extends ArrayList<Punishment> {

    public PunishmentList(int initialCapacity) {
        super(initialCapacity);
    }

    public PunishmentList() {}

    public PunishmentList(Collection<? extends Punishment> c) {
        super(c);
    }

    public PunishmentList id(int id){
        return stream().filter(punishment -> punishment.getId() == id).collect(Collectors.toCollection(PunishmentList::new));
    }

    public PunishmentList member(DbMember member){
        return stream().filter(punishment -> punishment.getMember().equals(member)).collect(Collectors.toCollection(PunishmentList::new));
    }

    public PunishmentList punisher(DbMember member){
        return stream().filter(punishment -> punishment.getPunisher().equals(member)).collect(Collectors.toCollection(PunishmentList::new));
    }

    public PunishmentList type(String type){
        return stream().filter(punishment -> punishment.getType().equals(type)).collect(Collectors.toCollection(PunishmentList::new));
    }

    public PunishmentList reason(String reason){
        return stream().filter(punishment -> punishment.getReason().equals(reason)).collect(Collectors.toCollection(PunishmentList::new));
    }

    public PunishmentList dateEquels(long date){
        return stream().filter(punishment -> punishment.getDate() == date).collect(Collectors.toCollection(PunishmentList::new));
    }

    public PunishmentList dateGreater(long date){
        return stream().filter(punishment -> punishment.getDate() >= date).collect(Collectors.toCollection(PunishmentList::new));
    }

    public PunishmentList dateLess(long date){
        return stream().filter(punishment -> punishment.getDate() <= date).collect(Collectors.toCollection(PunishmentList::new));
    }

    public PunishmentList expiredEquels(long expired){
        return stream().filter(punishment -> punishment.getExpired() == expired).collect(Collectors.toCollection(PunishmentList::new));
    }

    public PunishmentList expiredGreater(long expired){
        return stream().filter(punishment -> punishment.getExpired() >= expired).collect(Collectors.toCollection(PunishmentList::new));
    }

    public PunishmentList expiredLess(long expired){
        return stream().filter(punishment -> punishment.getExpired() <= expired).collect(Collectors.toCollection(PunishmentList::new));
    }

}