package Model;

import java.util.ArrayList;

public class SentenceComparison {
	
	private String [] wordArr = null;
	private int [] firstSentVector = null;
	private int [] secondSentVector = null;
	private String [] wordsArray = null;
	
	private Sentence s1 = null;
	private Sentence s2 = null;
	
	private double rank = 0;
	
	WordComparison wordComparison;
	
	public SentenceComparison(Sentence s1, Sentence s2)
	{
		
		this.s1 = s1;
		this.s2 = s2;
		
		wordComparison = new WordComparison();
		
		rank = findSentenceSimilarity(this.s1, this.s2);
	}
	
	public String [] getWordArr()
	{
		return wordArr;
	}
	
	public String [] getWordsArr()
	{
		//Veido words masivu
		wordsArray = new String [wordArr.length];
		
		for (int i = 0; i< wordsArray.length; i++)
		{
			wordsArray[i] = "";
		}
		
		for (int i = 0; i< wordsArray.length; i++)
		{
			String word = wordArr[i];
			for (OneWord w: s1.getWordList())
			{
				if (wordComparison.isSameWords(w.getWord(), word))
				{
					wordsArray[i]+=w.getWord()+" ";
				}
			}
			for (OneWord w: s2.getWordList())
			{
				if (wordComparison.isSameWords(w.getWord(), word))
				{
					wordsArray[i]+=w.getWord()+" ";
				}
				
			}
		}
		
		
		
		return wordsArray;
	}
	
	/*
	private boolean isInString(String word, String  words)
	{
		String [] wordsArray = words.split(" ");
		
		for (int i=0; i<wordsArray.length; i++)
		{
			if (wordsArray[i].equals(word))
			{
				return true;
			}
		}
		
		return false;
	}
	*/
	
	public int [] getFirstSentRank()
	{
		return firstSentVector;
	}
	
	public int [] getSecondSentRank()
	{
		return secondSentVector;
	}
	
	
	private double findSentenceSimilarity(Sentence s1, Sentence s2)
	{
		
		//Veido kopejo vardu vektoru
		ArrayList <OneWord> wordList = new ArrayList <>();
		
		for (OneWord w: s1.getWordList() )
		{
			if (!isWordInList(wordList, w))
			{
				wordList.add(w);
			}
		}
		for (OneWord w: s2.getWordList() )
		{
			if (!isWordInList(wordList, w))
			{
				wordList.add(w);
			}
			
		}
		
		//Novertejums 
		int [] vector_1 = new int [wordList.size()];
		int [] vector_2 = new int [wordList.size()];
		
		
		
		
		//Noverte pirmo teikumu
		for (int i=0; i<vector_1.length;i++)
		{
			vector_1[i] = 0;
			
			OneWord word = wordList.get(i);
			for (OneWord w: s1.getWordList() )
			//for (int j=0; j<s1.getWordList().size(); j++)
			{
				if (w.getIndex() == word.getIndex())
				{
					vector_1[i]+=1;
				}
			}
			
		}
		
		//Noverte otro teikumu
		for (int i=0; i<vector_2.length;i++)
		{
			vector_2[i] = 0;
			
			OneWord word = wordList.get(i);
			for (OneWord w: s2.getWordList() )
			{
				if (w.getIndex() == word.getIndex())
				{
					vector_2[i]+=1;
				}
			}
		}
		
		float a_b = 0;
		float a_kv2 = 0;
		float b_kv2 = 0;
		
		for (int i=0;i < vector_1.length;i++)
		{
			a_b = a_b + vector_1[i]*vector_2[i];
			a_kv2 = a_kv2 + vector_1[i]*vector_1[i];
			b_kv2 = b_kv2 + vector_2[i]*vector_2[i];
			
		}
		
		
		double cosO = a_b/((Math.sqrt(a_kv2)*Math.sqrt(b_kv2)));

		cosO = Math.round(cosO*1000.0)/1000.0;
		
		
		wordArr = new String[wordList.size()];
		
		for (int i=0;i<wordArr.length;i++)
		{
			wordArr[i] = wordList.get(i).getWord();
		}
		
		firstSentVector = vector_1;
		secondSentVector = vector_2;


		
		return cosO;
	}
	
	private boolean isWordInList(ArrayList <OneWord> wordList, OneWord word)
	{
		for (OneWord w:wordList)
		{
			if (w.getIndex() == word.getIndex())
			{
				return true;
			}
		}
		
		return false;
	}
	
	public double getRank()
	{
		return rank;
	}
	
	public String getFirstSentence()
	{
		return s1.getOriginalSentence();
	}
	
	public String getSecondSentence()
	{
		return s2.getOriginalSentence();
	}
	
	public int getFirstSentenceID()
	{
		return s1.getID();
	}
	
	public int getSecondSentenceID()
	{
		return s2.getID();
	}
}
