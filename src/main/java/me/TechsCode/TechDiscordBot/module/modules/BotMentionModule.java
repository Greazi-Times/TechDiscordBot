package me.TechsCode.TechDiscordBot.module.modules;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import me.TechsCode.TechDiscordBot.module.Module;
import me.TechsCode.TechDiscordBot.objects.Requirement;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;

import java.util.List;

public class BotMentionModule extends Module {

	public BotMentionModule(TechDiscordBot bot) {
		super(bot);
	}

	@SubscribeEvent
	public void onMention(MessageReceivedEvent e, String[] args){
		if(e.getMessage().getMentionedUsers().contains(TechDiscordBot.getJDA().getSelfUser())){
			e.getMessage().addReaction("👀").queue();
		}
	}

	@Override
	public void onEnable() {

	}

	@Override
	public void onDisable() {

	}

	@Override
	public String getName() {
		return "Bot Mention";
	}

	@Override
	public Requirement[] getRequirements() {
		return new Requirement[0];
	}
}
