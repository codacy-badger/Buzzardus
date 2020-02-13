package com.strixmc.buzzardus.command.commands;

import com.strixmc.buzzardus.CommandManager;
import com.strixmc.buzzardus.command.CommandContext;
import com.strixmc.buzzardus.command.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class HelpCommand implements ICommand {

    private final CommandManager manager;

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }


    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();
        ctx.getMessage().delete().queue();
        if (args.isEmpty()) {
            //TODO COMMANDS LIST

            final EmbedBuilder embed = EmbedUtils.embedMessageWithTitle("Ayuda del BOT", null);
            embed.setDescription("El prefix del bot es `b!`");
            embed.setThumbnail("https://img.icons8.com/cotton/2x/chat.png");
            StringBuilder comandos = new StringBuilder();
            StringBuilder staff = new StringBuilder();
            AtomicInteger ic = new AtomicInteger();
            AtomicInteger is = new AtomicInteger();
            manager.getCommands().stream().map(ICommand::getName).forEach((it) -> {
                ArrayList<String> staffcmds = new ArrayList<String>();
                staffcmds.add("announce");
                staffcmds.add("kick");
                staffcmds.add("clear");
                staffcmds.add("embed");
                staffcmds.add("poll");
                if (!staffcmds.contains(it)) {
                    comandos.append('`').append(it).append('`' + " ");
                    ic.incrementAndGet();
                } else {
                    staff.append('`').append(it).append('`' + " ");
                    is.incrementAndGet();
                }
            });
            embed.addField("> Comandos disponibles [" + ic + "]", comandos.toString(), true);
            embed.addField("> Comandos Staff [" + is + "]", staff.toString(), false);
            channel.sendMessage(embed.build()).queue(m -> m.delete().queueAfter(10, TimeUnit.SECONDS));
            return;
        }

        String search = args.get(0);

        ICommand command = manager.getCommand(search);

        if (command == null) {
            channel.sendMessageFormat("No existe el comando " + search).queue(m -> m.delete().queueAfter(5, TimeUnit.SECONDS));
            return;
        }

        channel.sendMessageFormat("Informacion del comando \"" + command.getName() + "\"\n" + command.getHelp()).queue(m -> m.delete().queueAfter(10, TimeUnit.SECONDS));

    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getHelp() {
        return "Muestra la lista de comandos del bot.\n" + "Metodo de uso: `b!!help [command]`";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("commands", "cmds", "commandlist");
    }
}
