package model;

import java.util.ArrayList;

public class WordSimMatrix {

	private ArrayList<Word> wordList;

	private int[][] simMatrix;

	public WordSimMatrix(ArrayList<Word> wordList) {
		this.wordList = wordList;

		simMatrix = new int[wordList.size()][wordList.size()];
	}

	public void generateWordSimMatrix() {

		for (int i = 0; i < simMatrix.length; i++) {
			Word w1 = wordList.get(i);
			ArrayList<WordPosition> wp1 = w1.getWordPosList();
			// double wordCount = wp1.size();

			// System.out.println(i+"---> " + wp1.size());
			for (int j = 0; j < simMatrix.length; j++) {
				Word w2 = wordList.get(j);
				ArrayList<WordPosition> wp2 = w2.getWordPosList();

				// Cik vietas abi vardi ir blakus!
				int neighbourCount = 0;

				for (WordPosition w_i : wp1) {
					for (WordPosition w_j : wp2) {
						if (w_i.getSentN() == w_j.getSentN()
								&& isWordsNear(w_i.getWordPos(),
										w_j.getWordPos(), wp1.size())) {
							neighbourCount++;
						}
					}
				}

				// System.out.print(neighbourCount+" | ");
				simMatrix[i][j] = (neighbourCount);
			}
			// System.out.println();

		}

		// writeSimMatrix();

	}

	private boolean isWordsNear(int w1_p, int w2_p, int sentLength) {
		boolean rezult = false;

		if (Math.abs(w2_p - w1_p) == 1) {
			return true;
		}

		return rezult;
	}

	public int[][] getSimMatrix() {
		return simMatrix;
	}
}
