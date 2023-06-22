import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class UnixUI {

	public static void main(String[] args) {
		UnixUI test = new UnixUI();
//		test.defaultFrame();
		test.Tester1();
	}

	private void Tester1() {
		JFrame frame = new JFrame();
		JButton button = new JButton("jnu Me");
		button.setBounds(150, 200, 220, 50);
		frame.add(button);

		frame.setSize(500, 600);
		frame.setLayout(null);
		frame.setVisible(true);
	}

	public void defaultFrame() {

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("UMCES K-Foldernator [By: Stephen]");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLayout(new FlowLayout());
			frame.setSize(500, 500);

			JButton button = new JButton("Click me");
			JTextField textField = new JTextField(20);

			frame.add(button);
			frame.add(textField);

			frame.pack();
			frame.setVisible(true);
		});
	}
}