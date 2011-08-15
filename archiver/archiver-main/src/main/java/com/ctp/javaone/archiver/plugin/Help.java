package com.ctp.javaone.archiver.plugin;

import javax.enterprise.context.ApplicationScoped;

import com.ctp.javaone.archiver.command.Command;

@Command("help")
@ApplicationScoped
public class Help implements Plugin {

    public String executeCommand(String... params) {
        StringBuilder greeting = new StringBuilder();
        greeting.append("Available Archiver Commands:\n\n");
        greeting.append("help\n");
        greeting.append("exit\n");
        return greeting.toString();
    }
}
