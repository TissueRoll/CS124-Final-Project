public class TestComponent1 {

	private String name = "comp1";

	public String getName() {
		return name;
	}

	
	public String getNameLongTime() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format("TestComponent1 [name=%s]", name);
	}
	
	
}
