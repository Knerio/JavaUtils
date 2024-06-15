package de.derioo.javautils.discord.command.parsed;

import de.derioo.javautils.discord.command.CommandManager;
import de.derioo.javautils.discord.command.annotations.Argument;
import de.derioo.javautils.discord.command.annotations.Prefix;
import de.derioo.javautils.discord.command.annotations.SubCommand;
import de.derioo.javautils.discord.command.reciever.Receiver;
import de.derioo.javautils.discord.command.reciever.predefined.PrefixedReceiver;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class ParsedCommand {

    private Receiver<?, ?> receiver;
    @Nullable
    private String prefix;

    private final Object command;

    private final Set<ParsedArgument> arguments = new HashSet<>();

    public ParsedCommand(@NotNull Object command, CommandManager manager) {
        this.command = command;
        Class<?> clazz = command.getClass();
        if (clazz.isAnnotationPresent(Prefix.class)) {
            receiver = new PrefixedReceiver(manager);
            this.prefix = clazz.getAnnotation(Prefix.class).value();
        }
        for (Method declaredMethod : command.getClass()
                .getDeclaredMethods()) {
            if (declaredMethod.isAnnotationPresent(SubCommand.class)) this.arguments.add(new ParsedArgument(declaredMethod));
        }


    }

    @Getter
    public static class ParsedArgument {

        private final List<ParsedSubArgument> subArguments = new ArrayList<>();

        private final Method method;

        public ParsedArgument(@NotNull Method method) {
            this.method = method;
            for (Parameter parameter : method.getParameters()) {
                if (parameter.isAnnotationPresent(Argument.class)) {
                    this.subArguments.add(new ParsedSubArgument(parameter));
                }
            }
        }


    }

    @Getter
    public static class ParsedSubArgument {

        private final Argument.ArgumentType type;
        private final String value;

        public ParsedSubArgument(@NotNull Parameter parameter) {
            this.type = parameter.getAnnotation(Argument.class).type();
            this.value = parameter.getAnnotation(Argument.class).value();
        }
    }
}
