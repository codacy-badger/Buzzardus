package com.strixmc.buzzardus.command.commands;

import com.strixmc.buzzardus.command.CommandContext;
import com.strixmc.buzzardus.command.ICommand;
import net.dv8tion.jda.api.JDA;

public class PingCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        JDA jda = ctx.getJDA();

        jda.getRestPing().queue(
                (ping) -> ctx.getChannel()
                        .sendMessageFormat("La latencia del bot es: %sms\nLatencia WS: %sms", ping, jda.getGatewayPing()).queue()
        );
    }

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getHelp() {
        return "Muestra la latencia del bot hasta el servidor.";
    }
}
