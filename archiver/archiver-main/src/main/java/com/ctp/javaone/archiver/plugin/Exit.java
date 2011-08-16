package com.ctp.javaone.archiver.plugin;

import javax.enterprise.context.ApplicationScoped;

import com.ctp.javaone.archiver.command.Command;

@Command("exit")
@ApplicationScoped
public class Exit implements Plugin {

    public String executeCommand(String... params) {
        System.exit(0);
        return null;
    }

}
