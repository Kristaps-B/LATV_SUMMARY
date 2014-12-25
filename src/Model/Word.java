package Model;

import java.util.ArrayList;

public class Word {
	private String word;
	
	private ArrayList <WordPosition> wordPosition;
	
	public Word(String word)
	{
		this.word = word;
		
		wordPosition = new ArrayList <>();
	}
	
	public void addWordToPosition(int sentenceNumb, int wordPos)
	{
		wordPosition.add(new WordPosition(sentenceNumb, wordPos));
		
	}
	
	public String getWord()
	{
		return word;
	}
	
	public String getAllWordPos()
	{
		String rez = "";
		
		for (WordPosition p: wordPosition)
		{
			rez+= p.getSentN() +" - "+p.getWordPos()+" ";
		}
		
		return rez;
		
	}
	
	public ArrayList <WordPosition> getWordPosList()
	{
		return wordPosition;
	}
	
	
}

class WordPosition {
	private int sentenceNumber;
	private int wordPosition;
	
	public WordPosition(int sentenceNumber, int wordPosition)
	{
		this.sentenceNumber = sentenceNumber;
		this.wordPosition = wordPosition;
	}
	
	public int getSentN()
	{
		return sentenceNumber;
	}
	
	public int getWordPos()
	{
		return wordPosition;
	}
}
