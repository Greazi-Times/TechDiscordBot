package me.TechsCode.TechDiscordBot.mysql.Models.Lists;

import me.TechsCode.TechDiscordBot.mysql.Models.*;
import me.TechsCode.TechDiscordBot.verification.Verification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class VerificationPluginList extends ArrayList<VerificationPlugin> {

    public VerificationPluginList(int initialCapacity) {
        super(initialCapacity);
    }

    public VerificationPluginList() {}

    public VerificationPluginList(Collection<? extends VerificationPlugin> c) {
        super(c);
    }

    public VerificationPluginList id(int id){
        return stream().filter(VerificationPlugin -> VerificationPlugin.getId() == id).collect(Collectors.toCollection(VerificationPluginList::new));
    }

    public VerificationPluginList marketId(int marketId){
        return stream().filter(VerificationPlugin -> VerificationPlugin.getMarketId() == marketId).collect(Collectors.toCollection(VerificationPluginList::new));
    }

    public VerificationPluginList market(DbMarket market){
        return stream().filter(VerificationPlugin -> VerificationPlugin.getMarket().equals(market)).collect(Collectors.toCollection(VerificationPluginList::new));
    }

    public VerificationPluginList verificationId(int verificationId){
        return stream().filter(VerificationPlugin -> VerificationPlugin.getVerificationId() == verificationId).collect(Collectors.toCollection(VerificationPluginList::new));
    }

    public VerificationPluginList verification(Verification verification){
        return stream().filter(VerificationPlugin -> VerificationPlugin.getVerification().equals(verification)).collect(Collectors.toCollection(VerificationPluginList::new));
    }

    public VerificationPluginList resourceId(int resourceId){
        return stream().filter(VerificationPlugin -> VerificationPlugin.getResourceId() == resourceId).collect(Collectors.toCollection(VerificationPluginList::new));
    }

    public VerificationPluginList resource(Resource resource){
        return stream().filter(VerificationPlugin -> VerificationPlugin.getResource().equals(resource)).collect(Collectors.toCollection(VerificationPluginList::new));
    }

    public VerificationPluginList transactionId(String transactionId){
        return stream().filter(VerificationPlugin -> VerificationPlugin.getTransactionId().equalsIgnoreCase(transactionId)).collect(Collectors.toCollection(VerificationPluginList::new));
    }

    public VerificationPluginList purchaseData(String purchaseData){
        return stream().filter(VerificationPlugin -> VerificationPlugin.getPurchaseData().equalsIgnoreCase(purchaseData)).collect(Collectors.toCollection(VerificationPluginList::new));
    }

    public VerificationPluginList reviewed(){
        return stream().filter(VerificationPlugin::isReviewed).collect(Collectors.toCollection(VerificationPluginList::new));
    }

}