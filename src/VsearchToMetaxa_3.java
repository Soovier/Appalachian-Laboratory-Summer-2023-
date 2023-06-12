import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Re-Creating 3_VsearchToMetaxa2.py into Java and optimizing it
// Stephen Osunkunle Remake 
public class VsearchToMetaxa_3 {
	public static void main(String[] args) {
		VsearchToMetaxa_3 test = new VsearchToMetaxa_3();

//		String trainTax = "1Training_Taxonomy.tax";
//		String alignment = "1TestAlignments.txt";
//		String output = "1OutputTest.tax";

		for (int i = 0; i < args.length; i++) { // Removes the -v, -t, -o before firing
			args[i] = args[i].substring(2);
		}

		test.TrimAlignment(args[0]); // Aligned Fasta Data
		test.TrimTrainedTaxonomy(args[1]); // Trained Taxonomy File Goes Here
		test.OutputFile(args[2]);
	}

	/**
	 * Trims the Taxonomy Data by it's header and the Taxonomy Data for example
	 * Header, k__Eukaryota;p__Chordata;c__Actinopteri...
	 * 
	 * @param taxFile
	 */
	HashMap<String, String> TaxDct = new HashMap<>();
	public void TrimTrainedTaxonomy(String taxFile) {
		File trained_Taxonomy = new File(taxFile);
		try {
			Scanner UIS = new Scanner(trained_Taxonomy);
			while (UIS.hasNextLine()) {
				String line = UIS.nextLine();
				if (line.equals("")) { // IF WE FIND ANY EMPTY LINES WE SKIP
					continue;
				}
				TaxDct.put(line.split("\t")[0], line.strip().split("\t")[1]);
			}
			UIS.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for (Map.Entry<String, String> e : TaxDct.entrySet()) {
			System.out.println(e);
		}

	}

	/**
	 * Trims Alignment Data aligns the sequence with it's length
	 * 
	 * vDict aligns the two sequences that was compared
	 * 
	 * ID aligns the sequence with the Percentage of identity
	 * 
	 * Query (1), Target (2), Percentage of identity (3)
	 * 
	 * @param fileName
	 */
	HashMap<String, String> vDict = new HashMap<>();
	HashMap<String, String> ID = new HashMap<>();
	HashMap<String, String> SeqLength = new HashMap<>();
	public void TrimAlignment(String fileName) {
		File alignFile = new File(fileName);
		try {
			Scanner UIS = new Scanner(alignFile);
			while (UIS.hasNextLine()) {
				String[] line = UIS.nextLine().split("\t");
				vDict.put(line[0], line[1]);
				ID.put(line[0], line[2]);
				SeqLength.put(line[0], line[3]);
			}
			UIS.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// UNCOMMENT TO SEE OUTPUT OF HASHMAP!
//		for (Map.Entry<String, String> e : vDict.entrySet()) {
//			System.out.println(e.getKey() + " , " + e.getValue());
//		}
	}

	// Putting all data into the output file created from parameter
	// Passed in unix
	public void OutputFile(String fileName) {
		File newFile = new File(fileName);
		try {
			FileWriter fw = new FileWriter(newFile, true);
			BufferedWriter bw = new BufferedWriter(fw);

			for (Map.Entry<String, String> map : vDict.entrySet()) {
				String key = map.getKey();
				bw.write(key + '\t' + TaxDct.get(map.getValue()) + '\t' + ID.get(key) + '\t' + SeqLength.get(key)
						+ '\n');
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



}