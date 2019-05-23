package aspects;

import java.lang.reflect.Method;

import aop.annotations.Around;
import aop.annotations.Aspect;
import aop.annotations.Pointcut;
import aop.annotations.Targets;
import aspects.tracker.TrackerSampleAfterAspect;
import aspects.tracker.TrackerSampleAroundAspect;

@Aspect
public class SampleAroundAspect {
	@Pointcut(methodPatterns= {"set.*", "get.*"}, params = {}, returnType = void.class)
	public void methods()
	{
	}
	

	@Targets(classPatterns= "Test.*")
	public void targets()
	{
	}
	
	// assume all methods are this parameter signature
	// @Around
	public Object around(Object instance, Method m, Object[] args) throws Exception
	{
		Object returnedObject = null;
		System.out.println("AROUND START "+m.getName());
		returnedObject = m.invoke(instance, args);
		System.out.println("AROUND END "+m.getName());
		return returnedObject;
	}
	
	@Around
	public Object around2(Integer callerID, Object instance, Method m, Object[] args) throws Exception {
		// can automate this
		// if you want a different around action, just make a new class and link it properly
		SampleAroundChainAspect saci1 = new SampleAroundChainAspect();
		SampleAroundChainAspect saci2 = new SampleAroundChainAspect();
		
		saci1.init(saci2, 1);
		saci2.init(null, 2);
		
		Object returnedObject = saci1.process(instance, m, args);
		TrackerSampleAroundAspect tsaa = TrackerSampleAroundAspect.getAroundTracker();
		System.out.println("\tAROUND: times called by callerID " + callerID +  ": " + tsaa.getCount(callerID));
		
		return returnedObject;
	}
	
}
