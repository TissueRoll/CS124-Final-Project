package aspects;
import java.lang.reflect.Method;

import aop.annotations.Aspect;
import aop.annotations.Before;
import aop.annotations.Pointcut;
import aop.annotations.Targets;
import aspects.tracker.TrackerSampleBeforeAspect;


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
	public void before(Integer callerID, Method m, Object[] args) throws Exception
	{
		System.out.println("BEFORE "+m.getName());
		TrackerSampleBeforeAspect tsba = TrackerSampleBeforeAspect.getBeforeTracker();
		System.out.println("\tBEFORE: times called by callerID " + callerID +  ": " + tsba.getCount(callerID));
	}
}
