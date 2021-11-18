package me.TechsCode.TechDiscordBot.module.modules;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import me.TechsCode.TechDiscordBot.module.Module;
import me.TechsCode.TechDiscordBot.mysql.Models.DbMarket;
import me.TechsCode.TechDiscordBot.mysql.Models.DbMember;
import me.TechsCode.TechDiscordBot.objects.DefinedQuery;
import me.TechsCode.TechDiscordBot.objects.Query;
import me.TechsCode.TechDiscordBot.objects.Requirement;
import me.TechsCode.TechDiscordBot.util.TechEmbedBuilder;
import me.TechsCode.TechDiscordBot.verification.Verification;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import net.dv8tion.jda.api.interactions.components.Button;

import java.util.stream.Collectors;

public class VerificationModule extends Module {

    private TextChannel channel;
    private Message lastSelectionEmbed;
    private String selectedMarket = null;
    private Message instruction;

    public VerificationModule(TechDiscordBot bot) {
        super(bot);
    }

    @Override
    public String getName() {
        return "Verification";
    }

    private final DefinedQuery<TextChannel> VERIFICATION_CHANNEL = new DefinedQuery<TextChannel>() {
        @Override
        protected Query<TextChannel> newQuery() {
            return bot.getChannels("\uD83D\uDCD8︱verification");
        }
    };

    @Override
    public Requirement[] getRequirements() {
        return new Requirement[]{
                new Requirement(VERIFICATION_CHANNEL, 1, "Missing Verification Channel (#verification)")
        };
    }

    @Override
    public void onEnable() {
        channel = VERIFICATION_CHANNEL.query().first();
        channel.getIterableHistory()
                .takeAsync(100)
                .thenAccept(msg -> channel.purgeMessages(msg.stream().filter(m -> m.getEmbeds().size() > 0 && m.getEmbeds().get(0).getAuthor() != null && m.getEmbeds().get(0).getAuthor().getName() != null && m.getEmbeds().get(0).getAuthor().getName().equals("Marketplace Selector")).collect(Collectors.toList())));

        lastSelectionEmbed = null;

        if (lastSelectionEmbed != null) {
            lastSelectionEmbed.delete().complete();
        }

        sendSelection();
    }

    @Override
    public void onDisable() {
        if (lastSelectionEmbed != null) lastSelectionEmbed.delete().submit();
    }

    @SubscribeEvent
    public void onButtonClick(ButtonClickEvent e) {
        Member member = e.getMember();
        assert member != null;

        if(e.getComponentId().equals("spigot")){
            if(member.getRoles().contains("Spigot")){
                new TechEmbedBuilder("ERROR Verification").text("You have already verified a spigot account!").error().sendTemporary(channel, 15);
                return;
            }

            selectedMarket = "spigotmc";

        }
        if(e.getComponentId().equals("mc-market")){
            selectedMarket = "mcmarket";
        }
        if(e.getComponentId().equals("songoda")){
            //new TechEmbedBuilder("Songoda Verification").text("To verify your Songoda purchase you need to connect your discord account to your songoda account.\n\nNeed help with connecting?\n*You can connect your account [here](https://songoda.com/account/integrations)*").error().sendTemporary(channel, 15);;
            selectedMarket = "songoda";
        }
        if(e.getComponentId().equals("polymart")){
            selectedMarket = "polymart";
        }
        DbMarket market = TechDiscordBot.getStorage().retrieveMarketByName(selectedMarket);

        e.replyEmbeds(new TechEmbedBuilder("Verification").text("A DM with instructions has been send to you.").build()).setEphemeral(true).queue();

        Verification.Verification(member, market, channel);
    }

    public void sendSelection() {
        deleteSelection();

        channel.sendMessageEmbeds( new TechEmbedBuilder("Marketplace Selector")
                .text("Have you purchased one or more of our plugins and wish to verify yourself?\n\n"+
                        "You have come to the right place!\n"+
                        "Just select the emoji below that corresponds to the marketplace where you bought the plugin(s), and we will explain the next steps after your selection.")
                .build()
        ).setActionRow(
                Button.primary("spigot", "Spigot").withEmoji(Emoji.fromMarkdown("<:spigot:879756315747053628>")),
                Button.primary("mc-market", "MC-Market").withEmoji(Emoji.fromMarkdown("<:mcmarket:879756190089895988>")),
                Button.primary("songoda", "Songoda").withEmoji(Emoji.fromMarkdown("<:songoda:879756362861666375>")),
                Button.primary("polymart", "Polymart").withEmoji(Emoji.fromMarkdown("<:polymart:879756228589400145>"))
        ).queue((message) -> {
            lastSelectionEmbed = message;
        });
    }

    public void deleteSelection() {
        if (lastSelectionEmbed != null){
            lastSelectionEmbed.delete().submit();
            lastSelectionEmbed = null;
        }
    }
}
