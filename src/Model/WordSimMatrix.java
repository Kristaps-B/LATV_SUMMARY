package Model;

import java.util.ArrayList;

public class WordSimMatrix {
	
	private ArrayList <Word> wordList;
	
	private double [][] simMatrix;
	
	public WordSimMatrix(ArrayList <Word> wordList)
	{
		this.wordList = wordList;
		
		simMatrix = new double[wordList.size()][wordList.size()];
	}
	
	public void generateWordSimMatrix()
	{
		
		for (int i = 0; i<simMatrix.length;i++)
		{
			Word w1 = wordList.get(i);
			ArrayList <WordPosition> wp1 = w1.getWordPosList();
			double wordCount = wp1.size();
			
			System.out.println(i+"---> " + wp1.size());
			for (int j=0; j<simMatrix.length; j++)
			{
				Word w2 = wordList.get(j);
				ArrayList <WordPosition> wp2 = w2.getWordPosList();
				
				//Cik vietas abi vardi ir blakus!
				int neighbourCount = 0;
				
				for (WordPosition w_i: wp1)
				{
					for (WordPosition w_j: wp2)
					{
						if (w_i.getSentN()==w_j.getSentN() && (w_j.getWordPos()-w_i.getSentN())==1)
						{
							neighbourCount++;
						}
					}
				}
				
				System.out.print(neighbourCount+" | ");
				simMatrix[i][j] = (neighbourCount/wordCount);
			}
			System.out.println();
			
		}
		writeSimMatrix();
		
	}
	
	
	private void writeSimMatrix()
	{
		for (int i = 0; i<simMatrix.length;i++)
		{
			for (int j=0; j<simMatrix.length; j++)
			{
				System.out.print(simMatrix[i][j]+" ");
			}
			System.out.println();
		}
	}
}
