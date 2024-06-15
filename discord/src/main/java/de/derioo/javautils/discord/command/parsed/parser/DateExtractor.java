package de.derioo.javautils.discord.command.parsed.parser;

import de.derioo.javautils.common.DateUtility;
import kotlin.Pair;

import java.util.Date;

public class DateExtractor extends Extractor<Pair<String, Date>> {
    @Override
    public Pair<String, Date> extract(String s) {
        StringBuilder current = new StringBuilder();
        Pair<String, Date> latest = null;

        for (int i = 0; i < s.length(); i++) {
            try {
                Pair<String, Date> tmp = new Pair<>(current.toString(), DateUtility.parseDynamic(current.toString()));

                if (latest != null && latest.getSecond().getTime() == tmp.getSecond().getTime() && !latest.getFirst().equals(tmp.getFirst())) {
                    return tmp;
                }
                latest = tmp;
            } catch (Exception ignored) {}
            current.append(s.charAt(i));
        }

        return latest;
    }

}
