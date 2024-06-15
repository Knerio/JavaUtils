package de.derioo.javautils.discord.command.reciever.predefined;

import com.cronutils.model.Cron;
import de.derioo.javautils.common.StringUtility;
import de.derioo.javautils.discord.command.CommandManager;
import de.derioo.javautils.discord.command.annotations.Argument;
import de.derioo.javautils.discord.command.exception.CommandNotFoundException;
import de.derioo.javautils.discord.command.parsed.ParsedCommand;
import de.derioo.javautils.discord.command.parsed.parser.CronExtractor;
import de.derioo.javautils.discord.command.parsed.parser.DateExtractor;
import de.derioo.javautils.discord.command.reciever.ReceiveContext;
import de.derioo.javautils.discord.command.reciever.Receiver;
import kotlin.Pair;
import lombok.extern.java.Log;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.derioo.javautils.discord.command.annotations.Argument.ArgumentType.GREEDY_STRING;

@Log
public class PrefixedReceiver extends Receiver<PrefixedReceiver, MessageReceivedEvent> {

    public PrefixedReceiver(CommandManager commandManager) {
        super(commandManager);
    }

    @Override
    public boolean receive(MessageReceivedEvent event) {
        for (ParsedCommand command : getCommandManager().getCommands()) {
            if (!(command.getReceiver() instanceof PrefixedReceiver)) return false;

            String content = event.getMessage().getContentRaw();
            if (!content.startsWith(command.getPrefix())) return false;

            String afterPrefix = content.replaceFirst(command.getPrefix(), "").trim();
            List<Object> args = new ArrayList<>();

            for (ParsedCommand.ParsedArgument argument : command.getArguments()) {
                try {
                    Pair<Boolean, List<Object>> matchResult = checkIfArgumentCouldMatch(argument, afterPrefix);
                    if (!matchResult.getFirst()) continue;

                    args = matchResult.getSecond();
                    List<Object> params = buildParams(argument, event, args, command);

                    argument.getMethod().setAccessible(true);
                    argument.getMethod().invoke(command.getCommand(), params.toArray());
                    return true;
                } catch (Exception e) {
                    handleException(event, command, e, args);
                }
            }
            handleException(event, command, new CommandNotFoundException("Command not found"), args);
        }
        return false;
    }

    private @NotNull List<Object> buildParams(ParsedCommand.@NotNull ParsedArgument argument, MessageReceivedEvent event, List<Object> args, ParsedCommand command) {
        List<Object> params = new ArrayList<>();
        int argIndex = 0;

        for (Parameter parameter : argument.getMethod().getParameters()) {
            if (parameter.isAnnotationPresent(Argument.class)) {
                Object param = args.get(argIndex++);
                params.add(param);
            } else if (parameter.getType().isAssignableFrom(MessageReceivedEvent.class)) {
                params.add(event);
            } else if (parameter.getType().isAssignableFrom(ReceiveContext.class)) {
                params.add(new ReceiveContext(command, args));
            } else {
                params.add(null);
            }
        }

        return params;
    }

    private void handleException(MessageReceivedEvent event, ParsedCommand command, Throwable e, List<Object> args) {
        try {
            Method defaultMethod = findDefaultMethod(command);
            if (defaultMethod == null) throw new RuntimeException(e);

            List<Object> params = buildDefaultParams(defaultMethod, e, event, command, args);
            defaultMethod.setAccessible(true);
            defaultMethod.invoke(command.getCommand(), params.toArray());
        } catch (InvocationTargetException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    private @Nullable Method findDefaultMethod(@NotNull ParsedCommand command) {
        for (Method method : command.getCommand().getClass().getDeclaredMethods()) {
            if (method.getName().equals("defaultCommand")) {
                return method;
            }
        }
        return null;
    }

    private @NotNull List<Object> buildDefaultParams(@NotNull Method method, Throwable e, MessageReceivedEvent event, ParsedCommand command, List<Object> args) {
        List<Object> params = new ArrayList<>();

        for (Parameter parameter : method.getParameters()) {
            if (parameter.getType().isAssignableFrom(Throwable.class)) {
                params.add(e);
            } else if (parameter.getType().isAssignableFrom(ReceiveContext.class)) {
                params.add(new ReceiveContext(command, args));
            } else if (parameter.getType().isAssignableFrom(MessageReceivedEvent.class)) {
                params.add(event);
            } else {
                params.add(null);
            }
        }

        return params;
    }

    @Contract("_, _ -> new")
    private @NotNull Pair<Boolean, List<Object>> checkIfArgumentCouldMatch(ParsedCommand.@NotNull ParsedArgument argument, String after) {
        List<Object> args = new ArrayList<>();
        boolean matches = true;
        String current = after;

        for (ParsedCommand.ParsedSubArgument arg : argument.getSubArguments()) {
            Pair<String, Object> extractionResult = extractArgument(arg, current);
            String extracted = extractionResult.getFirst();
            Object value = extractionResult.getSecond();

            if (value == null || (arg.getType() != GREEDY_STRING && !extracted.equals(arg.getValue()))) {
                matches = false;
            }
            args.add(value);
            current = StringUtility.replaceFirst(current, extracted, "").trim();
        }

        return new Pair<>(matches, args);
    }

    @Contract("_, _ -> new")
    private @NotNull Pair<String, Object> extractArgument(ParsedCommand.@NotNull ParsedSubArgument arg, String current) {
        Matcher matcher;
        String extracted;
        Object value;

        switch (arg.getType()) {
            case RAW, STRING -> {
                matcher = Pattern.compile("(^\\S*)").matcher(current);
                extracted = matcher.find() ? matcher.group() : "";
                value = extracted;
            }
            case REGEX -> {
                matcher = Pattern.compile(arg.getValue()).matcher(current);
                extracted = matcher.find() ? matcher.group() : "";
                value = extracted;
            }
            case DATE -> {
                DateExtractor extractor = new DateExtractor();
                Pair<String, Date> dateResult = extractor.extract(current);
                extracted = dateResult.getFirst();
                value = dateResult.getSecond();
            }
            case CRON_JOB -> {
                CronExtractor extractor = new CronExtractor();
                Pair<String, Cron> cronResult = extractor.extract(current);
                extracted = cronResult.getFirst();
                value = cronResult.getSecond();
            }
            case GREEDY_STRING -> {
                matcher = Pattern.compile("(^.*)").matcher(current);
                extracted = matcher.find() ? matcher.group() : "";
                value = extracted;
            }
            default -> throw new IllegalStateException("Unexpected value: " + arg.getType());
        }

        return new Pair<>(extracted, value);
    }
}