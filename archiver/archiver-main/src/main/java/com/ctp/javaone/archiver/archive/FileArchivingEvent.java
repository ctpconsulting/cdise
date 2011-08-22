package com.ctp.javaone.archiver.archive;

import java.io.File;

public class FileArchivingEvent {

    private File source;
    private File target;

    public FileArchivingEvent(File source, File target) {
        this.source = source;
        this.target = target;
    }

    public File getSource() {
        return source;
    }

    public File getTarget() {
        return target;
    }

}
