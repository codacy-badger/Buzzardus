package com.strixmc.buzzardus.command.commands;

import com.fasterxml.jackson.databind.JsonNode;
import com.strixmc.buzzardus.command.CommandContext;
import com.strixmc.buzzardus.command.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class IPCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        User author = ctx.getAuthor();

        String bannerURL = "http://status.mclive.eu/StrixMC/strix-mc.com/25565/banner.png";

        EmbedBuilder embed = EmbedUtils.embedMessageWithTitle("IP del Servidor", author.getAsMention() + " La IP del servidor es strix-mc.com\nY soportamos desde la version 1.7.x hasta 1.15.x!!");
        ctx.getMessage().delete().queue();
        channel.sendMessage(embed.build()).queue(m -> m.delete().queueAfter(10, TimeUnit.SECONDS));
        WebUtils.ins.getJSONObject("https://api.mcsrvstat.us/2/strix-mc.com").async((json) -> {

            JsonNode online = json.get("online");
            EmbedBuilder sembed = EmbedUtils.embedMessage("");

            sembed.setTitle("**Estado del Servidor**");
            sembed.setThumbnail("https://api.mcsrvstat.us/icon/strix-mc.com");
            if (online.asBoolean()) {
                JsonNode debug = json.get("debug");
                JsonNode motd = json.get("motd");
                JsonNode players = json.get("players");
                JsonNode version = json.get("version");
                JsonNode software = json.get("software");
                JsonNode hostname = json.get("hostname");

                if (hostname != null){
                    sembed.addField("IP:", hostname.asText(), true);
                }
                sembed.addField("Estado:", (debug.get("ping").asBoolean() ? "Online" : "Offline"), true);
                sembed.addField("MoTD:", motd.get("clean").get(0).asText().replace("player", author.getName()) + "\n" + motd.get("clean").get(1).asText().replace("player", author.getName()), false);
                sembed.addField("Jugadores:", players.get("online").asText() + "/" + players.get("max").asText(), true);
                sembed.addField("Version:", (software != null ? software.asText().replace(software.asText(), "StrixBungee | "): "Mantenimiento | ") + version.asText(), true);
                sembed.setImage(bannerURL);
            } else {
                sembed.setDescription("Nuestra network actualmente se encuenta apagada.");
            }


            channel.sendMessage(sembed.build()).queue(m -> m.delete().queueAfter(10 , TimeUnit.SECONDS));
        });
    }

    @Override
    public String getName() {
        return "ip";
    }

    @Override
    public String getHelp() {
        return "Ver la IP del servidor!";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("serverip");
    }
}
