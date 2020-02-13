package com.strixmc.buzzardus.command.commands;

import com.strixmc.buzzardus.command.CommandContext;
import com.strixmc.buzzardus.command.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.utils.AttachmentOption;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Announce implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final User author = ctx.getAuthor();
        List<String> args = ctx.getArgs();
        Message message = ctx.getMessage();
        final Member selfMember = ctx.getSelfMember();

        if (!selfMember.hasPermission(Permission.ADMINISTRATOR)) {
            return;
        }

        if (args.isEmpty()) {
            message.delete().queue();
            channel.sendMessage(author.getAsMention() + " debes incluir un mensaje!").queue((m) -> m.delete().queueAfter(5, TimeUnit.SECONDS));
            return;
        }

        if (message.getMentionedChannels().isEmpty()) {
            channel.sendMessage(message.getContentRaw().replace("b!say", "").replace("b!announce", "")).queue();

            if (!message.getAttachments().isEmpty()) {
                for (Message.Attachment attachment : message.getAttachments()) {

                    attachment.downloadToFile("/tmp/" + attachment.getFileName()).thenAccept(file -> {
                        System.out.println("Saved attachment to " + file.getName());
                        boolean isSpoiler = file.getName().split("_")[0].equals("SPOILER");
                        System.out.println("Spoiler image " + isSpoiler);
                        if (isSpoiler) {
                            channel.sendFile(file, AttachmentOption.SPOILER).queue();
                        } else {
                            channel.sendFile(file).queue();
                        }
                        file.delete();
                    }).exceptionally(t -> {
                        t.printStackTrace();
                        return null;
                    });

                }
            }
            message.delete().queue();
            return;
        }

        final TextChannel targetChannel = message.getMentionedChannels().get(0);
        targetChannel.sendMessage(message.getContentRaw().replace(targetChannel.getAsMention(), "").replace("b!say", "").replace("b!announce", "")).queue();
        message.delete().queue();
    }


    @Override
    public String getName() {
        return "announce";
    }

    @Override
    public String getHelp() {
        return "Envia un anuncio en el mismo canal o uno especificado.\n" + "Metodo de uso: `b!announce [#canal] <anuncio>`";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("say");
    }
}
