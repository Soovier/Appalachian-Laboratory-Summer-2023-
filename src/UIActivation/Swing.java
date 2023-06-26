package UIActivation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import Cluster.ClusterExE;

@SuppressWarnings("serial")
public class Swing extends JPanel {
	private static String TaxonomyFile = "";
	private static String FastaFile = "";
	private static int K_Amount;
	public static boolean Test_Train_Made;
	public static HashMap<String, String> hashMap;

	JPanel logsPanel;
	JFrame frame;
	JFrame doamsda;
	JPanel centerPanel;
	JPanel gridPanel;

	// Setting up the layout for the whole UI and setting the default value type
	{
		this.logsPanel = new JPanel(new FlowLayout());
		hashMap = new HashMap<>();
		Test_Train_Made = false;
		K_Amount = 5;
		frame = new JFrame("UMCES K-Foldanator By: Stephen Osunkunle");
		centerPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon background = new ImageIcon("UI-Items/newbackground.jpg");
				Image img = background.getImage();
				g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
			}

		};
		gridPanel = new JPanel(new GridLayout(8, 1, 0, 5));
		gridPanel.setBackground(new Color(19, 108, 150));
		frame.add(centerPanel, BorderLayout.CENTER);
		frame.add(gridPanel, BorderLayout.EAST);
	}

	public static void main(String[] args) {
		Swing test = new Swing();
		test.Settings();
	}

	// THE MAIN OF THIS CLASS
	public void Settings() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		ImageIcon icon = new ImageIcon("UI-Items/UMCESLogo.jpg");
		frame.setPreferredSize(new Dimension(950, 520));
		frame.setResizable(false);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(icon.getImage());

		addDirectoryExplorer();
		gridPanelMethod();
		
		this.logsPanel.setPreferredSize(new Dimension(200, frame.getPreferredSize().height));

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		// Add the JLabel
		JLabel titleLabel = new JLabel("Logs");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
		titleLabel.setForeground(Color.BLACK);
		logsPanel.add(titleLabel);

		// Wrap the logsPanel in a JScrollPane
		JScrollPane scrollPane = new JScrollPane(logsPanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		// Add a vertical glue to push the logsPanel to the bottom
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(scrollPane);

		frame.add(mainPanel, BorderLayout.WEST);
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
	}

	private void gridPanelMethod() {
		// Creating buttons and adding them to the grid panel
		JButton b1 = new JButton("Get Alignment Data");
		gridPanel.add(b1);
		b1.setBackground(Color.black);
		b1.setForeground(Color.white);
		JButton b2 = new JButton("Get Annotation Data");
		gridPanel.add(b2);
		b2.setBackground(Color.black);
		b2.setForeground(Color.white);
		JButton b3 = new JButton("Get Trimmed Data");
		gridPanel.add(b3);
		b3.setBackground(Color.black);
		b3.setForeground(Color.white);
		JButton b4 = new JButton("Add/Change Taxonomy Database");
		gridPanel.add(b4);
		b4.setBackground(Color.black);
		b4.setForeground(Color.white);
		JButton b5 = new JButton("Add/Change Fasta Database");
		gridPanel.add(b5);
		b5.setBackground(Color.black);
		b5.setForeground(Color.white);
		JButton b6 = new JButton("Set K Fold");
		gridPanel.add(b6);
		b6.setBackground(Color.black);
		b6.setForeground(Color.green);
		JButton b7 = new JButton("Create Test and Train Data");
		gridPanel.add(b7);
		b7.setBackground(Color.black);
		b7.setForeground(Color.red);
		JButton b8 = new JButton("Remove Test and Train Data");
		gridPanel.add(b8);
		b8.setBackground(Color.black);
		b8.setForeground(Color.ORANGE);

		// Setting font styles for the buttons
		b1.setFont(new Font("Times New Roman\r\n", Font.BOLD, 14));
		b2.setFont(new Font("Times New Roman\r\n", Font.BOLD, 14));
		b3.setFont(new Font("Times New Roman\r\n", Font.BOLD, 14));
		b4.setFont(new Font("Baskerville Old Face" + "", Font.BOLD, 10));
		b5.setFont(new Font("Baskerville Old Face" + "", Font.BOLD, 10));
		b6.setFont(new Font("Baskerville Old Face" + "", Font.BOLD, 10));
		b7.setFont(new Font("Baskerville Old Face" + "", Font.BOLD, 10));
		b8.setFont(new Font("Baskerville Old Face" + "", Font.BOLD, 10));

		// Setting focusability for the buttons
		b1.setFocusable(false);
		b2.setFocusable(false);
		b3.setFocusable(false);
		b4.setFocusable(false);
		b5.setFocusable(false);
		b6.setFocusable(false);
		b7.setFocusable(false);
		b8.setFocusable(false);

		// Adding action listeners to the buttons
		b1.addActionListener(e -> {
			Boolean ifRan = HasRequirments("Aligment Data Added");
			fileLogs log = new fileLogs(ifRan);
			log.getType(1);
		});

		b2.addActionListener(e -> {
			Boolean ifRan = HasRequirments("Annotation Data Added!");
			fileLogs log = new fileLogs(ifRan);
			log.getType(2);
		});

		b3.addActionListener(e -> {
			Boolean ifRan = HasRequirments("Trimmed Annotation Data Added!");
			fileLogs log = new fileLogs(ifRan);
			log.getType(3);
		});

		b4.addActionListener(e -> {
			// Prompting the user to enter a file name for the taxonomy database
			String taxfileName = JOptionPane.showInputDialog("Enter File Name:", TaxonomyFile);
			if (!taxfileName.equals("null")) {
				TaxonomyFile = taxfileName;
			}
		});

		b5.addActionListener(e -> {
			// Prompting the user to enter a file name for the fasta database
			String fasta = JOptionPane.showInputDialog("Enter File Name:", FastaFile);
			if (!fasta.equals("null")) {
				FastaFile = fasta;
			}
		});

		b6.addActionListener(e -> {
			// Prompting the user to enter a value for K amount
			String amount = JOptionPane.showInputDialog("Enter K Amount:", K_Amount);
			try {
				K_Amount = Integer.valueOf(amount);
				String soundFilePath = "UI-Items/Conformation.aifc";
				playSound(soundFilePath);
			} catch (Exception e2) {
				if (amount.equals("null")) {
					e2.printStackTrace();
				} else {
					String soundFilePath = "UI-Items/ErrorSound.aifc";
					playSound(soundFilePath);
					JOptionPane.showMessageDialog(null, "Invalid Entry", "Error: 201", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		b7.addActionListener(e -> {
			Test_Train_Made = true;
			Boolean ifRan = HasRequirments("Test and Train Data Created!");
			if (ifRan) {
				ClusterExE cluster = new ClusterExE(logsPanel, hashMap);
				cluster.Input_TestData(K_Amount, TaxonomyFile, "", "TAX");
				cluster.Input_TestData(K_Amount, FastaFile, "", "FASTA");
				cluster.testData(K_Amount, "", "TAX");
				cluster.testData(K_Amount, "", "FASTA");
			}
		});

		b8.addActionListener(e -> {
			String currentDirectory = System.getProperty("user.dir");
			int cn = 0;
			// List files in the current directory
			File folder = new File(currentDirectory);
			File[] listOfFiles = folder.listFiles();

			for (File file : listOfFiles) {
				if (file.isFile()) {
					String temp = file.getName();
					// just finding all the tranining and testing files we made and remove them from
					// path
					String[] labels = { "Training_Fasta", "Training_Taxonomy", "Test_Fasta", "Test_Taxonomy" };
					for (int i = 0; i < labels.length; i++) {
						if (temp.contains(labels[i])) {
							cn += 1;
							file.delete();
						}
					}
				}
			}

			// Just clearing the hashmap and the JPanel
			hashMap.clear();
			Component[] components = logsPanel.getComponents();
			for (Component component : components) {
				if (component instanceof JLabel) {
					JLabel label = (JLabel) component;
					String labelText = label.getText();
					String[] dataname = { "Training_Fasta", "Training_Taxonomy", "Test_Fasta", "Test_Taxonomy" };
					for (int i = 0; i < dataname.length; i++) {
						if (labelText.contains(dataname[i])) {
							logsPanel.remove(label);
						}
					}
				}
			}

			logsPanel.revalidate();
			logsPanel.repaint();

			String message;
			String statusmessage;
			String soundFilePath;
			int OPTION;
			if (cn > 0) {
				message = "Test and Train Files Have Been Removed!";
				statusmessage = "Sucess 102";
				OPTION = JOptionPane.INFORMATION_MESSAGE;
				soundFilePath = "UI-Items/Conformation.aifc";
			} else {
				message = "No Files To Remove";
				statusmessage = "Error 501";
				OPTION = JOptionPane.ERROR_MESSAGE;
				soundFilePath = "UI-Items/ErrorSound.aifc";
			}
			Test_Train_Made = false;

			playSound(soundFilePath);
			JOptionPane.showMessageDialog(null, message, statusmessage, OPTION);
		});

	}

	public void AddToHashMap(String key, String val) {
		JLabel v1 = new JLabel(key);
		v1.setBackground(Color.gray);
		v1.setForeground(Color.BLACK);
		v1.setFont(new Font("Baskerville Old Face" + "", Font.BOLD, 7));
		System.out.println(this.logsPanel);
		logsPanel.add(v1);
		hashMap.put(key, val);

		logsPanel.revalidate();
		logsPanel.repaint();
	}

	/**
	 * setting the default error messages if we don't have certain files / Type 1
	 * checks for errors
	 */

	public boolean HasRequirments(String sucessMessage) {
		String soundFilePath = "UI-Items/ErrorSound.aifc";
		if (TaxonomyFile.equals("") && FastaFile.equals("")) {
			playSound(soundFilePath);
			String Error = "Please Import Taxonomy Database and Fasta Database.";
			JOptionPane.showMessageDialog(null, Error, "Error: 101", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (TaxonomyFile.equals("") && !FastaFile.equals("")) {
			playSound(soundFilePath);
			String Error = "Please Import Taxonomy Database.";
			JOptionPane.showMessageDialog(null, Error, "Error: 102", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (!TaxonomyFile.equals("") && FastaFile.equals("")) {
			playSound(soundFilePath);
			String Error = "Please Import Fasta Database.";
			JOptionPane.showMessageDialog(null, Error, "Error: 103", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (!Test_Train_Made) {
			playSound(soundFilePath);
			String Error = "No Train or Testing Data.";
			JOptionPane.showMessageDialog(null, Error, "Error: 109", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		String corret_music = "UI-Items/Conformation.aifc";
		playSound(corret_music);
		JOptionPane.showMessageDialog(null, sucessMessage, "Succession: 101", JOptionPane.INFORMATION_MESSAGE);
		return true;
	}

	// PROMPT BROWSING FOR FILES //
	private void addDirectoryExplorer() {
		// Creating a text field to display the file path
		JTextField textField = new JTextField("FILE PATH WILL BE PRINTED HERE!!");
		textField.setFont(new Font("Times New Roman\r\n" + "" + "", Font.ITALIC, 12));
		textField.setEditable(false);
		frame.add(textField, BorderLayout.NORTH);

		// Creating a button for browsing directories
		JButton button = new JButton("Browse Directory");
		button.setFont(new Font("Times New Romana" + "" + "", Font.LAYOUT_RIGHT_TO_LEFT, 12));

		frame.add(button, BorderLayout.SOUTH);
		// Adding an action listener to the button
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Opening a file chooser dialog
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
				int result = fileChooser.showOpenDialog(frame);

				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					textField.setText(selectedFile.getAbsolutePath());
				}
			}
		});
	}

	// Method for playing sound
	public void playSound(String soundFilePath) {
		try {
			File soundFile = new File(soundFilePath);
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public String getTaxonomyFile() {
		return TaxonomyFile;
	}

	public void setTaxonomyFile(String taxonomyFile) {
		TaxonomyFile = taxonomyFile;
	}

	public String getFastaFile() {
		return FastaFile;
	}

	public void setFastaFile(String fastaFile) {
		FastaFile = fastaFile;
	}

	public int getK_Amount() {
		return K_Amount;
	}

	public void setK_Amount(int k_Amount) {
		K_Amount = k_Amount;
	}

}
