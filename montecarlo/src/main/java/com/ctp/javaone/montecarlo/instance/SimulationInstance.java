package com.ctp.javaone.montecarlo.instance;

import java.util.Random;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.jboss.weld.environment.se.contexts.ThreadScoped;

import com.ctp.javaone.montecarlo.annotation.MaxIterations;
import com.ctp.javaone.montecarlo.event.SampleEvent;
import com.ctp.javaone.montecarlo.event.SimulationResultEvent;

@ThreadScoped
public class SimulationInstance {

    @Inject
    private Random random;

    @Inject @MaxIterations
    private long maxIterations;

    @Inject
    private Event<SimulationResultEvent> resultEvent;

    @Inject
    private Event<SampleEvent> sampleEvent;

    private long inUnitCircle;

    public void runSimulation() {

        double x, y;

        for (int i = 0; i < maxIterations; i++) {
            x = nextVal();
            y = nextVal();
            fireSampleEvent(x, y);

            if (x * x + y * y <= 1) {
                inUnitCircle++;
            }
        }

        fireResultEvent();
    }

    private synchronized void fireSampleEvent(double x, double y) {
        sampleEvent.fire(new SampleEvent(x, y));
    }

    private synchronized void fireResultEvent() {
        resultEvent.fire(new SimulationResultEvent(inUnitCircle, maxIterations));
    }

    /**
     * Generates a random number in the range [-1,+1]
     * 
     * @return random number
     */
    private double nextVal() {
        return random.nextDouble() * 2 - 1;
    }

    @PostConstruct
    public void init() {
        inUnitCircle = 0;
    }

}
