package Model;
import java.util.ArrayList;



public class SimMatrix {
	
	private ArrayList <Sentence> sentenceList = null;
	
	private double [][] simMatrix = null;
	
	public SimMatrix(ArrayList <Sentence> sentenceList)
	{
		this.sentenceList = sentenceList;
	}
	
	public void createMatrix()
	{
		int dimension = sentenceList.size();
		simMatrix = new double[dimension][dimension];
		
		for (int i=0; i<simMatrix.length;i++)
		{
			for (int j=0;j<simMatrix[i].length;j++)
			{
				
				SentenceComparison sentenceComparison = new SentenceComparison(sentenceList.get(i), sentenceList.get(j));
				simMatrix[i][j] = sentenceComparison.getRank();
			}
		}		
	}
	
	public double [][] getSimMatrix()
	{
		return simMatrix;
	}
	
	

}
