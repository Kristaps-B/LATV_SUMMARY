package Model;

public class WordComparison {
	
	
	private String getCommonLongestSubstring(String word1, String word2)
	{
		String commonSubstring = null;
		
		word1 = word1.toLowerCase();
		word2 = word2.toLowerCase();
		
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
	
	public boolean isSameWords(String word1, String word2)
	{
		String commonSubstring = getCommonLongestSubstring(word1, word2);
		
		if (commonSubstring.length()>=word1.length()*0.75 || commonSubstring.length()>=word2.length()*0.75)
		{
			return true;
		}
		
		return false;
	}
}
