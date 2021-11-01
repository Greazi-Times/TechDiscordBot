package me.TechsCode.TechDiscordBot.util;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Role;

import java.util.ArrayList;

public class Roles {
	public static JDA api = TechDiscordBot.getJDA();

	//TODO change to the main discord

	// Staff roles
	public static Role STAFF(){
		return api.getRoleById("608114002387533844");
	}
	public static Role TRUSTED_HELPER(){
		return api.getRoleById("740045311443075163");
	}
	public static Role JUNIOR_SUPPORT(){
		return api.getRoleById("664998195880919060");
	}
	public static Role SUPPORT(){
		return api.getRoleById("440672147422183454");
	}
	public static Role SENIOR_SUPPORT(){
		return api.getRoleById("854044253885956136");
	}
	public static Role ASSISTANT(){
		return api.getRoleById("608113993038561325");
	}
	public static Role DEVELOPER(){
		return api.getRoleById("774690360836096062");
	}
	public static Role CODING_WIZARD(){
		return api.getRoleById("311178859171282944");
	}
	public static Role RETIRED(){
		return api.getRoleById("522049115840839719");
	}
	public static Role RETIRED_DEVELOPER(){
		return api.getRoleById("848338083652960277");
	}

	// Verified roles
	public static Role VERIFIED(){
		return api.getRoleById("416174015141642240");
	}
	public static Role SPIGOT(){
		return api.getRoleById("904745009096564827");
	}
	public static Role MCMARKET(){
		return api.getRoleById("904745041598251008");
	}
	public static Role SONGODA(){
		return api.getRoleById("904745048841785406");
	}
	public static Role POLYMART(){
		return api.getRoleById("904745044244852796");
	}
	public static Role SUB_VERIFIED(){
		return api.getRoleById("855858606447525928");
	}

	// Plugin roles
	public static Role PLUGIN_LAB(){
		return api.getRoleById("742172247002710107");
	}
	public static Role ULTRA_ECONOMY(){
		return api.getRoleById("749034791936196649");
	}
	public static Role ULTRA_PERMISSIONS(){
		return api.getRoleById("416194311080771596");
	}
	public static Role ULTRA_SCOREBOARDS(){
		return api.getRoleById("811397836616630352");
	}
	public static Role ULTRA_PUNISHMENTS(){
		return api.getRoleById("531255363505487872");
	}
	public static Role ULTRA_CUSTOMIZER(){
		return api.getRoleById("416194287567372298");
	}
	public static Role ULTRA_REGIONS(){
		return api.getRoleById("465975554101739520");
	}
	public static Role INSANE_SHOPS(){
		return api.getRoleById("576739274297442325");
	}

	// Permission roles
	public static Role KEEPROLES(){
		return api.getRoleById("779166270415175710");
	}
	public static Role MUTED(){
		return api.getRoleById("477676501739896843");
	}
	public static Role PATREON(){
		return api.getRoleById("795101981051977788");
	}
	public static Role ARTIST(){
		return api.getRoleById("811273548165021706");
	}
	public static Role DONATOR(){
		return api.getRoleById("311179148691505152");
	}
	public static Role WIKI_EDITOR(){
		return api.getRoleById("755954709231173794");
	}
	public static Role VERIFIED_CREATOR(){
		return api.getRoleById("435183665719541761");
	}
	public static Role FUNERAL_DONATOR(){
		return api.getRoleById("777665478050447432");
	}
	public static Role REVIEW_SQUAD(){
		return api.getRoleById("457934035549683713");
	}
	public static Role REQUESTED_TRANSFER(){
		return api.getRoleById("727399907291562004");
	}
	public static Role ANNOUNCEMENTS(){
		return api.getRoleById("837716735577554964");
	}
	public static Role UPDATES(){
		return api.getRoleById("837717109156872225");
	}
	public static Role GIVEAWAYS(){
		return api.getRoleById("837717052701016085");
	}
	public static Role PATREON_NEWS(){
		return api.getRoleById("873378074727694346");
	}

	/*public static ArrayList<String> PLUGIN_ROLES = new ArrayList<String>() {{
		add(ULTRA_PERMISSIONS().getName());
		add(ULTRA_CUSTOMIZER().getName());
		add(ULTRA_PUNISHMENTS().getName());
		add(ULTRA_ECONOMY().getName());
		add(ULTRA_SCOREBOARDS().getName());
		add(ULTRA_ECONOMY().getName());
		add(INSANE_SHOPS().getName());
	}};*/

	/*public static ArrayList<String> MARKET_ROLES = new ArrayList<String>() {{
		add(SPIGOT().getName());
		add(MCMARKET().getName());
		add(SONGODA().getName());
		add(POLYMART().getName());
	}};*/

	/*public static ArrayList<String> STAFF_ROLES = new ArrayList<String>() {{
		add(CODING_WIZARD().getName());
		add(DEVELOPER().getName());
		add(ASSISTANT().getName());
		add(SENIOR_SUPPORT().getName());
		add(SUPPORT().getName());
		add(JUNIOR_SUPPORT().getName());
		add(STAFF().getName());
	}};*/

	/*public static ArrayList<String> STAFF_ROLES_MINOR = new ArrayList<String>() {{
		add(SENIOR_SUPPORT().getName());
		add(SUPPORT().getName());
		add(JUNIOR_SUPPORT().getName());
		add(STAFF().getName());
	}};*/
}
