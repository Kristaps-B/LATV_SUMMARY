package model;

import java.util.ArrayList;

public class WordListGenerator {

	private ArrayList<Sentence> sentenceList;

	private ArrayList<Word> allWordList;

	private WordComparison wordComparison;

	public WordListGenerator(ArrayList<Sentence> sentenceList) {
		this.sentenceList = sentenceList;

		allWordList = new ArrayList<>();
		wordComparison = new WordComparison();
	}

	public ArrayList<Word> getWordList() {
		return allWordList;
	}

	public void generateWordList() {
		for (int i = 0; i < sentenceList.size(); i++) {
			ArrayList<OneWord> wordList = sentenceList.get(i).getWordList();

			for (int j = 0; j < wordList.size(); j++) {
				int poz = 0;
				if ((poz = getPositionInAllWordList(wordList.get(j).getWord(),
						allWordList)) == allWordList.size()) {
					allWordList.add((new Word(wordList.get(j).getWord())));
					int newID = allWordList.size();
					allWordList.get(allWordList.size() - 1).setID(newID);

					// Vel vairâk

				}
				wordList.get(j).setIndex(poz);
				allWordList.get(poz).addWordToPosition(i, j);
				sentenceList.get(i).setWordID(j, poz);

				// Veido pozi
			}

			// writeWordList();
		}
	}


	private int getPositionInAllWordList(String word, ArrayList<Word> wordList) {
		for (int i = 0; i < wordList.size(); i++) {
			if (wordComparison.isSameWords(wordList.get(i).getWord(), word)) {
				return i;
			}
		}

		return wordList.size();
	}
}
