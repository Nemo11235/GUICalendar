import java.util.ArrayList;
import javax.swing.event.*;

/**
 * (borrowed from textbook, edited)
  A Subject class for the observer pattern.
*/
public class Model
{
   /**
      Constructs a DataModel object
      @param d the data to model
   */
   public Model(ArrayList<Event> d)
   {
      data = d;
      listeners = new ArrayList<ChangeListener>();
   }

   /**
      Constructs a DataModel object
      @return the data in an ArrayList
   */
   public ArrayList<Event> getData()
   {
      return (ArrayList<Event>) (data.clone());
   }

   /**
      Attach a listener to the Model
      @param c the listener
   */
   public void attach(ChangeListener c)
   {
      listeners.add(c);
   }

   /**
      Change the data in the model at a particular location
      @param location the index of the field to change
      @param value the new value
   */
   public void update(String n, String t)
   {
      data.add(new Event(n, t));
      for (ChangeListener l : listeners) {
         l.stateChanged(new ChangeEvent(this));
      }
   }

   ArrayList<Event> data;
   ArrayList<ChangeListener> listeners;
}