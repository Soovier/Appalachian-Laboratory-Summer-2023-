package UIActivation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class Swing extends JPanel {
	private String TaxonomyFile = "";
	private String FastaFile = "";
	private int K_Amount;
	private boolean Fixed_Amount;
	public boolean PromptError;

	JFrame frame;
	JPanel centerPanel;
	JPanel gridPanel;

	{
		Fixed_Amount = false;
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
		gridPanel = new JPanel(new GridLayout(5, 1, 0, 10));
		gridPanel.setBackground(new Color(19, 108, 150));
		frame.add(centerPanel, BorderLayout.CENTER);
		frame.add(gridPanel, BorderLayout.EAST);
	}

	public static void main(String[] args) {
		Swing test = new Swing();
		test.Settings();
	}

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

		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
	}

	private void gridPanelMethod() {
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

		b1.setFont(new Font("Times New Roman\r\n", Font.BOLD, 14));
		b2.setFont(new Font("Times New Roman\r\n", Font.BOLD, 14));
		b3.setFont(new Font("Times New Roman\r\n", Font.BOLD, 14));
		b4.setFont(new Font("Baskerville Old Face\r\n" + "", Font.BOLD, 10));
		b5.setFont(new Font("Baskerville Old Face\r\n" + "", Font.BOLD, 10));

		b1.setFocusable(false);
		b2.setFocusable(false);
		b3.setFocusable(false);
		b4.setFocusable(false);
		b5.setFocusable(false);
		
		b1.addActionListener(e -> {
			Boolean ifRan = HasRequirments();
		});
		
		b2.addActionListener(e -> {
			Boolean ifRan = HasRequirments();
		});

		b3.addActionListener(e -> {
			Boolean ifRan = HasRequirments();
		});

		b4.addActionListener(e -> {
			String taxfileName = JOptionPane.showInputDialog("Enter File Name:", TaxonomyFile);
			if (!taxfileName.equals("null")) {
				TaxonomyFile = taxfileName;
			}
		});

		b5.addActionListener(e -> {
			String fasta = JOptionPane.showInputDialog("Enter File Name:", FastaFile);
			if (!fasta.equals("null")) {
				FastaFile = fasta;
			}
		});

	}

	/**
	 * setting the default error messages if we don't have certain files / Type 1
	 * checks for errors
	 */

	@SuppressWarnings("static-access")
	public boolean HasRequirments() {
		String soundFilePath = "UI-Items/ErrorSound.aifc";
		JOptionPane pane = new JOptionPane();
		if (TaxonomyFile.equals("") && FastaFile.equals("")) {
			playSound(soundFilePath);
			String Error = "Please Import Taxonomy Database and Fasta Database.";
			pane.showMessageDialog(null, Error, "Error: 101", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (TaxonomyFile.equals("") && !FastaFile.equals("")) {
			playSound(soundFilePath);
			String Error = "Please Import Taxonomy Database.";
			pane.showMessageDialog(null, Error, "Error: 102", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (!TaxonomyFile.equals("") && FastaFile.equals("")) {
			playSound(soundFilePath);
			String Error = "Please Import Fasta Database.";
			pane.showMessageDialog(null, Error, "Error: 103", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	// PROMPT BROWSING FOR FILES //
	private void addDirectoryExplorer() {
		JTextField textField = new JTextField("FILE PATH WILL BE PRINTED HERE!!");
		textField.setFont(new Font("Times New Roman\r\n" + "" + "", Font.ITALIC, 12));
		textField.setEditable(false);
		frame.add(textField, BorderLayout.NORTH);

		JButton button = new JButton("Browse Directory");
		button.setFont(new Font("Times New Roman\r\n" + "" + "", Font.LAYOUT_RIGHT_TO_LEFT, 12));

		frame.add(button, BorderLayout.SOUTH);

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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

	public static void playSound(String soundFilePath) {
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

	// GETTERS AND SETTERS //

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

	public boolean getFixed_Amount() {
		return Fixed_Amount;
	}

	public void setFixed_Amount(boolean fixed_Amount) {
		Fixed_Amount = fixed_Amount;
	}

}