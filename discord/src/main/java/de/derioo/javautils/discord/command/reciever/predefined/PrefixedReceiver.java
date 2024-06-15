package de.derioo.javautils.discord.command.reciever.predefined;

import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import de.derioo.javautils.common.DateUtility;
import de.derioo.javautils.common.StringUtility;
import de.derioo.javautils.discord.command.CommandManager;
import de.derioo.javautils.discord.command.annotations.Argument;
import de.derioo.javautils.discord.command.annotations.Prefix;
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log
public class PrefixedReceiver extends Receiver<PrefixedReceiver, MessageReceivedEvent> {


    public PrefixedReceiver(CommandManager commandManager) {
        super(commandManager);
    }


    @Override
    public boolean receive(MessageReceivedEvent event) {
        for (ParsedCommand command : getCommandManager().getCommands()) {
            List<Object> args = new ArrayList<>();
            try {
                if (!(command.getReceiver() instanceof PrefixedReceiver)) return false;
                if (!event.getMessage().getContentRaw().startsWith(command.getPrefix()))
                    return false;
                String after = event.getMessage().getContentRaw().replaceFirst(command.getPrefix(), "").trim();

                for (ParsedCommand.ParsedArgument argument : command.getArguments()) {
                    Pair<Boolean, List<Object>> booleanListPair = checkIfArgumentCouldMatch(argument, after);
                    args = booleanListPair.getSecond();
                    if (!booleanListPair.getFirst()) {
                        break;
                    }
                    List<Object> parameters = new ArrayList<>();
                    int argumentsCount = 0;
                    for (Parameter parameter : argument.getMethod().getParameters()) {
                        if (parameter.isAnnotationPresent(Argument.class)) {
                            Object e = booleanListPair.getSecond().get(argumentsCount);
                            parameters.add(e);
                            argumentsCount++;
                            if (!parameter.getType().equals(e.getClass())) {
                                log.warning("Types dont match. Expected " + e.getClass());
                            }
                            continue;
                        }
                    }
                    try {
                        argument.getMethod().setAccessible(true);
                        argument.getMethod().invoke(command.getCommand(), parameters.toArray(Object[]::new));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                    return true;
                }
            } catch (Throwable e) {
                try {
                    Method method = command.getCommand().getClass().getDeclaredMethod("catcher");
                    method.setAccessible(true);
                    List<Object> params = new ArrayList<>();
                    for (Parameter parameter : method.getParameters()) {
                        if (parameter.getType().isInstance(Throwable.class)) {
                            params.add(e);
                            continue;
                        }
                        if (parameter.getType().isAssignableFrom(ReceiveContext.class)) {
                            params.add(new ReceiveContext(command, args));
                            continue;
                        }
                        params.add(null);
                    }
                    method.invoke(command.getCommand(), params.toArray(Object[]::new));
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }

            }
        }
        return false;
    }

    @Contract("_, _ -> new")
    private @NotNull Pair<Boolean, List<Object>> checkIfArgumentCouldMatch(ParsedCommand.@NotNull ParsedArgument argument, String after) {
        boolean matches = true;
        List<Object> args = new ArrayList<>();
        String current = after;
        for (ParsedCommand.ParsedSubArgument arg : argument.getSubArguments()) {
            switch (arg.getType()) {
                case RAW -> {
                    Pattern pattern = Pattern.compile("(^\\S*)");
                    Matcher matcher = pattern.matcher(current);
                    boolean a = matcher.find();
                    String group = matcher.group();
                    if (!group.equals(arg.getValue())) matches = false;
                    args.add(group);
                    current = StringUtility.replaceFirst(current, group, "").trim();
                }
                case REGEX -> {
                    Pattern pattern = Pattern.compile(arg.getValue());
                    Matcher matcher = pattern.matcher(current);
                    String group = matcher.group();
                    args.add(group);
                    if (!group.equals(arg.getValue())) matches = false;
                    current = StringUtility.replaceFirst(current, group, "").trim();
                }
                case DATE -> {
                    DateExtractor extractor = new DateExtractor();
                    Pair<String, Date> extract = extractor.extract(current);
                    args.add(extract.getSecond());
                    current = StringUtility.replaceFirst(current, extract.getFirst(), "").trim();
                }
                case CRON_JOB -> {
                    CronExtractor extractor = new CronExtractor();
                    Pair<String, Cron> extract = extractor.extract(current);
                    args.add(extract.getSecond());
                    current = StringUtility.replaceFirst(current, extract.getFirst(), "").trim();
                }
                case STRING -> {
                    Pattern pattern = Pattern.compile("(^\\S*)");
                    Matcher matcher = pattern.matcher(current);
                    boolean a = matcher.find();
                    String group = matcher.group();
                    args.add(group);
                    current = StringUtility.replaceFirst(current, group, "").trim();
                }
                case GREEDY_STRING -> {
                    Pattern pattern = Pattern.compile("(^.*)");
                    Matcher matcher = pattern.matcher(current);
                    boolean a = matcher.find();
                    String group = matcher.group();
                    args.add(group);
                    current = StringUtility.replaceFirst(current, group, "").trim();
                }
            }
        }
        return new Pair<>(matches, args);
    }
}
