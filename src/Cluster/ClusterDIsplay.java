package Cluster;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ClusterDIsplay {
	HashMap<String, String> newdata = new HashMap<>();
	HashMap<String, String> confusData = new HashMap<>();
	final public String NegativeCase = "TPN";

	public static void main(String[] args) {
		ClusterDIsplay test = new ClusterDIsplay();
		test.addData("2Output.tax");
		test.addConfusion("NEWOUTPUTCONFUSION.txt");
		test.makeOutput("DisplayOutput.txt");
	}

	public void addData(String fileName) {
		try {
			Scanner UIS = new Scanner(new File(fileName));
			while (UIS.hasNextLine()) {
				String[] temp = UIS.nextLine().strip().split("\t");
				newdata.put(temp[0], temp[temp.length - 2]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void addConfusion(String fileName) {
		try {
			Scanner UIS = new Scanner(new File(fileName));
			while (UIS.hasNextLine()) {
				String[] temp = UIS.nextLine().strip().split("\t");
				confusData.put(temp[0], temp[1]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String adjustValue(String value) {
		String[] tempData = value.split(";");
		String Final = "";

		for (int i = 0; i < tempData.length; i++) {
			switch (tempData[i].substring(3)) {
			case (NegativeCase): // IF THEY HAVE LABEL BUT NOT EQUAL
				Final += "1 ";
				break;
			default:
				Final += "0 ";
				break;
			}
		}
		return Final;
	}

	public void makeOutput(String fileName) {
		try {
			File output = new File(fileName);

			if (output.createNewFile()) {
			} else {
				output.delete();
				output = new File(fileName);
			}

			FileWriter fw = new FileWriter(output, true);
			BufferedWriter bw = new BufferedWriter(fw);
			for (Map.Entry<String, String> e : confusData.entrySet()) {
				if (newdata.containsKey(e.getKey())) {
					String header = String.format("%-20s", e.getKey());
					String value = String.format("%-4s", adjustValue(e.getValue()));
					String percentId = String.format("Percent ID: %-15s", newdata.get(e.getKey()));
					bw.write(header + " | " + (value) + " | " + percentId + "\n");
				}
			}
			fw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

}
