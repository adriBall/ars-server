package parball.arsserver.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Gamification {
	public static final String SPLIT_ROW = "\n";
	public static final String SPLIT_COLUMN = ":";

	class Pair {
		int hits;
		int fails;
	}

	private Map<Audience, Pair> usersRows = new ConcurrentHashMap<>();

	public void addResult(Audience audience, boolean success) {

		if (!usersRows.containsKey(audience))
			usersRows.putIfAbsent(audience, new Pair());
		Pair pair = usersRows.get(audience);

		if (success)
			pair.hits++;
		else
			pair.fails++;

	}

	@Override
	public String toString() {
		String gamification = "";
		Set<Audience> keys = usersRows.keySet();
		List<Audience> sortedKeys = new LinkedList<>();
		for (Audience audience : keys)
			sortedKeys.add(audience);

		sortedKeys.sort((audience1, audience2) -> Integer.compare(usersRows.get(audience1).hits,
				usersRows.get(audience2).hits));
		for (Audience audience : sortedKeys) {
			Pair pair = usersRows.get(audience);
			gamification += audience.getUsername() + SPLIT_COLUMN + pair.hits + SPLIT_COLUMN + pair.fails + SPLIT_ROW;
		}
		return gamification;
	}

}
