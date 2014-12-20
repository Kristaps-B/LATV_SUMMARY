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
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;


public class GUI {
	
	private JFrame frame;
	private JPanel centerPanel_1;
	private JPanel centerPanel_2;
	private JPanel centerPanel_3;
	private JPanel northPanel;
	private JTextArea textArea;
	private JTextField fileText;
	private JButton loadButton;
	private JTable sentTable;
	private JTable simTable;
	private DefaultTableModel sentTableModel;
	private DefaultTableModel simTableModel;
	
	
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
		
		northPanel = new JPanel();
		
		jtb.addTab("Sâkuma teksts", centerPanel_1);
		jtb.addTab("Teksta teikumi", centerPanel_2);
		jtb.addTab("Teikumu lîdzibas matrica", centerPanel_3);
		
		frame.add(jtb,BorderLayout.CENTER);
		frame.add(northPanel,BorderLayout.NORTH);
		
		//Veido teksta logu
		textArea = new JTextArea("",18,70);
		JScrollPane scrollPane = new JScrollPane(textArea);
		textArea.setEnabled(false);
		textArea.setLineWrap(true);
		centerPanel_1.add(scrollPane);
		
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
		sentTableModel = new DefaultTableModel(0,3)
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
		
		
		
		String [] columns = {"Nr.","Originalais teikums","Teikums bez apstâðanas vârdiem un punktuâcijâs zîmçm"};
		
		sentTableModel.setColumnIdentifiers(columns);
		
		
		JScrollPane tableScrollPane = new JScrollPane(sentTable);
		sentTable.getTableHeader().setReorderingAllowed(false);
		
		sentTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		sentTable.setPreferredScrollableViewportSize(new Dimension(680,300));
		
		sentTable.getColumnModel().getColumn(0).setPreferredWidth(40);
		sentTable.getColumnModel().getColumn(1).setPreferredWidth(440);
		sentTable.getColumnModel().getColumn(2).setPreferredWidth(400);
		
		
		
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
		
		//Parada logu
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	private void loadFile(File file)
	{
		FileLoader loader = new FileLoader();
		
		loader.loadFile(file);
		
		String text = loader.getFileContent();
		
		//Pievieno tekstu
		textArea.setEnabled(true);
		textArea.setText(text);
		
		//Apstrada tekstu
		TextProcessing textProcessing = new TextProcessing(text);
		textProcessing.splitIntoSentences();
		
		ArrayList <Sentence> sentenceList = textProcessing.getSentenceList();
		
		SimMatrix simMatrix = new SimMatrix(sentenceList);
		simMatrix.createMatrix();
		
		double [][] simMatrixRes = simMatrix.getSimMatrix();
		
		
		showText(sentenceList);
		
		showSimMatrix(simMatrixRes);
		
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
					simTableModel.setValueAt(""+i, i, j);
				}
				else if (i == 0)
				{
					simTableModel.setValueAt(""+j, i, j);
				}
				else
				{
					simTableModel.setValueAt(""+simMatrixRes[j-1][i-1], i, j);
				}
				simTableModel.setValueAt("", 0, 0);
			}
		}
		
		
	}
}
