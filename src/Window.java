/**
 * Allows us to close the window
 */

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Window extends WindowAdapter
{
	public void windowClosing(WindowEvent event)
	{
		System.exit(0);
	}
}