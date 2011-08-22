package com.ctp.javaone.archiver.multithreading;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import com.ctp.javaone.archiver.archive.ArchivingTask;

@ApplicationScoped
public class ThreadPool {
    
    protected Thread threads[] = null;
    
    Collection<ArchivingTask> assignments = new ArrayList<ArchivingTask>(3);
    Collection<File[]> assignmentsParams = new ArrayList<File[]>(3);

    private int activeAssignments;

    private boolean started;
    private boolean ended;

    public static final int SIZE = 2;
    @Inject
    Instance<WorkerThread> workerInstance;

    @PostConstruct
    public void init() {
        threads = new WorkerThread[SIZE];
        activeAssignments = 0;
        started = false;
        ended = false;
        
        for (int i = 0; i < threads.length; i++) {
            threads[i] = workerInstance.get();
            threads[i].start();
        }
    }

    public synchronized void assign(ArchivingTask archiverTask, File[] params) {
        workerBegin();
        assignments.add(archiverTask);
        assignmentsParams.add(params);
        notify();
    }

    public synchronized ArchivingTask getAssignment() {
        try {
            while (!assignments.iterator().hasNext())
                wait();

            ArchivingTask r = (ArchivingTask) assignments.iterator().next();
            assignments.remove(r);
            return r;
        } catch (InterruptedException e) {
            workerEnd();
            return null;
        }
    }

    public synchronized File[] getParams() {
        if (assignmentsParams.iterator().hasNext()) {

            File[] r = (File[]) assignmentsParams.iterator().next();
            assignmentsParams.remove(r);
            return r;
        }
        return null;
    }

    synchronized public void workerBegin() {
        activeAssignments++;
        started = true;
        notify();
    }

    synchronized public void workerEnd() {
        activeAssignments--;
        if (activeAssignments == 0) {
            ended = true;
        }
        notify();
    }
    
    synchronized public void waitDone() {
        try {
            while (activeAssignments > 0) {
                wait();
            }
        } catch (InterruptedException e) {
        }
    }
    
    synchronized public void waitBegin() {
        try {
            while (!started) {
                wait();
            }
        } catch (InterruptedException e) {
        }
    }
    
    public void complete() {
        waitBegin();
        waitDone();
    }

    public boolean isEnded() {
        if (ended) {
            ended = false;
            return true;
        }
        return false;
    }

    public boolean isStarted() {
        return started;
    }

}
