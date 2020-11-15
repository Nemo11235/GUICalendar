import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.util.*;

public class CalendarFrame extends JFrame implements ChangeListener{
	public CalendarFrame(Model d) {
		// basic settings of the frame----------------------
		dataModel = d;
		setLocation(0, 200);
		setSize(2000, 1000);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(2,2));
		
		MonthlyView monthPanel = new MonthlyView();
		add(monthPanel);
		
	} // end of CalendFrame
		
		
		
	
	
	// overloading the stateChanged method
	public void stateChanged(ChangeEvent e) {
		dataModel.getData();
		repaint();
	} // end of stateChanged
	
	// buttons
	JButton leftButton = new JButton("<");
	JButton rightButton = new JButton(">");
	JButton createButton = new JButton("Create");
	JButton quitButton = new JButton("Quit");
	// data and model
	private Model dataModel;
	private ArrayList<Event> data;
}
