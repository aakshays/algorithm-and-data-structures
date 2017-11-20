import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.stream.Stream;

public class Commit {
	static HashMap<String, String> logList;
	static HashSet<String> logDates;
	public static void clearLog() throws IOException, ParseException {
		HashSet<String> recordSet = new HashSet<>();
		getLogHash();
		if (logList != null) {
			String fileName = "src/disk/temp.txt";
			File tempFile = new File(fileName);
			for (String date : logDates) {
				File file = new File("src/disk/Master_Table_" + date + ".txt");
				recordSet = loadRecords(file.getPath());
				tempFile.createNewFile();
				FileWriter fw = new FileWriter(fileName, true);
				for (String record : recordSet) {
					String[] record_parts = record.split("\\|");
					if (record_parts.length > 1) {
						String hashMaster = record_parts[0] + "|" + record_parts[1];
						if (logList.containsKey(hashMaster))
							fw.write(logList.get(hashMaster) + "\n");
						else
							fw.write(record + "\n");
					}
				}
				fw.close();
				emptyFile(file);
				copyFile(tempFile.getPath(), file.getPath());
				emptyFile(tempFile);
			}
			emptyFile(new File("src/log/log.txt"));
		}
	}

	public static void getLogHash() throws FileNotFoundException {
		logList = new HashMap<String, String>();
		logDates = new HashSet<String>();
		File logFile = new File("src/log/log.txt");
		Scanner s = new Scanner(logFile);
		while (s.hasNextLine()) {
			String rec = s.nextLine();
			if (rec.length() > 0) {
				String[] record_parts = rec.split("\\|");
				String hash = record_parts[0] + "|" + record_parts[1];
				logList.put(hash, rec);
				logDates.add(record_parts[3]);
			}
		}
		s.close();
	}

	public static HashSet<String> loadRecords(String path) throws IOException, ParseException {
		HashSet<String> recordSet = new HashSet<>();
		Path file = Paths.get(path);
		try (Stream<String> lines = Files.lines(file)) {
			lines.forEach(record -> {
				recordSet.add(record);
			});
		}
		return recordSet;
	}

	public static void copyFile(String input, String output) {
		FileInputStream instream = null;
		FileOutputStream outstream = null;
		try {
			File infile = new File(input);
			File outfile = new File(output);
			instream = new FileInputStream(infile);
			outstream = new FileOutputStream(outfile);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = instream.read(buffer)) > 0) {
				outstream.write(buffer, 0, length);
			}
			instream.close();
			outstream.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static void emptyFile(File toEmpty) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(toEmpty);
		writer.print("");
		writer.close();
	}
}