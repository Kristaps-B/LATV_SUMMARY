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
				if (character=='.' || character=='!' || character == '?')
				{
					sentence = sentence.trim();
					sentenceList.add(new Sentence(id, sentence));
					
					id++;
					
					sentence ="";
				}
				
			}
			
			
			
		}
		
		//Izvada teikumu sarakstu
		/*
		for (Sentence s: sentenceList)
		{
			System.out.println(s.getOriginalSentence());
		}
		*/
	}
	
	public ArrayList <Sentence> getSentenceList()
	{
		return sentenceList;
	}
}
