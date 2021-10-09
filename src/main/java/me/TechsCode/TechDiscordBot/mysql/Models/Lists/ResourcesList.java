package me.TechsCode.TechDiscordBot.mysql.Models.Lists;

import me.TechsCode.TechDiscordBot.mysql.Models.Resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class ResourcesList extends ArrayList<Resource> {

    public ResourcesList(int initialCapacity) {
        super(initialCapacity);
    }

    public ResourcesList() {}

    public ResourcesList(Collection<? extends Resource> c) {
        super(c);
    }

    public ResourcesList id(int id){
        return stream().filter(resource -> resource.getId() == id).collect(Collectors.toCollection(ResourcesList::new));
    }

    public ResourcesList spigotId(int spigotId){
        return stream().filter(resource -> resource.getSpigotId() == spigotId).collect(Collectors.toCollection(ResourcesList::new));
    }

    public ResourcesList name(String name){
        return stream().filter(resource -> resource.getName().equalsIgnoreCase(name)).collect(Collectors.toCollection(ResourcesList::new));
    }

}