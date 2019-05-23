package aspects;

import java.lang.reflect.Method;

import aop.annotations.Around;
import aop.annotations.Aspect;
import aop.annotations.Pointcut;
import aop.annotations.Targets;

@Aspect
public class SampleAroundChainAspect {
	@Pointcut(methodPatterns= {"set.*", "get.*"}, params = {}, returnType = void.class)
	public void methods()
	{
	}
	

	@Targets(classPatterns= "Test.*")
	public void targets()
	{
	}
	
	// assume all methods are this parameter signature
	@Around
	public Object around1(Object instance, Method m, Object[] args) throws Exception
	{
		Object returnedObject = null;

		System.out.println("AROUND1 START" + m.getName());
		returnedObject = around2(instance, m, args);
		System.out.println("AROUND2 END" + m.getName());

		return returnedObject;
	}

	public Object around2(Object instance, Method m, Object[] args) throws Exception {

		Object returnedObject = null;
		System.out.println("AROUND START "+m.getName());
		returnedObject = m.invoke(instance, args);
		System.out.println("AROUND END "+m.getName());

		return returnedObject;
	}
}
