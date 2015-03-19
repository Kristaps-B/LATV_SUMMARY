package model;

public class WordComparison {

	private String[] wordBegining = { "aiz", "ap", "at", "bez", "caur", "ie",
			"iz", "ne", "no", "pa", "pâr", "pçc", "pie", "pret", "sa", "uz",
			// Ne
			"neaiz", "neap", "neat", "nebez", "necaur", "neie", "neiz", "neno",
			"nepa", "nepâr", "nepçc", "nepie", "nepret", "nesa", "neuz" };

	private String[] wordEnding = {
			// Pamata galotnes
			"s", "ð", "is", "us", "a",
			"e",
			// Locijumu galotnes
			// Nom
			"i", "as", "es", "âs", "ies",
			// Ìen
			"u", "os",
			// Dat
			"am", "iem", "im", "um", "ai", "âm", "ei", "çm", "ij", "îm", "ûm",
			// Akuz
			"l", "iu",
			// Lok
			"î", "û", "ç",
			"çs",
			"ûs",
			// Vok
			// .....
			// Darbîbas vârdi
			"t", "o", "inâ", "aïâ", "alç", "elç", "uïo",
			// îpaðîbas vârdi
			"ajam", "ais", "ai", "ajai", "ajâ", "iem", "ajiem", "ajos", "os",
			"ajâm", "ajâ", "ajâs" };

	private String getCommonLongestSubstring(String word1, String word2) {
		String commonSubstring = null;

		word1 = word1.toLowerCase();
		word2 = word2.toLowerCase();

		commonSubstring = longestCommonSubstring(word1, word2);

		return commonSubstring;
	}

	private String longestCommonSubstring(String S1, String S2) {
		int Start = 0;
		int Max = 0;
		for (int i = 0; i < S1.length(); i++) {
			for (int j = 0; j < S2.length(); j++) {
				int x = 0;
				while (S1.charAt(i + x) == S2.charAt(j + x)) {
					x++;
					if (((i + x) >= S1.length()) || ((j + x) >= S2.length()))
						break;
				}
				if (x > Max) {
					Max = x;
					Start = i;
				}
			}
		}
		return S1.substring(Start, (Start + Max));
	}

	public boolean isSameWords(String word1, String word2) {
		// Ja vienâdi vârdi
		if (word1.equals(word2))
			return true;

		String commonSubstring = getCommonLongestSubstring(word1, word2);

		if (commonSubstring.length() < 2)
			return false;

		/*
		 * if (commonSubstring.length()>=word1.length()*0.75 ||
		 * commonSubstring.length()>=word2.length()*0.75) { return true; }
		 */

		// Ja nav vienâdi pârbauda tâlâk

		// Sadala pçc kopejâs virknes
		String w1_begining = mySplit(word1, commonSubstring)[0];
		String w1_ending = mySplit(word1, commonSubstring)[1];

		String w2_begining = mySplit(word2, commonSubstring)[0];
		String w2_ending = mySplit(word2, commonSubstring)[1];


		if (isValidWordBegining(w1_begining)
				&& isValidWordBegining(w2_begining)
				&& isValidWordEnding(w1_ending) && isValidWordEnding(w2_ending))
			return true;

		return false;
	}

	public String[] mySplit(String originalWord, String splitter) {
		String[] result = new String[2];

		int startPos = originalWord.indexOf(splitter);
		int endPos = startPos + splitter.length();

		result[0] = originalWord.substring(0, startPos);
		result[1] = originalWord.substring(endPos, originalWord.length());

		return result;
	}

	public boolean isValidWordBegining(String word) {
		if (word.length() == 0)
			return true;

		for (String w : wordBegining) {
			if (w.equals(word))
				return true;
		}

		return false;
	}

	public boolean isValidWordEnding(String word) {
		if (word.length() == 0)
			return true;

		for (String w : wordEnding) {
			if (w.equals(word))
				return true;
		}

		return false;
	}
}
