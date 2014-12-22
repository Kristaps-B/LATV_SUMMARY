package Model;
import java.util.ArrayList;



public class TextProcessing {
	
	private String text = null;
	
	private ArrayList <Sentence> sentenceList = new ArrayList<>();
	
	public TextProcessing(String text)
	{
		this.text = text;
		
	}
	
	public void splitIntoSentences()
	{
		String sentence = "";
		
		int id = 1;
		
		for (int i = 0;i<text.length();i++)
		{
			char character = text.charAt(i);
			
			//Sadala teikumos ar naivu pieeju, neizmantojot regexus!
			if (character != '\n' || character != '\r')
			{
				
				sentence+=character;
				if (isSentenceEnd(text, i))
				{
					sentence = sentence.trim();
					sentenceList.add(new Sentence(id, sentence));
					
					id++;
					
					sentence ="";
				}
				
			}
			
			
			
		}
	}
	
	private boolean isSentenceEnd(String text, int i)
	{
		char character = text.charAt(i);
		
		
		if (character=='.' || character=='!' || character == '?')
		{
			//Vai beigas
			if (i == text.length()-1)
			{
				return true;
			}
			else if (Character.isUpperCase(text.charAt(i-1)) && text.charAt(i-2) ==' ')
			{
				return false;
			}
			//Vai rindas beigas
			else if (text.charAt(i+1)=='\n' || text.charAt(i+1)=='\r')
			{
				return true;
			}
			else if (!Character.isDigit(text.charAt(i-1)) && Character.isUpperCase(text.charAt(i+2)) && text.charAt(i+1) ==' ')
			{
				return true;
			}
			else if (!Character.isDigit(text.charAt(i-1)) && Character.isUpperCase(text.charAt(i+1)) && text.charAt(i+1) !=' ')
			{
				return true;
			}
			
		}
		
		return false;
	}
	
	public ArrayList <Sentence> getSentenceList()
	{
		return sentenceList;
	}
}
