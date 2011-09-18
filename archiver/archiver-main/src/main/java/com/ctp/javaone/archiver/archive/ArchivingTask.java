package com.ctp.javaone.archiver.archive;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.slf4j.Logger;

public class ArchivingTask implements Runnable {
    
    @Inject
    private Logger logger;

    @Inject
    private Event<FileArchivingEvent> archiveEvent;

    private File source;
    private File target;
    private long sleep;

    @Override
    public void run() {
        execute();
    }
    
    public void execute() {
        copyFile();
        fireResultEvent();
    }

    private void copyFile() {
        try {
            // waiting loop, simulate log op
            for (int i = 0; i < 5; i++) {
                logger.info("Wait iteration " + i);
                Thread.sleep(sleep);
            }
            File parent = new File(target.getParent());
            if (!parent.exists()) {
                parent.mkdirs();
            }
            InputStream in = new FileInputStream(source);
            OutputStream out = new FileOutputStream(target);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void setSource(File source) {
        this.source = source;
    }

    public void setTarget(File target) {
        this.target = target;
    }

    public void setSleep(long sleep) {
        this.sleep = sleep;
    }

    private void fireResultEvent() {
        archiveEvent.fire(new FileArchivingEvent(source, target));
    }
}
