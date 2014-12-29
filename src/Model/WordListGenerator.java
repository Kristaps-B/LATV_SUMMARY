package Model;

import java.util.ArrayList;

public class WordListGenerator {
	
	private ArrayList <Sentence> sentenceList;
	
	private ArrayList <Word> allWordList;
	
	private WordComparison wordComparison;
	
	public WordListGenerator(ArrayList <Sentence> sentenceList)
	{
		this.sentenceList = sentenceList;
		
		allWordList = new ArrayList <>();
		wordComparison = new WordComparison();
	}
	
	public ArrayList <Word> getWordList()
	{
		return allWordList;
	}
	
	public void generateWordList()
	{
		for (int i = 0; i< sentenceList.size(); i++)
		{
			ArrayList <String> wordList = sentenceList.get(i).getWordList();
			
			
			
			for (int j = 0; j < wordList.size(); j++)
			{
				int poz = 0;
				if ((poz = getPositionInAllWordList(wordList.get(j), allWordList)) == allWordList.size())
				{
					allWordList.add((new Word(wordList.get(j))));
					allWordList.get(allWordList.size()-1).setID(allWordList.size());
					
				}
				allWordList.get(poz).addWordToPosition(i, j);
				sentenceList.get(i).setWordID(j, poz);
				
				//Veido pozi
			}
			
			//writeWordList();
		}
	}
	
	private void writeWordList()
	{
		for (Word w: allWordList)
		{
			System.out.println(w.getWord()+": "+w.getAllWordPos());
			
		}
	}
	
	private int getPositionInAllWordList(String word, ArrayList <Word> wordList)
	{
		for (int i = 0 ; i < wordList.size(); i++)
		{
			if (wordComparison.isSameWords(wordList.get(i).getWord(), word))
			{
				return i;
			}
		}
		
		
		return wordList.size();
	}
}


