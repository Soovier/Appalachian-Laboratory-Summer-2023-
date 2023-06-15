//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class SummarizeDataAllRanks_5 {
//	public static void main(String[] args) throws IOException {
//		String baseFileNames = args[0];
//		int rank = Integer.parseInt(args[1]);
//		String outFileName = args[2];
//
//		List<String> taxLst = new ArrayList<>();
//		Map<String, List<String>> lineageDict = new HashMap<>();
//		lineageDict.put("aaa_header", List.of("k", "p", "c", "o", "f", "g", "s"));
//
//		try (BufferedReader inputs = new BufferedReader(new FileReader(baseFileNames))) {
//			String iLine;
//			while ((iLine = inputs.readLine()) != null) {
//				try (BufferedReader file = new BufferedReader(new FileReader(iLine.strip()))) {
//					String fLine;
//					while ((fLine = file.readLine()) != null) {
//						String tax = fLine.split("\t")[1].strip();
//						String[] taxSplit = tax.split(";");
//						if (taxSplit.length >= (rank + 1)) {
//							String taxElement = taxSplit[rank];
//							if (!taxElement.isEmpty() && !taxLst.contains(taxElement)) {
//								taxLst.add(taxElement);
//								lineageDict.put(taxElement, List.of(taxSplit));
//							}
//						}
//					}
//				}
//			}
//		}
//
//		Map<String, List<Integer>> taxDct = new HashMap<>();
//		taxDct.put("aaa_header", new ArrayList<>());
//		for (String tax : taxLst) {
//			taxDct.put(tax, new ArrayList<>());
//		}
//
//		try (BufferedReader inputs = new BufferedReader(new FileReader(baseFileNames))) {
//			String iLine;
//			while ((iLine = inputs.readLine()) != null) {
//				String header = iLine.strip().split("\\.")[0];
//				taxDct.get("aaa_header").add(Integer.parseInt(header));
//
//				Map<String, Integer> tmpTaxDct = new HashMap<>();
//				for (String tax : taxLst) {
//					tmpTaxDct.put(tax, 0);
//				}
//
//				try (BufferedReader file = new BufferedReader(new FileReader(iLine.strip()))) {
//					String fLine;
//					while ((fLine = file.readLine()) != null) {
//						String tax = fLine.split("\t")[1].strip();
//						String[] taxSplit = tax.split(";");
//						if (taxSplit.length >= (rank + 1)) {
//							String taxElement = taxSplit[rank];
//							if (!taxElement.isEmpty()) {
//								tmpTaxDct.put(taxElement, tmpTaxDct.get(taxElement) + 1);
//							}
//						}
//					}
//				}
//
//				for (Map.Entry<String, Integer> entry : tmpTaxDct.entrySet()) {
//					taxDct.get(entry.getKey()).add(entry.getValue());
//				}
//			}
//		}
//
//		try (BufferedWriter outFile = new BufferedWriter(new FileWriter(outFileName))) {
//			for (Map.Entry<String, List<Integer>> entry : taxDct.entrySet()) {
//				List<String> lineage = lineageDict.get(entry.getKey());
//				if (lineage != null) {
//					lineage = lineage.subList(0, rank + 1);
//				}
//				String lineageStr = lineage != null ? String.join(",", lineage) : "";
//				outFile.write(lineageStr + "," + entry.getKey() + ","
//						+ String.join(",", entry.getValue().stream().map(Object::toString).toArray(String[]::new))
//						+ "\n");
//			}
//		}
//	}
//}