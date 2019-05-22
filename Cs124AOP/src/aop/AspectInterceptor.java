package aop;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.Super;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

public class AspectInterceptor {
	
	// NOTE: pasted from ByteBuddyInterceptDemo intercept 2
	
	// @Origin -- reflective construct of the thing that was called
	// @AllArguments -- takes an array to hold all incoming arguments to the
	// call
	// @SuperCall -- takes a Callable<?> which represents the original super
	// class method call
	// @Super -- represents and uninstrumented version of the class -- note this can limit flexibility of the interceptor
	
	
	@RuntimeType
	public static Object intercept(@Origin Method method, 
								   @AllArguments Object[] args, 
								   @SuperCall Callable<?> zuper,
								   @Super Object zuperClass)
			throws Exception 
	{
		// THIS IS THE GENERIC INTERCEPTION METHOD
			// NOT GOOD IF YOU NEED TO WORRY ABOUT THE RETURN TYPE
			// AND THE PROXIED METHOD DOES NOT RETURN ANYTHING
		
		// USUALLY THIS STYLE IS BEST FOR SITUATIONS YOU DONT CARE ABOUT THE RETURN
		// JUST RETURN zuper.call()
		
		System.out.println("ENTER METHOD: "+method.getName());
		
		Object returnValue =  zuper.call();	
		
		System.out.println("EXIT METHOD: "+method.getName());
		
		return returnValue;

	}
}
