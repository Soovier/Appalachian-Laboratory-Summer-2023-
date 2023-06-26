package UIActivation;

import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class HashMapUI extends JFrame {
	private JPanel logsPanel;
	private HashMap<String, String> hashMap;

	public HashMapUI() {
		setTitle("HashMap UI");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



		hashMap = new HashMap<>();
	}


	public static void main(String[] args) {
		HashMapUI ui = new HashMapUI();
		ui.setVisible(true);
	}
}