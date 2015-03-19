package model;

import java.util.ArrayList;

public class Summarizer {

	private ArrayList<Sentence> sentenceList;
	private Sentence[] sentenceArray;

	public Summarizer(ArrayList<Sentence> sentenceList) {
		this.sentenceList = sentenceList;

		createSentenceArray();

		// printSentences(sentenceArray);

		// printSentences(sortByRank(sentenceArray));

		// getNSentences(5);
	}

	private void createSentenceArray() {
		sentenceArray = new Sentence[sentenceList.size()];
		int i = 0;
		for (Sentence s : sentenceList) {
			sentenceArray[i] = s;
			i++;
		}
	}

	private Sentence[] sortByRank(Sentence[] sentArray) {
		Sentence[] rez = new Sentence[sentArray.length];

		for (int i = 0; i < rez.length; i++) {
			rez[i] = sentArray[i];
		}

		for (int i = 0; i < rez.length - 1; i++) {
			int index = i;
			for (int j = i + 1; j < rez.length; j++)
				if (rez[j].getRank() > rez[index].getRank())
					index = j;

			Sentence largerSentence = rez[index];
			rez[index] = rez[i];
			rez[i] = largerSentence;
		}

		return rez;
	}

	private Sentence[] sortByID(Sentence[] sentArray) {
		Sentence[] rez = new Sentence[sentArray.length];

		for (int i = 0; i < rez.length; i++) {
			rez[i] = sentArray[i];
		}

		for (int i = 0; i < rez.length - 1; i++) {
			int index = i;
			for (int j = i + 1; j < rez.length; j++)
				if (rez[j].getID() < rez[index].getID())
					index = j;

			Sentence smallerSentence = rez[index];
			rez[index] = rez[i];
			rez[i] = smallerSentence;
		}

		return rez;
	}

	/*
	 * private void printSentences(Sentence [] sentArray) {
	 * System.out.println("Izvada masivu!"); for (int i = 0; i<
	 * sentArray.length; i++) {
	 * System.out.println(sentArray[i].getID()+"] <Rangs: "
	 * +sentArray[i].getRank()+"> "+sentArray[i].getOriginalSentence()); } }
	 */

	public Sentence[] getNSentences(int n) {
		Sentence[] rez = new Sentence[n];
		Sentence[] rankedSentences = sortByRank(sentenceArray);

		for (int i = 0; i < n; i++) {
			rez[i] = rankedSentences[i];
		}

		rez = sortByID(rez);

		// printSentences(rez);

		return rez;
	}
}
