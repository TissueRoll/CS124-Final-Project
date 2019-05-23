package aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class AspectInterceptorIH implements InvocationHandler {
	private Object original;
	private Object key;
	public AspectInterceptorIH(Object o, Object key) {
		original = o;
		this.key = key;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		Object returnedObject = null;
		AspectManager am = AspectManager.getAspectManager();
		am.processBefore(key.hashCode(), method, args);
		returnedObject = am.processAround(key.hashCode(), original, method, args);
//		returnedObject = method.invoke(original, args); // for no around
		am.processAfter(key.hashCode(), method, args);
		return returnedObject;
	}
	
}
