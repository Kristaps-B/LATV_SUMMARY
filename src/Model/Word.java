package model;

import java.util.ArrayList;

public class Word {
	private String word;

	private int ID = 0;

	private double rank = 0;

	private ArrayList<WordPosition> wordPosition;

	public Word(String word) {
		this.word = word;

		wordPosition = new ArrayList<>();
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public int getID() {
		return this.ID;
	}

	public void addWordToPosition(int sentenceNumb, int wordPos) {
		wordPosition.add(new WordPosition(sentenceNumb, wordPos));

	}

	public void setRank(double rank) {
		this.rank = rank;
	}

	public double getRank() {
		return this.rank;
	}

	public String getWord() {
		return word;
	}

	public String getAllWordPos() {
		String rez = "";

		for (WordPosition p : wordPosition) {
			rez += p.getSentN() + " - " + p.getWordPos() + " ";
		}

		return rez;

	}

	public ArrayList<WordPosition> getWordPosList() {
		return wordPosition;
	}

}

class WordPosition {
	private int sentenceNumber;
	private int wordPosition;

	public WordPosition(int sentenceNumber, int wordPosition) {
		this.sentenceNumber = sentenceNumber;
		this.wordPosition = wordPosition;
	}

	public int getSentN() {
		return sentenceNumber;
	}

	public int getWordPos() {
		return wordPosition;
	}
}
