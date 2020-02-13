package com.strixmc.buzzardus.command.commands;

import com.strixmc.buzzardus.command.CommandContext;
import com.strixmc.buzzardus.command.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PollCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();
        User author = ctx.getAuthor();
        final Member selfMember = ctx.getSelfMember();
        final List<String> args = ctx.getArgs();
        final Message message = ctx.getMessage();

        if (!selfMember.hasPermission(Permission.ADMINISTRATOR)){
            channel.sendMessage(selfMember.getAsMention() + " no tienes permisos para usar este comando!").queue(m -> m.delete().queueAfter(3, TimeUnit.SECONDS));
            return;
        }

        message.delete().queue();

        if (args.isEmpty()){
            channel.sendMessage(author.getAsMention() + " Necesitas ingresar una pregunta para la encuesta.").queue(m -> m.delete().queueAfter(5, TimeUnit.SECONDS));
            return;
        }

        channel.sendMessage(message.getContentRaw().replace("b!poll", "") + "\nReacciona para votar.\nSi :white_check_mark:\nNo :x:\nQuizas :person_shrugging:").queue(x ->{
            channel.addReactionById(x.getIdLong(), "U+2705").queue();
            channel.addReactionById(x.getIdLong(), "U+274C").queue();
            channel.addReactionById(x.getIdLong(), "U+1F937").queue();
        });

    }

    @Override
    public String getName() {
        return "poll";
    }

    @Override
    public String getHelp() {
        return "Crea una encuesta.";
    }
}
