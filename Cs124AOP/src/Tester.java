import aop.ProxyMaker;

public class Tester {

	public static void main(String[] args) throws Exception{

		// SAMPLE USAGE
		TestComponent1 tc = (TestComponent1) ProxyMaker.makeInstance(TestComponent1.class); 
			
		tc.getName();
		tc.getNameLongTime();
		tc.setName("test");
		tc.setName("test");
		tc.toString();
	
		
// EXPECTED OUTPUT
//		BEFORE getName
//		AFTER getName
//		BEFORE getNameLongTime
//		AFTER getNameLongTime
//		AFTER setName
//		AFTER setName
		
		
	}

}
