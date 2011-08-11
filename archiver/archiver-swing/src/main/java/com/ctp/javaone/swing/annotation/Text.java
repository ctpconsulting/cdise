package com.ctp.javaone.swing.annotation;

import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({FIELD, PARAMETER, METHOD,TYPE})
public @interface Text {
String value();
}
