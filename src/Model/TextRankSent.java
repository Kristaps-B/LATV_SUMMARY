package model;

public class TextRankSent {

	private double[][] simMatrix;
	private double[] scoreVector;
	private double[] oldScoreVector;

	private final double d = 0.85;

	public TextRankSent(double[][] simMatrix) {
		this.simMatrix = simMatrix;
		scoreVector = new double[simMatrix.length];
		oldScoreVector = new double[simMatrix.length];

		for (int i = 0; i < oldScoreVector.length; i++) {
			oldScoreVector[i] = 0.0;
		}

	}

	public void startTextRank() {
		// System.out.println("Sak textrank vertejumu!");

		setStartingVectorScore();

		// System.out.println(findOutLinkSumm(0));
		while (isIterationGoing()) {
			for (int i = 0; i < oldScoreVector.length; i++) {
				oldScoreVector[i] = scoreVector[i];
			}
			textRankIteration();

			// showScoreVector();
		}

	}

	private void setStartingVectorScore() {
		double n = scoreVector.length;
		// System.out.println(n);

		for (int i = 0; i < n; i++) {
			scoreVector[i] = (1.0 / n);

			scoreVector[i] = Math.round(scoreVector[i] * 100000.0) / 100000.0;
			// System.out.println(scoreVector[i]);
		}
	}

	private void textRankIteration() {
		// double it_summ = 0;
		for (int i = 0; i < scoreVector.length; i++) {
			double pr_w_a = scoreVector[i];

			double a = (1 - d) * pr_w_a;

			double b = 0;

			for (int j = 0; j < simMatrix[i].length; j++) {
				if (i != j) {
					if (simMatrix[j][i] != 0) {
						// Rekinajums
						// System.out.print(simMatrix[j][i]+" ");

						double w = simMatrix[j][i];
						double pr_w = scoreVector[j];
						double w_out = findOutLinkSumm(j);

						b += w * (pr_w / w_out);

					}

				}
			}
			// System.out.println();
			b = d * b;
			pr_w_a = a + b;
			pr_w_a = Math.round(pr_w_a * 100000.0) / 100000.0;
			// it_summ+=pr_w_a;
			scoreVector[i] = pr_w_a;
			// System.out.println("Jaunais novertejums: " + pr_w_a);
		}

		// System.out.println("Dotas iteracijas summa ir: "+it_summ);
	}

	private double findOutLinkSumm(int index) {
		double rez = 0;

		for (int i = 0; i < simMatrix.length; i++) {
			if (i != index) {
				rez += simMatrix[index][i];
				// System.out.println(simMatrix[index][i]);
			}
		}

		return rez;
	}


	private boolean isIterationGoing() {
		double alfa = 0.0001;
		// System.out.println("----------------------------------");
		for (int i = 0; i < scoreVector.length; i++) {
			// System.out.println("Atskiriba: "+Math.abs(scoreVector[i] -
			// oldScoreVector[i]));
			if (Math.abs(scoreVector[i] - oldScoreVector[i]) > alfa) {
				return true;
			}
		}
		return false;
	}

	public double[] getScoreVector() {
		return scoreVector;
	}

}
