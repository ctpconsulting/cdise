package com.ctp.javaone.archiver.command;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import javax.inject.Qualifier;

@Qualifier
@Retention(RUNTIME)
@Target({
        TYPE, METHOD, FIELD, PARAMETER
})
public @interface Command {

    String value();
}
