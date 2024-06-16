package de.derioo.javautils.discord.command.parsed;

import com.cronutils.model.Cron;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@RequiredArgsConstructor
public class ParsedArgument<T> {

    private final T value;

    private final ParsedCommand.ParsedSubArgument argument;


    public static class StringArgument extends ParsedArgument<String> {


        public StringArgument(String value, ParsedCommand.ParsedSubArgument argument) {
            super(value, argument);
        }

        public String get() {
            return getValue();
        }

    }

    public static class DateArgument extends ParsedArgument<Date> {


        public DateArgument(Date value, ParsedCommand.ParsedSubArgument argument) {
            super(value, argument);
        }

        public Date get() {
            return getValue();
        }

    }

    public static class CronArgument extends ParsedArgument<Cron> {


        public CronArgument(Cron value, ParsedCommand.ParsedSubArgument argument) {
            super(value, argument);
        }

        public Cron get() {
            return getValue();
        }

    }


}
