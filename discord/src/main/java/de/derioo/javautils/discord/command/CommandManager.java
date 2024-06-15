package de.derioo.javautils.discord.command;

import de.derioo.javautils.discord.command.exception.CommandNotFoundException;
import de.derioo.javautils.discord.command.parsed.ParsedCommand;
import de.derioo.javautils.discord.command.reciever.ReceiveContext;
import de.derioo.javautils.discord.command.reciever.predefined.PrefixedReceiver;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

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
                try {
                    if (prefixed.receive(event)) {
                        defaultMethodCall(event, command, new CommandNotFoundException("Command was not found"), prefixed.getArgs());
                    }
                } catch (Throwable e) {
                    defaultMethodCall(event, command, e, new ArrayList<>());
                }

            }

        }
    }

    private static void defaultMethodCall(MessageReceivedEvent event, @NotNull ParsedCommand command, Throwable e, List<Object> args) {
        try {
            Optional<Method> defaultCommand = Arrays.stream(command.getCommand().getClass().getDeclaredMethods())
                    .filter(method -> method.getName().equals("defaultCommand"))
                    .findAny();
            if (defaultCommand.isEmpty()) {
                throw new RuntimeException(e);
            }
            defaultCommand.get().setAccessible(true);
            List<Object> params = new ArrayList<>();
            for (Parameter parameter : defaultCommand.get().getParameters()) {
                if (parameter.getType().isAssignableFrom(Throwable.class)) {
                    params.add(e);
                    continue;
                }
                if (parameter.getType().isAssignableFrom(ReceiveContext.class)) {
                    params.add(new ReceiveContext(command, args));
                    continue;
                }
                if (parameter.getType().isAssignableFrom(MessageReceivedEvent.class)) {
                    params.add(event);
                    continue;
                }
                params.add(null);
            }
            defaultCommand.get().invoke(command.getCommand(), params.toArray(Object[]::new));
        } catch (InvocationTargetException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void registerCommand(@NotNull Object command) {
        this.commands.add(new ParsedCommand(command, this));
    }

}
