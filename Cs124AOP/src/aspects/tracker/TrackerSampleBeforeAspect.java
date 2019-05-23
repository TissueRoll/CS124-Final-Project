package aspects.tracker;

import java.util.HashMap;

public class TrackerSampleBeforeAspect {
	private static TrackerSampleBeforeAspect tsba = null;
	private HashMap<Integer, Integer> nCalls = null;
	
	private TrackerSampleBeforeAspect() {
		nCalls = new HashMap<Integer, Integer>();
	}
	
	public static TrackerSampleBeforeAspect getBeforeTracker() {
		if (tsba == null)
			tsba = new TrackerSampleBeforeAspect();
		return tsba;
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
