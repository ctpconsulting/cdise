package com.ctp.javaone.archiver.plugin;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import com.ctp.javaone.archiver.command.Command;
import com.ctp.javaone.archiver.persistence.Auditable;

@Command("list")
@ApplicationScoped
@Auditable
public class List implements Plugin {
    
    @Inject 
    private BeanManager beanManager;

    @SuppressWarnings("serial")
    public String executeCommand(String... params) {
        StringBuilder greeting = new StringBuilder();
        Set<Bean<?>> plugins = beanManager.getBeans(Plugin.class, new AnnotationLiteral<Any>() {});
        
        greeting.append("Available Archiver Commands:\n");
        for (Bean<?> bean : plugins) {
            Set<Annotation> qualifiers = bean.getQualifiers();
            for (Annotation annotation : qualifiers) {
                if (annotation instanceof Command){
                    greeting.append("\n");
                    Command command = (Command) annotation;
                    greeting.append(command.value());
                }
            }
        }
        return greeting.toString();
    }

}
