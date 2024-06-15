package de.derioo.javautils.discord.command.reciever;

import de.derioo.javautils.discord.command.CommandManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.events.GenericEvent;

@Getter
@AllArgsConstructor
public abstract class Receiver<R extends Receiver<R, T>,T extends GenericEvent> {

    @Setter
    private CommandManager commandManager;

    public abstract boolean receive(T event);




}
