package me.TechsCode.TechDiscordBot.mysql.Models.Lists;

import me.TechsCode.TechDiscordBot.mysql.Models.*;
import me.TechsCode.TechDiscordBot.verification.Verification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class VerficationPluginList extends ArrayList<VerficationPlugin> {

    public VerficationPluginList(int initialCapacity) {
        super(initialCapacity);
    }

    public VerficationPluginList() {}

    public VerficationPluginList(Collection<? extends VerficationPlugin> c) {
        super(c);
    }

    public VerficationPluginList id(int id){
        return stream().filter(verficationPlugin -> verficationPlugin.getId() == id).collect(Collectors.toCollection(VerficationPluginList::new));
    }

    public VerficationPluginList marketId(int marketId){
        return stream().filter(verficationPlugin -> verficationPlugin.getMarketId() == marketId).collect(Collectors.toCollection(VerficationPluginList::new));
    }

    public VerficationPluginList market(DbMarket market){
        return stream().filter(verficationPlugin -> verficationPlugin.getMarket().equals(market)).collect(Collectors.toCollection(VerficationPluginList::new));
    }

    public VerficationPluginList verificationId(int verificationId){
        return stream().filter(verficationPlugin -> verficationPlugin.getVerificationId() == verificationId).collect(Collectors.toCollection(VerficationPluginList::new));
    }

    public VerficationPluginList verification(Verification verification){
        return stream().filter(verficationPlugin -> verficationPlugin.getVerification().equals(verification)).collect(Collectors.toCollection(VerficationPluginList::new));
    }

    public VerficationPluginList resourceId(int resourceId){
        return stream().filter(verficationPlugin -> verficationPlugin.getResourceId() == resourceId).collect(Collectors.toCollection(VerficationPluginList::new));
    }

    public VerficationPluginList resource(Resource resource){
        return stream().filter(verficationPlugin -> verficationPlugin.getResource().equals(resource)).collect(Collectors.toCollection(VerficationPluginList::new));
    }

    public VerficationPluginList transactionId(String transactionId){
        return stream().filter(verficationPlugin -> verficationPlugin.getTransactionId().equalsIgnoreCase(transactionId)).collect(Collectors.toCollection(VerficationPluginList::new));
    }

    public VerficationPluginList purchaseData(String purchaseData){
        return stream().filter(verficationPlugin -> verficationPlugin.getPurchaseData().equalsIgnoreCase(purchaseData)).collect(Collectors.toCollection(VerficationPluginList::new));
    }

    public VerficationPluginList reviewed(){
        return stream().filter(VerficationPlugin::isReviewed).collect(Collectors.toCollection(VerficationPluginList::new));
    }

}