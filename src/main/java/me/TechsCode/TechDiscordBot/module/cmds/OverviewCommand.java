package me.TechsCode.TechDiscordBot.module.cmds;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import me.TechsCode.TechDiscordBot.module.CommandModule;
import me.TechsCode.TechDiscordBot.objects.DefinedQuery;
import me.TechsCode.TechDiscordBot.objects.Query;
import me.TechsCode.TechDiscordBot.util.Plugin;
import me.TechsCode.TechDiscordBot.util.TechEmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.privileges.CommandPrivilege;

import java.awt.*;
import java.util.Arrays;

public class OverviewCommand extends CommandModule {

    private final DefinedQuery<Role> STAFF_ROLE = new DefinedQuery<Role>() {
        @Override
        protected Query<Role> newQuery() { return bot.getRoles("Staff"); }
    };
    private final DefinedQuery<TextChannel> OVERVIEW_CHANNEL = new DefinedQuery<TextChannel>() {
        @Override
        protected Query<TextChannel> newQuery() { return bot.getChannels("\uD83D\uDCCC︱overview"); }
    };

    public OverviewCommand(TechDiscordBot bot) {
        super(bot);
    }

    @Override
    public String getName() {
        return "overview";
    }

    @Override
    public String getDescription() {
        return "Resend the #overview messages.";
    }

    @Override
    public CommandPrivilege[] getCommandPrivileges() {
        return new CommandPrivilege[] { CommandPrivilege.enable(STAFF_ROLE.query().first()) };
    }

    @Override
    public OptionData[] getOptions() {
        return new OptionData[0];
    }

    @Override
    public int getCooldown() {
        return 10;
    }

    @Override
    public void onCommand(TextChannel channel, Member m, SlashCommandEvent e) {
        e.reply("Sending messages...").queue();

        showAll();
    }

    public void showAll() {
        showInfo();
        showFeedback();
        //showRules();
        showPlugins();
        //showVerify();
        showPatreon();
        showInvite();
    }

    public void showVerify() {
        new TechEmbedBuilder("Verify Yourself")
            .text("Due to the recent bot attack, you're now required to verify yourself, to do so, all you have to do is react to the message below!")
            .thumbnail("https://www.groovypost.com/wp-content/uploads/2016/11/500px-Icon_robot.svg_.png")
            .queue(OVERVIEW_CHANNEL.query().first(), msg -> msg.addReaction(bot.getEmotes("TechSupport").first()).complete());
    }

//    @SubscribeEvent
//    public void onReactAdd(GuildMessageReactionAddEvent e) {
//        if(e.getChannel() != OVERVIEW_CHANNEL.query().first() || e.getUser().isBot())
//            return;
//
//        Emote emote = bot.getEmotes("TechSupport").first();
//
//        if(e.getReaction().getReactionEmote().isEmote() && e.getReaction().getReactionEmote().getEmote() == emote) {
//            if(e.getMember().getRoles().stream().anyMatch(r -> r.getName().equals("Member")))
//                return;
//
//            e.getGuild().addRoleToMember(e.getMember(), bot.getRoles("Member").first()).queue();
//        }
//    }

    public void showInfo() {
        new TechEmbedBuilder("Tech's Plugin Support")
                .text("Welcome to **Tech's Plugin Support**. Here, not only can you get support for Tech's Plugins. You can talk and socialize with people too! You can also get help with other plugins!\n\nIf you're new here and need help with one or more of Tech's Plugins, you can verify in <#695493411117072425> to get support. Once you do, you will get access to the specified support channels.\n\nIf you are already verified and you have bought another plugin, simply wait for the bot to give you the role *(could take up to 15 minutes, possibly longer)*.")
                .thumbnail("https://i.imgur.com/SfFEnoU.png")
                .queue(OVERVIEW_CHANNEL.query().first());
    }

    public void showFeedback() {
        new TechEmbedBuilder("Feedback")
                .text("Would you like to suggest a feature or report a bug for Tech's Plugins? You can do so by clicking here: https://feedback.techscode.com")
                .thumbnail("https://i.imgur.com/nzfiUTy.png")
                .queue(OVERVIEW_CHANNEL.query().first());
    }

    public void showInvite() {
        new TechEmbedBuilder()
                .text("**Oh, look!** There is an invite: https://discord.gg/3JuHDm8")
                .queue(OVERVIEW_CHANNEL.query().first());
    }

    public void showRules() {
        new TechEmbedBuilder("Rules")
                .text("Just use common sense. Also, do not mention people for absolutely no reason.\n\nIf a staff member gives you a warning, kick, or ban, do not argue about it, and simply don't do the thing you were warned about again! Staff decide what they think is right or wrong.\n\n**The staff's decisions are final.**")
                .thumbnail("https://static.thenounproject.com/png/358077-200.png")
                .queue(OVERVIEW_CHANNEL.query().first());
    }

    public void showPlugins() {
        Arrays.stream(Plugin.values()).forEach(plugin -> new TechEmbedBuilder(plugin.getRoleName())
                .text((plugin.getDescription().replace(" (", ". (") + (plugin.getDescription().endsWith(".") || plugin.getDescription().endsWith(")") ? "" : ".")))
                .field("Download Links", plugin.getPluginMarketplace().toString(), true)
                .field("Wiki", plugin.getWiki(), true)
                .color(plugin.getColor())
                .thumbnail(plugin.getResourceLogo())
                .queue(OVERVIEW_CHANNEL.query().first()));
    }

    public void showPatreon() {
        new TechEmbedBuilder("TechsCode Patreon")
                .text("Join our Patreon **program today** and get __exclusive__ and great rewards! \n\n http://patreon.techscode.com")
                .color(Color.decode("#f96854"))
                .thumbnail("https://techscode.com/patreon.gif")
                .queue(OVERVIEW_CHANNEL.query().first());
    }
}
