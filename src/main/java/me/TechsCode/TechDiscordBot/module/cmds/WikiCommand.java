package me.TechsCode.TechDiscordBot.module.cmds;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import me.TechsCode.TechDiscordBot.module.CommandModule;
import me.TechsCode.TechDiscordBot.util.Emojis;
import me.TechsCode.TechDiscordBot.util.Plugin;
import me.TechsCode.TechDiscordBot.util.TechEmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.privileges.CommandPrivilege;

import java.util.List;
import java.util.stream.Collectors;

public class WikiCommand extends CommandModule {

    public WikiCommand(TechDiscordBot bot) {
        super(bot);
    }

    @Override
    public String getName() {
        return "wiki";
    }

    @Override
    public String getDescription() {
        return "Returns the wiki website!";
    }

    @Override
    public CommandPrivilege[] getCommandPrivileges() {
        return new CommandPrivilege[0];
    }

    @Override
    public OptionData[] getOptions() {
        return new OptionData[] {
                new OptionData(OptionType.BOOLEAN, "all", "Show all plugins"),
                new OptionData(OptionType.BOOLEAN, "mine", "Show your plugins"),
        };
    }

    @Override
    public int getCooldown() {
        return 2;
    }

    @Override
    public void onCommand(TextChannel channel, Member m, SlashCommandEvent e) {
        boolean all = e.getOption("all") != null && e.getOption("all").getAsBoolean();
        boolean mine = e.getOption("mine") != null && e.getOption("mine").getAsBoolean();

        //e.replyEmbeds(new TechEmbedBuilder().text("Showing all the wiki's for all the plugins.\n<:UltraPermissions~1:> https://ultrapermissions.com/wiki \n<:UltraCustomizer~1:> https://ultracustomizer.com/wiki \n:UltraRegions~1: https://ultraregions.com/wiki \n<:UltraPunishments~1:> https://ultrapunishments.com/wiki \n<:InsaneShops~1:> https://insaneshops.com/wiki \n<:UltraEconomy~1:> https://ultraeconomy.com/wiki \n<:UltraScoreboards~1:> https://ultrascoreboards.com/wiki")
        //        .build()).queue();


        if(Plugin.isPluginChannel(channel)) {
            if(!all && !mine) {
                showCurrentChannel(e, channel);
            } else {
                if(all) {
                    showAll(e);
                } else if (mine) {
                    showYourPlugins(e, m);
                }
            }
        } else {
            if(!all && !mine) {
                showYourPlugins(e, m);
            } else if(all) {
                showAll(e);
            }
        }
    }

    public void showCurrentChannel(SlashCommandEvent e, TextChannel channel) {
        if(Plugin.isPluginChannel(channel)) {
            Plugin plugin = Plugin.byChannel(channel);
            if(!plugin.hasWiki()) {
                new TechEmbedBuilder("Wikis").error().text(plugin.getEmoji().getAsMention() + " " + plugin.getRoleName() + " unfortunately does not have a wiki!").sendTemporary(channel, 10);
                return;
            }

            e.replyEmbeds(
                    new TechEmbedBuilder("Wikis")
                        .text("*Showing the wiki of the support channel you're in.*\n\n" + plugin.getEmoji().getAsMention() + " " + plugin.getWiki() + "\n\nFor more info please execute the command `wiki help`.")
                        .build()
            ).queue();
        }
    }

    public void showYourPlugins(SlashCommandEvent e, Member member) {
        //TODO boolean apiIsUsable = TechDiscordBot.getBot().getSpigotStatus().isUsable();
        boolean apiIsUsable = false;


        List<Plugin> plugins = Plugin.allWithWiki();

        StringBuilder sb = new StringBuilder();
        if(plugins.isEmpty())
            sb.append("**You do not own of any of Tech's plugins, showing all wikis!**\n\n");
        if(plugins.isEmpty())
            plugins = Plugin.allWithWiki();
        plugins.forEach(p -> sb.append(p.getEmoji().getAsMention()).append(" ").append(p.getWiki()).append("\n"));

        e.replyEmbeds(
            new TechEmbedBuilder("Wikis")
                .text(sb.substring(0, sb.toString().length() - 1))
                .build()
        ).queue();
    }

    public void showAll(SlashCommandEvent e) {
        List<Plugin> plugins = Plugin.allWithWiki();
        StringBuilder sb = new StringBuilder();

        sb.append("*Showing all wikis!*\n\n");
        plugins.forEach(p -> sb.append(p.getEmoji().getAsMention()).append(" ").append(p.getWiki()).append("\n"));

        e.replyEmbeds(
            new TechEmbedBuilder("Wikis")
                .text(sb.substring(0, sb.toString().length() - 1))
                .build()
        ).queue();
    }

    /**
     * !DISBALED!
     * Get the wiki link by from the member his roles.
     *
     * @param member
     * @return The wiki links
     */
    /*private static String getWikis(Member member) {
        String wikis = null;
        List<Role> roles = member.getRoles();

        if(roles.contains("Ultra Permissions")) {
            wikis = wikis + Emojis.ULTRA_PERMISSIONS() + "https://ultrapermissions.com/wiki\n";
        }
        if(roles.contains("Ultra Punishments")) {
            wikis = wikis + Emojis.ULTRA_PERMISSIONS() + "https://ultrapunishments.com/wiki\n";
        }
        if(roles.contains("Ultra Customizer")) {
            wikis = wikis + Emojis.ULTRA_PERMISSIONS() + "https://ultracustomizer.com/wiki\n";
        }
        if(roles.contains("Ultra Economy")) {
            wikis = wikis + Emojis.ULTRA_PERMISSIONS() + "https://ultraeconomy.com/wiki\n";
        }
        if(roles.contains("Ultra Regions")) {
            wikis = wikis + Emojis.ULTRA_PERMISSIONS() + "https://ultraregions.com/wiki\n";
        }
        if(roles.contains("Ultra Scoreboards")) {
            wikis = wikis + Emojis.ULTRA_PERMISSIONS() + "https://ultrascoreboards.com/wiki\n";
        }
        if(roles.contains("Insane Shops")) {
            wikis = wikis + Emojis.ULTRA_PERMISSIONS() + "https://insaneshops.com/wiki\n";
        }

        // If members has no plugin roll send all wiki's
        if (wikis == null) {
            return "https://ultrapermissions.com/wiki\n"+
                    "https://ultrapunishments.com/wiki\n"+
                    "https://ultracustomizer.com/wiki\n"+
                    "https://ultraeconomy.com/wiki\n"+
                    "https://ultraregions.com/wiki\n"+
                    "https://ultrascoreboards.com/wiki\n"+
                    "https://insaneshops.com/wiki\n";
        }

        return wikis;
    }*/

}
