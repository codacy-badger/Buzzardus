package com.strixmc.buzzardus.command.commands;

import com.strixmc.buzzardus.Config;
import com.strixmc.buzzardus.command.CommandContext;
import com.strixmc.buzzardus.command.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MediaCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final EmbedBuilder embed = EmbedUtils.embedMessageWithTitle("Requisitos", null);

        boolean dev = false;

        ctx.getMessage().delete().queue();
        if (dev) {
            channel.sendMessage("Este comando aun no esta disponible, esta en desarrollo.").queue((m) -> m.delete().queueAfter(5, TimeUnit.SECONDS));
        } else {

            embed.setDescription("Requisitos para rangos \"Media\" (MiniYT, Youtuber, Streamer, Famous, Partner)\nPara conseguir alguno de los rangos mencionados\ntienes que grabar un video en el servidor y subirlo al canal #spam-youtube");
            embed.addField("MiniYT", "75 suscriptores", false);
            embed.addField("Youtuber", "100 suscriptores", false);
            embed.addField("Streamer", "50 seguidores", false);
            embed.addField("Famous", "400 suscriptores", false);
            embed.addField("Partner", "No disponible por requisitos.", false);
            channel.sendMessage(embed.build()).queue(message -> message.delete().queueAfter(30,TimeUnit.SECONDS));
        }
    }

    @Override
    public String getName() {
        return "yt";
    }

    @Override
    public String getHelp() {
        return "";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("youtube", "rango", "publicista", "media", "yt");
    }
}
