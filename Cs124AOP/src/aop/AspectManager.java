package aop;

import java.lang.annotation.Annotation;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import aop.annotations.After;
import aop.annotations.Around;
import aop.annotations.Aspect;
import aop.annotations.Before;
import aop.annotations.Pointcut;
import aop.annotations.Targets;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;

public class AspectManager {
	
	private static AspectManager singleton = null;
	
	private HashMap<Object, Pointcut> pointcutMap = new HashMap<Object, Pointcut>();
	private HashMap<Object, Targets> targetsMap = new HashMap<Object, Targets>();
	private HashMap<Object, Method> beforeMap = new HashMap<Object, Method>();
	private HashMap<Object, Method> aroundMap = new HashMap<Object, Method>();
	private HashMap<Object, Method> afterMap = new HashMap<Object, Method>();
	
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
	
	private void init() 
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
				for (Method m : c.getDeclaredMethods()) {
					if(m.getDeclaredAnnotation(Targets.class)!= null) {
						targetsMap.put(aspect, (Targets) m.getDeclaredAnnotation(Targets.class));
					}
					if(m.getDeclaredAnnotation(Pointcut.class)!= null) {
						pointcutMap.put(aspect, (Pointcut) m.getDeclaredAnnotation(Pointcut.class));
					}
					if(m.getDeclaredAnnotation(Before.class) != null) {
						beforeMap.put(aspect, m);
					}
					if(m.getDeclaredAnnotation(After.class) != null) {
						afterMap.put(aspect, m);
					}
					if(m.getDeclaredAnnotation(Around.class) != null) {
						aroundMap.put(aspect, m);
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
		for (Map.Entry<Object, Method> entry : beforeMap.entrySet()) {
			if (pointcutMatch(pointcutMap.get(entry.getKey()), method, args)) {
				Object[] nargs = {method, args}; // this stays
				entry.getValue().invoke(entry.getKey(), nargs);	// this stays
			}
		}
	}

	public void processAfter(Integer callerID, Method method,  Object[] args) throws Exception
	{
		// process all the @After that are applicable to this Method
		for (Map.Entry<Object, Method> entry : afterMap.entrySet()) {
			if (pointcutMatch(pointcutMap.get(entry.getKey()), method, args)) {
				Object[] nargs = {method, args}; // this stays
				entry.getValue().invoke(entry.getKey(), nargs);	
			}
		}
	}
	
	// probably works
	public Object processAround(Object instance, Method method, Object[] args) throws Exception
	{
		Object returnedObject = null;
		for (Map.Entry<Object, Method> entry : aroundMap.entrySet()) {
			if (pointcutMatch(pointcutMap.get(entry.getKey()), method, args)) {
				Object[] nargs = {instance, method, args}; // this stays
				returnedObject = entry.getValue().invoke(entry.getKey(), nargs);
			}
		}
		return returnedObject;
	}
	
	// need to apply matching to param list and return type
	public boolean pointcutMatch(Pointcut p, Method method, Object args[]) {
		// check the RegEx
		boolean regexMatched = false; // at least one must match
		for(String pattern: p.methodPatterns()) {
			if(Pattern.matches(pattern, method.getName())) {
				regexMatched = true;
			}
		}
		
		// check the Parameters and Return Type
		boolean paramsAndReturnMatched = true; // all must match
		Class<?>[] params = p.params();
		if (args.length != p.params().length)  
		{
			paramsAndReturnMatched = false;
		}
		else 
		{
			for (int i = 0; i < args.length; i++) {
				if (args[i].getClass() != params[i]) {
					paramsAndReturnMatched = false;
				}
			}
		}
		
		if (method.getReturnType() != p.returnType())
			paramsAndReturnMatched = false;
		
		return regexMatched|paramsAndReturnMatched;
	}
	
	public boolean needsProxy(Class c, Object obj) throws Exception
	{
		// go through all Aspects scanned
			// see if the class name matches any of their @Targets patterns
		for (Map.Entry<Object, Targets> entry : targetsMap.entrySet()) {
			for(String s : entry.getValue().classPatterns()) {
				if(c.getName().matches(s)) {
					// store obj hashcode here
					
					return true;
				}
			}
		}
		return false;
	}

}
