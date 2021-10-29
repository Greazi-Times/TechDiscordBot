package me.TechsCode.TechDiscordBot.module.cmds;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import me.TechsCode.TechDiscordBot.module.CommandModule;
import me.TechsCode.TechDiscordBot.objects.DefinedQuery;
import me.TechsCode.TechDiscordBot.objects.Query;
import me.TechsCode.TechDiscordBot.util.Plugin;
import me.TechsCode.TechDiscordBot.util.Roles;
import me.TechsCode.TechDiscordBot.util.TechEmbedBuilder;
import me.TechsCode.TechDiscordBot.verification.PaypalAPI;
import me.TechsCode.TechDiscordBot.verification.Verification;
import me.TechsCode.TechDiscordBot.verification.data.Lists.TransactionsList;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.privileges.CommandPrivilege;

import java.util.Comparator;
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
        String marketid = Objects.requireNonNull(e.getOption("market-id")).getAsString();



        TransactionsList transactions = TechDiscordBot.getPaypalAPI().checkTransaction(market, marketid);
        String plugins = transactions.stream().map(transaction -> transaction.getPlugin().getName()).collect(Collectors.joining(", "));


        if(transactions.isEmpty()){
            e.replyEmbeds(new TechEmbedBuilder("CHECK - ERROR")
                    .text("That user does not own any of Tech's plugins.")
                    .error().build()).setEphemeral(true).queue();
        } else {
            e.replyEmbeds(new TechEmbedBuilder("Check Information")
                    .text("Showing the information that came up from your search:")
                    .field("User ID: "+marketid,"["+marketid+"](https://www.spigotmc.org/members/"+marketid+")", true)
                    .field("Purchase Amount", "" /*TODO Add the methode that checks the purchased amount*/, true)
                    .field("Last Purchase", ""/*TODO Add the methode that shows the last purchase*/, true)
                    .field("Purchases", ""/*TODO Add the methode that get all the purchases*/, true)
                    .success().build()).setEphemeral(true).queue();
        }
    }
}
