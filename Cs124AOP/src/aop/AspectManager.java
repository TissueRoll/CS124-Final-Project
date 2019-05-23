package aop;

import java.lang.annotation.Annotation;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import aop.annotations.After;
import aop.annotations.Aspect;
import aop.annotations.Before;
import aop.annotations.Pointcut;
import aop.annotations.Targets;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;

public class AspectManager {
	
	private static AspectManager singleton = null;
	private ArrayList<Object> aspectList = new ArrayList();
	private ArrayList<Object> beforeList = new ArrayList();
	private ArrayList<Object> afterList = new ArrayList();
	
	private AspectManager() 
	{
		init();
	}
	
	// used singleton design pattern because it just does 1 thing and it can just persist through all
	public static AspectManager getAspectManager()
	{
		if (singleton == null) {
			singleton = new AspectManager();
		}
		return singleton;
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
				for (Method m : c.getDeclaredMethods()) {
					if(m.getDeclaredAnnotation(Before.class) != null) {
						beforeList.add(aspect);
					}
					if(m.getDeclaredAnnotation(After.class) != null) {
						afterList.add(aspect);
					}
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
			Pointcut p = (Pointcut) aspect.getDeclaredMethod("methods").getDeclaredAnnotation(Pointcut.class);
			for(Method m: aspect.getDeclaredMethods()) {
				if(m.getDeclaredAnnotation(Before.class) != null && pointcutMatch(p, method)) {
//					System.out.println("processBefore: " + args.length);
//					System.out.println(m.getName());
					Object[] nargs = {method, args};
					m.invoke(beforeAspect, nargs);	
				}
			}
		}
	}

	public void processAfter(Method method,  Object[] args) throws Exception
	{
		// process all the @After that are applicable to this Method
		for(Object afterAspect: afterList) {
			Class aspect = afterAspect.getClass();
			Pointcut p = (Pointcut) aspect.getDeclaredMethod("methods").getDeclaredAnnotation(Pointcut.class);
			for(Method m: aspect.getDeclaredMethods()) {
				if(m.getDeclaredAnnotation(After.class) != null && pointcutMatch(p, method)) {
					Object[] nargs = {method, args};
					m.invoke(afterAspect, nargs);	
				}
			}
		}
	}
	
	public Object processAround(Method method, Object[] args) throws Exception
	{
		return null;
	}
	
	public boolean pointcutMatch(Pointcut p, Method method) {
		for(String pattern: p.methodPatterns()) {
			if(Pattern.matches(pattern, method.getName())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean needsProxy(Class c) throws Exception
	{
		// go through all Aspects scanned
			// see if the class name matches any of their @Targets patterns
		for(Object o: aspectList) {
			Class aspect = o.getClass();
			Targets target = (Targets) aspect.getDeclaredMethod("targets").getDeclaredAnnotation(Targets.class);
			for(String s : target.classPatterns()) {
				if(c.getName().matches(s)) {
					return true;
				}
			}
		}
		return false;
	}

}
