package model;

import org.apache.lucene.analysis.lv.LatvianStemmer;

	

public class Stemmer {
	
	private LatvianStemmer stemmer;
	
	public Stemmer() {
		stemmer = new LatvianStemmer();
	}
	
	public String stem(String s) {
		String rez = "";
		
		int n = stemmer.stem(s.toCharArray(), s.length());
		
		rez = s.substring(0, n);
		//System.out.println("Vârds: "+s+" tika pârveidots: "+rez);
		
		return rez;
	}
}
