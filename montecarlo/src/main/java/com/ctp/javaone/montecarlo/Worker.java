package com.ctp.javaone.montecarlo;

import javax.inject.Inject;

import com.ctp.javaone.montecarlo.instance.SimulationInstance;

public class Worker implements Runnable {
	
	@Inject
	SimulationInstance instance;
	
	public void run() {
		instance.runSimulation();
	}
	
}
