package aspects.tracker;

import java.util.HashMap;

public class TrackerSampleAfterAspect {
	private static TrackerSampleAfterAspect tsaa = null;
	private HashMap<Integer, Integer> nCalls = null;
	
	private TrackerSampleAfterAspect() {
		nCalls = new HashMap<Integer, Integer>();
	}
	
	public static TrackerSampleAfterAspect getAfterTracker() {
		if (tsaa == null)
			tsaa = new TrackerSampleAfterAspect();
		return tsaa;
	}
	
	public int getCount(Integer o) {
		if (nCalls.containsKey(o)) {
			nCalls.put(o, nCalls.get(o) + 1);
		} else {
			nCalls.put(o, 1);
		}
		return nCalls.get(o);
	}
	
	
}
