package de.derioo.javautils.discord.command.annotations;

import de.derioo.javautils.discord.command.reciever.Receiver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Prefix {

    String value();


}
