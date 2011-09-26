package com.ctp.javaone.archiver.log;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;

public class LoggerFactory {

    @Produces 
    public Logger createLogger(InjectionPoint injection) {
        return org.slf4j.LoggerFactory.getLogger(injection.getBean().getBeanClass());
    }

}
