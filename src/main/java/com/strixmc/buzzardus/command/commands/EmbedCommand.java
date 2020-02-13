package com.strixmc.buzzardus.command.commands;

import com.strixmc.buzzardus.Config;
import com.strixmc.buzzardus.command.CommandContext;
import com.strixmc.buzzardus.command.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.utils.AttachmentOption;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class EmbedCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final List<String> args = ctx.getArgs();
        Message message = ctx.getMessage();
        final User author = ctx.getAuthor();
        final TextChannel channel = ctx.getChannel();
        final Member selfMember = ctx.getSelfMember();


        if (!selfMember.hasPermission(Permission.ADMINISTRATOR)){
            channel.sendMessage(author.getAsMention() + " No tienes permisos para ejecutar este comando.").queue(m -> m.delete().queueAfter(5, TimeUnit.SECONDS));
        }

        message.delete().queue();

        if (args.isEmpty()){
            channel.sendMessage("Metodo de uso: "  + Config.get("PREFIX") + "embed <titulo> <descripcion>").queue();
            return;
        }

        if (args.size() >= 2){
            EmbedBuilder embed = EmbedUtils.embedMessageWithTitle(args.get(0), message.getContentRaw().replace("b!embed", "").replace(args.get(0),  ""));
            channel.sendMessage(embed.build()).queue();
        } else {
            channel.sendMessage("Necesitas ingresar una descripcion.").queue(m -> m.delete().queueAfter(10, TimeUnit.SECONDS));
        }

    }

    @Override
    public String getName() {
        return "embed";
    }

    @Override
    public String getHelp() {
        return "Create a embed.";
    }
}
