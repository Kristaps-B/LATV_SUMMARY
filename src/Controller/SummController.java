package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import opennlp.tools.dictionary.Dictionary;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

import model.Sentence;
import model.SentenceComparison;
import model.SummModel;
import View.View;

public class SummController {

	private SummModel model;
	private View view;
	private ArrayList<Sentence> sentenceList;
	private String text;

	public SummController(View view, SummModel model) {
		this.model = model;
		this.view = view;

		createProgramm();
	}

	private void createProgramm() {

		view.createGUI();
		view.addLoadButtonEvent(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final File file = view.showFileDialog();

				if (file != null) {
					view.disableTabs();

					new Thread() {
						@Override
						public void run() {
							loadFile(file);
							sentenceList = null;
						}
					}.start();

				} else {
					// Neko neieladeja!
				}
			}
		});

		view.addSentSummEvent(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.disableSummButtons();

				new Thread() {
					@Override
					public void run() {
						generateSummary(text);
						view.enableTab_1();
					}

				}.start();

			}
		});

		view.addChangeListenerSent(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				sliderSentChanged();
			}
		});


		view.addTableMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = 0;
				int col = 0;

				row = view.getRowAtPoint(e.getPoint());
				col = view.getColumnAtPoint(e.getPoint());

				if (row >= 1 && col >= 1) {
					// System.out.println(row+" - "+col);
					showTwoSentenceComparison(row - 1, col - 1);
				}

			}
		});

	}


	private void loadFile(File file) {
		view.setProgress("Progress: Ielade failu...");
		this.text = "";
		try {
			this.text = model.getFile(file);
			view.setTextArea(text);
			view.enableSummButtons();
			// view.enableKeyWordButton();
			// view.enableKeyWordButton();
			view.setProgress("Progress: Teksts tika ieladçts...");
		} catch (Exception e) {
			view.showError(e.getMessage());
		}

	}

	private void generateSummary(String text) {

		view.setProgress("Progress: Sadala tekstu teikumos, uzgaidiet...");
		
		
		//Sadala teikumos
		
		SentenceModel mod;
		SentenceDetectorME sentenceDetector = null;
		
		try {
				InputStream modelIn = new FileInputStream("lv-sent.bin");
				System.out.println("Ielade!");
				mod = new SentenceModel(modelIn);
			   
			   sentenceDetector = new SentenceDetectorME(mod);
			   Dictionary d = mod.getAbbreviations();
			    System.out.println(d.size());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} 
		
		for (String s:sentenceDetector.sentDetect("Sveiki. Mani sauc. Kristaps")) {
			System.out.println(s);
		}
		

		sentenceList = model.getSentenceList(text, sentenceDetector);

		//model.getWordList(sentenceList);

		view.setProgress("Progress: Veido teikumu tuvuma matricu, uzgaidiet...");

		double[][] simMatrix = model.getSimilarityMatrix(sentenceList);

		view.setProgress("Progress: Izpilda TextRank algoritmu, uzgaidiet...");

		sentenceList = model.getRankedSentences(sentenceList, simMatrix);

		view.setProgress("Teikumi tika veiksmîgi novertçti.");

		view.showTextTable(sentenceList);
		view.showSimMatrixTable(simMatrix);
		showSummaryText();
	}

	private void showSummaryText() {
		Sentence[] sentArray = model.getSummary(view.getSliderSentValue(),
				sentenceList);

		view.showSummaryText(sentArray);
	}


	private void sliderSentChanged() {
		showSummaryText();
	}

	private void showTwoSentenceComparison(int row, int col) {
		// Salidzina teikumus!
		SentenceComparison sentenceComparison = new SentenceComparison(
				sentenceList.get(row), sentenceList.get(col));

		view.showTwoSentenceComparison(sentenceComparison);
	}
}
