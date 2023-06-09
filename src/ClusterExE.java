import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// This was made by Stephen Osunkunle 2023 Internship
// Creates Files In The SAME DIRECTORY (RELATIVE) IT WAS FIRED IN!!
public class ClusterExE {

	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		String Directory = "C:/Users/Stephen Osunkunle/Desktop/Unix_Java"; // DEFAULT
		String TaxonomyLocation = "12S_Combined.tax"; // DEFAULT
		String FastaLocation = "12S_Combined.fa"; // DEFAULT
		int K = 5; // DEFAULT

		ClusterExE test = new ClusterExE();
		test.Prompt(K, Directory, TaxonomyLocation, FastaLocation); // Changes Default Values
		test.Input_TestData(K, TaxonomyLocation, Directory, "TAX"); // tax
		test.Input_TestData(K, FastaLocation, Directory, "FASTA"); // fasta
		test.testData(K, Directory, "TAX");
		test.testData(K, Directory, "FASTA");

		System.out.println(System.currentTimeMillis() - time + " ms");
	}


	/**
	 * Takes Input In Order To Change The amount of splits, Location of the files
	 * and the new directory of where you want these file to be placed at
	 * 
	 * @param K
	 * @param mainDirectory
	 * @param TaxDirect
	 * @param FastaDirect
	 */
	public void Prompt(int K, String mainDirectory, String TaxDirect, String FastaDirect) {
		Scanner UIS = new Scanner(System.in);
//		System.out.print("\t NOTE: PLEASE MAKE SLASHES FRONT '/' AND NOT BACK SLAHSES '\\' !! : \n\n");
//		System.out.print("What Directory Do You Want These Files To Be Placed At? ");
//		mainDirectory = UIS.nextLine();
		System.out.print("Where Is The Taxonomy File Located?: ");
		TaxDirect = UIS.nextLine();
		System.out.print("Where Is The Fasta File Located?: ");
		FastaDirect = UIS.nextLine();
		System.out.print("How Many Parts Do You Want To Split The Data By? ");
		K = UIS.nextInt();

		UIS.close();
	}

	private List<String> TypeChecking(String Type) {
		String newFileName = "";
		String ending = "";
		if (Type == "TAX") {
			newFileName = "Test_Taxonomy";
			ending = ".tax";
		} else if (Type == "FASTA") {
			newFileName = "Test_Fasta";
			ending = ".fa";
		} else {
			return null;
		}
		List<String> newString = new ArrayList();
		newString.add(newFileName);
		newString.add(ending);
		return newString;
	}

	public void testData(int K, String mainDirectory, String Type) {
		String newFileName = "";
		String ending = "";

		if (Type == "TAX") {
			newFileName = "Training_Taxonomy";
			ending = ".tax";
		} else if (Type == "FASTA") {
			newFileName = "Training_Fasta";
			ending = ".fa";
		} else {
			return;
		}

		try {
			for (int i = 1; i <= K; i++) {
//				String trainData_Name = mainDirectory + "\\" + "\\" + i + newFileName + ending;
				String trainData_Name = i + newFileName + ending;
				File trainedTaxfile = new File(trainData_Name);
				if (trainedTaxfile.createNewFile()) {
					System.out.printf("%s Data Has Been Made! \n", trainData_Name);
					enterFileInFile(trainData_Name, K, mainDirectory, i, Type);
				} else {
					System.out.printf("%s Was Already Created \n", trainData_Name);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Take a parameter and it tells us which testing data to ignore and fill if up
	 * with the other data to create out training data
	 */
	public void enterFileInFile(String createdFile, int K, String Directory, int Position, String type) {
		List<String> findType = TypeChecking(type);
		String currentFileString = findType.get(0);
		String ending = findType.get(1);
		
		for (int i = 1; i <= K; i++) {
			if (i == Position) {
				continue;
			}

//			String currString = Directory + "\\" + "\\" + i + currentFileString + ending;
			String currString = i + currentFileString + ending;
			File currentFile = new File(currString);
			FileInputStream fileInputStream;
			try {
				FileWriter fw = new FileWriter(createdFile, true);
				PrintWriter pw = new PrintWriter(fw);
				fileInputStream = new FileInputStream(currentFile);
				byte[] byteValue = new byte[(int) currentFile.length()];
				fileInputStream.read(byteValue);
				fileInputStream.close();

				String fileContent = new String(byteValue, "UTF-8");
				pw.println((fileContent));
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * Creates The File and Inputs Data Into the File Either Fasta and Or Taxonomy
	 * File
	 * 
	 * @param K
	 * @param mainFile
	 * @param Directory
	 * @param type
	 * 
	 *                  K is the number of times the data is split; mainFile is
	 *                  either is fasta file or the taxonomy type can either be
	 *                  "TAX" or "FASTA" is not return Directory is where you want
	 *                  all the files to go to
	 */
	public void Input_TestData(int K, String mainFile, String Directory, String type) {
		int counter = 0;

		String FillDesc = "";
		String ending = "";

		if (type.equals("TAX")) {
			FillDesc = "Test_Taxonomy";
			ending = ".tax";
		} else if (type.equals("FASTA")) {
			FillDesc = "Test_Fasta";
			ending = ".fa";
		} else {
			return;
		}

		try {
			List<String> lines = Files.readAllLines(Paths.get(mainFile));
			int LinesPerFile = Math.round(lines.size() / K);
			v1: for (int i = 1; i <= K; i++) {
				// Directory + "\\" + "\\" + (GOT TO CURR FILE)
				File currFile = new File(i + FillDesc + ending);
				FileWriter fw = new FileWriter(currFile, true);
				BufferedWriter bw = new BufferedWriter(fw);
				for (int j = counter; j < LinesPerFile * i; j++) {
					if (j >= lines.size() * i) {
						continue v1;
					}
					counter = counter + 1;
					String line = lines.get(j);
					if (!line.isEmpty()) {
						bw.write(line);
						if (line.contains(">")) {
							bw.newLine();
							bw.write(lines.get(j));
							j = j + 1;
							counter = counter + 1;
						}
						bw.newLine();
					}
				}
				bw.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}