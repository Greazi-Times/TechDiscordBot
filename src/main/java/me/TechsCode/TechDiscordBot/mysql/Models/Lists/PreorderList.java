package me.TechsCode.TechDiscordBot.mysql.Models.Lists;

import me.TechsCode.TechDiscordBot.mysql.Models.DbMarket;
import me.TechsCode.TechDiscordBot.mysql.Models.DbPreorder;
import org.apache.xpath.operations.Bool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class PreorderList extends ArrayList<DbPreorder> {

    public PreorderList(int initialCapacity) {
        super(initialCapacity);
    }

    public PreorderList() {}

    public PreorderList(Collection<? extends DbPreorder> c) {
        super(c);
    }

    public PreorderList id(int id){
        return stream().filter(preorder -> preorder.getId() == id).collect(Collectors.toCollection(PreorderList::new));
    }

    public PreorderList email(String email){
        return stream().filter(preorder -> preorder.getEmail().equalsIgnoreCase(email)).collect(Collectors.toCollection(PreorderList::new));
    }

    public PreorderList discordId(long discordId){
        return stream().filter(preorder -> preorder.getDiscordId() == discordId).collect(Collectors.toCollection(PreorderList::new));
    }

    public PreorderList discordName(String discordName){
        return stream().filter(preorder -> preorder.getDiscordName().equalsIgnoreCase(discordName)).collect(Collectors.toCollection(PreorderList::new));
    }

    public PreorderList isPatreon(Boolean isPatreon){
        return stream().filter(preorder -> preorder.isPatreon() == isPatreon).collect(Collectors.toCollection(PreorderList::new));
    }

}