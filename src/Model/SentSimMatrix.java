package model;

import java.util.ArrayList;


public class SentSimMatrix {

	private ArrayList<Sentence> sentenceList = null;

	private double[][] simMatrix = null;

	public SentSimMatrix(ArrayList<Sentence> sentenceList) {
		this.sentenceList = sentenceList;
	}

	public void createMatrix() {
		int dimension = sentenceList.size();
		simMatrix = new double[dimension][dimension];

		for (int i = 0; i < simMatrix.length; i++) {

			//view.setProgress("Progress: Veido teikumu tuvuma matricu (" + i
			//		+ "/" + dimension + "), uzgaidiet...");
			
			for (int j = 0; j < simMatrix[i].length; j++) {

				SentenceComparison sentenceComparison = new SentenceComparison(
						sentenceList.get(i), sentenceList.get(j));
				simMatrix[i][j] = sentenceComparison.getRank();
			}
		}
	}

	public double[][] getSimMatrix() {
		return simMatrix;
	}

}
