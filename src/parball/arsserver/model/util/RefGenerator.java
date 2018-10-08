package parball.arsserver.model.util;

public class RefGenerator {

	public static String generateRef(int length) {
		char[] randomChars = new char[length * 5];
		String ref = "";
		for (int i = 0; i < randomChars.length; i += 2)
			randomChars[i] = (char) ('0' + Math.random() * ('9' - '0'));

		for (int i = 1; i < randomChars.length; i += 2) {
			randomChars[i] = (char) ('a' + Math.random() * ('z' - 'a' + 1));
			if (Math.round(Math.random()) == 1)
				randomChars[i] = Character.toUpperCase(randomChars[i]);
		}
		for (int i = 0; i < length; i++)
			ref += randomChars[(int) Math.round(Math.random() * (randomChars.length - 1))];
		return ref;
	}

}
