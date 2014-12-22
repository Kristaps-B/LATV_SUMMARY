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
						loadFile(file);
					}
					else
					{
						//Neko neieladeja!
					}
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
		try
		{
			String text = model.getFile(file);
			processText(text);
		}
		catch (Exception e)
		{
			view.showError(e.getMessage());
		}
	}
	
	private void processText(String text)
	{
		
		view.setTextArea(text);
		
		sentenceList = model.getSentenceList(text);
		
		
		double [][] simMatrix = model.getSimilarityMatrix(sentenceList);

		
		sentenceList = model.getRankedSentences(sentenceList, simMatrix);
		
		
		
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
