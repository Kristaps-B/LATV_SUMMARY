package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.BorderFactory;

import model.Sentence;
import model.SentenceComparison;


public class SummView extends View {

	private JFrame frame;
	private JPanel panelSentSumm;
	// private JPanel panelKeyWords;

	private JPanel centerPanel_1;
	private JPanel centerPanel_2;
	private JPanel centerPanel_3;
	private JPanel centerPanel_4;
	private JPanel centerPanel_5;
	private JPanel centerPanel_6;
	private JPanel centerPanel_7;

	private JPanel northPanel;
	private JTextArea textArea;
	private JTextArea summTextArea;
	private JTextField fileText;
	private JButton loadButton;
	private JTable sentTable;
	private JTable wordTable;
	private JTable simTable;
	private JTable wordSimTable;
	private DefaultTableModel sentTableModel;
	private DefaultTableModel wordTableModel;
	private DefaultTableModel simTableModel;
	private DefaultTableModel wordSimTableModel;
	private JTabbedPane jtb;
	private JTabbedPane jtb_1;
	// private JTabbedPane jtb_2;
	private JSlider sliderSent;
	private JSlider sliderWords;
	private JButton sentSummButton;
	private JLabel progressLabel;

	// Izveido grafisko interfeisu
	@Override
	public void createGUI() {
		createWindow();
		createPannels();
		createProgressLabel();
		createTextArea();
		createSentSummButton();
		// createKeyWordButton();
		createSummTextArea();
		// createKeyWordTextArea();
		createFileLoading();
		createSentenceTable();
		createWordTable();
		createSentenceSimilarityMatrix();
		createWordSimilarityMatrix();
		createSliderSent();
		createSliderWords();
		showAll();
	}

	private void createWindow() {
		// Veido logu
		frame = new JFrame("LATV_SUMMARY 0.9.2(Kristaps B) ");
		frame.setSize(840, 540);
		frame.setLayout(new BorderLayout());

	}

	private void createProgressLabel() {
		progressLabel = new JLabel("Progress: ");
		centerPanel_1.add(progressLabel);
	}

	private void showAll() {
		// Parada logu
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	private void createPannels() {
		// Izveido panelus
		jtb = new JTabbedPane();
		jtb_1 = new JTabbedPane();

		panelSentSumm = new JPanel();

		centerPanel_1 = new JPanel();
		centerPanel_2 = new JPanel();
		centerPanel_3 = new JPanel();
		centerPanel_4 = new JPanel();
		centerPanel_5 = new JPanel();
		centerPanel_6 = new JPanel();
		centerPanel_7 = new JPanel();

		northPanel = new JPanel();

		jtb.addTab("Sâkuma teksts", centerPanel_1);
		jtb.addTab("Teikumu kopsavilkums", panelSentSumm);

		jtb_1.setPreferredSize(new Dimension(800, 410));

		jtb_1.addTab("Teksta teikumi", centerPanel_2);
		jtb_1.addTab("Teikumu lîdzibas matrica", centerPanel_3);
		jtb_1.addTab("Kopsavilkums", centerPanel_4);

		jtb.setEnabledAt(1, false);

		panelSentSumm.add(jtb_1, BorderLayout.CENTER);

		frame.add(jtb, BorderLayout.CENTER);
		frame.add(northPanel, BorderLayout.NORTH);
	}

	private void createSentSummButton() {
		sentSummButton = new JButton("Teikumu kopsavilkums");
		sentSummButton.setEnabled(false);
		centerPanel_1.add(sentSummButton);
	}

	private void createTextArea() {
		// Veido teksta logu
		textArea = new JTextArea("", 18, 70);
		JScrollPane scrollPane = new JScrollPane(textArea);
		textArea.setEnabled(false);
		textArea.setLineWrap(true);
		centerPanel_1.add(scrollPane);
		textArea.setEditable(false);
	}

	private void createSummTextArea() {
		// Veido kopsavilkuma teksta logu
		summTextArea = new JTextArea("", 18, 70);
		JScrollPane summScrollPane = new JScrollPane(summTextArea);
		summTextArea.setEnabled(false);
		summTextArea.setLineWrap(true);
		summTextArea.setEditable(false);
		centerPanel_4.add(summScrollPane);
	}

	private void createFileLoading() {
		// Izveido faila ieladi
		northPanel.setLayout(new FlowLayout());
		northPanel.add(new JLabel("Ielâdçt teksta failu: "));

		fileText = new JTextField("", 18);
		northPanel.add(fileText);

		loadButton = new JButton("...");
		northPanel.add(loadButton);
	}

	private void createSentenceTable() {
		// Pievieno teikumu tabulu
		sentTableModel = new DefaultTableModel(0, 0) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		sentTable = new JTable(sentTableModel);

		String[] columns = { "Nr.", "Originalais teikums",
				"Teikums bez apstâðanas vârdiem un punktuâcijâs zîmçm",
				"TextRank vçrtçjums" };

		sentTableModel.setColumnIdentifiers(columns);

		JScrollPane tableScrollPane = new JScrollPane(sentTable);
		sentTable.getTableHeader().setReorderingAllowed(false);

		sentTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		sentTable.setPreferredScrollableViewportSize(new Dimension(680, 300));

		sentTable.getColumnModel().getColumn(0).setPreferredWidth(40);
		sentTable.getColumnModel().getColumn(1).setPreferredWidth(440);
		sentTable.getColumnModel().getColumn(2).setPreferredWidth(400);
		sentTable.getColumnModel().getColumn(3).setPreferredWidth(200);

		centerPanel_2.add(tableScrollPane);
	}

	private void createWordTable() {
		// Pievieno teikumu tabulu
		wordTableModel = new DefaultTableModel(0, 0) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		wordTable = new JTable(wordTableModel);

		String[] columns = { "Nr.", "Vârds", "TextRank vçrtçjums" };

		wordTableModel.setColumnIdentifiers(columns);

		JScrollPane tableScrollPane = new JScrollPane(wordTable);
		wordTable.getTableHeader().setReorderingAllowed(false);

		wordTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		wordTable.setPreferredScrollableViewportSize(new Dimension(680, 300));

		wordTable.getColumnModel().getColumn(0).setPreferredWidth(60);
		wordTable.getColumnModel().getColumn(1).setPreferredWidth(200);
		wordTable.getColumnModel().getColumn(2).setPreferredWidth(200);

		centerPanel_6.add(tableScrollPane);
	}

	private void createWordSimilarityMatrix() {
		// Pievieno teikumu tuvibas tabulu
		wordSimTableModel = new DefaultTableModel(0, 0) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		wordSimTable = new JTable(wordSimTableModel);

		JScrollPane wordSimTableScrollPane = new JScrollPane(wordSimTable);
		wordSimTable.getTableHeader().setReorderingAllowed(false);

		wordSimTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		wordSimTable
				.setPreferredScrollableViewportSize(new Dimension(680, 300));

		centerPanel_7.add(wordSimTableScrollPane);
	}

	private void createSentenceSimilarityMatrix() {
		// Pievieno teikumu tuvibas tabulu
		simTableModel = new DefaultTableModel(0, 0) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		simTable = new JTable(simTableModel);

		JScrollPane simTableScrollPane = new JScrollPane(simTable);
		simTable.getTableHeader().setReorderingAllowed(false);

		simTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		simTable.setPreferredScrollableViewportSize(new Dimension(680, 300));

		centerPanel_3.add(new JLabel(
				"Nospieþot uz tabulas ðûnas, var apskatît sîkâku informâciju"));
		centerPanel_3.add(simTableScrollPane);
	}

	private void createSliderSent() {
		// Izveido slaideru
		sliderSent = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
		sliderSent.setEnabled(false);
		centerPanel_4.add(sliderSent);

		sliderSent
				.setBorder(BorderFactory
						.createTitledBorder("Kopsavilkuma apjoms procentos no sâkumâ teksta!"));
		sliderSent.setMinorTickSpacing(5);
		sliderSent.setPaintTicks(true);
		sliderSent.setMajorTickSpacing(25);
		sliderSent.setPaintLabels(true);
		sliderSent.setPreferredSize(new Dimension(600, 70));
	}

	private void createSliderWords() {
		// Izveido slaideru
		sliderWords = new JSlider(JSlider.HORIZONTAL, 0, 100, 10);
		sliderWords.setEnabled(false);
		centerPanel_5.add(sliderWords);

		sliderWords
				.setBorder(BorderFactory
						.createTitledBorder("Atslegvârdu apjoms procentos no visiem vârdiem!"));
		sliderWords.setMinorTickSpacing(5);
		sliderWords.setPaintTicks(true);
		sliderWords.setMajorTickSpacing(25);
		sliderWords.setPaintLabels(true);
		sliderWords.setPreferredSize(new Dimension(600, 70));
	}

	@Override
	public void addLoadButtonEvent(ActionListener actionListener) {
		loadButton.addActionListener(actionListener);

	}

	@Override
	public void enableSummButtons() {
		sentSummButton.setEnabled(true);
	}

	@Override
	public void setProgress(String text) {
		progressLabel.setText(text);
	}

	@Override
	public void disableSummButtons() {
		sentSummButton.setEnabled(false);
	}

	@Override
	public void disableTabs() {
		jtb.setSelectedIndex(0);
		jtb.setEnabledAt(1, false);
		/*
		 * 
		 * 
		 * 
		 * jtb.setEnabledAt(2, false); jtb.setEnabledAt(3, false);
		 */

	}

	@Override
	public void addSentSummEvent(ActionListener actionListener) {
		sentSummButton.addActionListener(actionListener);
	}

	@Override
	public void enableTab_1() {
		jtb.setEnabledAt(1, true);

		
	}

	@Override
	public void setFileTextField(String text) {
		fileText.setText(text);
	}

	@Override
	public void enableTab_2() {
		jtb.setEnabledAt(2, true);
	}

	@Override
	public File showFileDialog() {
		File file = null;

		JFileChooser fileDialog = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"TEXT FILES", "txt", "text");
		fileDialog.setFileFilter(filter);
		int returnValue = fileDialog.showOpenDialog(frame);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			// Ielade failu
			file = fileDialog.getSelectedFile();

			setFileTextField(file.getAbsolutePath());
		} else {
			// Nekas netika izvelets!
			file = null;
		}

		return file;
	}

	@Override
	public void addChangeListenerSent(ChangeListener changeListener) {
		sliderSent.addChangeListener(changeListener);
	}

	@Override
	public void addChangeListenerKWords(ChangeListener changeListener) {
		sliderWords.addChangeListener(changeListener);
	}

	@Override
	public int getSliderSentValue() {
		int rez = 0;
		rez = sliderSent.getValue();
		return rez;
	}

	@Override
	public void setTextArea(String text) {
		// Pievieno tekstu
		textArea.setEnabled(true);
		textArea.setText(text);
	}

	@Override
	public void showTextTable(ArrayList<Sentence> sentenceList) {
		int i = 0;

		sentTableModel.setRowCount(sentenceList.size());

		for (Sentence s : sentenceList) {
			sentTableModel.setValueAt("" + (i + 1), i, 0);

			sentTableModel.setValueAt(s.getOriginalSentence(), i, 1);

			sentTableModel.setValueAt(s.getNewSentence(), i, 2);

			sentTableModel.setValueAt(s.getRank() + "", i, 3);

			i++;
		}
	}

	@Override
	public void showSimMatrixTable(double[][] simMatrix) {
		int dimensionCount = simMatrix.length + 1;
		simTableModel.setColumnCount(dimensionCount);
		simTableModel.setRowCount(dimensionCount);

		for (int i = 0; i < dimensionCount; i++) {
			for (int j = 0; j < dimensionCount; j++) {
				if (j == 0) {
					simTableModel.setValueAt("" + i, j, i);
				} else if (i == 0) {
					simTableModel.setValueAt("" + j, j, i);
				} else {
					simTableModel
							.setValueAt("" + simMatrix[j - 1][i - 1], j, i);
				}
				simTableModel.setValueAt("", 0, 0);
			}
		}

	}

	@Override
	public void showSummaryText(Sentence[] sentArray) {
		// Parada kopsavilkumu

		String summary = "";

		for (int i = 0; i < sentArray.length; i++) {
			summary += sentArray[i].getID() + ") <Rangs: "
					+ sentArray[i].getRank() + "> "
					+ sentArray[i].getOriginalSentence() + "\n";
		}

		sliderSent.setEnabled(true);
		summTextArea.setEnabled(true);
		summTextArea.setText(summary);
	}

	@Override
	public void addTableMouseListener(MouseAdapter mouseAdapter) {
		simTable.addMouseListener(mouseAdapter);
	}

	@Override
	public int getRowAtPoint(Point point) {

		return simTable.rowAtPoint(point);
	}

	@Override
	public int getColumnAtPoint(Point point) {
		return simTable.columnAtPoint(point);
	}

	@Override
	public void showError(String errorTxt) {
		JOptionPane.showMessageDialog(frame, errorTxt);
	}

	@Override
	public int getSliderWordsValue() {
		return sliderWords.getValue();
	}

	@Override
	public void showTwoSentenceComparison(SentenceComparison sentenceComparison) {
		// System.out.println(sentenceComparison.getFirstSentence());
		// System.out.println(sentenceComparison.getSecondSentence());

		DefaultTableModel tableModel = new DefaultTableModel(
				sentenceComparison.getWordArr().length, 3) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JTable table = new JTable(tableModel);
		JScrollPane tableScrollPane = new JScrollPane(table);

		String[] columns = { "Vârdi", "Pirmâ teikuma vektors",
				"Otrâ teikuma vektors" };
		tableModel.setColumnIdentifiers(columns);

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setPreferredScrollableViewportSize(new Dimension(100, 200));
		table.getTableHeader().setReorderingAllowed(false);

		for (int i = 0; i < sentenceComparison.getWordArr().length; i++) {
			// tableModel.setValueAt(sentenceComparison.getWordArr()[i], i, 0);
			tableModel.setValueAt(sentenceComparison.getWordsArr()[i], i, 0);
			tableModel.setValueAt(
					sentenceComparison.getFirstSentRank()[i] + "", i, 1);
			tableModel.setValueAt(sentenceComparison.getSecondSentRank()[i]
					+ "", i, 2);
		}

		JLabel label1 = new JLabel("<html> "
				+ sentenceComparison.getFirstSentenceID() + "] "
				+ sentenceComparison.getFirstSentence() + "</html>");
		JLabel label2 = new JLabel("<html> "
				+ sentenceComparison.getSecondSentenceID() + "] "
				+ sentenceComparison.getSecondSentence() + "</html>");

		label1.setPreferredSize(new Dimension(580, 80));
		label2.setPreferredSize(new Dimension(580, 80));

		table.getColumnModel().getColumn(0).setPreferredWidth(300);
		table.getColumnModel().getColumn(1).setPreferredWidth(130);
		table.getColumnModel().getColumn(2).setPreferredWidth(130);

		JComponent[] inputs = new JComponent[] {
				label1,
				label2,
				tableScrollPane,
				new JLabel("Cos(O) = (a.b)/(SQRT(a^2)SQRT(b^2)) = "
						+ sentenceComparison.getRank()) };

		JOptionPane.showMessageDialog(null, inputs, "Teikumu salidzinâjums",
				JOptionPane.PLAIN_MESSAGE);
	}
}
