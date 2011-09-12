package com.ctp.javaone.montecarlo.instance;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.ctp.javaone.montecarlo.event.SimulationResultEvent;
import com.ctp.javaone.montecarlo.gui.ResultsGUI;

@ApplicationScoped
public class Result {
	
        @Inject
        private ResultsGUI resultsGUI;

	private long inUnitCircle = 0;
	private long total = 0; 
	
	public void collectResult(@Observes SimulationResultEvent result) {
		addResult(result.getInUnitCircle(), result.getTotal());
		double pi = calculatePi();
		System.out.printf("Intermediate result: %f\n", pi);
		resultsGUI.updateResult(pi);
	}
	
	private synchronized void addResult(long in, long samples) {
		inUnitCircle += in;
		total += samples;
	}
	
	public synchronized double calculatePi() {
		return 4.0d * inUnitCircle / total;
	}
	
}
