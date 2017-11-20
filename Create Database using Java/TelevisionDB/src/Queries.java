import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeSet;

public class Queries {
	static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	public static void getByTitle(String title) throws ParseException, IOException {
		long startTime = System.currentTimeMillis();
		System.out.println("Executing...");
		File folder = new File("src/disk");
		File[] listOfFiles = folder.listFiles();
		double viewTime = 0;
		float revenue = 0;
		long count = 0;
		System.out.println("\nResults");
		System.out.println("--------");
		for (File file : listOfFiles) {
			String path = file.getPath();
			TreeSet<StbRecord> input = new TreeSet<>();
			long chunks = getNumberOfRecords(path);
			int currrentChunk = 0;
			while (currrentChunk < chunks) {
				input = LoadDataByDate.loadInMemory(currrentChunk, path);
				for (StbRecord rec : input) {
					if (rec.getTitle().equals(title)) {
						System.out.println(rec.getTitle() + ", " + rec.getRev() + ", " + df.format(rec.getDate()));
						viewTime += (rec.getTime().getSeconds() * 60); viewTime += ((double) rec.getTime().getNano() / 10000000);
						revenue += rec.getRev();
						count++;
					}
				}
				currrentChunk = currrentChunk + 1000000;
			}
		}
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		displayResults(viewTime, revenue, totalTime, count);
	}

	public static void orderByDateAndTitle() throws ParseException, IOException {
		long startTime = System.currentTimeMillis();
		System.out.println("Executing...");
		File folder = new File("src/disk");
		File[] listOfFiles = folder.listFiles();
		double viewTime = 0;
		float revenue = 0;
		long count = 0;
		System.out.println("\nResults");
		System.out.println("--------");
		for (File file : listOfFiles) {
			String path = file.getPath();
			TreeSet<StbRecord> input = new TreeSet<>();
			long chunks = getNumberOfRecords(path);
			int currrentChunk = 0;
			while (currrentChunk < chunks) {
				input = LoadDataByDate.loadInMemory(currrentChunk, path);
				for (StbRecord rec : input) {
					System.out.println(rec.getTitle() + ", " + rec.getRev() + ", " + df.format(rec.getDate()));
					viewTime += (rec.getTime().getSeconds() * 60); viewTime += ((double) rec.getTime().getNano() / 10000000);
					revenue += rec.getRev();
					count++;
				}
				currrentChunk = currrentChunk + 1000000;
			}
		}
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		displayResults(viewTime, revenue, totalTime, count);
	}

	public static void getByDate(String dateString) throws ParseException, IOException {
		long startTime = System.currentTimeMillis();
		System.out.println("Executing...");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(dateString);
		String path = "src/disk/Master_Table_" + df.format(date) + ".txt";
		TreeSet<StbRecord> input = new TreeSet<>();
		long chunks = getNumberOfRecords(path);
		int currrentChunk = 0;
		double viewTime = 0;
		float revenue = 0;
		long count = 0;
		System.out.println("\nResults");
		System.out.println("--------");
		while (currrentChunk < chunks) {
			input = LoadDataByDate.loadInMemory(currrentChunk, path);
			for (StbRecord rec : input) {
				System.out.println(rec.getTitle() + ", " + rec.getRev() + ", " + df.format(rec.getDate()));
				viewTime += (rec.getTime().getSeconds() * 60); viewTime += ((double) rec.getTime().getNano() / 10000000);
				revenue += rec.getRev();
				count++;
			}
			currrentChunk = currrentChunk + 1000000;
		}
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		displayResults(viewTime, revenue, totalTime, count);
	}

	public static long getNumberOfRecords(String path) throws IOException {
		LineNumberReader lnr = new LineNumberReader(new FileReader(path));
		lnr.skip(Long.MAX_VALUE);
		lnr.close();
		return lnr.getLineNumber() + 1;
	}

	public static void displayResults(double viewTime, float revenue, long totalTime, long count) throws IOException {
		System.out.println("\nAnalysis");
		System.out.println("--------");
		long mins = (long) viewTime % 60;
		long hours = (long)viewTime / 60;
		System.out.println("Total view time: " + hours + ":" + mins);
		System.out.println("Total Revenue: $ " + revenue);
		System.out.println("Execution time: " + (totalTime / 1000) + "." + (totalTime % 1000) + " seconds");
		System.out.println("Records found: " + count);
		System.out.println("\n");
	}
}
