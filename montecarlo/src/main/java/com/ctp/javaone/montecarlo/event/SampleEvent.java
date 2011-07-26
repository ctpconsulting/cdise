package com.ctp.javaone.montecarlo.event;

public class SampleEvent {

	private double x,y;
	
	public SampleEvent(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
}
