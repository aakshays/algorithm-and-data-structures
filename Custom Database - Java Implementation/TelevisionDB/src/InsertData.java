import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

public class InsertData {
	public static void newFile(String inputFile) throws IOException, ParseException {
		long startTime = System.currentTimeMillis();
		System.out.println("Loading data...");
		inputFile.replaceAll("\", ", "//");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		long chunks = getNumberOfRecords(inputFile);
		int currrentChunk = 0;
		TreeMap<Date, HashSet<StbRecord>> InputDateMap = new TreeMap<>();
		while (currrentChunk < chunks) {
			InputDateMap = LoadDataByDate.loadInputMemory(currrentChunk, inputFile);
			for (Map.Entry<Date, HashSet<StbRecord>> entry : InputDateMap.entrySet()) {
				String fileNameLog = "src/log/log.txt";
				String fileName = "src/disk/Master_Table_" + df.format(entry.getKey()) + ".txt";
				String fileNameHash = "src/hash/Master_Table_" + df.format(entry.getKey()) + "Hash.txt";
				File dateFile = new File(fileName);
				File dateFileHash = new File(fileNameHash);
				boolean newFile = false;
				if (!dateFile.exists())
					newFile = true;
				dateFile.createNewFile();
				dateFileHash.createNewFile();
				HashSet<String> hashSet = loadHashSet(fileNameHash);
				FileWriter fw = new FileWriter(fileName, true);
				FileWriter fwLog = new FileWriter(fileNameLog, true);
				FileWriter fwHash = new FileWriter(fileNameHash, true);
				for (StbRecord stb : entry.getValue()) {
					String hash = stb.getStb() + "|" + stb.getTitle();
					String record = stb.getStb() + "|" + stb.getTitle() + "|" + stb.getProvider() + "|"
							+ df.format(stb.getDate()) + "|" + stb.getRev() + "|" + stb.getTime();
					if (newFile) {
						fw.write(record);
						fwHash.write(hash);
						newFile = false;
					} else if (!hashSet.contains(hash)) {
						fw.write("\n" + record);
						fwHash.write("\n" + hash);
						hashSet.add(hash);
					} else {
						fwLog.write("\n" + record);
					}
				}
				fw.close();
				fwHash.close();
				fwLog.close();
			}
			currrentChunk = currrentChunk + 1000000;
		}
		Commit.clearLog();
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Execution time: " + (totalTime / 1000) + "." + (totalTime % 1000) + " seconds");
	}

	public static HashSet<String> loadHashSet(String path) throws IOException {
		HashSet<String> output = new HashSet<>();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line;
			while ((line = br.readLine()) != null) {
				output.add(line);
			}
		}
		return output;
	}

	public static long getNumberOfRecords(String path) throws IOException {
		LineNumberReader lnr = new LineNumberReader(new FileReader(path));
		lnr.skip(Long.MAX_VALUE);
		lnr.close();
		return lnr.getLineNumber() + 1;
	}
}
