package me.TechsCode.TechDiscordBot.module.cmds;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import me.TechsCode.TechDiscordBot.logs.RoleLogs;
import me.TechsCode.TechDiscordBot.module.CommandModule;
import me.TechsCode.TechDiscordBot.objects.DefinedQuery;
import me.TechsCode.TechDiscordBot.objects.Query;
import me.TechsCode.TechDiscordBot.util.Roles;
import me.TechsCode.TechDiscordBot.util.TechEmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.privileges.CommandPrivilege;
import net.dv8tion.jda.api.interactions.components.Component;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RoleCommand extends CommandModule {

    private final DefinedQuery<Role> STAFF_ROLES = new DefinedQuery<Role>() {
        @Override
        protected Query<Role> newQuery() {
            return bot.getRoles(Roles.SENIOR_SUPPORT().getName(), Roles.ASSISTANT().getName(), Roles.DEVELOPER().getName(), Roles.CODING_WIZARD().getName());
        }
    };

    private final ArrayList<String> SENIOR_SUPPORTER_ROLES = new ArrayList<String>() {{
        add(Roles.KEEPROLES().getName());
        add(Roles.VERIFIED().getName());
        add(Roles.REVIEW_SQUAD().getName());
    }};

    private final ArrayList<String> ASSISTANT_ROLES = new ArrayList<String>() {{
        addAll(SENIOR_SUPPORTER_ROLES);
        addAll(Roles.STAFF_ROLES_MINOR);
        add(Roles.WIKI_EDITOR().getName());
    }};

    private final ArrayList<String> DEVELOPER_ROLES = new ArrayList<String>() {{
        addAll(ASSISTANT_ROLES);
        add(Roles.ASSISTANT().getName());
    }};

    public RoleCommand(TechDiscordBot bot) {
        super(bot);
    }

    @Override
    public String getName() {
        return "role";
    }

    @Override
    public String getDescription() {
        return "Give or delete roles from a member";
    }

    @Override
    public CommandPrivilege[] getCommandPrivileges() {
        return STAFF_ROLES.query().stream().map(CommandPrivilege::enable).toArray(CommandPrivilege[]::new);
    }

    @Override
    public OptionData[] getOptions() {
        return new OptionData[] {
                new OptionData(OptionType.USER, "member", "The member to give a new role", true),
                new OptionData(OptionType.ROLE, "role", "The role which will be given to the member", true)

                /*new OptionData(OptionType.USER, "member", "The member where the action is taken", true),
                new OptionData(OptionType.STRING, "type", "Add or Remove roles", true)
                        .addChoice("add", "add")
                        .addChoice("remove", "remove")*/
        };
    }

    @Override
    public int getCooldown() {
        return 0;
    }

    @Override
    public void onCommand(TextChannel channel, Member m, SlashCommandEvent e) {
        Member member = e.getOption("member").getAsMember();
        Role role = e.getOption("role").getAsRole();

        if(!m.getRoles().contains(TechDiscordBot.getGuild().getRoleById(854044253885956136L)) && !m.getRoles().contains(TechDiscordBot.getGuild().getRoleById(608113993038561325L)) && !m.getRoles().contains(TechDiscordBot.getGuild().getRoleById(311178859171282944L))) {
            e.replyEmbeds(
                    new TechEmbedBuilder("Role Management")
                            .color(Color.orange)
                            .text("**Senior Supporter**: Verified, Plugins, Marketplaces, Review Squad & Keep Roles\n**Assistant**: Staff, Supporter Roles, Retired, Wiki Editor & Plugin Lab\n**Developer**: Assistant & Team Manager")
                            .build()
            ).queue();
            return;
        }

        if((m.getRoles().contains(TechDiscordBot.getGuild().getRoleById(854044253885956136L)) && !m.getRoles().contains(TechDiscordBot.getGuild().getRoleById(608113993038561325L)) && !SENIOR_SUPPORTER_ROLES.contains(role.getName())) || m.getRoles().contains(TechDiscordBot.getGuild().getRoleById(608113993038561325L)) && !ASSISTANT_ROLES.contains(role.getName())) {
            e.replyEmbeds(
                    new TechEmbedBuilder("Role Management")
                            .color(Color.orange)
                            .text("**Senior Supporter**: Verified, Plugins, Marketplaces, Review Squad & Keep Roles\n**Assistant**: Staff, Supporter Roles, Retired, Wiki Editor & Plugin Lab\n**Developer**: Assistant & Team Manager")
                            .build()
            ).queue();
            return;
        }

        if(m.equals(member)) {
            e.replyEmbeds(
                    new TechEmbedBuilder("Role Management - Error")
                            .error()
                            .text("You can't edit your own roles!")
                            .build()
            ).queue();
            return;
        }

        if(member.getRoles().contains(role)) {
            TechDiscordBot.getGuild().removeRoleFromMember(member, role).complete();
            e.replyEmbeds(
                    new TechEmbedBuilder("Role Removed")
                            .error().text("Removed " + role.getAsMention() + " from " + member.getAsMention())
                            .build()
            ).queue();

            RoleLogs.log(
                    new TechEmbedBuilder("Role Removed")
                            .error().text("Removed " + role.getAsMention() + " from " + member.getAsMention())
            );
        } else {
            TechDiscordBot.getGuild().addRoleToMember(member, role).complete();
            e.replyEmbeds(
                    new TechEmbedBuilder("Role Added")
                            .success().text("Added " + role.getAsMention() + " to " + member.getAsMention())
                            .build()
            ).queue();

            RoleLogs.log(
                    new TechEmbedBuilder("Role Added")
                            .success().text("Added " + role.getAsMention() + " to " + member.getAsMention())
            );
        }
    }

    /*Member member = e.getOption("member").getAsMember();
        String type = e.getOption("type").getAsString();
        List<Role> eRoles = e.getMember().getRoles();
        String perm = "";
        SelectionMenu selection = null;


        // Check if the executor equals the targeted member
        if(m.equals(member)) {
            e.replyEmbeds(
                    new TechEmbedBuilder("Role Management - Error")
                            .text("You can't edit your own roles!")
                            .error()
                            .build()).setEphemeral(true).queue();
            return;
        }

        if(eRoles.contains(Roles.SENIOR_SUPPORT())) {
            selection = SeniorSelectionMenu(e, type);
        }
        if(eRoles.contains(Roles.ASSISTANT())) {
            selection = AssistantSelectionMenu(e, type);
        }
        if(eRoles.contains(Roles.DEVELOPER()) || eRoles.contains(Roles.CODING_WIZARD())) {
            selection = DeveloperSelectionMenu(e, type);
        }

        if(type.equals("add")){
            e.replyEmbeds(new TechEmbedBuilder(type + "Roles")
                            .text("Select the roles you want to add to user: " + member.getAsMention() + " down below and press confirm when you want to perform the action.")
                            .build())
                    .setEphemeral(true)
                    .addActionRow(selection)
                    .queue();
        }
        if(type.equals("remove")){
            e.replyEmbeds(new TechEmbedBuilder("Removing Roles")
                            .text("Select the roles you want to remove from user: " + member.getAsMention() + " down below and press confirm when you want to perform the action.")
                            .build())
                    .setEphemeral(true)
                    .addActionRow(selection)
                    .queue();
        }

        String selected = selection.getOptions().toString();
        System.out.println(selected);

    private static SelectionMenu SeniorSelectionMenu(SlashCommandEvent e, String type){
        SelectionMenu menu = SelectionMenu.create("menu:SeniorRoles")
                .setPlaceholder("Choose the roles you want to " + type) // shows the placeholder indicating what this menu is for
                .setMinValues(1)
                .setMaxValues(25)
                .setRequiredRange(1, 25)
                .setPlaceholder("Select the roles here")
                .addOptions(SelectOption.of("Verified", "Verified")
                        ,SelectOption.of("Spigot", "Spigot"),SelectOption.of("MC-Market", "MC-Market"),SelectOption.of("Songoda", "Songoda"),SelectOption.of("Polymart", "Polymart")
                        ,SelectOption.of("Review_Squad", "Review_Squad")
                        ,SelectOption.of("Keep_Roles", "Keep_Roles")
                        ,SelectOption.of("Ultra_Permissions", "Ultra_Permissions"),SelectOption.of("Ultra_Punishments", "Ultra_Punishments"),SelectOption.of("Ultra_Regions", "Ultra_Regions"),SelectOption.of("Ultra_Economy", "Ultra_Economy"),SelectOption.of("Ultra_Scoreboards", "Ultra_Scoreboards"),SelectOption.of("Ultra_Customizer", "Ultra_Customizer"),SelectOption.of("Insane_Shops", "Insane_Shops"))
                .build();

        return menu;
    }

    private static SelectionMenu AssistantSelectionMenu(SlashCommandEvent e, String type){
        SelectionMenu menu = SelectionMenu.create("menu:AssistantRoles")
                .setPlaceholder("Choose the roles you want to " + type) // shows the placeholder indicating what this menu is for
                .setMinValues(1)
                .setMaxValues(25)
                .setRequiredRange(1, 25)
                .setPlaceholder("Select the roles here")
                .addOptions(SelectOption.of("Verified", "Verified"),
                        SelectOption.of("Spigot", "Spigot"),SelectOption.of("MC-Market", "MC-Market"),SelectOption.of("Songoda", "Songoda"),SelectOption.of("Polymart", "Polymart"),
                        SelectOption.of("Review_Squad", "Review_Squad"),
                        SelectOption.of("Keep_Roles", "Keep_Roles"),
                        SelectOption.of("Ultra_Permissions", "Ultra_Permissions"),SelectOption.of("Ultra_Punishments", "Ultra_Punishments"),SelectOption.of("Ultra_Regions", "Ultra_Regions"),SelectOption.of("Ultra_Economy", "Ultra_Economy"),SelectOption.of("Ultra_Scoreboards", "Ultra_Scoreboards"),SelectOption.of("Ultra_Customizer", "Ultra_Customizer"),SelectOption.of("Insane_Shops", "Insane_Shops"),
                        SelectOption.of("Staff","Staff"),SelectOption.of("Junior_Support","Junior_Support"),SelectOption.of("Support","Support"),SelectOption.of("Senior_Support","Senior_Support"),
                        SelectOption.of("Retired","Retired"),
                        SelectOption.of("Wiki_Editor","Wiki_Editor"),SelectOption.of("Plugin_Lab","Plugin_Lab"))
                .build();
        return menu;
    }

    private static SelectionMenu DeveloperSelectionMenu(SlashCommandEvent e, String type){
        SelectionMenu menu = SelectionMenu.create("menu:DeveloperRoles")
                .setPlaceholder("Choose the roles you want to " + type) // shows the placeholder indicating what this menu is for
                .setMinValues(1)
                .setMaxValues(25)
                .setRequiredRange(1, 25)
                .setPlaceholder("Select the roles here")
                .addOptions(SelectOption.of("Verified", "Verified"),
                        SelectOption.of("Spigot", "Spigot"),SelectOption.of("MC-Market", "MC-Market"),SelectOption.of("Songoda", "Songoda"),SelectOption.of("Polymart", "Polymart"),
                        SelectOption.of("Review_Squad", "Review_Squad"),
                        SelectOption.of("Keep_Roles", "Keep_Roles"),
                        SelectOption.of("Ultra_Permissions", "Ultra_Permissions"),SelectOption.of("Ultra_Punishments", "Ultra_Punishments"),SelectOption.of("Ultra_Regions", "Ultra_Regions"),SelectOption.of("Ultra_Economy", "Ultra_Economy"),SelectOption.of("Ultra_Scoreboards", "Ultra_Scoreboards"),SelectOption.of("Ultra_Customizer", "Ultra_Customizer"),SelectOption.of("Insane_Shops", "Insane_Shops"),
                        SelectOption.of("Staff","Staff"),SelectOption.of("Junior_Support","Junior_Support"),SelectOption.of("Support","Support"),SelectOption.of("Senior_Support","Senior_Support"),
                        SelectOption.of("Retired","Retired"),
                        SelectOption.of("Wiki_Editor","Wiki_Editor"),SelectOption.of("Plugin_Lab","Plugin_Lab"),
                        SelectOption.of("Assistant", "Assistant"))
                .build();
        return menu;
    }*/
}
