package aop;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.Super;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

public class AspectInterceptor {
	
	@RuntimeType
	public static Object intercept(@Origin Method method, 
								   @AllArguments Object[] args, 
								   @SuperCall Callable<?> zuper,
								   @Super Object zuperClass)
			throws Exception 
	{
		
		AspectManager am = new AspectManager();
		
		// before
		am.processBefore(method, args);
		
		Object returnValue =  zuper.call();	
		
		// after
		am.processAfter(method, args);
		
		return returnValue;

	}
}
