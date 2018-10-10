package parball.arsserver.sessionhandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import parball.arsserver.model.Lecturer;

public class AvailableLecturersMapSingleton {
	private static Map<String, Lecturer> availableLecturersmap = new ConcurrentHashMap<>();

	public static Map<String, Lecturer> getInstance() {
		return availableLecturersmap;
	}

}
