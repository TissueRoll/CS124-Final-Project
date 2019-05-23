package aspects.tracker;

import java.util.HashMap;

public class TrackerSampleAroundAspect {
	private static TrackerSampleAroundAspect tsaa = null;
	private HashMap<Integer, Integer> nCalls = null;
	
	private TrackerSampleAroundAspect() {
		nCalls = new HashMap<Integer, Integer>();
	}
	
	public static TrackerSampleAroundAspect getAroundTracker() {
		if (tsaa == null)
			tsaa = new TrackerSampleAroundAspect();
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
