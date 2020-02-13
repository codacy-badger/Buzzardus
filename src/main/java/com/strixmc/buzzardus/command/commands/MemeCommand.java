package com.strixmc.buzzardus.command.commands;

import com.strixmc.buzzardus.command.CommandContext;
import com.strixmc.buzzardus.command.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class MemeCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();

        int temp = (Math.random() <= 0.5) ? 1 : 2;
        if (temp == 2) {
            WebUtils.ins.getJSONObject("https://meme-api.herokuapp.com/gimme/espanol").async((json) -> {

                final String title = json.get("title").asText();
                final String url = json.get("url").asText();

                final EmbedBuilder embed = EmbedUtils.embedImageWithTitle(title, null, url);

                channel.sendMessage(embed.build()).queue();
            });
        }
        if (temp == 1) {
            WebUtils.ins.getJSONObject("https://meme-api.herokuapp.com/gimme/Divertido").async((json) -> {

                final String title = json.get("title").asText();
                final String url = json.get("url").asText();

                final EmbedBuilder embed = EmbedUtils.embedImageWithTitle(title, null, url);

                channel.sendMessage(embed.build()).queue();
            });
        }

    }

    @Override
    public String getName() {
        return "meme";
    }

    @Override
    public String getHelp() {
        return "Memes que mas.\n" + "Metodo de uso: `b!meme [es|en]`";
    }
}
