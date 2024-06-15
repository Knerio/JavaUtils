package de.derioo.javautils.discord.command.parsed.parser;

public abstract class Extractor<T> {

    public abstract T extract(String s);

}
