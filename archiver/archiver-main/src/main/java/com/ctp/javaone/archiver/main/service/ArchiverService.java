package com.ctp.javaone.archiver.main.service;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ArchiverService {

	public String sayHello(String name) {
		return "Hello " + name;
	}
	
}
