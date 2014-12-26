package Model;

import java.util.ArrayList;

public class KeyWords {
	private ArrayList <Word> wordList;
	
	private Word [] wordArray;
	
	public KeyWords(ArrayList <Word> wordList)
	{
		this.wordList = wordList;
		
		createWordArray();
		
		for (Word w: wordList)
		{
			System.out.println(w.getID()+" - "+w.getWord()+ " : "+w.getRank());
		}
	}
	
	private void createWordArray()
	{
		wordArray = new Word [wordList.size()];
		int i = 0;
		for (Word w: wordList)
		{
			wordArray[i] = w;
			i++;
		}
	}
	
	private Word [] sortByRank(Word [] wordArray)
	{
		Word [] rez = new Word[wordArray.length];
		
		for (int i = 0; i< rez.length; i++)
		{
			rez[i] = wordArray[i];
		}
		
		for (int i = 0; i < rez.length - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < rez.length; j++)
                if (rez[j].getRank() > rez[index].getRank())
                    index = j;
      
            Word largerSentence = rez[index]; 
            rez[index] = rez[i];
            rez[i] = largerSentence;
        }
		
		
		return rez;
	}
	
	private Word [] sortByID(Word [] wordArray)
	{
		Word [] rez = new Word[wordArray.length];
		
		for (int i = 0; i< rez.length; i++)
		{
			rez[i] = wordArray[i];
		}
		
		for (int i = 0; i < rez.length - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < rez.length; j++)
                if (rez[j].getID() < rez[index].getID())
                    index = j;
      
            Word smallerSentence = rez[index]; 
            rez[index] = rez[i];
            rez[i] = smallerSentence;
        }
		
		
		
		return rez;
	}
	
	public Word [] getNWords (int n)
	{
		Word [] rez = new Word [n];
		Word [] rankedWords = sortByRank(wordArray);  
		
		
		for (int i = 0; i< rez.length; i++)
		{
			rez[i] = rankedWords[i];
		}
		
		rez = sortByID(rez);
		
		//printSentences(rez);
		
		return rez;
	}
	
	
}
