package com.ctp.javaone.montecarlo.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

@Retention(RUNTIME)
@Qualifier
@Target({PARAMETER, FIELD, METHOD})
@Documented
public @interface MaxThreads {

}
