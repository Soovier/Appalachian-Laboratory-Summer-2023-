package UIActivation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class Swing extends JPanel {

	public static void main(String[] args) {
		Swing test = new Swing();
		test.Settings();
	}

	private void drawRectangle(Graphics g, int x, int y, int width, int height) {
		g.drawRect(x, y, width, height);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, 400, 75);
	}


	public void Settings() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JFrame frame = new JFrame("UMCES K-Foldanator By: Stephen");
		frame.setPreferredSize(new Dimension(400, 500));
		frame.setResizable(false);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new Swing());

		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setLayout(null);
		frame.setVisible(true);
	}

	public void setTitles(JFrame Frame) {

	}
}
