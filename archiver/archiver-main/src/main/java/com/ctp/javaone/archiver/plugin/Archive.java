package com.ctp.javaone.archiver.plugin;

import javax.enterprise.context.ApplicationScoped;

import com.ctp.javaone.archiver.command.Command;

@Command("archive")
@ApplicationScoped
public class Archive implements Plugin {

    public String executeCommand(String... params) {
        return "Execute Archiving (not yet implemented)...";
    }

}
