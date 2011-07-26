package com.ctp.javaone.montecarlo.event;

public class SimulationResultEvent {
	
	public SimulationResultEvent(long inUnitCircle, long total) {
		this.inUnitCircle = inUnitCircle;
		this.total = total;
	}
	
	private long inUnitCircle;
	private long total;
	
	public long getInUnitCircle() {
		return inUnitCircle;
	}
	
	public long getTotal() { 
		return total;
	}

}
