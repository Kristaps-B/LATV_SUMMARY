package Model;
import java.util.ArrayList;


public class Sentence {
	private String originalSentence = null;
	
	private ArrayList <String> wordList = new ArrayList<>();
	
	private final int ID;
	
	private double rank;
	
	
	
	public Sentence(int ID, String originalSentence)
	{
		this.originalSentence = originalSentence;
		this.ID = ID;
		
		createWordList();
	}
	
	private void createWordList()
	{
		//Veido svarigo vardu sarakstu
		String tempSentence = originalSentence.replaceAll("[^a-zA-ZÂÈÇÌÎÍÏÒĞÛŞâèçìîíïòğûş ]", "").replaceAll("  ", " ").toLowerCase();
		
		String [] wordArray = tempSentence.split(" ");
		
		/*
		System.out.println("=============================================");
		System.out.println("Teikuma apstrade!");
		System.out.println("Pagaidu teikums: "+tempSentence);
		*/
		
		//Pievieno vardu sarakstam svarigos vardus
		for (int i=0;i<wordArray.length;i++)
		{
			//System.out.print(wordArray[i]+" ");
			if (!StopWords.isStopWord(wordArray[i]) && wordArray[i].length()>0)
			{
				wordList.add(wordArray[i]);
			}
		}
		
		/*
		System.out.println("Teikums ar svarigajiem vardiem:");
		for (String w: wordList)
		{
			System.out.println(w);
		}

		
		System.out.println("=============================================");
		*/
	}
	
	
	
	public String getOriginalSentence()
	{
		return originalSentence;
	}
	
	
	
	public ArrayList <String> getWordList()
	{
		return wordList;
	}
	
	public String getNewSentence()
	{
		String rez = "";
		
		for (String w:wordList)
		{
			rez+=w+" ";
		}
		
		return rez;
	}
	
	public int getID()
	{
		return ID;
	}
	
	public double getRank()
	{
		return rank;
	}
	
	public void setRank(double rank)
	{
		this.rank = rank;
	}
	
	
}
