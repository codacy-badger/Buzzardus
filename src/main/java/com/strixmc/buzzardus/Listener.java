package com.strixmc.buzzardus;

import me.duncte123.botcommons.BotCommons;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

public class Listener extends ListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
    private final CommandManager manager = new CommandManager();
    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        LOGGER.info("{} is ready", event.getJDA().getSelfUser().getName());
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent e) {

        User user = e.getAuthor();

        if (user.isBot() || e.isWebhookMessage()){
            return;
        }

        String prefix = Config.get("PREFIX");
        String raw = e.getMessage().getContentRaw();

        if (raw.equalsIgnoreCase(prefix + "shutdown") && user.getId().equals(Config.get("OWNER_ID"))) {
            e.getChannel().sendMessageFormat("Apagando el bot!").queue();
            LOGGER.info("Apagando Bot!");
            e.getJDA().shutdown();
            BotCommons.shutdown(e.getJDA());

            return;
        }

        if (raw.startsWith(prefix)){
            manager.handle(e);
        }
    }
}