package de.derioo.javautils.discord.command.annotations;

import net.dv8tion.jda.internal.utils.tuple.Pair;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Argument {

    ArgumentType type();

    String value() default "";

    enum ArgumentType {

        RAW,
        REGEX,
        STRING,
        CRON_JOB,
        GREEDY_STRING, DATE,

    }


}
