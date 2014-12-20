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
		//System.out.println("Veido matricu!");
		
		//findSentenceSimilarity(new Sentence("Mani sauc Kristaps."), new Sentence("Govs mani dzivo kaut kur."));
		int dimension = sentenceList.size();
		simMatrix = new double[dimension][dimension];
		
		for (int i=0; i<simMatrix.length;i++)
		{
			for (int j=0;j<simMatrix[i].length;j++)
			{
				simMatrix[i][j] = findSentenceSimilarity(sentenceList.get(i), sentenceList.get(j));
			}
		}
		
		//Izvada
		/*
		System.out.println("Izvada matricu: ");
		for (int i=0; i<simMatrix.length;i++)
		{
			
			for (int j=0;j<simMatrix[i].length;j++)
			{
				System.out.print(simMatrix[i][j]+" | ");
			}
			System.out.println();
		}
		*/
		
			
	}
	
	private double findSentenceSimilarity(Sentence s1, Sentence s2)
	{
		
		//Veido kopejo vardu vektoru
		ArrayList <String> wordList = new ArrayList <>();
		
		for (String w: s1.getWordList() )
		{
			if (!isWordInList(wordList, w))
			{
				wordList.add(w);
			}
		}
		for (String w: s2.getWordList() )
		{
			if (!isWordInList(wordList, w))
			{
				wordList.add(w);
			}
			
		}
		
		/*
		System.out.println("Abu teikumu visi vardi ir: ");
		
		for (String w: wordList)
		{
			System.out.println(w);
		}
		*/
		
		
		//Novertejums 
		int [] vector_1 = new int [wordList.size()];
		int [] vector_2 = new int [wordList.size()];
		
		//Noverte pirmo teikumu
		for (int i=0; i<vector_1.length;i++)
		{
			vector_1[i] = 0;
			
			String word = wordList.get(i);
			for (String w: s1.getWordList() )
			{
				if (isSameWords(w, word))
				{
					vector_1[i]+=1;
				}
			}
			
		}
		
		//Noverte otro teikumu
		for (int i=0; i<vector_2.length;i++)
		{
			vector_2[i] = 0;
			
			String word = wordList.get(i);
			for (String w: s2.getWordList() )
			{
				if (isSameWords(w, word))
				{
					vector_2[i]+=1;
				}
			}
		}
		
		float a_b = 0;
		float a_kv2 = 0;
		float b_kv2 = 0;
		
		
		//System.out.println("--------------------------------------------------------------");
		for (int i=0;i < vector_1.length;i++)
		{
			//System.out.println(wordList.get(i)+" | "+vector_1[i]+" | "+vector_2[i]);
			
			//Aprekina visus mainigos 
			
			a_b = a_b + vector_1[i]*vector_2[i];
			a_kv2 = a_kv2 + vector_1[i]*vector_1[i];
			b_kv2 = b_kv2 + vector_2[i]*vector_2[i];
			
		}
		
		
		double cosO = a_b/((Math.sqrt(a_kv2)*Math.sqrt(b_kv2)));
		
		//cosO = Math.acos(cosO);
		//System.out.println("Tuvums ir: "+cosO);
		


		cosO = Math.round(cosO*1000.0)/1000.0;


		
		return cosO;
	}
	
	private boolean isWordInList(ArrayList <String> wordList, String word)
	{
		for (String w:wordList)
		{
			if (isSameWords(w, word))
			{
				return true;
			}
		}
		
		return false;
	}
	
	private String getCommonLongestSubstring(String word1, String word2)
	{
		String commonSubstring = null;
		
		word1 = word1.toLowerCase();
		word2 = word2.toLowerCase();
		
		
		//Mekle garako kopejo simbolu rinki!
		commonSubstring = longestCommonSubstring(word1, word2);
		
		return commonSubstring;
	}
	
	private String longestCommonSubstring(String S1, String S2)
	{
	    int Start = 0;
	    int Max = 0;
	    for (int i = 0; i < S1.length(); i++)
	    {
	        for (int j = 0; j < S2.length(); j++)
	        {
	            int x = 0;
	            while (S1.charAt(i + x) == S2.charAt(j + x))
	            {
	                x++;
	                if (((i + x) >= S1.length()) || ((j + x) >= S2.length())) break;
	            }
	            if (x > Max)
	            {
	                Max = x;
	                Start = i;
	            }
	         }
	    }
	    return S1.substring(Start, (Start + Max));
	}
	
	private boolean isSameWords(String word1, String word2)
	{
		String commonSubstring = getCommonLongestSubstring(word1, word2);
		
		if (commonSubstring.length()>=word1.length()*0.75 || commonSubstring.length()>=word2.length()*0.75)
		{
			return true;
		}
		
		return false;
	}
	
	public double [][] getSimMatrix()
	{
		return simMatrix;
	}
	
	

}
