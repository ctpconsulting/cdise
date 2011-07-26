package com.ctp.javaone.montecarlo.instance;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import com.ctp.javaone.montecarlo.event.SimulationResultEvent;

@ApplicationScoped
public class Result {
	
	private long inUnitCircle = 0;
	private long total = 0; 
	
	public void collectResult(@Observes SimulationResultEvent result) {
		addResult(result.getInUnitCircle(), result.getTotal());
		System.out.printf("Intermediate result: %f\n", calculatePi());
	}
	
	private synchronized void addResult(long in, long samples) {
		inUnitCircle += in;
		total += samples;
	}
	
	public synchronized double calculatePi() {
		return 4.0d * inUnitCircle / total;
	}
	
}
