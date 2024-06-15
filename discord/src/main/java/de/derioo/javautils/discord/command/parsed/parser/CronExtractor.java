package de.derioo.javautils.discord.command.parsed.parser;

import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import kotlin.Pair;

public class CronExtractor extends Extractor<Pair<String, Cron>> {
    @Override
    public Pair<String, Cron> extract(String s) {
        CronParser parser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.UNIX));
        StringBuilder current = new StringBuilder(s);

        for (int i = s.length(); i > 0; i--) {
            try {
                Cron cron = parser.parse(current.toString());
                return new Pair<>(cron.asString(), cron);
            } catch (Exception e) {
                current = new StringBuilder(current.substring(0, current.toString().length() - 1));
            }
        }

        return null;
    }
}
