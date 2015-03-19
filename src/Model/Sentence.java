package model;

import java.util.ArrayList;

public class Sentence {
	private String originalSentence = null;

	private ArrayList<OneWord> wordList = new ArrayList<>();

	private final int ID;

	private int[] wordID;

	private double rank;

	public Sentence(int ID, String originalSentence) {
		this.originalSentence = originalSentence;
		this.ID = ID;

		createWordList();
		createWordIDArray(wordList.size());
	}

	private void createWordList() {
		// Veido svarigo vardu sarakstu
		String tempSentence = originalSentence
				.replaceAll("[^a-zA-ZÂÈÇÌÎÍÏÒÐÛÞâèçìîíïòðûþ ]", "")
				.replaceAll("  ", " ").toLowerCase();

		String[] wordArray = tempSentence.split(" ");

		/*
		 * System.out.println("=============================================");
		 * System.out.println("Teikuma apstrade!");
		 * System.out.println("Pagaidu teikums: "+tempSentence);
		 */

		// Pievieno vardu sarakstam svarigos vardus
		for (int i = 0; i < wordArray.length; i++) {
			// System.out.print(wordArray[i]+" ");
			if (!StopWords.isStopWord(wordArray[i])
					&& wordArray[i].length() > 0) {
				wordList.add(new OneWord(wordArray[i], -1));
			}
		}
		
		Stemmer stemmer = new Stemmer();
		
		for (int i= 0; i < wordList.size(); i++) {
			wordList.get(i).setStem(stemmer.stem(wordList.get(i).getWord()));
		}

	}

	public String getOriginalSentence() {
		return originalSentence;
	}

	public ArrayList<OneWord> getWordList() {
		return wordList;
	}

	public String getNewSentence() {
		String rez = "";

		for (OneWord w : wordList) {
			rez += w.getWord()+" ["+w.getStem()+"] ";
		}

		return rez;
	}

	public int getID() {
		return ID;
	}

	public double getRank() {
		return rank;
	}

	public void setRank(double rank) {
		this.rank = rank;
	}

	private void createWordIDArray(int length) {
		wordID = new int[length];
	}

	public void setWordID(int index, int ID) {
		wordID[index] = ID;
	}

	public int getWordID(int index) {
		return wordID[index];
	}

}
