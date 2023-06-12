import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // Parse command line arguments
		String inputFile = "";
        String outputFile = "";
        for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-i") || args[i].equals("--inputFile")) {
				inputFile = args[++i];
			} else if (args[i].equals("-o") || args[i].equals("--outputFile")) {
                outputFile = args[++i];
            }
        }

		// Read input data from file
		StringBuilder inputData = new StringBuilder();
		BufferedReader inputFileReader = new BufferedReader(new FileReader(inputFile));
        String line;
		while ((line = inputFileReader.readLine()) != null) {
			inputData.append(line);
			inputData.append("\n");
        }
		inputFileReader.close();

        // Write output data to file
		FileWriter outputFileWriter = new FileWriter(outputFile);
		outputFileWriter.write(inputData.toString());
		outputFileWriter.close();
    }
}