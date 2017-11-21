import java.time.Duration;
import java.util.Date;

public class StbRecord {
	String stb;
	String title;
	String provider;
	Date date;
	float rev;
	Duration time;

	public StbRecord createStbRecord(String stb, String title, String provider, Date date, float rev, Duration time) {
		this.stb = stb;
		this.title = title;
		this.provider = provider;
		this.date = date;
		this.rev = rev;
		this.time = time;
		return this;
	}

	public String getStb() {
		return stb;
	}

	public void setStb(String stb) {
		this.stb = stb;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getRev() {
		return rev;
	}

	public void setRev(float rev) {
		this.rev = rev;
	}

	public Duration getTime() {
		return time;
	}

	public void setTime(Duration time) {
		this.time = time;
	}
}
