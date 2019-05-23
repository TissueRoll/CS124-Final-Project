package aspects;
import java.lang.reflect.Method;

import aop.annotations.After;
import aop.annotations.Aspect;
import aop.annotations.Pointcut;
import aop.annotations.Targets;
import aspects.tracker.TrackerSampleAfterAspect;


@Aspect
public class SampleAfterAspect 
{
	@Pointcut(methodPatterns= {"set.*", "get.*"}, params = {}, returnType = Void.class)
	public void methods()
	{
	}
	

	@Targets(classPatterns= "Test.*")
	public void targets()
	{
	}
	
	// assume all methods are this parameter signature
	@After
	public void after(Integer callerID, Method m, Object[] args) throws Exception
	{
		System.out.println("AFTER "+m.getName());
		TrackerSampleAfterAspect tsaa = TrackerSampleAfterAspect.getAfterTracker();
		System.out.println("\tAFTER: times called by callerID " + callerID +  ": " + tsaa.getCount(callerID));
	}
}
