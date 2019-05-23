package aop.annotations;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(ElementType.METHOD)
public @interface Pointcut {
	public String[] methodPatterns();
	public Class<?>[] params();
	public Class<?> returnType();
}
