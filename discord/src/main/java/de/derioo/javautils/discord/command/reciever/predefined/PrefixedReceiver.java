package de.derioo.javautils.discord.command.reciever.predefined;

import com.cronutils.model.Cron;
import de.derioo.javautils.common.StringUtility;
import de.derioo.javautils.discord.command.CommandManager;
import de.derioo.javautils.discord.command.annotations.Argument;
import de.derioo.javautils.discord.command.parsed.ParsedCommand;
import de.derioo.javautils.discord.command.parsed.parser.CronExtractor;
import de.derioo.javautils.discord.command.parsed.parser.DateExtractor;
import de.derioo.javautils.discord.command.reciever.ReceiveContext;
import de.derioo.javautils.discord.command.reciever.Receiver;
import kotlin.Pair;
import lombok.Getter;
import lombok.extern.java.Log;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log
@Getter
public class PrefixedReceiver extends Receiver<PrefixedReceiver, MessageReceivedEvent> {


    private final List<Object> args = new ArrayList<>();

    public PrefixedReceiver(CommandManager commandManager) {
        super(commandManager);
    }


    @Override
    public boolean receive(MessageReceivedEvent event) {
        for (ParsedCommand command : getCommandManager().getCommands()) {
            if (!(command.getReceiver() instanceof PrefixedReceiver)) continue;
            if (!event.getMessage().getContentRaw().startsWith(command.getPrefix())) continue;
            String after = event.getMessage().getContentRaw().replaceFirst(command.getPrefix(), "").trim();


            for (ParsedCommand.ParsedArgument argument : command.getArguments()) {
                Pair<Boolean, List<Object>> booleanListPair;
                try {
                    booleanListPair = checkIfArgumentCouldMatch(argument, after);
                } catch (Exception e) {
                    continue;
                }

                args.addAll(booleanListPair.getSecond());
                if (!booleanListPair.getFirst()) {
                    continue;
                }

                List<Object> params = new ArrayList<>();
                int argumentsCount = 0;
                for (Parameter parameter : argument.getMethod().getParameters()) {
                    if (parameter.isAnnotationPresent(Argument.class)) {
                        params.add(booleanListPair.getSecond().get(argumentsCount++));
                        continue;
                    }
                    if (parameter.getType().isAssignableFrom(MessageReceivedEvent.class)) {
                        params.add(event);
                        continue;
                    }
                    if (parameter.getType().isAssignableFrom(ReceiveContext.class)) {
                        params.add(new ReceiveContext(command, args));
                        continue;
                    }
                    params.add(null);
                }
                try {
                    argument.getMethod().setAccessible(true);
                    argument.getMethod().invoke(command.getCommand(), params.toArray(Object[]::new));
                    return true;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
            return false;
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
                    String group = matcher.find() ? matcher.group() : "";
                    if (!group.equals(arg.getValue())) matches = false;
                    args.add(group);
                    current = StringUtility.replaceFirst(current, group, "").trim();
                }
                case REGEX -> {
                    Pattern pattern = Pattern.compile(arg.getValue());
                    Matcher matcher = pattern.matcher(current);
                    String group = matcher.find() ? matcher.group() : "";
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
                    String group = matcher.find() ? matcher.group() : "";
                    args.add(group);
                    current = StringUtility.replaceFirst(current, group, "").trim();
                }
                case GREEDY_STRING -> {
                    Pattern pattern = Pattern.compile("(^.*)");
                    Matcher matcher = pattern.matcher(current);
                    String group = matcher.find() ? matcher.group() : "";
                    args.add(group);
                    current = StringUtility.replaceFirst(current, group, "").trim();
                }
            }
        }
        return new Pair<>(matches, args);
    }
}
