package UIActivation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class fileLogs {
	public int K;
	private static HashMap<String, String> map;
	private int AlignCounter;

	{
		Swing v1 = new Swing();
		K = v1.getK_Amount();
		AlignCounter = 1;
	}

	/**
	 * @param passed
	 * @param Type   If Type 1 then makes Alignment Data, if Type 2 then makes
	 *               Annotation Data , If Type 3 then make Trimmed Annotation data
	 */

	public fileLogs(Boolean passed, int Type) {
		if (!passed) {System.err.println("Log Not Passed"); return;}

	}

	public void getType(int Type) {
		Swing uiclass = new Swing();

		switch (Type) {
		case (1):
			break;
		case (2):
			break;
		case (3):
			break;
		default:
			uiclass.playSound("UI-Items/ErrorSound.aifc");
			String error = "Case Erorr, Please Contant Developer";
			JOptionPane.showMessageDialog(null, error, "Error: 707", JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	private void executeVsearchCommand(String testFasta, String trainFasta) {
		String[] command = { "/bin/sh", "-c",
				"vsearch --usearch_global " + testFasta + " --db " + trainFasta
						+ " --id 0.70 --maxaccepts 100 --maxrejects 50 --maxhits 1 --gapopen 0TE --gapext 0TE --userout "
						+ (String.valueOf(AlignCounter) + "TestAlignment")
						+ " --userfields query+target+id+alnlen+mism+opens+qlo+qhi+tlo+thi+evalue+bits+qcov --query_cov 0.8 --threads 28" };

		ProcessBuilder processBuilder = new ProcessBuilder(command);
		try {
			Process process = processBuilder.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			int exitCode = process.waitFor();
			System.out.println("Exit code: " + exitCode);
			AlignCounter += 1;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static String getCurrentTimeString() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		return now.format(formatter);
	}

	public static HashMap<String, String> getMap() {
		return map;
	}

	public static void setMap(HashMap<String, String> map) {
		fileLogs.map = map;
	}

}
