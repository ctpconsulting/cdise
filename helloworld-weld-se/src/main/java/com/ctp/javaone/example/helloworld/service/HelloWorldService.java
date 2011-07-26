package com.ctp.javaone.example.helloworld.service;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HelloWorldService {

	public String sayHello(String name) {
		return "Hello " + name;
	}
	
}
