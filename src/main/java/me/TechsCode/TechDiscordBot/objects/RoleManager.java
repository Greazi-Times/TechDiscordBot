package me.TechsCode.TechDiscordBot.objects;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import me.TechsCode.TechDiscordBot.mysql.Models.DbMember;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoleManager {
    private final Member member;
    private final Set<Role> memberRoles;

    public RoleManager(DbMember dbMember){
        this.member = dbMember.getDiscordMember();
        this.memberRoles = new HashSet<>(member.getRoles());
    }

    public RoleManager(Member member){
        this.member = member;
        this.memberRoles = new HashSet<>(member.getRoles());
    }

    public void addRole(Role role){
        memberRoles.add(role);
    }

    public void addRoles(List<Role> roles){
        memberRoles.addAll(roles);
    }

    public void removeRole(Role role){
        memberRoles.remove(role);
    }

    public void removeRoles(List<Role> roles){
        roles.forEach(memberRoles::remove);
    }

    public boolean hasRole(Role role){
        return memberRoles.contains(role);
    }

    public boolean hasAllRoles(List<Role> roles){
        return memberRoles.containsAll(roles);
    }

    public boolean hasAnyRoles(List<Role> roles){
        return roles.stream().anyMatch(memberRoles::contains);
    }

    public void save(){
        TechDiscordBot.getGuild().modifyMemberRoles(member, memberRoles).queue();
    }

}
