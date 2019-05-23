package aspects;

import java.lang.reflect.Method;

import aop.annotations.Around;
import aop.annotations.Aspect;
import aop.annotations.Pointcut;
import aop.annotations.Targets;

@Aspect
public class SampleAroundAspect {
	@Pointcut(methodPatterns= {"set.*", "get.*"}, params = {}, returnType = Void.class)
	public void methods()
	{
	}
	

	@Targets(classPatterns= "Test.*")
	public void targets()
	{
	}
	
	// assume all methods are this parameter signature
	@Around
	public Object before(Method m, Object[] args) throws Exception
	{
		Object returnedObject = null;
		System.out.println("AROUND START "+m.getName());
		
		System.out.println("AROUND END "+m.getName());
		return returnedObject;
	}
}
