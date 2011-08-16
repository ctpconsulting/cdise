package com.ctp.javaone.swing.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

/**
 * Set additional Qualifiers to the Event procuce by an Injected UI Component
 * @author sberthouzoz
 * @version <pre>
 * 16.08.2011: Initial version
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({
        FIELD, PARAMETER, METHOD, TYPE
})
public @interface Action {
    Qualifier[] value() default {};

}
