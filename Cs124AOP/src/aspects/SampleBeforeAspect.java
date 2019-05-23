package aspects;
import java.lang.reflect.Method;

import aop.annotations.Aspect;
import aop.annotations.Before;
import aop.annotations.Pointcut;
import aop.annotations.Targets;


@Aspect
public class SampleBeforeAspect 
{
	@Pointcut(methodPatterns="get.*", params = {}, returnType = Void.class)
	public void methods()
	{
	}
	

	@Targets(classPatterns="Test.*")
	public void targets()
	{
	}
	
	
	// assume all methods are this parameter signature
	@Before
	public void before(Method m, Object[] args) throws Exception
	{
		System.out.println("BEFORE "+m.getName());
	}
}
