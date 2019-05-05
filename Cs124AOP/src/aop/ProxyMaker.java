package aop;

public class ProxyMaker 
{
	static AspectManager manager = new AspectManager();
	
	public static Object makeInstance(Class target) throws Exception
	{
		// check if target class requires using a proxy
			// if not, just return a new instance of the class
		
		return null;

	}
	
	
}
