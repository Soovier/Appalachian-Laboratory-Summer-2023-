import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TrimMtxa2IDs_4 {
	private final Map<String, String> prdctTax = new HashMap<>();

	public static void main(String[] args) {
		TrimMtxa2IDs_4 test = new TrimMtxa2IDs_4();

//		String preditctedTaxonomy = "1OutputTest.tax"; args[0]
//		String newFileOutPut = "Trmd.txt"; args[1];

		test.TrimPredTaxonomy(args[0], args[1]);
	}

	public void TrimPredTaxonomy(String madeFile, String newFile) {
		File predicTax = new File(madeFile);
		File outputFile = new File(newFile);

		try {
			Scanner UIS = new Scanner(predicTax);

			while (UIS.hasNextLine()) {
				String[] line = UIS.nextLine().strip().split("\t");
				String GI = line[0];
				String[] tempLineage = line[1].split(",");
				List<String> Lineage = new ArrayList<>(Arrays.asList(tempLineage));
				for (int i = 0; i < 8; i++) {
					if (Lineage.size() < 8) {
						Lineage.add("");
					}
				}
//				int Score = Integer.valueOf(line[line.length - 1]);
				Double PI = Double.parseDouble(line[line.length - 2]);
				String x;
				if (PI >= 98) {
					x = String.join(";", Lineage.subList(0, 7));
				} else if (PI >= 96) {
					x = String.join(";", Lineage.subList(0, 6));
				} else if (PI >= 85) {
					x = String.join(";", Lineage.subList(0, 5));
				} else if (PI >= 80) {
					x = String.join(";", Lineage.subList(0, 4));
				} else {
					continue;
				}
				prdctTax.put(GI, x);

//				System.out.println("Score: " + Score + " PI: " + PI);
//				System.out.println("GI: " + line[0]);
//				System.out.println("Lineage: " + Arrays.toString(line[1].split(";")));
			}
			FileWriter fw = new FileWriter(outputFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			for (Map.Entry<String, String> e : prdctTax.entrySet()) {
				if (e.getValue().contains("null") || e.getKey().contains("null")) {
					continue;
				}
				bw.write(e.getKey() + "\t" + prdctTax.get(e.getKey()) + "\n");
			}
			UIS.close();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
