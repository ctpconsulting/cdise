package com.ctp.javaone.swing.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

@Retention(RetentionPolicy.RUNTIME)
@Target({FIELD,PARAMETER,METHOD,TYPE})
public @interface Action {
<<<<<<< HEAD
	Class<? extends Object> value();
	Qualifier[] qualifiers() default {};
=======
    Class<?> value();
    Qualifier[] qualifiers();
>>>>>>> 8155a2d00518726d796c3208ffbb975d604ef268
}
