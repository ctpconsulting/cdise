package com.ctp.javaone.archiver.command;

import java.io.File;

import javax.inject.Inject;

@ShellCommand("ls")
public class ListDirectory implements Command {
    
    @Inject
    private File currentDir;

    @Override
    public Result execute(String... params) {
        StringBuilder list = new StringBuilder();
        for (File file : currentDir.listFiles()) {
            if (file.isDirectory()) {
                list.append("d ");
            } else {
                list.append("f ");
            }
            list.append(file.getName()).append("\n");
        }
        return new Result(list.toString(), Status.SUCCESS);
    }

}
