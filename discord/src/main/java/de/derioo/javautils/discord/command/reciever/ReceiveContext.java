package de.derioo.javautils.discord.command.reciever;

import de.derioo.javautils.discord.command.parsed.ParsedCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReceiveContext {

    private final ParsedCommand command;

    private final List<Object> args;

}
