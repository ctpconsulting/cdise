package com.ctp.javaone.archiver.plugin;

import javax.enterprise.context.ApplicationScoped;

import com.ctp.javaone.archiver.command.Command;

@Command("list")
@ApplicationScoped
public class List implements Plugin {

    public String executeCommand(String... params) {
        StringBuilder greeting = new StringBuilder();
        greeting.append("Available Archiver Commands:\n\n");
        greeting.append("list\n");
        greeting.append("archive\n");
        greeting.append("exit\n");
        return greeting.toString();
    }

}
