
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// Stephen Osunkunle Cross Validation
public class WindowClusterJ {
	static String newFileName_Fasta = "KParts_FastaFile.txt";
	static String newFileName_Tax = "KParts_TaxFile.txt";

	private static String TaxFileName = "NAN";
	private static String FastaFileName = "NAN";
	private static String mainDirectory = "NAN";
	private static int K = 5; // DEFAULT K IF K ISN'T INPUTED

	public static void main(String[] args) {
//		String Directory = "C:\Users\Stephen Osunkunle\Desktop\Java Practice";
//		FastaFile = 12C_Combined[FASTA].txt;
//		TaxFile = 12C_Combinded[TAX].txt
		ClusterProperties clusterProp = new ClusterProperties();
		WindowClusterJ mainCluster = new WindowClusterJ();
		mainCluster.Prompt();
		ArrayList<String[]> tax_FILE = clusterProp.ReadFile_TAX(TaxFileName, 1, "");
		ArrayList<String[]> fasta_FILE = clusterProp.ReadFile_FASTA(FastaFileName);
		System.out.println(mainDirectory);
		clusterProp.Create_K_Partition(K, tax_FILE, fasta_FILE, mainDirectory);
		clusterProp.testData(K, mainDirectory);
	}

	// Changes all Tax file and Fasta File to .txt files
	public String CheckForFileType(String fileName) {
		String changedString = fileName;
		if (changedString.contains(".fa")) {
			changedString.replace(".fa", ".txt");
			System.err.print(" " + "Replaced Fasta File With Text File");
			;
		} else if (changedString.contains(".tax")) {
			changedString.replace(".tax", ".txt");
			System.err.print(" " + "Replaced Tax File With Text File");
			;
		}
		System.out.println(changedString);
		return changedString;
	}

	public void Prompt() {
		Scanner UIS = new Scanner(System.in);
		System.out.print("\t NOTE: PLEASE MAKE SLASHES FRONT '/' AND NOT BACK SLAHSES '\\' !! : \n\n");
		System.out.print("What Directory Do You Want These Files To Be Placed At? ");
		mainDirectory = UIS.nextLine();
		System.out.print("Where Is The Taxonomy File Located?: ");
		TaxFileName = UIS.nextLine();
		System.out.println(TaxFileName);
		System.out.print("Where Is The Fasta File Located?: ");
		FastaFileName = UIS.nextLine();
		System.out.println(FastaFileName);
		System.out.print("How Many Parts Do You Want To Split The Data By? ");
		K = UIS.nextInt();
		System.out.println(K);

		UIS.close();
	}

	static class ClusterProperties {
		/**
		 * 'k__': 'Kingdom', 'p__': 'Phylum', 'c__': 'Class', 'o__': 'Order', 'f__':
		 * 'Family', 'g__': 'Genus', 's__': 'Species' converts the input into the words
		 * about ^^^
		 */
		public String newFileReadType(String input) {
			String newWord = "";
			switch (input.toLowerCase()) {
			case "k":
				newWord = "Kingdom";
				break;
			case "p":
				newWord = "Phylum";
				break;
			case "c":
				newWord = "Class";
				break;
			case "f":
				newWord = "Family";
				break;
			case "g":
				newWord = "Genus";
				break;
			case "s":
				newWord = "Species";
				break;
			}

			return newWord;
		}

		/**
		 * @param fileName  Reads the fileName parameter and then splits the file by by
		 *                  it's headers and sequences and puts them into an Array list
		 *                  Separated by commas and spaces
		 * @param TypeRegex Type 1: Split Data By Spaces and Commas Type 2: Splits Data
		 *                  By Spaces, Commas, AND Underlines Type 3: Splits Data Using
		 *                  Type 2 and Extra Characters (Type 3: Extra Character Must Go
		 *                  In The Type3Char Parameter)
		 * @return
		 */
		public ArrayList<String[]> ReadFile_TAX(String fileName, int TypeRegex, String Type3Char) {
			ArrayList<String[]> newData = new ArrayList<>();
			try {
				Scanner UIS = new Scanner(new File(fileName));
				String regex = " "; // Dosen't split it at all

				switch (TypeRegex) {
				case 1:
					regex = "[\\s;]+";
					break;
				case 2:
					regex = "[\\s;_]+";
					break;
				case 3:
					regex = "[_" + "\\" + "\\s;" + Type3Char + "]+";
					break;
				}

				while (UIS.hasNextLine()) {
					String[] newString = UIS.nextLine().split(regex);
					String[] letterRemover = { "k", "p", "c", "o", "f", "g", "s" };

					// Changing the @__ --> newFileReadType name!
					for (int i = 0; i < letterRemover.length; i++) {
						for (int k = 0; k < newString.length; k++) {
							if (newString[k].contains(letterRemover[i] + "__")) {
								String exl = newString[k].substring(3); // removes first 3 elements in string
								newString[k] = newFileReadType(letterRemover[i]) + ":" + exl;
							}
						}
					}
//					System.out.println("Length: " + newString.length + ": " + Arrays.toString(newString));
					newData.add(newString);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return newData;
		}

		/**
		 * Reads the file name and also add a comma to all ".1" in the string to
		 * Separate it then removes the header label and put them into an arraylist with
		 * takes a String array
		 * 
		 * @param fileName
		 * @return
		 */
		public ArrayList<String[]> ReadFile_FASTA(String fileName) {
			ArrayList<String[]> newData = new ArrayList<>();
			try {
				Scanner UIS = new Scanner(new File(fileName));
				while (UIS.hasNextLine()) {
					String newString = UIS.nextLine().replace(".1", ".1,");

					if (newString.contains(">")) {
						newString = newString.substring(1);
					}
					String[] tempData = newString.split(",");
//					System.out.println(Arrays.toString(tempData));
					newData.add(tempData);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return newData;
		}

		/**
		 * Making The File To The Directory Listed and add the data into the file
		 * 
		 * 
		 * @param K
		 * @param TaxFile
		 * @param FastaFile
		 * @param mainDirectory
		 */
		@SuppressWarnings("finally")
		public void Create_K_Partition(int K, ArrayList<String[]> TaxFile, ArrayList<String[]> FastaFile,
				String mainDirectory) {
			if (K <= 0) { // BASE CASE
				K = 5;
			}
			int Tax_Parts = Math.round(TaxFile.size() / K);

			createFastaFileWithK(K, FastaFile, mainDirectory);
			try {

				int counter = 0;
				v1: for (int i = 0; i < K; i++) {
					File Tax_File_Split = new File(mainDirectory.toString() + "\\" + "\\" + (i + 1) + newFileName_Tax);
					if (Tax_File_Split.createNewFile()) {
						System.out.println((i + 1) + " File Created!");
					} else {
						System.out.printf("%s Was Already Created Inside of Directory! "
								+ "\n Please Try A Diffrent File Name For .TAX \n", Tax_File_Split);
					}
					for (String k[] : TaxFile) {
						FileWriter fw = new FileWriter(Tax_File_Split, true);
						PrintWriter pw = new PrintWriter(fw);
						pw.println(Arrays.toString(k));
						counter++;
						pw.close();
						if (counter == (Tax_Parts)) {
							try {
								for (int num = 0; num < Tax_Parts; num++) {
									TaxFile.remove(num);
								}
							} catch (Exception e) {
								System.err.println("Error | Extra Sequences Here If Need To Be Used!");
							} finally {
								counter = 0;
								continue v1;
							}
						}
					}
				}

				// X ALWAYS HAS DOUBLE MISSING PARTS FROM FASTA SEQUENCES
				// TAX IS ODD, FASTA IS EVEN

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Adds data to fastafile inside the directory listed into K parts same as top
		 * method
		 * 
		 * @param K
		 * @param FastaFile
		 * @param Directory
		 */
		@SuppressWarnings("finally")
		public void createFastaFileWithK(int K, ArrayList<String[]> FastaFile, String Directory) {
			try {
				int counter2 = 0;
				int Fasta_Parts = Math.round((FastaFile.size() / K));

				v2: for (int i = 0; i < K; i++) {
					File Fasta_File_Split = new File(Directory.toString() + "\\" + "\\" + (i + 1) + newFileName_Fasta);
					if (Fasta_File_Split.createNewFile()) {
						System.out.println((i + 1) + " File Created!");
					} else {
						System.out.printf("%s Was Already Created Inside of Directory! "
								+ "\n Please Try A Diffrent File Name For .TAX \n", Fasta_File_Split);
					}

					for (String k[] : FastaFile) {
						FileWriter fw = new FileWriter(Fasta_File_Split, true);
						PrintWriter pw = new PrintWriter(fw);
						pw.println(Arrays.toString(k));
						counter2++;
						pw.close();
						if (counter2 == (Fasta_Parts)) {
							try {
								for (int num = 0; num < Fasta_Parts; num++) {
									FastaFile.remove(num);
								}
							} catch (Exception e) {
								System.err.println("Error | Extra Sequences Here If Need To Be Used!");
							} finally {
								counter2 = 0;
								continue v2;
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		public void testData(int K, String mainDirectory) {
			try {
				for (int i = 1; i <= K; i++) {
					String trainData_Name = mainDirectory.toString() + "\\" + "\\" + "TaxTrainData_ " + i + ".txt";
					File trainedTaxfile = new File(trainData_Name);
					if (trainedTaxfile.createNewFile()) {
						System.out.printf("%s Data Has Been Made! \n", trainData_Name);
						enterFileInFile(trainData_Name, K, mainDirectory.toString(), i, "Tax");
					} else {
						System.out.printf("%s Was Already Created \n", trainData_Name);
					}
				}

				for (int v = 1; v <= K; v++) {
					String trainData_Name = mainDirectory.toString() + "\\" + "\\" + "FastaTrainData_ " + v + ".txt";
					File trainedFastafile = new File(trainData_Name);
					if (trainedFastafile.createNewFile()) {
						System.out.printf("%s Data Has Been Made! \n", trainData_Name);
						enterFileInFile(trainData_Name, K, mainDirectory.toString(), v, "Fasta");
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
			for (int i = 1; i <= K; i++) {
				if (i == Position) {
					continue;
				}
				String currentFileString = "";

				if (type == "Tax") {
					currentFileString = newFileName_Tax;
				} else {
					currentFileString = newFileName_Fasta;
				}

				String currString = Directory + "\\" + "\\" + i + currentFileString;
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
	}

}
