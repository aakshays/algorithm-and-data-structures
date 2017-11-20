import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class Main {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, ParseException {
		while (true) {
			System.out.println(
					"1. Press 1 to Upload data\n2. Press 2 to Query Database\n3. Press any other key to exit");
			Scanner scanner = new Scanner(System.in);
			String command = scanner.nextLine();
			if (command.equals("1")) {
				System.out.print("Enter file path: ");
				InsertData.newFile(scanner.next());
				System.out.println("Data stored succesfully!\n--------\n");
				continue;
			}
			if (command.equals("2")) {
				while (true) {
					System.out.println(
							"1. Press 1 to Filter by Show Title\n2. Press 2 to Filter by Date\n3. Press 3 to order by Date and Title\n4. Press any other key to go back");
					command = scanner.nextLine();
					if (command.equals("1")) {
						System.out.print("Enter title: ");
						String title = scanner.nextLine();
						Queries.getByTitle(title);
						continue;
					}
					if (command.equals("2")) {
						System.out.print("Enter date (yyyy-MM-dd): ");
						String date = scanner.nextLine();
						Queries.getByDate(date);
						continue;
					}
					if (command.equals("3")) {
						Queries.orderByDateAndTitle();
						continue;
					} else {
						break;
					}
				}
				continue;
			} else {
				System.out.println("Thank you!");
				break;
			}
		}
	}
}
