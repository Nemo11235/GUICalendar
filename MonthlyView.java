import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.JButton;

public class MonthlyView extends JPanel implements ChangeListener{

	public MonthlyView() {
		LocalDate c = LocalDate.now(); 
		Month month = c.getMonth();
		JLabel monthLabel = new JLabel(month.getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + c.getYear()); // the current month and year info
		JButton preBttn = new JButton("<");
		JButton nextBttn = new JButton(">");
		Border blackline = BorderFactory.createLineBorder(Color.black);
		// the monthly view panel----------------------
		setLayout(new GridLayout(7, 8));
		System.out.println(monthLabel.getText());
		
//		add(monthLabel);
//		add(preBttn);
//		add(nextBttn);
//		for(int i = 0; i < 4; i ++) { add(new JLabel("jlj"));	};
//		
		
		ArrayList<JLabel> days = new ArrayList<JLabel>();
		JLabel []title = new JLabel[7];
		for(int i = 0; i < 7; i++)
		{	title[i] = new JLabel();
			title[i].setBorder(blackline);
			//title[i].setPreferredSize(new Dimension(50, 50));
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
			add(title[i]);
		}
		
		// get the first day of month, strat to print the month view from the corresponding location.
		int first_day_of_month = LocalDate.of(c.getYear(), c.getMonth(), 1).getDayOfWeek().getValue(); 
		
		
		for(int i = 0; i< 42; i++) {
			days.add(new JLabel("hi"));
			add(days.get(i));
		}
		
	}
	
	public void stateChanged(ChangeEvent e) {
		
	}
	
}
