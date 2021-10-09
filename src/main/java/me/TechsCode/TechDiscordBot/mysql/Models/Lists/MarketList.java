package me.TechsCode.TechDiscordBot.mysql.Models.Lists;

import me.TechsCode.TechDiscordBot.mysql.Models.DbMarket;
import me.TechsCode.TechDiscordBot.mysql.Models.Resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class MarketList extends ArrayList<DbMarket> {

    public MarketList(int initialCapacity) {
        super(initialCapacity);
    }

    public MarketList() {}

    public MarketList(Collection<? extends DbMarket> c) {
        super(c);
    }

    public MarketList id(int id){
        return stream().filter(market -> market.getId() == id).collect(Collectors.toCollection(MarketList::new));
    }

    public MarketList name(String name){
        return stream().filter(market -> market.getName().equalsIgnoreCase(name)).collect(Collectors.toCollection(MarketList::new));
    }

}