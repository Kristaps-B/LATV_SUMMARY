package View;

import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.util.ArrayList;

import javax.swing.event.ChangeListener;

import Model.Sentence;
import Model.SentenceComparison;

public abstract class View {
	
	public abstract void createGUI();
	public abstract void addLoadButtonEvent(ActionListener actionListener);
	public abstract void setFileTextField(String text);
	public abstract File showFileDialog();
	public abstract void addChangeListener(ChangeListener changeListener);
	public abstract void setTextArea(String text);
	public abstract void showTextTable(ArrayList <Sentence> sentenceList);
	public abstract void showSimMatrixTable(double [][] simMatrix);
	public abstract int getSliderValue();
	public abstract void showSummaryText(Sentence [] sentArray);
	public abstract void addTableMouseListener(MouseAdapter mouseAdapter);
	public abstract int getRowAtPoint(Point point);
	public abstract int getColumnAtPoint(Point point);
	public abstract void showTwoSentenceComparison(SentenceComparison sentenceComparison);
	public abstract void showError(String errorTxt);
	public abstract void disableTabs();
	public abstract void enableTabs();
	public abstract void addSentSummEvent(ActionListener actionListener);
	public abstract void enableSummButtons();
	public abstract void disableSummButtons();
	public abstract void setProgress(String text);
}