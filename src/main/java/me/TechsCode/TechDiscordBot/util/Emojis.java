package me.TechsCode.TechDiscordBot.util;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Emote;

public class Emojis {
	public static JDA api = TechDiscordBot.getJDA();

	public static String ULTRA_PERMISSIONS(){
		return "<:UltraPermissions:576937960176353293>";
	}
	public static String ULTRA_CUSTOMIZER(){
		return "<:UltraCustomizer:576873108506411018>";
	}
	public static String ULTRA_PUNISHMENTS(){
		return "<:UltraPunishments:576873108422524954>";
	}
	public static String ULTRA_ECONOMY(){
		return "<:UltraEconomy:748004373971337216>";
	}
	public static String ULTRA_REGIONS(){
		return "<:UltraRegions:639288786236473354>";
	}
	public static String ULTRA_SCOREBOARDS(){
		return "<:UltraScoreboards:843611772481044520>";
	}
	public static String INSANE_SHOPS(){
		return "<:InsaneShops:576871756816449536>";
	}
}
