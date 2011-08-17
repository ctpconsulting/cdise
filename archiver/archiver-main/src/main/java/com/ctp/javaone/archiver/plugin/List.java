package com.ctp.javaone.archiver.plugin;

import javax.enterprise.context.ApplicationScoped;

import com.ctp.javaone.archiver.command.Command;
import com.ctp.javaone.archiver.persistence.Auditable;

@Command("list")
@ApplicationScoped
@Auditable
public class List implements Plugin {

    public String executeCommand(String... params) {
        StringBuilder greeting = new StringBuilder();
        greeting.append("Available Archiver Commands:\n\n");
        greeting.append("list\n");
        greeting.append("archive\n");
        greeting.append("audit\n");
        greeting.append("exit\n");
        return greeting.toString();
    }

}
