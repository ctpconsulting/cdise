package com.ctp.javaone.swing.annotation;

import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

@Retention(RetentionPolicy.RUNTIME)
@Target({FIELD,PARAMETER,METHOD,TYPE})
public @interface Action {
	Class<?> value();
	Qualifier[] qualifiers();
}
