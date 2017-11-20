import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Stream;

public class LoadDataByDate {
	public static TreeSet<StbRecord> loadInMemory(long chunk, String path) throws IOException, ParseException {
		TreeSet<StbRecord> output = new TreeSet<StbRecord>(
				(StbRecord s1, StbRecord s2) -> (s1.getTitle().compareTo(s2.getTitle())));
		Path file = Paths.get(path);
		try (Stream<String> lines = Files.lines(file)) {
			lines.skip(chunk).limit(1000000).forEach(record -> {
				String[] record_parts = record.split("\\|");
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date date = null;
				Duration duration = null;
				try {
					date = format.parse(record_parts[3]);
					duration = Duration.parse(record_parts[5]);
				} catch (ParseException e) {
					System.out.println(e);
				}

				StbRecord stbRec = new StbRecord();

				stbRec = stbRec.createStbRecord(record_parts[0], record_parts[1], record_parts[2], date,
						Float.parseFloat(record_parts[4]), duration);

				output.add(stbRec);
			});
		}
		return output;
	}

	public static TreeMap<Date, HashSet<StbRecord>> loadInputMemory(long chunk, String path)
			throws IOException, ParseException {
		TreeMap<Date, HashSet<StbRecord>> DateMap = new TreeMap<>();
		Path file = Paths.get(path);
		try (Stream<String> lines = Files.lines(file)) {
			lines.skip(chunk).limit(1000000).forEach(record -> {
				String[] record_parts = record.split("\\|");
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();
				Duration duration = null;
				try {
					date = df.parse(record_parts[3]);
					duration = Duration.parse("PT" + record_parts[5].replace(':', '.') + "S");
				} catch (ParseException e) {
					System.out.println(e);
				}

				StbRecord stbRec = new StbRecord();

				stbRec = stbRec.createStbRecord(record_parts[0], record_parts[1], record_parts[2], date,
						Float.parseFloat(record_parts[4]), duration);
				if (!DateMap.containsKey(date))
					DateMap.put(date, new HashSet<StbRecord>());
				DateMap.get(date).add(stbRec);
			});
		}
		return DateMap;
	}
}
