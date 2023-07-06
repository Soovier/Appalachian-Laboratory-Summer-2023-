package UIActivation;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Cluster.TrimMtxa2IDs_4;
import Cluster.VsearchToMetaxa_3;

@SuppressWarnings("serial")
public class fileLogs extends Swing {
	private boolean canRun;

	/**
	 * @param passed
	 * @param Type   
	 */
	public fileLogs(Boolean passed) {
		canRun = passed;
	}

	public fileLogs(JFrame frame) {
        this.frame = frame;
		this.canRun = false;
	}

	public void getType(int Type) {
		if (!this.canRun) {
			return;
		}
		// Case O(n) Worst Case O(n)
		switch (Type) {
		case (1):
			// Run the alignment K times
			for (int i = 0; i < getK_Amount(); i++) {// Takes Test and Training Fasta Data
				executeVsearchCommand(String.valueOf(i) + "Test_Fasta", String.valueOf(i) + "Training_Fasta",
						String.valueOf(i));
				AddToHashMap(String.valueOf(i) + "TestAlignment", "Created: " + getCurrentTimeString());
			}
			super.playSound("UI-Items/Conformation.aifc");
			String error3 = "Alignment Data Has Been Added!";
			JOptionPane.showMessageDialog(null, error3, "Sucess: 701", JOptionPane.INFORMATION_MESSAGE);
			break;
		case (2):
			VsearchToMetaxa_3 annotation = new VsearchToMetaxa_3();
			for (int i = 0; i < getK_Amount(); i++) {
				annotation.TrimAlignment(String.valueOf(i) + "TestAlignment"); // Alignment Data
				annotation.TrimTrainedTaxonomy(getTaxonomyFile()); // ORIGNAL DATA BASE
				annotation.OutputFile(String.valueOf(i) + "Annotation_Data"); // OUTPUT FILE
				AddToHashMap(String.valueOf(i) + "Annotation_Data", "Created: " + getCurrentTimeString());
			}
			super.playSound("UI-Items/Conformation.aifc");
			String error2 = "Annotation Data Has Been Added!";
			JOptionPane.showMessageDialog(null, error2, "Sucess: 702", JOptionPane.INFORMATION_MESSAGE);
			break;
		case (3):
			TrimMtxa2IDs_4 trim = new TrimMtxa2IDs_4();
			for (int i = 0; i < getK_Amount(); i++) {
				trim.TrimPredTaxonomy(String.valueOf(i) + "Annotation_Data", String.valueOf(i) + "Trmd_Annotation");
				AddToHashMap(String.valueOf(i) + "Annotation_Data", "Created: " + getCurrentTimeString());
			}
			super.playSound("UI-Items/Conformation.aifc");
			String error1 = "Trim Added Has Been Made";
			JOptionPane.showMessageDialog(null, error1, "Sucess: 703", JOptionPane.INFORMATION_MESSAGE);
			break;
		default:
			super.playSound("UI-Items/ErrorSound.aifc");
			String error = "Case Erorr, Please Contant Developer";
			JOptionPane.showMessageDialog(null, error, "Error: 707", JOptionPane.ERROR_MESSAGE);
			break;
			}
	}

	private void executeVsearchCommand(String testFasta, String trainFasta, String increment) {
		String[] command = { "/bin/sh", "-c", "vsearch --usearch_global " + testFasta + " --db " + trainFasta
				+ " --id 0.70 --maxaccepts 100 --maxrejects 50 --maxhits 1 --gapopen 0TE --gapext 0TE --userout "
				+ (increment + "TestAlignment")
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
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			}
		}

		public static String getCurrentTimeString() {
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
			return now.format(formatter);
		}

	}
