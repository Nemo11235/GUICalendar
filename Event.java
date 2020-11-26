import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Event {
	private String name;
	private LocalDate startDate, endDate;
	private TimeInterval interval;
	
	
	public Event(Event e) {
		name = e.getName();
		interval = e.getInterval();
	}
	
	
	/**
	 * Constructor 
	 * @param n name of event
	 * @param t time interval
	 */
	public Event(String n, String t) {
		name = n;
		Vector<String> tokens = new Vector<String>();
		StringTokenizer st = new StringTokenizer(t);
	    while (st.hasMoreTokens()) {
	        tokens.add(st.nextToken());
	    }
	    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yy"); 
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
	    // here initiate the time interval for the event
		
		LocalDate d = LocalDate.parse(tokens.get(0), dateFormat);
		LocalTime s = LocalTime.parse(tokens.get(1), timeFormat);
		LocalTime e = LocalTime.parse(tokens.get(2), timeFormat);
		interval = new TimeInterval(d, s, e);
	}
	
	/**
	 * Function that prints the event in format that fits the required format for printEventList function in MyCalendar class
	 * @param e event to print
	 */
	public static String printEvent(Event e) {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("E, MMM d yyyy");
		return dateFormat.format(e.getInterval().getDate()) + " " + e.getInterval().getStartTime() + " - " + e.getInterval().getEndTime() + " " + e.getName() + "\n";
	}
	
	
	
	/**
	 * Setter function for event name
	 * @param n new name of event 
	 */
	public void setName(String n) { name = n; }
	
	/**
	 * Setter function for event time
	 * @param t new time interval of event
	 */
	public void setTime(TimeInterval t) { interval = t; }
	/**
	 * Getter function for event name
	 * @return name name of event
	 */
	public String getName() { return name; }
	
	/**
	 * Getter function for event time
	 * @return time time interval of event
	 */
	public TimeInterval getInterval() { return interval; }
	
	/**
	 * This function will set the date of the interval
	 * @para date date to replace
	 */
	public void setIntervalDate(LocalDate date) {
		interval.setDate(date);
	}
	
	/**
	 * Getter function for event time
	 * @return startDate start date of event
	 */
	public LocalDate getStartDate() { return startDate; }
	
	/**
	 * Getter function for event time
	 * @return endDate end date of event
	 */
	public LocalDate getEndDate() { return endDate; }
}
