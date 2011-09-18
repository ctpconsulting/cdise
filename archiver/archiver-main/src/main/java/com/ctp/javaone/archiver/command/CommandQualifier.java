package com.ctp.javaone.archiver.command;

import javax.enterprise.util.AnnotationLiteral;
import javax.enterprise.util.Nonbinding;

@SuppressWarnings("all")
public class CommandQualifier extends AnnotationLiteral<Command> implements Command {

    private final String value;

    public CommandQualifier(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    @Nonbinding
    public boolean async() {
        return false; // non-binding
    }

}
