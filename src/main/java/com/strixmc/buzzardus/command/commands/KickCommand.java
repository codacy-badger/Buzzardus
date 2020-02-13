package com.strixmc.buzzardus.command.commands;

import com.strixmc.buzzardus.command.CommandContext;
import com.strixmc.buzzardus.command.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class KickCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Message message = ctx.getMessage();
        final Member member = ctx.getMember();
        final List<String> args = ctx.getArgs();

        if (args.size()< 2 || message.getMentionedMembers().isEmpty()){
            channel.sendMessage("Necesitas mencionar un miembro y especificar una razon para kickear!").queue();
            return;
        }

        final Member target = message.getMentionedMembers().get(0);

        if (!member.canInteract(target) || !member.hasPermission(Permission.KICK_MEMBERS)) {
            channel.sendMessage("No tienes permisos para kickear a este usuario.").queue();
            return;
        }

        final Member selfMember = ctx.getSelfMember();

        if (!selfMember.canInteract(target) || !selfMember.hasPermission(Permission.KICK_MEMBERS)) {
            channel.sendMessage("I am missing permissions to kick that member").queue();
            return;
        }

        final String reason = String.join(" ", args.subList(1, args.size()));

        message.delete().queue();
        ctx.getGuild()
                .kick(target, reason)
                .reason(reason)
                .queue(
                        (__) -> channel.sendMessage("El kick fue exitoso!").queue(),
                        (error) -> channel.sendMessageFormat("No se pudo kickear %s", error.getMessage()).queue()
                        );
    }

    @Override
    public String getName() {
        return "kick";
    }

    @Override
    public String getHelp() {
        return "Kickear un miembro del servidor de discord.\n" +
                "Metodo de uso: `b!kick <@user> <reason>`";
    }

}
