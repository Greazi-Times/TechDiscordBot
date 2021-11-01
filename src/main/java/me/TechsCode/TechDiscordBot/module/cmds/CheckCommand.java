package me.TechsCode.TechDiscordBot.module.cmds;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import me.TechsCode.TechDiscordBot.module.CommandModule;
import me.TechsCode.TechDiscordBot.mysql.Models.DbVerification;
import me.TechsCode.TechDiscordBot.mysql.Models.Lists.VerificationPluginList;
import me.TechsCode.TechDiscordBot.mysql.Models.VerificationPlugin;
import me.TechsCode.TechDiscordBot.util.Emojis;
import me.TechsCode.TechDiscordBot.util.Roles;
import me.TechsCode.TechDiscordBot.util.TechEmbedBuilder;
import me.TechsCode.TechDiscordBot.verification.data.Lists.TransactionsList;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.privileges.CommandPrivilege;

import java.util.Objects;
import java.util.stream.Collectors;

public class CheckCommand extends CommandModule {

    public CheckCommand(TechDiscordBot bot) {
        super(bot);
    }

    @Override
    public String getName() {
        return "check";
    }

    @Override
    public String getDescription() {
        return "Check a Member's Purchases & Spigot Info.";
    }

    @Override
    public CommandPrivilege[] getCommandPrivileges() {
        return new CommandPrivilege[] { CommandPrivilege.enable(Roles.STAFF()) };
    }

    @Override
    public OptionData[] getOptions() {
        return new OptionData[] {
                new OptionData(OptionType.STRING, "market", "Buyers marketplace", true)
                        .addChoice("spigot", "spigotmc")
                        .addChoice("mc-market", "mcmarket")
                        .addChoice("polymart", "polymart"),
                new OptionData(OptionType.INTEGER, "market-id", "Member's spigot id.", true)
        };
    }

    @Override
    public int getCooldown() {
        return 5;
    }

    @Override
    public void onCommand(TextChannel channel, Member m, SlashCommandEvent e) {
        String market = Objects.requireNonNull(e.getOption("market")).getAsString();
        String marketId = Objects.requireNonNull(e.getOption("market-id")).getAsString();

        DbVerification verification = TechDiscordBot.getStorage().retrieveVerificationById(Integer.parseInt(marketId));
        if(verification == null){
            TransactionsList transactions = TechDiscordBot.getPaypalAPI().checkTransaction(market, marketId);
            String plugins = transactions.stream().map(transaction -> transaction.getPlugin().getName()).collect(Collectors.joining(", "));

            if(transactions.isEmpty()) {
                e.replyEmbeds(new TechEmbedBuilder("CHECK - ERROR")
                        .text("That user does not own any of Tech's plugins.")
                        .error().build()).setEphemeral(true).queue();
            }
            e.replyEmbeds(new TechEmbedBuilder("Check Information")
                    .text("This user hasn't verified them self but has purchased the following:")
                    .field("User ID: ", marketUserLink(market, marketId), true)
                    .field("Purchases", plugins, true)
                    .build()).setEphemeral(true).queue();
        }
        else {
            String purchases = null;
            String lastPurchase = null;
            int lastPurchaseTime = 0;
            int purchaseAmount = 0;

            VerificationPluginList boughtPlugins = TechDiscordBot.getStorage().retrieveVerificationPlugins(verification);

            for(VerificationPlugin plugin : boughtPlugins){
                String resource = plugin.getResource().toString();
                float price = plugin.getPrice();
                int time = Integer.parseInt(plugin.getPurchaseData());
                String emoji = getEmoji(resource);
                purchaseAmount = purchaseAmount + 1;

                if(lastPurchaseTime == 0){
                    lastPurchase = emoji + " <t:" + time + ">";
                } if (lastPurchaseTime != 0 && lastPurchaseTime < time){
                    lastPurchase = emoji + " <t:" + time + ">";
                }

                purchases = purchases + "- " + emoji + " " + resource + " for €" + price + " EUR on <t:" + time + ">.\n";
            }

            e.replyEmbeds(new TechEmbedBuilder("Check Information")
                    .text("Showing the information that came up from your search:")
                    .field("User ID: "+marketId, marketUserLink(market, marketId), true)
                    .field("Purchase Amount", purchaseAmount + "**/**7 purchased", true)
                    .field("Last Purchase", lastPurchase, true)
                    .field("Purchases", purchases, true)
                    .success().build()).setEphemeral(true).queue();
        }
    }

    private String marketUserLink(String market, String marketId){
        switch (market) {
            case "spigotmc":
                return "["+marketId+"](https://www.spigotmc.org/members/"+marketId+")";
            case "mcmarket":
                return "["+marketId+"](https://www.mc-market.org/members/"+marketId+")";
            case "polymart":
                return "["+marketId+"](https://polymart.org/user/"+marketId+")";
            default:
                return marketId;
        }
    }

    private String getEmoji(String resource){
        switch (resource){
            case "Ultra Permissions":
                return Emojis.ULTRA_PERMISSIONS();
            case "Ultra Customizer":
                return Emojis.ULTRA_CUSTOMIZER();
            case "Ultra Economy":
                return Emojis.ULTRA_ECONOMY();
            case "Ultra Punishments":
                return Emojis.ULTRA_PUNISHMENTS();
            case "Ultra Regions":
                return Emojis.ULTRA_REGIONS();
            case "Ultra Scoreboards":
                return Emojis.ULTRA_SCOREBOARDS();
            case "Insane Shops":
                return Emojis.INSANE_SHOPS();
        }
        return "";
    }
}
