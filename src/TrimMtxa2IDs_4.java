import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class TrimMtxa2IDs_4 {

	public static void main(String[] args) {
		TrimMtxa2IDs_4 test = new TrimMtxa2IDs_4();

		String preditctedTaxonomy = "1OutputTest.tax";
		String newFileOutPut = "Trmd.txt";

		test.TrimPredTaxonomy(preditctedTaxonomy, newFileOutPut);

	}

	public void TrimPredTaxonomy(String madeFile, String newFile) {
		File predicTax = new File(madeFile);
//		File outputFile = new File(newFile);

		try {
			Scanner UIS = new Scanner(predicTax);

			while (UIS.hasNextLine()) {
				String[] line = UIS.nextLine().strip().split("\t");
				System.out.println("GI: " + line[0]);
				System.out.println("Lineage: " + Arrays.toString(line[1].split(";")));
			}
			UIS.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
