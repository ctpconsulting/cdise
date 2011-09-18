package com.ctp.javaone.archiver.command;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

@Qualifier
@Retention(RUNTIME)
@Target(TYPE)
public @interface Command {

    String value();
    
    @Nonbinding
    boolean async() default false;
    
}
