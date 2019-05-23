package aspects;

import java.lang.reflect.Method;

public interface SampleAroundChainInterface {
	public void init(SampleAroundChainInterface nextSaci, int level);
	public Object process(Object instance, Method m, Object[] args) throws Exception;
}
