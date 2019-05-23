package aop;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.Super;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

/*
 * when using Around, the Object is IMPORTANT -- need to change this implementation
 */

public class AspectInterceptor {
	@RuntimeType
	public static Object intercept(@Origin Method method, 
								   @AllArguments Object[] args, 
								   @SuperCall Callable<?> zuper,
								   @Super Object zuperClass)
			throws Exception 
	{
		
		AspectManager am = AspectManager.getAspectManager();
		
		// before		
		am.processBefore(method, args);
		
		// need to be able to use around here (am.processAround should return Object)
		Object returnValue =  zuper.call();	
		
		// after
		am.processAfter(method, args);
		
		return returnValue;

	}
}
