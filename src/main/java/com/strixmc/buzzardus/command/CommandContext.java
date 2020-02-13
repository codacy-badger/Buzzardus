package com.strixmc.buzzardus.command;

import me.duncte123.botcommons.commands.ICommandContext;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class CommandContext implements ICommandContext {

    private final GuildMessageReceivedEvent e;
    private final List<String> args;



    public CommandContext(GuildMessageReceivedEvent e, List<String> args) {
        this.e = e;
        this.args = args;
    }

    @Override
    public Guild getGuild() {
        return this.e.getGuild();
    }

    @Override
    public GuildMessageReceivedEvent getEvent() {
        return this.e;
    }

    public List<String> getArgs(){
        return this.args;
    }


}
