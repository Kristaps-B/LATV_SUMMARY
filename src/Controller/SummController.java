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
					File file = view.showFileDialog();
					
					if (file != null)
					{
						view.disableTabs();
						loadFile(file);
						
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
					processText(text);
					view.enableTabs();
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
	
	private void loadFile(File file)
	{
		view.setProgress("Progress: Ielade failu...");
		this.text ="";
		try
		{
			this.text = model.getFile(file);
			view.setTextArea(text);
			view.enableSummButtons();
			view.setProgress("Progress: Teksts tika ieladçts...");
		}
		catch (Exception e)
		{
			view.showError(e.getMessage());
		}
		
	}
	
	private void processText(String text)
	{
		
		
		view.setProgress("Progress: Sadala tekstu teikumos...");
		sentenceList = model.getSentenceList(text);
		
		view.setProgress("Progress: Veido teikumu tuvuma matricu...");
		double [][] simMatrix = model.getSimilarityMatrix(sentenceList);

		view.setProgress("Progress: Izpilda TextRank algoritmu...");
		sentenceList = model.getRankedSentences(sentenceList, simMatrix);
		
		
		view.setProgress("Progress: Teikumi tika veiksmîgi novertçti...");
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
