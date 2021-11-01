package me.TechsCode.TechDiscordBot.util;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Emote;

public class Emojis {
	public static JDA api = TechDiscordBot.getJDA();

	public static String ULTRA_PERMISSIONS(){
		return "<:UltraPermissions:882666109260693505>";
	}
	public static String ULTRA_CUSTOMIZER(){
		return "<:UltraCustomizer:882665998904361021>";
	}
	public static String ULTRA_PUNISHMENTS(){
		return "<:UltraPunishments:882666158824755250>";
	}
	public static String ULTRA_ECONOMY(){
		return "<:UltraEconomy:882666088595329115>";
	}
	public static String ULTRA_REGIONS(){
		return "<:UltraRegions:882666234842333246>";
	}
	public static String ULTRA_SCOREBOARDS(){
		return "<:UltraScoreboards:882666294032367656>";
	}
	public static String INSANE_SHOPS(){
		return "<:InsaneShops:882666272674955264>";
	}
}
