import java.util.ArrayList;


public class Sentence {
	private String originalSentence = null;
	
	private ArrayList <String> wordList = new ArrayList<>();
	
	//Stop vardu saraksts
	private String [] stopWordArray = {
	"aiz",
	"ap",
	"apak�",
	"�rpus",
	"aug�pus",
	"bez",
	"caur",
	"d��",
	"gar",
	"iek�",
	"iz",
	"kop�",
	"labad",
	"lejpus",
	"l�dz",
	"no",
	"otrpus",
	"pa",
	"par",
	"p�r",
	"p�c",
	"pie",
	"pirms",
	"pret",
	"priek�",
	"starp",
	"�aipus",
	"uz",
	"vi�pus",
	"virs",
	"virspus",
	"zem",
	"apak�pus",
	//Conjuctions
	"un",
	"bet",
	"jo",
	"ja",
	"ka",
	"lai",
	"tom�r",
	"tikko",
	"turpret�",
	"ar�",
	"kaut",
	"gan",
	"t�d��",
	"t�",
	"ne",
	"tikvien",
	"vien",
	"k�",
	"ir",
	"te",
	"vai",
	"kam�r",
	//Particles
	"ar",
	"diezin",
	"dro�i",
	"diem��l",
	"neb�t",
	"ik",
	"it",
	"ta�u",
	"nu",
	"pat",
	"tiklab",
	"iek�pus",
	"nedz",
	"tik",
	"nevis",
	"turpretim",
	"jeb",
	"iekam",
	"iek�m",
	"iek�ms",
	"kol�dz",
	"l�dzko",
	"tikl�dz",
	"jeb�u",
	"t�lab",
	"t�p�c",
	"nek�",
	"itin",
	"j�",
	"jau",
	"jel",
	"n�",
	"nezin",
	"tad",
	"tikai",
	"vis",
	"tak",
	"iekams",
	"vien",
	//Modal verbs
	"b�t",
	"biju",
	"biji",
	"bija",
	"bij�m",
	"bij�t",
	"esmu",
	"esi",
	"esam",
	"esat",
	"b��u",
	"b�si",
	"b�s",
	"b�sim",
	"b�siet",
	"tikt",
	"tiku",
	"tiki",
	"tika",
	"tik�m",
	"tik�t",
	"tieku",
	"tiec",
	"tiek",
	"tiekam",
	"tiekat",
	"tik�u",
	"tiks",
	"tiksim",
	"tiksiet",
	"tapt",
	"tapi",
	"tap�t",
	"topat",
	"tap�u",
	"tapsi",
	"taps",
	"tapsim",
	"tapsiet",
	"k��t",
	"k�uvu",
	"k�uvi",
	"k�uva",
	"k�uv�m",
	"k�uv�t",
	"k��stu",
	"k��sti",
	"k��st",
	"k��stam",
	"k��stat",
	"k���u",
	"k��si",
	"k��s",
	"k��sim",
	"k��siet",
	//verbs
	"var�t",
	"var�ju",
	"var�j�m",
	"var��u",
	"var�sim",
	"var",
	"var�ji",
	"var�j�t",
	"var�si",
	"var�siet",
	"varat",
	"var�ja",
	"var�s",
	//more
	"b�tu",
	"kuras",
	"diez",
	"viss",
	"vismaz",
	"kas",
	"vi��",
	"daudz",
	"t�m"
	};
	
	public Sentence(String originalSentence)
	{
		this.originalSentence = originalSentence;
		
		createWordList();
	}
	
	private void createWordList()
	{
		//Veido svarigo vardu sarakstu
		String tempSentence = originalSentence.replaceAll("[^a-zA-Z���������������������� ]", "").replaceAll("  ", " ").toLowerCase();
		
		String [] wordArray = tempSentence.split(" ");
				
		System.out.println("=============================================");
		System.out.println("Teikuma apstrade!");
		System.out.println("Pagaidu teikums: "+tempSentence);
		
		//Pievieno vardu sarakstam svarigos vardus
		for (int i=0;i<wordArray.length;i++)
		{
			//System.out.print(wordArray[i]+" ");
			if (!isStopWord(wordArray[i]))
			{
				wordList.add(wordArray[i]);
			}
		}
		
		System.out.println("Teikums ar svarigajiem vardiem:");
		for (String w: wordList)
		{
			System.out.println(w);
		}

		
		System.out.println("=============================================");
		
	}
	
	private boolean isStopWord(String word)
	{
		for (int i=0;i<stopWordArray.length;i++)
		{
			if (stopWordArray[i].equals(word))
			{
				return true;
			}
		}
			
		return false;
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
	
	
}
