package Cluster;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class DataToConfusion_5 {
	// Fields //
	public int TruePositive;
	public int TrueNegative;
	public int FalsePositive;
	public int FalseNegative;

	public int TP_Positive;
	public int TP_Negative;

	HashMap<String, String> originalData = new HashMap<>();

	// Takes the Original Data, then the predicted Data, then the output file name
	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		DataToConfusion_5 test = new DataToConfusion_5();
		test.AppendData(args[0]);
		test.confusionMatrix_1(args[1], args[0], args[2]);

		/*
		 * Arugment 1: ORIGINAL DATA BASE TAXONOMY FILE! Arugment 2: TRIMED TAXONOMY
		 * FILE! Arugment 3: NEW FILE OUTPUT NAME!
		 */

		System.out.println(System.currentTimeMillis() - time + " ms");
	}

	public double getFDR() {
		return TP_Negative + (TP_Positive + TP_Negative);
	}

	public void AppendData(String orignalFile) {
		try {
			Scanner UIS = new Scanner(new File(orignalFile));
			while (UIS.hasNextLine()) {
				String[] parts = UIS.nextLine().strip().split("\t");
				originalData.put(parts[0], parts[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void confusionMatrix_1(String predicted, String orignal, String outputFile) {
		StringBuilder fileResult = new StringBuilder();
		try {
			List<String> trmdFile = Files.readAllLines(Paths.get(predicted));

			for (int i = 0; i < trmdFile.size(); i++) {
				String trmdHeader = trmdFile.get(i).strip().split("\t")[0];
				String trmdLineage = trmdFile.get(i).strip().split("\t")[1];
				String Lineage = originalData.get(trmdHeader);
				if (Lineage != null) {
					String[] predictedData = trmdLineage.split(";");
					String[] originData = Lineage.split(";");
					fileResult.append(trmdHeader + "\t");
					getType(predictedData, originData, fileResult);
				}
			}
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
				writer.write(fileResult.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(FalseNegative);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getType(String[] PredD, String[] OrigD, StringBuilder fileResultRef) {
		String[] taxOrder = { "k__", "p__", "c__", "o__", "f__", "g__", "s__" };
		StringBuilder helper = new StringBuilder();

		int minLength = Math.min(PredD.length, OrigD.length);
		for (int i = 0; i < minLength; i++) {
			String PredAlign = PredD[i];
			String OrigAlign = OrigD[i];
			if (PredAlign.equals(OrigAlign)) {
				helper.append(taxOrder[i]).append("TPP;");
				TP_Positive += 1;
			} else {
				helper.append(taxOrder[i]).append("TPN;");
				TP_Negative += 1;
			}
		}

		if (PredD.length > OrigD.length) {
			for (int i = minLength; i < taxOrder.length; i++) {
				helper.append(taxOrder[i]).append("FP;");
				FalsePositive += 1;
			}
		} else if (PredD.length < OrigD.length) {
			for (int i = minLength; i < taxOrder.length; i++) {
				helper.append(taxOrder[i]).append("FN;");
				FalseNegative += 1;
			}
		}

		int maxLength = Math.max(PredD.length, OrigD.length);
		for (int i = maxLength; i < taxOrder.length; i++) {
			helper.append(taxOrder[i]).append("TN;");
			TrueNegative += 1;
		}

		// Fixes any string that exceeds the limit of the taxOrder!
		List<String> LineageArray = new ArrayList<String>(Arrays.asList(helper.toString().split(";")));
		while (LineageArray.size() >= 8) {
			LineageArray.remove(LineageArray.size() - 1);
			StringBuilder newBuilder = new StringBuilder();
			String newString = LineageArray.toString().substring(1, (LineageArray.toString().length() - 1))
					.replace(" ", "").replace(",", ";");
			newBuilder.append(newString);
			helper = newBuilder;
		}

		fileResultRef.append(helper).append("\n");
	}

}
