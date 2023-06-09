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
public class ClusterExE {

	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		int K = 5;
		String Directory = "C:/Users/Stephen Osunkunle/Desktop/Unix_Java";
		ClusterExE test = new ClusterExE();
		test.Input_TestData(K, "12S_Combined.tax", Directory, "TAX"); // tax
		test.Input_TestData(K, "12S_Combined.fa", Directory, "FASTA"); // fasta
		test.testData(K, Directory, "TAX");
		test.testData(K, Directory, "FASTA");

		System.out.println(System.currentTimeMillis() - time + " ms");
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
			newFileName = "Trainig_Taxonomy";
			ending = ".tax";
		} else if (Type == "FASTA") {
			newFileName = "Training_Fasta";
			ending = ".fa";
		} else {
			return;
		}

		try {
			for (int i = 1; i <= K; i++) {
				String trainData_Name = mainDirectory + "\\" + "\\" + i + newFileName + ending;
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

			String currString = Directory + "\\" + "\\" + i + currentFileString + ending;
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
		File mainTax_File = new File(mainFile);
		int counter = 0;
		int TotalLineAmount = 0;

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
			Scanner UIS = new Scanner(mainTax_File);
			while (UIS.hasNextLine()) {
				UIS.nextLine();
				TotalLineAmount++;
			}
			UIS.close();
			int LinesPerFile = Math.round(TotalLineAmount / K);
			List<String> lines = Files.readAllLines(Paths.get(mainFile));
			v1: for (int i = 1; i <= K; i++) {
				File currFile = new File(Directory + "\\" + "\\" + i + FillDesc + ending);
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