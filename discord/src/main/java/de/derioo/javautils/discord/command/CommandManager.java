package de.derioo.javautils.discord.command;

import de.derioo.javautils.discord.command.parsed.ParsedCommand;
import de.derioo.javautils.discord.command.reciever.predefined.PrefixedReceiver;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class CommandManager extends ListenerAdapter {

    private final JDA jda;

    @Getter
    private final Set<ParsedCommand> commands = new HashSet<>();


    public CommandManager(JDA jda) {
        this.jda = jda;
        jda.addEventListener(this);
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        for (ParsedCommand command : this.commands) {
            if (command.getReceiver() instanceof PrefixedReceiver prefixed) {
                prefixed.receive(event);
            }

        }
    }

    public void registerCommand(@NotNull Object command) {
        this.commands.add(new ParsedCommand(command, this));
    }

}
