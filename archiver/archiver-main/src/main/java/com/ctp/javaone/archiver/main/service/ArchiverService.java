package com.ctp.javaone.archiver.main.service;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ArchiverService {

	public String sayHello(String name) {
		return "Hello " + name;
	}
	
	public String getGreeting() {
		StringBuilder greeting = new StringBuilder();
		greeting.append("Welcome to archiver!\n");
		greeting.append("Run 'help' for a list of the available commands. \n\n");
		return greeting.toString();
	}
	
	public enum Command {
    	help, exit, unknown;
    	
    	public static Command toCommand(String command)
        {
            try {
                return valueOf(command);
            } 
            catch (Exception e) {
                return unknown;
            }
        }   
    }

	public String getHelp() {
		StringBuilder greeting = new StringBuilder();
		greeting.append("Available Archiver Commands:\n\n");
		greeting.append("help\n");
		greeting.append("exit\n");
		return greeting.toString();
	}
}
