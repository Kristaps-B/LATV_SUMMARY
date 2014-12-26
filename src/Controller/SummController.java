package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import View.View;
import Model.Sentence;
import Model.SentenceComparison;
import Model.SummModel;
import Model.Word;


public class SummController {
	
	private SummModel model;
	private View view;
	private ArrayList <Sentence> sentenceList;
	private String text;
	
	
	public SummController(View view, SummModel model)
	{
		this.model = model;
		this.view = view;
		
		createProgramm();
	}
	
	private void createProgramm()
	{
		
		view.createGUI();
		view.addLoadButtonEvent(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					final File file = view.showFileDialog();
					
					if (file != null)
					{
						view.disableTabs();
						
						new Thread()
						{
							@Override
							public void run()
							{
								loadFile(file);
								sentenceList = null;
							}
						}.start();
						
					}
					else
					{
						//Neko neieladeja!
					}
				}
			}
		);
		
		view.addSentSummEvent(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					view.disableSummButtons();
					
					new Thread()
					{
						@Override
						public void run()
						{
							generateSummary(text);
							view.enableTab_1();
						}
						
					}.start();
					
					
				}
			}		
		);
		
		view.addKeyWordButtonEven(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					new Thread()
					{
						@Override
						public void run()
						{
							view.disableKeyWordButton();
							generateKeyWords(text);
							view.enableTab_2();
						}
					}.start();
				}
			}	
		);
		
		view.addChangeListener(new ChangeListener()
			{
				@Override
				public void stateChanged(ChangeEvent e)
				{
					sliderChanged();
				}
			}
		);
		
		view.addTableMouseListener(new MouseAdapter()
			{
				@Override
				public void mouseClicked(MouseEvent e)
				{
					int row = 0;
					int col = 0;
					
					row = view.getRowAtPoint(e.getPoint());
					col = view.getColumnAtPoint(e.getPoint());
					
					if (row>=1 && col >=1)
					{
						//System.out.println(row+" - "+col);
						showTwoSentenceComparison(row-1, col-1);
					}
							
				}
			}
		);
		
	}
	
	private void generateKeyWords(String text)
	{
		//Genere atslegas vardus
		
		
		view.setProgress("Progress: Sadala tekstu teikumos, uzgaidiet...");
		
		if (sentenceList == null)
		{
			sentenceList = model.getSentenceList(text);
		}
		
		view.setProgress("Progress: Iegust potenciâlo vârdu sarakstu, uzgaidiet...");
		
		ArrayList <Word> wordList = model.getWordList(sentenceList);
		
		int [][] simMatrix = model.getWordSimmMatrix(wordList);
		
		wordList = model.getWordScore(wordList, simMatrix);
		
		Word [] rezWordList =  model.getKeyWords(wordList, 6);
		
		System.out.println("Rezultats ir:");
		if (rezWordList != null)
		for (Word w: rezWordList)
		{
			System.out.println(w.getID()+") "+w.getWord()+" "+w.getRank());
		}
		
		//Beigas
		view.setProgress("Atslegvârdi tika veiksmîgi novertçti");
		
	}
	
	private void loadFile(File file)
	{
		view.setProgress("Progress: Ielade failu...");
		this.text ="";
		try
		{
			this.text = model.getFile(file);
			view.setTextArea(text);
			view.enableSummButtons();
			view.enableKeyWordButton();
			view.enableKeyWordButton();
			view.setProgress("Progress: Teksts tika ieladçts...");
		}
		catch (Exception e)
		{
			view.showError(e.getMessage());
		}
		
	}
	
	private void generateSummary(String text)
	{
		
		
		view.setProgress("Progress: Sadala tekstu teikumos, uzgaidiet...");
	
		sentenceList = model.getSentenceList(text);
		
		view.setProgress("Progress: Veido teikumu tuvuma matricu, uzgaidiet...");
		
		double [][] simMatrix = model.getSimilarityMatrix(sentenceList, view);

		view.setProgress("Progress: Izpilda TextRank algoritmu, uzgaidiet...");
		
		sentenceList = model.getRankedSentences(sentenceList, simMatrix);
		
		
		view.setProgress("Teikumi tika veiksmîgi novertçti.");
		
		view.showTextTable(sentenceList);
		view.showSimMatrixTable(simMatrix);
		showSummaryText();
	}
	
	private void showSummaryText()
	{
		Sentence [] sentArray = model.getSummary(view.getSliderValue(), sentenceList);
		
		view.showSummaryText(sentArray);
	}
	
	private void sliderChanged()
	{
		showSummaryText();
	}
	
	private void showTwoSentenceComparison(int row, int col)
	{
		//Salidzina teikumus!
		SentenceComparison sentenceComparison = new SentenceComparison(sentenceList.get(row), sentenceList.get(col));
		
		
		view.showTwoSentenceComparison(sentenceComparison);
	}
}
