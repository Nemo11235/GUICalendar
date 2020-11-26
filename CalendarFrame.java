import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

public class CalendarFrame extends JFrame implements ChangeListener{
	
	/**
	 * Constructor of the frame
	 * @param d the model data
	 */
	public CalendarFrame(Model d) {
		dataModel = d;
		data = d.getData();
		setLocation(0, 0);
		setSize(1500, 1000);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(2,2));
		
		JPanel monthPanel = new JPanel();
		// the monthly view panel----------------------
		monthPanel.setLayout(new GridLayout(8, 7));
		monthPanel.add(monthLabel);
		for(int i = 0; i < 4; i ++) { monthPanel.add(new JLabel(""));	};
		
		// implements the action listeners of the < and > buttons
		preBttn.addActionListener((e) ->{
			for(int i = 0; i < data.size(); i++) {
				System.out.println(Event.printEvent(data.get(i)));
			}
			currentDate = currentDate.minusDays(1);
			resetBorder(currentDate.getDayOfMonth());
			eventArea.setText(printDayView(currentDate));
			year = currentDate.getYear() % 100; 
			if (currentDate.getDayOfMonth() < 10)
				day = "0" + currentDate.getDayOfMonth();
			else
				day = "" + currentDate.getDayOfMonth();
			if (currentDate.getMonthValue() < 10)
				monthStr = "0" + currentDate.getMonthValue();
			else
				monthStr = "" + currentDate.getMonthValue();
			updateCalendar(days, currentDate);
			repaint();
			resetBorder(currentDate.getDayOfMonth());
			System.out.println(currentDate.toString());
		});
		nextBttn.addActionListener((e) ->{
			currentDate = currentDate.plusDays(1);
			resetBorder(currentDate.getDayOfMonth());
			eventArea.setText(printDayView(currentDate));
			year = currentDate.getYear() % 100; 
			if (currentDate.getDayOfMonth() < 10)
				day = "0" + currentDate.getDayOfMonth();
			else
				day = "" + currentDate.getDayOfMonth();
			if (currentDate.getMonthValue() < 10)
				monthStr = "0" + currentDate.getMonthValue();
			else
				monthStr = "" + currentDate.getMonthValue();
			updateCalendar(days, currentDate);
			repaint();
			resetBorder(currentDate.getDayOfMonth());
			System.out.println(currentDate.toString());
		});
		monthPanel.add(preBttn); // add them to the panel
		monthPanel.add(nextBttn);
		
		// set the titles
		for(int i = 0; i < 7; i++) {
			title[i] = new JLabel();
		}
		
		title[0].setText("S");
		title[1].setText("M");
		title[2].setText("T");
		title[3].setText("W");
		title[4].setText("T");
		title[5].setText("F");
		title[6].setText("S");
		// add the title to the panel
		for(int i = 0; i < 7; i++) {
			monthPanel.add(title[i]);
		}
		
		// get the first day of month, start to print the month view from the corresponding location.
		if(first_day_of_month == 7) {
			for(int i = 1; i <= daysInMonth; i++) {
				if(i == c.getDayOfMonth())
					days.add((addBorder(new JLabel("" + i))));
				else
					days.add(new JLabel("" + i));
			}
		} else {
			for(int i = 0; i < first_day_of_month; i++) {	
				days.add(new JLabel(""));
			}
			for(int i = 1; i <= daysInMonth; i++) {
				if(i == c.getDayOfMonth())
					days.add(addBorder(new JLabel("" + i)));
				else
					days.add(new JLabel("" + i));
			}
		}
		
		for(int i = 0; i < 10; i++) {
			days.add(new JLabel(""));
		}
		
		
		// add all the labels to the panel, and implement their action listeners
		for(int i = 0; i < days.size(); i++) {
			monthPanel.add(days.get(i));	
			days.get(i).addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					JLabel label = (JLabel)e.getSource();
					if(label.getText() != "") {
						resetBorder(Integer.parseInt(label.getText()));
						year = currentDate.getYear() % 100; 
						if (Integer.parseInt(label.getText()) < 10)
							day = "0" + label.getText();
						else
							day = label.getText();
						if (currentDate.getMonthValue() < 10)
							monthStr = "0" + currentDate.getMonthValue();
						else
							monthStr = "" + currentDate.getMonthValue();
						currentDate = LocalDate.parse(monthStr + "/" + day + "/" + year, dateFormat);
						eventArea.setText(printDayView(currentDate));
					} else {
						
					}
				}
			});
		} // add all labels in the ArrayList<JLabel> days to the panel
		
		add(monthPanel);
		// end of adding things into month panel----------------------------------------------------------------------------------------
		
		
		// starting to set up the text area that shows the event of the day------------------------------------------------------------------------------------
		txtPanel.setLayout(new FlowLayout());
		eventArea.setText(printDayView(currentDate)); // find all the events in the date and set the text in eventArea
		// update the eventArea when the user presses < or > button or when user clicks on one of the date
		txtPanel.add(eventArea);
		add(txtPanel);
		
		// end of setting up the event text area------------------------------------------------------------------------------------
		
		// starting to set up the buttons that creates new event and quit program------------------------------------------------------------------------------------
		saveBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// get the data from field and make a new event, update the data model
				System.out.println("This has been called");
				year = currentDate.getYear() % 100; 
				if (currentDate.getDayOfMonth() < 10)
					day = "0" + currentDate.getDayOfMonth();
				else
					day = "" + currentDate.getDayOfMonth();
				if (currentDate.getMonthValue() < 10)
					monthStr = "0" + currentDate.getMonthValue();
				else
					monthStr = "" + currentDate.getMonthValue();
				System.out.println(currentDate.getMonthValue() + "/" + day + "/" + year + " " + startField.getText() + " " + endField.getText());
				Event event = new Event(nameField.getText(), currentDate.getMonthValue() + "/" + day + "/" + year + " " + startField.getText() + " " + endField.getText());
				Boolean conflict = false;
				for(Event evt : data) {
					if (TimeInterval.conflict(evt.getInterval(), event.getInterval())){
						conflict = true;
					}
				}
				if(conflict) {
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "There's a time conflict, can't create this new event.");
				} else {
					dataModel.update(nameField.getText(), currentDate.getMonthValue() + "/" + day + "/" + year + " " + startField.getText() + " " + endField.getText());
					eventFrame.setVisible(false);
				}
				
			}
		});
		createBttn.addActionListener((e) -> {
			eventFrame.setLayout(new GridLayout(2,1));
			dateField.setEditable(false);
			
			
			dateField.setText(currentDate.toString());
			eventFrame.add(nameField);
			inputPanel.add(dateField);
			inputPanel.add(startField);
			inputPanel.add(toLabel);
			inputPanel.add(endField);
			inputPanel.add(saveBttn);
			eventFrame.add(inputPanel);
			eventFrame.pack();
			eventFrame.setVisible(true);
			eventFrame.setLocation(500, 500);
		});
		quitBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveToFile("events");
				System.exit(0);
			}
			
		});
		createPanel.add(createBttn);
		add(createPanel);
		quitPanel.add(quitBttn);
		add(quitPanel);
		pack();
	}
	


    // functions ----------------------------------------------------------------------------------

	
	/**
	 * This funtion will save the events to a file
	 * @param filename name of the file to save to
	 */
	public void saveToFile(String filename) {
		try {
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yy"); 
			File file = new File(filename + ".txt");
			FileWriter myWriter = new FileWriter(filename + ".txt");
			for(int i = 0; i < data.size(); i++) {
				myWriter.write(data.get(i).getName() + "\n");
				myWriter.write(dateFormat.format(data.get(i).getInterval().getDate()) + " " + data.get(i).getInterval().getStartTime() + " " + data.get(i).getInterval().getEndTime() + "\n");
			}
			myWriter.close();
		} catch(IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}	

	/**
	 * this function will reset the border to highlight the current date
	 * @param day the day of month to highlight
	 */
	public void resetBorder(int day) {
		for(int i = 0; i < days.size(); i++) {
			if (days.get(i).getText() == "") {
				
			}
			else if(Integer.parseInt(days.get(i).getText()) != day) {
				days.get(i).setBorder(null);
			} else {
				days.get(i).setBorder(blackline);
			}
		}
	}
	
	/**
	 * This function will receive a JLabel object and add border to it
	 * 
	 * @param label the label to add border on
	 * @return the label with border
	 */
	public JLabel addBorder(JLabel label) {
		Border blackline = BorderFactory.createLineBorder(Color.black);
		label.setBorder(blackline);
		return label;
	}
	
	
	
	/**
	 * Function that prints all events in a day for day view option in main menu
	 * @param date date of which the events will be printed
	 */
	public String printDayView(LocalDate date) {
		String toReturn = "";
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getInterval().getDate().isEqual(date))
				toReturn += (data.get(i).getName() + " : " + data.get(i).getInterval().getStartTime() + " - " + data.get(i).getInterval().getEndTime()) + "\n";
		}
		return toReturn;
	}
	
	
	
	
	/**
	 * This is the overloaded function that notify event list has been changed
	 */
	public void stateChanged(ChangeEvent e) {
		data = dataModel.getData();
		repaint();
	}
	
	
	/**
	 * This function will return an arraylist of Jlabels that contains all the days in a month. The function will be called when the user goes to a new month.
	 * @param date the current date
	 * @return days the arraylist that has all the labels.
	 */
	public void updateCalendar(ArrayList<JLabel> days, LocalDate date) {
		int firstDay = LocalDate.of(date.getYear(), date.getMonth(), 1).getDayOfWeek().getValue();
		YearMonth yearMonthObject = YearMonth.of(date.getYear(), date.getMonthValue());	
		int daysInMonth = yearMonthObject.lengthOfMonth();	// number of days in the month
		if(firstDay == 7) {
			for(int i = 1; i <= daysInMonth; i++) {
				days.get(i - 1).setText("" + i);
				if(i == date.getDayOfMonth())
					days.get(i - 1).setBorder(blackline);
			}
			for(int j = daysInMonth; j < days.size(); j++) {
				days.get(j).setBorder(null);
				days.get(j).setText("");
			}
		} else {
			for(int i = 0; i <= firstDay; i++) {	
				days.get(i).setText("");
				days.get(i).setBorder(null);
			}
			for(int i = firstDay; i < firstDay + daysInMonth ; i++) {
				days.get(i).setText("" + (int)(i - firstDay + 1));
				if(i == date.getDayOfMonth())
					days.get(i - 1).setBorder(blackline);
			}
			for(int j = firstDay + daysInMonth; j < days.size(); j++) {
				days.get(j).setBorder(null);
				days.get(j).setText("");
			}
		}
		monthLabel.setText(currentDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + c.getYear());
	}
	
	private String monthStr;
	private int year; // for making the date parseable
	private String day;
	private JButton createBttn = new JButton("Create"); // button that activates the frame that ask user to input event
	private JButton quitBttn = new JButton("Quit"); // save the data and quit the program
	private JButton saveBttn = new JButton("Save"); // button that saves the event
	JPanel quitPanel = new JPanel(); // panel that contains the bottom half of the main frame
	JPanel createPanel = new JPanel(); // panel that holds the button create
	private JLabel toLabel = new JLabel("To:"); // label between starting time and end time
	private JTextField startField = new JTextField(10); // starting time
	private JTextField endField = new JTextField(10); // ending time
	private JTextField nameField = new JTextField(40); // name of the event
	private JTextField dateField = new JTextField(10); // get from date, unchangeable
	JFrame eventFrame = new JFrame(); // the frame that shows up when user clicks create button to make new event
	JPanel txtPanel = new JPanel(); // textpanel that takes the 
	JPanel inputPanel = new JPanel(); // panel that holds the date, time and save button
	private Model dataModel;
	private ArrayList<Event> data;
	private JTextArea eventArea = new JTextArea(); // contains the information of the selected day
	private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yy"); 
	private Border blackline = BorderFactory.createLineBorder(Color.black); // border object
	private ArrayList<JLabel> days = new ArrayList<JLabel>();	// ArrayList that holds all the labels for each day in month
	private JLabel []title = new JLabel[7];	// array that holds the days of week
	private LocalDate c = LocalDate.now();	// current time
	private Month month = c.getMonth();	// current month
	private JLabel monthLabel = new JLabel(month.getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + c.getYear()); // the current month and year info
	private JButton preBttn = new JButton("<");	// button that goes to next day
	private JButton nextBttn = new JButton(">");	// button that goes to previous day
	private int first_day_of_month = LocalDate.of(c.getYear(), c.getMonth(), 1).getDayOfWeek().getValue();  //first day of month
	private YearMonth yearMonthObject = YearMonth.of(c.getYear(), c.getMonthValue());	
	private int daysInMonth = yearMonthObject.lengthOfMonth();	// number of days in the month
	private LocalDate currentDate = c;	// LocalDate that keeps track of the current date that is editing
}
