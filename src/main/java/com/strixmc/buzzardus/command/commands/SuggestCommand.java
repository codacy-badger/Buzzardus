package com.strixmc.buzzardus.command.commands;


import com.strixmc.buzzardus.command.CommandContext;
import com.strixmc.buzzardus.command.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SuggestCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {

        List<String> args = ctx.getArgs();
        TextChannel channel = (TextChannel) ctx.getGuild().getGuildChannelById("624043164487122944");
        User author = ctx.getAuthor();
        Message message = ctx.getMessage();

        message.delete().queue();
        if (channel == null) {

            return;
        }
        if (args.isEmpty()) {
            channel.sendMessage(author.getAsMention() + " debes incluir una sugerencia!").queue((m) -> m.delete().queueAfter(10, TimeUnit.SECONDS));
            return;
        }

        final EmbedBuilder embed = EmbedUtils.embedMessageWithTitle("Sugerencia de " + author.getName(), null);
        embed.setAuthor(author.getName(), null, author.getAvatarUrl());
        embed.setTimestamp(Instant.now());
        embed.setThumbnail("https://image.flaticon.com/icons/png/512/427/427735.png");
        embed.addField("Sugerencia", message.getContentRaw().replace("b!sugerencia", "").replace("b!suggest", "") , true);

        embed.addField("Info", "Reacciona con :white_check_mark: para apoyar la sugerencia\nReacciona con :x: si crees que no es buena.", false);
        channel.sendMessage(embed.build()).queue((x) -> {
            channel.addReactionById(x.getIdLong(), "U+2705").queue();
            channel.addReactionById(x.getIdLong(), "U+274C").queue();
        });

        channel.sendMessage("Gracias por tu sugerencia " + author.getAsMention() + "!").queue((m) -> m.delete().queueAfter(5, TimeUnit.SECONDS));

    }

    @Override
    public String getName() {
        return "sugerencia";
    }

    @Override
    public String getHelp() {
        return "Envianos tus sugerencias para mejorar el servidor!\n" + "Metodo de uso: `b!sugerencia <sugerencia>`";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("suggest");
    }
}
