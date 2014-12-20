import java.util.ArrayList;


public class Sentence {
	private String originalSentence = null;
	
	private ArrayList <String> wordList = new ArrayList<>();
	
	//Stop vardu saraksts
	private String [] stopWordArray = {
	"aiz",
	"ap",
	"apakð",
	"ârpus",
	"augðpus",
	"bez",
	"caur",
	"dçï",
	"gar",
	"iekð",
	"iz",
	"kopð",
	"labad",
	"lejpus",
	"lîdz",
	"no",
	"otrpus",
	"pa",
	"par",
	"pâr",
	"pçc",
	"pie",
	"pirms",
	"pret",
	"priekð",
	"starp",
	"ðaipus",
	"uz",
	"viòpus",
	"virs",
	"virspus",
	"zem",
	"apakðpus",
	//Conjuctions
	"un",
	"bet",
	"jo",
	"ja",
	"ka",
	"lai",
	"tomçr",
	"tikko",
	"turpretî",
	"arî",
	"kaut",
	"gan",
	"tâdçï",
	"tâ",
	"ne",
	"tikvien",
	"vien",
	"kâ",
	"ir",
	"te",
	"vai",
	"kamçr",
	//Particles
	"ar",
	"diezin",
	"droði",
	"diemþçl",
	"nebût",
	"ik",
	"it",
	"taèu",
	"nu",
	"pat",
	"tiklab",
	"iekðpus",
	"nedz",
	"tik",
	"nevis",
	"turpretim",
	"jeb",
	"iekam",
	"iekâm",
	"iekâms",
	"kolîdz",
	"lîdzko",
	"tiklîdz",
	"jebðu",
	"tâlab",
	"tâpçc",
	"nekâ",
	"itin",
	"jâ",
	"jau",
	"jel",
	"nç",
	"nezin",
	"tad",
	"tikai",
	"vis",
	"tak",
	"iekams",
	"vien",
	//Modal verbs
	"bût",
	"biju",
	"biji",
	"bija",
	"bijâm",
	"bijât",
	"esmu",
	"esi",
	"esam",
	"esat",
	"bûðu",
	"bûsi",
	"bûs",
	"bûsim",
	"bûsiet",
	"tikt",
	"tiku",
	"tiki",
	"tika",
	"tikâm",
	"tikât",
	"tieku",
	"tiec",
	"tiek",
	"tiekam",
	"tiekat",
	"tikðu",
	"tiks",
	"tiksim",
	"tiksiet",
	"tapt",
	"tapi",
	"tapât",
	"topat",
	"tapðu",
	"tapsi",
	"taps",
	"tapsim",
	"tapsiet",
	"kïût",
	"kïuvu",
	"kïuvi",
	"kïuva",
	"kïuvâm",
	"kïuvât",
	"kïûstu",
	"kïûsti",
	"kïûst",
	"kïûstam",
	"kïûstat",
	"kïûðu",
	"kïûsi",
	"kïûs",
	"kïûsim",
	"kïûsiet",
	//verbs
	"varçt",
	"varçju",
	"varçjâm",
	"varçðu",
	"varçsim",
	"var",
	"varçji",
	"varçjât",
	"varçsi",
	"varçsiet",
	"varat",
	"varçja",
	"varçs",
	//more
	"bûtu",
	"kuras",
	"diez",
	"viss",
	"vismaz",
	"kas",
	"viòð",
	"daudz",
	"tâm"
	};
	
	public Sentence(String originalSentence)
	{
		this.originalSentence = originalSentence;
		
		createWordList();
	}
	
	private void createWordList()
	{
		//Veido svarigo vardu sarakstu
		String tempSentence = originalSentence.replaceAll("[^a-zA-ZÂÈÇÌÎÍÏÒÐÛÞâèçìîíïòðûþ ]", "").replaceAll("  ", " ").toLowerCase();
		
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
