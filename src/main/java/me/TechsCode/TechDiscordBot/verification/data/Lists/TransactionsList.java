package me.TechsCode.TechDiscordBot.verification.data.Lists;

import me.TechsCode.TechDiscordBot.verification.data.Transaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class TransactionsList extends ArrayList<Transaction> {

    public TransactionsList(int initialCapacity) {
        super(initialCapacity);
    }

    public TransactionsList() {}

    public TransactionsList(Collection<? extends Transaction> c) {
        super(c);
    }

    public TransactionsList id(String id){
        return stream().filter(transaction -> transaction.getId().equalsIgnoreCase(id)).collect(Collectors.toCollection(TransactionsList::new));
    }

    public TransactionsList initiation_date(long date){
        return stream().filter(transaction -> transaction.getInitiation_date() == date).collect(Collectors.toCollection(TransactionsList::new));
    }

    public TransactionsList updated_date(long date){
        return stream().filter(transaction -> transaction.getUpdated_date() == date).collect(Collectors.toCollection(TransactionsList::new));
    }

    public TransactionsList priceCurrency(String priceCurrency){
        return stream().filter(transaction -> transaction.getPrice().getCurrency().equalsIgnoreCase(priceCurrency)).collect(Collectors.toCollection(TransactionsList::new));
    }

    public TransactionsList priceAmount(float priceAmount){
        return stream().filter(transaction -> transaction.getPrice().getAmount() == priceAmount).collect(Collectors.toCollection(TransactionsList::new));
    }

    public TransactionsList payerId(String id){
        return stream().filter(transaction -> transaction.getPayerInfo().getId().equalsIgnoreCase(id)).collect(Collectors.toCollection(TransactionsList::new));
    }

    public TransactionsList payerEmail(String email){
        return stream().filter(transaction -> transaction.getPayerInfo().getEmail().equalsIgnoreCase(email)).collect(Collectors.toCollection(TransactionsList::new));
    }

    public TransactionsList payerName(String name){
        return stream().filter(transaction -> transaction.getPayerInfo().getName().equalsIgnoreCase(name)).collect(Collectors.toCollection(TransactionsList::new));
    }

    public TransactionsList payerCountryCode(String countryCode){
        return stream().filter(transaction -> transaction.getPayerInfo().getCountryCode().equalsIgnoreCase(countryCode)).collect(Collectors.toCollection(TransactionsList::new));
    }

    public TransactionsList pluginName(String pluginName){
        return stream().filter(transaction -> transaction.getPlugin().getNameClean().equalsIgnoreCase(pluginName)).collect(Collectors.toCollection(TransactionsList::new));
    }

    public TransactionsList market(String market){
        return stream().filter(transaction -> transaction.getMarketplace().getMarket().equalsIgnoreCase(market)).collect(Collectors.toCollection(TransactionsList::new));
    }

    public TransactionsList userId(String pluginMarket){
        return stream().filter(transaction -> transaction.getMarketplace().getUserId().equalsIgnoreCase(pluginMarket)).collect(Collectors.toCollection(TransactionsList::new));
    }

}
