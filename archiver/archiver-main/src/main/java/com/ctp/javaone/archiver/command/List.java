package com.ctp.javaone.archiver.command;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import org.jboss.weld.environment.se.contexts.ThreadScoped;

@ThreadScoped
@ShellCommand("list")
public class List implements Command {
    
    @Inject 
    private BeanManager beanManager;

    @SuppressWarnings("serial")
    public Result execute(String... params) {
        StringBuilder greeting = new StringBuilder();
        Set<Bean<?>> plugins = beanManager.getBeans(Command.class, new AnnotationLiteral<Any>() {});
        
        greeting.append("Available Archiver Commands:\n");
        for (Bean<?> bean : plugins) {
            Set<Annotation> qualifiers = bean.getQualifiers();
            for (Annotation annotation : qualifiers) {
                if (annotation instanceof ShellCommand){
                    greeting.append("\n");
                    ShellCommand command = (ShellCommand) annotation;
                    greeting.append(command.value());
                }
            }
        }
        return new Result(greeting.toString(), Status.SUCCESS);
    }

}
