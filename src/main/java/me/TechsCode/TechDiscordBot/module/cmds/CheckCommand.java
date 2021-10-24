package me.TechsCode.TechDiscordBot.module.cmds;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import me.TechsCode.TechDiscordBot.module.CommandModule;
import me.TechsCode.TechDiscordBot.objects.DefinedQuery;
import me.TechsCode.TechDiscordBot.objects.Query;
import me.TechsCode.TechDiscordBot.util.Plugin;
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

public class CheckCommand extends CommandModule {

    private final DefinedQuery<Role> STAFF_ROLE = new DefinedQuery<Role>() {
        @Override
        protected Query<Role> newQuery() { return bot.getRoles("Staff"); }
    };

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
        return new CommandPrivilege[] { CommandPrivilege.enable(STAFF_ROLE.query().first()) };
    }

    @Override
    public OptionData[] getOptions() {
        return new OptionData[] {
                new OptionData(OptionType.STRING, "market", "Buyers marketplace", true)
                        .addChoice("spigot", "spigot")
                        .addChoice("mc-market", "mc-market")
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

        TransactionsList check = TechDiscordBot.getPaypalAPI().checkTransaction(market, marketid);






        //TODO Spigot Check Command
//        Verification verification = spigotId == null ? TechDiscordBot.getStorage().retrieveMemberByDiscordId(member.getUser().getId()).getVerification() : TechDiscordBot.getStorage().retrieveVerificationWithSpigot(spigotId);
//
//        if(verification == null) {
//            e.replyEmbeds(
//                new TechEmbedBuilder((spigotId != null ? spigotId : member.getEffectiveName()) + " Is Not Verified!")
//                    .text((spigotId != null ? spigotId : member.getAsMention()) + " has not verified themselves!")
//                    .error()
//                    .build()
//            ).queue();
//            return;
//        }
//
//        if(verification.getDiscordId().equals(m.getId()) && !canView)
//            canView = true;
//
//        if(member == null)
//            member = bot.getMember(verification.getDiscordId());

        //TODO Setup new system
        //PurchasesList purchases = TechDiscordBot.getSpigotAPI().getSpigotPurchases().userId(verification.getUserId());
//        PurchasesList purchases = null;

//        if (member == null || purchases == null || purchases.size() == 0) {
//            e.replyEmbeds(
//                new TechEmbedBuilder((spigotId != null ? spigotId : member.getEffectiveName()) + "'s Purchases")
//                    .error()
//                    .text((member != null ? spigotId : member.getAsMention()) + " has not bought of any Tech's Resources!")
//                    .build()
//            ).queue();
//            return;
//        }
//
//        Purchase purchase = purchases.stream().sorted(Comparator.comparingLong(p -> p.getTime().getUnixTime())).skip(purchases.size() - 1).findFirst().orElse(null);
//        if (purchase == null)
//            return;

//        String date = purchase.getTime().getHumanTime();
        //TODO Setup new system
        //boolean hasBoughtAll = TechDiscordBot.getSpigotAPI().getSpigotResources().premium().size() == purchases.size();
        boolean hasBoughtAll = false;

        StringBuilder sb = new StringBuilder();

//        for (Purchase p : purchases) {
//            sb.append("- ").append(Plugin.fromId(p.getResource().getId()).getEmoji().getAsMention()).append(" ").append(p.getResource().getName()).append(" ").append(!p.getCost().isPresent() ? "as a Gift/Free" : "for " + p.getCost().get().getValue() + p.getCost().get().getCurrency()).append(" on").append((p.getTime().getHumanTime() != null ? " " + p.getTime().getHumanTime() : " Unknown (*too early to calculate*)")).append("\n ");
//        }

//        String purchasesString = sb.toString();
//        e.replyEmbeds(
//            new TechEmbedBuilder(member.getEffectiveName())
//                .success()
//                .thumbnail(purchase.getUser().getAvatar())
//                .text("Showing " + member.getAsMention() + "'s Spigot Information.")
//                .field("Username / ID", "[" + purchase.getUser().getUsername() + "." + purchase.getUser().getUserId() + "](https://www.spigotmc.org/members/" + purchase.getUser().getUsername().toLowerCase() + "." + purchase.getUser().getUserId() + ")", true)
//                .field("Purchases Amount", hasBoughtAll ? " **All** " + purchases.size() + " plugins purchased!" : purchases.size() + "**/**" + /*TODO Setup new system TechDiscordBot.getSpigotAPI().getSpigotResources().premium().size() +*/ " purchased.", true)
//                .field("Last Purchase", Plugin.fromId(purchase.getResource().getId()).getEmoji().getAsMention() + " " + (date != null ? date + ".": "Unknown\n*or cannot calculate*."), true)
//                .field("Purchases", purchasesString.substring(0, purchasesString.length() - 2) + ".", false)
//                .build()
//        ).queue();
    }
}
