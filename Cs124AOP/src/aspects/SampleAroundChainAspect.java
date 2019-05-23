package aspects;

import java.lang.reflect.Method;

import aop.annotations.Around;
import aop.annotations.Aspect;
import aop.annotations.Pointcut;
import aop.annotations.Targets;

public class SampleAroundChainAspect implements SampleAroundChainInterface{
	SampleAroundChainInterface saci;
	int level;
	
	public SampleAroundChainAspect() {
		saci = null;
		level = -1;
	}
	
	@Pointcut(methodPatterns= {"set.*", "get.*"}, params = {}, returnType = void.class)
	public void methods()
	{
	}
	

	@Targets(classPatterns= "Test.*")
	public void targets()
	{
	}

	@Override
	public Object process(Object instance, Method m, Object[] args) throws Exception {
		// TODO Auto-generated method stub
		Object returnedObject = null;
		System.out.println("AROUND" + level + " START "+m.getName());
		if (saci == null) returnedObject = m.invoke(instance, args);
		else returnedObject = saci.process(instance, m, args);
		System.out.println("AROUND" + level + " END "+m.getName());
		return returnedObject;
	}

	@Override
	public void init(SampleAroundChainInterface nextSaci, int level) {
		// TODO Auto-generated method stub
		this.saci = nextSaci;
		this.level = level;
	}
}
