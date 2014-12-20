import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.BorderFactory;


public class GUI {
	
	private JFrame frame;
	private JPanel centerPanel_1;
	private JPanel centerPanel_2;
	private JPanel centerPanel_3;
	private JPanel centerPanel_4;
	private JPanel northPanel;
	private JTextArea textArea;
	private JTextArea summTextArea;
	private JTextField fileText;
	private JButton loadButton;
	private JTable sentTable;
	private JTable simTable;
	private DefaultTableModel sentTableModel;
	private DefaultTableModel simTableModel;
	private Summarizer summarizer;
	private ArrayList <Sentence> sentenceList;
	
	private JSlider slider;
	
	
	//Izveido grafisko interfeisu
	public void createGUI()
	{
		//Veido logu
		frame = new JFrame("LATV_SUMMARY (Kristaps Babrovskis ITI) ");
		frame.setSize(840,480);
		frame.setLayout(new BorderLayout());
		
		//Izveido panelus
		JTabbedPane jtb = new JTabbedPane();
		
		
		
		
		centerPanel_1 = new JPanel();
		centerPanel_2 = new JPanel();
		centerPanel_3 = new JPanel();
		centerPanel_4 = new JPanel();
		
		northPanel = new JPanel();
		
		jtb.addTab("Sâkuma teksts", centerPanel_1);
		jtb.addTab("Teksta teikumi", centerPanel_2);
		jtb.addTab("Teikumu lîdzibas matrica", centerPanel_3);
		jtb.addTab("Kopsavilkums", centerPanel_4);
		
		frame.add(jtb,BorderLayout.CENTER);
		frame.add(northPanel,BorderLayout.NORTH);
		
		//Veido teksta logu
		textArea = new JTextArea("",18,70);
		JScrollPane scrollPane = new JScrollPane(textArea);
		textArea.setEnabled(false);
		textArea.setLineWrap(true);
		centerPanel_1.add(scrollPane);
		
		//Veido kopsavilkuma teksta logu
		summTextArea = new JTextArea("",18,70);
		JScrollPane summScrollPane = new JScrollPane(summTextArea);
		summTextArea.setEnabled(false);
		summTextArea.setLineWrap(true);
		centerPanel_4.add(summScrollPane);
		
		//Izveido faila ieladi
		northPanel.setLayout(new FlowLayout());
		northPanel.add(new JLabel("Ielâdçt teksta failu: "));
		
		fileText = new JTextField("",18);
		northPanel.add(fileText);
		
		loadButton = new JButton("...");
		northPanel.add(loadButton);
		
		loadButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					JFileChooser fileDialog = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES","txt","text");
					fileDialog.setFileFilter(filter);
					int returnValue = fileDialog.showOpenDialog(frame);
					
					if (returnValue == JFileChooser.APPROVE_OPTION)
					{
						//Ielade failu
						File file = fileDialog.getSelectedFile();
						fileText.setText(file.getAbsolutePath());
						
						loadFile(file);
					}
					else
					{
						//Nekas netika izvelets!
					}
				}
			}
		);
		
		//Pievieno teikumu tabulu
		sentTableModel = new DefaultTableModel(0,0)
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		sentTable = new JTable(sentTableModel);
		
		
		
		String [] columns = {"Nr.","Originalais teikums","Teikums bez apstâðanas vârdiem un punktuâcijâs zîmçm", "TextRank vçrtçjums"};
		
		sentTableModel.setColumnIdentifiers(columns);
		
		
		JScrollPane tableScrollPane = new JScrollPane(sentTable);
		sentTable.getTableHeader().setReorderingAllowed(false);
		
		sentTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		sentTable.setPreferredScrollableViewportSize(new Dimension(680,300));
		
		sentTable.getColumnModel().getColumn(0).setPreferredWidth(40);
		sentTable.getColumnModel().getColumn(1).setPreferredWidth(440);
		sentTable.getColumnModel().getColumn(2).setPreferredWidth(400);
		sentTable.getColumnModel().getColumn(3).setPreferredWidth(200);
		
		
		centerPanel_2.add(tableScrollPane);
		
		
		//Pievieno teikumu tuvibas tabulu
		simTableModel = new DefaultTableModel(0,0)
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		simTable = new JTable(simTableModel);
		
		
		JScrollPane simTableScrollPane = new JScrollPane(simTable);
		simTable.getTableHeader().setReorderingAllowed(false);
		
		simTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		simTable.setPreferredScrollableViewportSize(new Dimension(680,300));	
		centerPanel_3.add(simTableScrollPane);
		
		//Izveido slaideru
		slider = new JSlider(JSlider.HORIZONTAL,0,100,50);
		slider.setEnabled(false);
		centerPanel_4.add(slider);
		
		slider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				sliderChanged();
			}
		});
		
		slider.setBorder(BorderFactory.createTitledBorder("Kopsavilkuma apjoms procentos no sâkumâ teksta!"));
		slider.setMinorTickSpacing(5);
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(25);
		slider.setPaintLabels(true);
		slider.setPreferredSize(new Dimension(600,70));
		
		//Parada logu
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	private void loadFile(File file)
	{
		FileLoader loader = new FileLoader();
		
		loader.loadFile(file);
		
		String text = loader.getFileContent();
		
		processText(text);
		
	}
	
	private void processText(String text)
	{
		//Pievieno tekstu
		textArea.setEnabled(true);
		textArea.setText(text);
		
		//Apstrada tekstu
		TextProcessing textProcessing = new TextProcessing(text);
		textProcessing.splitIntoSentences();
		
		sentenceList = textProcessing.getSentenceList();
		
		SimMatrix simMatrix = new SimMatrix(sentenceList);
		simMatrix.createMatrix();
		
		double [][] simMatrixRes = simMatrix.getSimMatrix();
		
		
		//Veic textrank algoritmu
		TextRank textRank = new TextRank(simMatrixRes);
		textRank.startTextRank();
		
		double [] sentRank = textRank.getScoreVector();
		
		
		for (int i = 0; i< sentRank.length; i++)
		{
			sentenceList.get(i).setRank(sentRank[i]);
		}
		
		
		
		
		showText(sentenceList);
		showSimMatrix(simMatrixRes);
		showSummary(sentenceList);
	}
	
	private void showText(ArrayList <Sentence> sentenceList)
	{
		int i = 0;
		
		sentTableModel.setRowCount(sentenceList.size());
		
		
		for (Sentence s:sentenceList)
		{
			sentTableModel.setValueAt(""+(i+1), i, 0);
			
			sentTableModel.setValueAt(s.getOriginalSentence(), i, 1);
			
			sentTableModel.setValueAt(s.getNewSentence(), i, 2);
			
			sentTableModel.setValueAt(s.getRank()+"", i, 3);
			
			i++;
		}
	}
	
	private void showSimMatrix(double [][] simMatrixRes)
	{
		int dimensionCount = simMatrixRes.length + 1; 
		simTableModel.setColumnCount(dimensionCount);
		simTableModel.setRowCount(dimensionCount);
		
		for (int i = 0; i< dimensionCount;i++)
		{
			for (int j=0; j< dimensionCount;j++)
			{
				if (j == 0)
				{
					simTableModel.setValueAt(""+i, j, i);
				}
				else if (i == 0)
				{
					simTableModel.setValueAt(""+j, j, i);
				}
				else
				{
					simTableModel.setValueAt(""+simMatrixRes[j-1][i-1], j, i);
				}
				simTableModel.setValueAt("", 0, 0);
			}
		}
		
		
	}
	
	private void showSummary(ArrayList <Sentence> sentenceList)
	{
		//Parada kopsavilkumu
		summarizer = new Summarizer(sentenceList);
		
		int n_max = sentenceList.size();
		
		int proc = slider.getValue();
		
		int n = (int)Math.round(n_max *(proc/100.0));
		
		//System.out.println("Panem: "+n);
		
		Sentence [] sentArray = summarizer.getNSentences(n);
		
		String summary = "";
		
		for (int i=0; i<sentArray.length; i++)
		{
			summary += sentArray[i].getID()+ ") <Rangs: "+sentArray[i].getRank()+"> "+sentArray[i].getOriginalSentence() + "\n";
		}
		
		slider.setEnabled(true);
		summTextArea.setEnabled(true);
		summTextArea.setText(summary);
	}
	
	public void sliderChanged()
	{
		showSummary(sentenceList);
	}
}
