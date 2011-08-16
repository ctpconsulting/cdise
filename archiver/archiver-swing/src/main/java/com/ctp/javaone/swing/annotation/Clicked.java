package com.ctp.javaone.swing.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Indicate that an UI Component has been clicked.
 * @author sberthouzoz
 * @version <pre>
 * 16.08.2011: Initial version
 * </pre>
 */
@Qualifier
@Inherited
@Target({
        PARAMETER, FIELD
})
@Retention(RUNTIME)
@Documented
public @interface Clicked {

}
