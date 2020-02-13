package com.strixmc.buzzardus.command.commands;

import com.strixmc.buzzardus.command.CommandContext;
import com.strixmc.buzzardus.command.ICommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ClearCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        List<String> args = ctx.getArgs();
        final Message message = ctx.getMessage();

        message.delete().queue();

        if (args.size() < 1) {
            channel.sendMessage("Necesitas especificar un numero de mensajes a borrar!").queue((m) -> m.delete().queueAfter(3, TimeUnit.SECONDS));
        } else {
            if (message.getMentionedChannels().isEmpty()) {
                int num = Integer.parseInt(args.get(0));
                int total = Math.addExact(num, 1);
                purgeMessages(channel, total);
            } else {
                final TextChannel targetChannel = message.getMentionedChannels().get(0);
                int num = Integer.parseInt(args.get(0));
                int total = Math.addExact(num, 1);
                purgeMessages(targetChannel, total);
            }
        }
    }

    private void purgeMessages(TextChannel channel, int num) {

        MessageHistory history = new MessageHistory(channel);
        List<Message> msg;

        msg = history.retrievePast(num).complete();

        channel.deleteMessages(msg).queue();
    }


    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getHelp() {
        return "Elimina una cantidad de mensajes espeficados.\n" + "Metodo de uso: `b!clear [#canal] <numero>`";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("purge");
    }
}
