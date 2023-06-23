package UIActivation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class Swing extends JPanel {
	JFrame frame;
	JPanel centerPanel;
	JPanel gridPanel;

	{
		frame = new JFrame("UMCES K-Foldanator By: Stephen Osunkunle");
		centerPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon background = new ImageIcon("newbackground.jpg");
				Image img = background.getImage();
				g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
			}

		};
		gridPanel = new JPanel(new GridLayout(5, 1));
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
		ImageIcon icon = new ImageIcon("UMCESLogo.jpg");
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
		JButton b2 = new JButton("Get Annotation Data");
		gridPanel.add(b2);
		JButton b3 = new JButton("Get Trimmed Data");
		gridPanel.add(b3);
		JButton b4 = new JButton("Add/Change Taxonomy Database");
		gridPanel.add(b4);
		JButton b5 = new JButton("Add/Change Fasta Database");
		gridPanel.add(b5);

		b1.setFont(new Font("Times New Roman\r\n", Font.BOLD, 9));
		b2.setFont(new Font("Times New Roman\r\n", Font.BOLD, 9));
		b3.setFont(new Font("Times New Roman\r\n", Font.BOLD, 9));
		b4.setFont(new Font("Jokerman", Font.ITALIC, 10));
		b5.setFont(new Font("Jokerman", Font.ITALIC, 10));

		b1.setFocusable(false);
		b2.setFocusable(false);
		b3.setFocusable(false);
		b4.setFocusable(false);
		b5.setFocusable(false);

	}

	private void addDirectoryExplorer() {
		JTextField textField = new JTextField("FILE PATH WILL BE PRINTED HERE!!");
		textField.setFont(new Font("Times New Roman\r\n" + "" + "", Font.ITALIC, 16));
		textField.setEditable(false);
		frame.add(textField, BorderLayout.NORTH);

		JButton button = new JButton("Browse Directory");
		button.setFont(new Font("Times New Roman\r\n" + "" + "", Font.ITALIC, 16));

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
}