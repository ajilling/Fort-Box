/**
 * Defines an Alarm object that will be used in all animations
 *
 */

public class Alarm extends Thread
{
	/**
	 * Contains an AlarmListener
	 */
	private AlarmListener whoWantsToKnow;
	
	/**
	 * Initial delay set to zero
	 */
	private int delay = 0;

	/**
	 * Default constructor
	 */
	public Alarm()
	{
		super("Alarm");
		whoWantsToKnow = null;
	}

	/**
	 * Constructor that will notify somebody
	 * @param someBody
	 */
	public Alarm(AlarmListener someBody)
	{
		super("Alarm");
		whoWantsToKnow = someBody;
	}

	/**
	 * Constructor that will notify and name the Alarm
	 */
	public Alarm(String name, AlarmListener someBody)
	{
		super(name);
		whoWantsToKnow = someBody;
	}

	/**
	 * Sets the period of the Alarm
	 * @param someDelay
	 */
	public void setPeriod(int someDelay)
	{
		delay = someDelay;
	}

	/**
	 * Set recurring notification
	 * @param someDelay
	 */
	private void setPeriodicBeep(int someDelay)
	{
		delay = someDelay;

		try {
			while (true)
			{
				Thread.sleep(delay);
				if (whoWantsToKnow != null)
					whoWantsToKnow.takeNotice();
			}								
		}
		catch(InterruptedException e) {
			System.err.println("Oh, oh ... " + e.getMessage());
		}
	}

	/**
	 * Runs the Alarm
	 */
	public void run()
	{
		setPeriodicBeep(delay);
	}

}
