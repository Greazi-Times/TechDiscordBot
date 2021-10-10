package me.TechsCode.TechDiscordBot.mysql.Models.Lists;

import me.TechsCode.TechDiscordBot.mysql.Models.DbUpdate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class UpdateList extends ArrayList<DbUpdate> {

    public UpdateList(int initialCapacity) {
        super(initialCapacity);
    }

    public UpdateList() {}

    public UpdateList(Collection<? extends DbUpdate> c) {
        super(c);
    }

    public UpdateList id(int id){
        return stream().filter(update -> update.getId() == id).collect(Collectors.toCollection(UpdateList::new));
    }

    public UpdateList resourceId(int resourceId){
        return stream().filter(update -> update.getResourceId() == resourceId).collect(Collectors.toCollection(UpdateList::new));
    }

    public UpdateList name(String name){
        return stream().filter(update -> update.getName().equalsIgnoreCase(name)).collect(Collectors.toCollection(UpdateList::new));
    }

}