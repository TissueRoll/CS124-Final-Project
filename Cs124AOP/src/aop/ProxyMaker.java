package aop;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.DynamicType.Loaded;
import net.bytebuddy.dynamic.DynamicType.Default.Unloaded;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

public class ProxyMaker 
{
	static AspectManager manager = AspectManager.getAspectManager();
	
	public static Object makeInstance(Class target) throws Exception
	{
		// check if target class requires using a proxy
			// if not, just return a new instance of the class
		if (manager.needsProxy(target)) {
			// when I need to implement Around, I need to change .method to use the InvocationHandler
			Class<?> proxy = new ByteBuddy()
					.subclass(target)
					.method(ElementMatchers.isDeclaredBy(target))
					//.intercept(MethodDelegation.to(AspectInterceptor.class))
					.intercept(InvocationHandlerAdapter.of(new AspectInterceptorIH(target.newInstance())))
					.make()
					.load(target.getClassLoader())
					.getLoaded();
			
			Object o = proxy.newInstance();
			return o;
		} else {
			return target.newInstance();
		}
		// return null;
	}
	
	
}
