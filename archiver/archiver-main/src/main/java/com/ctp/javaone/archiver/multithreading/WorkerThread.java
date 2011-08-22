package com.ctp.javaone.archiver.multithreading;

import java.io.File;

import javax.inject.Inject;

import com.ctp.javaone.archiver.archive.ArchivingTask;

public class WorkerThread extends Thread {

    @Inject
    private ThreadPool owner;

    public void run() {
        ArchivingTask target = null;

        do {
            target = owner.getAssignment();
            if (target != null) {
                File[] params = owner.getParams();
                target.setSource(params[0]);
                target.setTarget(params[1]);
                target.run();
                owner.workerEnd();
            }
        } while (target != null);
    }
}
