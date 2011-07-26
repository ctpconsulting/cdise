package com.ctp.javaone.montecarlo;

import java.util.Random;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import com.ctp.javaone.montecarlo.annotation.MaxIterations;
import com.ctp.javaone.montecarlo.annotation.MaxThreads;

@ApplicationScoped
public class Generator {

	private static final long ITERATIONS = 10000l;
	public static final int MAX_THREADS = 5;
	
	private Random random;
	
	@Produces
	@MaxIterations
	public long interations() {
		return ITERATIONS;
	}
	
	@Produces
	public Random getRandom() {
		return random;
	}
	
	@Produces
	@MaxThreads
	public int getMaxThreads() {
		return MAX_THREADS;
	}
	
	@PostConstruct
	public void init() {
		random = new Random(System.currentTimeMillis());
	}
	
}
