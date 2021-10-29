package me.TechsCode.TechDiscordBot.module.modules;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import me.TechsCode.TechDiscordBot.module.Module;
import me.TechsCode.TechDiscordBot.objects.Requirement;
import me.TechsCode.TechDiscordBot.util.Plugin;
import net.dv8tion.jda.api.entities.Icon;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class BannerModule extends Module {

    private final Plugin[] plugins = Plugin.values();
    private int current = 0;

    public BannerModule(TechDiscordBot bot) {
        super(bot);
    }

    @Override
    public void onEnable() {
        if(TechDiscordBot.getGuild().getBoostTier().equals("TIER_2") || TechDiscordBot.getGuild().getBoostTier().equals("TIER_3"))
        new Thread(() -> {
            while(true) {
                updateBanner();

                try {
                    Thread.sleep(TimeUnit.MINUTES.toMillis(5L));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void updateBanner() {
        if(!TechDiscordBot.getGuild().getFeatures().contains("BANNER")) return;

        try {
            TechDiscordBot.getGuild().getManager().setBanner(Icon.from(Objects.requireNonNull(plugins[current].getBannerAsFile()))).queue();

            current++;
            if (current >= plugins.length) current = 0;
        } catch (Exception ignored) { }
    }

    @Override
    public void onDisable() {}

    @Override
    public String getName() {
        return "Banner";
    }

    @Override
    public Requirement[] getRequirements() {
        return new Requirement[0];
    }
}
