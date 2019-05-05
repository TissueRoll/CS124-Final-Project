package aspects;
import java.lang.reflect.Method;

import aop.annotations.After;
import aop.annotations.Aspect;
import aop.annotations.Pointcut;
import aop.annotations.Targets;


@Aspect
public class SampleAfterAspect 
{
	@Pointcut(methodPatterns= {"set.*", "get.*"})
	public void methods()
	{
	}
	

	@Targets(classPatterns="Test.*")
	public void targets()
	{
	}
	
	// assume all methods are this parameter signature
	@After
	public void before(Method m, Object[] args) throws Exception
	{
		System.out.println("AFTER "+m.getName());
	}
}
