package aop;

import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import aop.annotations.After;
import aop.annotations.Aspect;
import aop.annotations.Before;
import aop.annotations.Pointcut;
import aop.annotations.Targets;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;

public class AspectManager {

	private ArrayList<Object> aspectList = new ArrayList();
	private ArrayList<Object> beforeList = new ArrayList();
	private ArrayList<Object> afterList = new ArrayList();
	public AspectManager() 
	{
		init();
	}
	
	public void init() 
	{
		// scan all @Aspect in folder
			// for each
				// scan @Targets, @Pointcut and advices
		try {
			ScanResult results = new FastClasspathScanner("aspects").scan();
			List<String> allResults = results.getNamesOfClassesWithAnnotation(Aspect.class);
			for(String s: allResults) {
				Class c = Class.forName(s);
				Object aspect = c.newInstance();
				aspectList.add(aspect);
				if(c.getDeclaredAnnotation(Before.class) != null) {
					beforeList.add(aspect);
				}
				if(c.getDeclaredAnnotation(After.class) != null) {
					afterList.add(aspect);
				}
			}
		} catch (Exception e) {
			//TODO: handle exception
		}

	}
	

	public void processBefore(Method method,  Object[] args) throws Exception
	{
		// process all the @Before that are applicable to this Method	
		for(Object beforeAspect: beforeList) {
			Class aspect = beforeAspect.getClass();
			Pointcut p = (Pointcut) aspect.getDeclaredAnnotation(Pointcut.class);
			for(Method m: aspect.getDeclaredMethods()) {
				if(m.getDeclaredAnnotation(Before.class) != null && pointcutMatch(p, method)) {
					m.invoke(beforeAspect);	
				}
			}
		}
	}

	public void processAfter(Method method,  Object[] args) throws Exception
	{
		// process all the @After that are applicable to this Method
		for(Object afterAspect: afterList) {
			Class aspect = afterAspect.getClass();
			Pointcut p = (Pointcut) aspect.getDeclaredAnnotation(Pointcut.class);
			for(Method m: aspect.getDeclaredMethods()) {
				if(m.getDeclaredAnnotation(Before.class) != null && pointcutMatch(p, method)) {
					m.invoke(afterAspect);	
				}
			}
		}
	}
	
	public boolean pointcutMatch(Pointcut p, Method method) {
		for(String pattern: p.methodPatterns()) {
			if(pattern.matches(method.getName())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean needsProxy(Class c)
	{
		// go through all Aspects scanned
			// see if the class name matches any of their @Targets patterns
		for(Object o: aspectList) {
			Class aspect = o.getClass();
			Targets target = (Targets) aspect.getDeclaredAnnotation(Target.class);
			for(String s : target.classPatterns()) {
				if(c.getName().matches(s)) {
					return true;
				}
			}
		}
		return false;
	}

}
