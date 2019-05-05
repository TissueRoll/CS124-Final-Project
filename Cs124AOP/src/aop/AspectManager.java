package aop;

import java.lang.reflect.Method;


public class AspectManager {


	public AspectManager()
	{
		init();
	}
	
	public void init()
	{
		// scan all @Aspect in folder
			// for each
				// scan @Targets, @Pointcut and advices

	}
	

	public void processBefore(Method method,  Object[] args)
	{
		// process all the @Before that are applicable to this Method
	}

	public void processAfter(Method method,  Object[] args)
	{
		// process all the @After that are applicable to this Method
	}

	
	public boolean needsProxy(Class c)
	{
		// go through all Aspects scanned
			// see if the class name matches any of their @Targets patterns
		
		return false;
	}

}
