package com.ctp.javaone.archiver.archive;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.jboss.weld.environment.se.contexts.ThreadScoped;

@ThreadScoped
public class ArchivingTask {

    @Inject
    private Event<FileArchivingEvent> archiveEvent;

    private File source;
    private File target;

    public ArchivingTask() {
    }

    public void run() {
        copyFile();
        fireResultEvent();
    }

    private void copyFile() {
        try {
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
        } catch (FileNotFoundException e) {
            throw new NullPointerException(e.getMessage());
        } catch (IOException e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public void setSource(File source) {
        this.source = source;
    }

    public void setTarget(File target) {
        this.target = target;
    }

    private synchronized void fireResultEvent() {
        archiveEvent.fire(new FileArchivingEvent(source, target));
    }
}
