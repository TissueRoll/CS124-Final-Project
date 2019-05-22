package aop;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

public class ProxyMaker 
{
	static AspectManager manager = new AspectManager();
	
	public static Object makeInstance(Class target) throws Exception
	{
		// check if target class requires using a proxy
			// if not, just return a new instance of the class
		if (manager.needsProxy(target)) {
			ClassLoader classLoader = target.getClassLoader();
			Class<?> proxiedTarget = new ByteBuddy()
					.subclass(target)
					.method(ElementMatchers.any())
					.intercept(FixedValue.value("hi"))
					.make()
					.load(classLoader)
					.getLoaded();
			return proxiedTarget.newInstance();
		} else {
			return target.newInstance();
		}
		// return null;
	}
	
	
}
