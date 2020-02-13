package com.strixmc.buzzardus;

import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Bot {

    private Bot() throws LoginException {

        EmbedUtils.setEmbedBuilder(
                () -> new EmbedBuilder()
                .setColor(0xffaf2e)
                .setFooter("Buzzardus")
        );
        new JDABuilder()
                .setToken(Config.get("TOKEN"))
                .addEventListeners(new Listener())
                .setActivity(Activity.playing("StrixMC Network"))
                .build();
    }

    public static void main(String[] args) throws LoginException {
        new Bot();
    }
}
