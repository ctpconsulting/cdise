package com.ctp.javaone.archiver.plugin;

import javax.enterprise.context.ApplicationScoped;

import com.ctp.javaone.archiver.command.Command;

@Command("test")
@ApplicationScoped
public class TestCommand implements Plugin {

    public String executeCommand(String... params) {
        return "Executing Test Command..";
    }

}
