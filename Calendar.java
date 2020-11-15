import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Calendar {
	
	// parallel array list for time and event
	private Vector<Event> events = new Vector<Event>();
	
	/**
	 * Default constructor of MyCalendar, it should read the event.txt file and load them into the two array lists.
	 */
	public Calendar() {
		try {
			File FN = new File("events.txt");
			Scanner sc = new Scanner(FN);
			while(sc.hasNextLine()) {
				String name = sc.nextLine();
				String time = sc.nextLine();
				addEvent(name, time);
			}
		}catch(IOException e){
			System.out.println("File not found");
		}
	}
	
	/**
	 * This function will add one-time or recurring event to the events vector
	 * @param name name of the event
	 * @param time time information of the event
	 */
	public void addEvent(String name, String time) {
		Event toAdd = new Event(name, time);
		events.add(toAdd);
	}

	
	/**
	 * This function will print all the events of a given date
	 * @param date the date input by user
	 */
	public void printEventsOfDate(LocalDate date) {
		for (int i = 0; i < events.size(); i++) {
			if (events.get(i).getInterval().getDate().isEqual(date))
				Event.printEvent(events.get(i));
		}
	}
	
	/**
	 * Function that prints all events in a day for day view option in main menu
	 * @param date date of which the events will be printed
	 */
	public void printDayView(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM d yyyy");
		for (int i = 0; i < events.size(); i++) {
			if (events.get(i).getInterval().getDate().isEqual(date)) {
				System.out.println(formatter.format(date));
				System.out.println(events.get(i).getName() + " : " + events.get(i).getInterval().getStartTime() + " - " + events.get(i).getInterval().getEndTime());
			}
		}
	}
	
	/**
	* Function that saves the calendar to a file called filename.txt
	* @param filename name of file 
	*/
	public void saveToFile(String filename) {
		try 
		{
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yy"); 
			File file = new File(filename + ".txt");
			FileWriter myWriter = new FileWriter(filename + ".txt");
			for(int i = 0; i < events.size(); i++) {
				myWriter.write(events.get(i).getName() + "\n");
				myWriter.write(dateFormat.format(events.get(i).getInterval().getDate()) + " " + events.get(i).getInterval().getStartTime() + " " + events.get(i).getInterval().getEndTime() + "\n");

			}
			myWriter.close();
		}
		catch(IOException e)
		{
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
		
}


