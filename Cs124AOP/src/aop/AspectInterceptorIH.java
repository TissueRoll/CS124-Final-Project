package aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class AspectInterceptorIH implements InvocationHandler {
	private Object original;
	public AspectInterceptorIH(Object o) {
		original = o;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		Object returnedObject = null;
		AspectManager am = AspectManager.getAspectManager();
		am.processBefore(method, args);
		returnedObject = am.processAround(original, method, args);
//		returnedObject = method.invoke(original, args); // for no around
		am.processAfter(proxy.hashCode(), method, args);
		return returnedObject;
	}
	
}
