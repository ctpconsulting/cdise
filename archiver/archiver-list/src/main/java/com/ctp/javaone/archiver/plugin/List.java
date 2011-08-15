package com.ctp.javaone.archiver.plugin;

import javax.enterprise.context.ApplicationScoped;

import com.ctp.javaone.archiver.command.Command;

@Command("list")
@ApplicationScoped
public class List implements Plugin {

    public String executeCommand(String... params) {
        return "Executing List Command..";
    }

}
