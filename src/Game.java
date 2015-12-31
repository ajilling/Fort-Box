/**
 * The Game class holds and instantiates all objects needed for the game
 */

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane; // pop-up dialog box

public class Game extends Frame implements MouseListener, AlarmListener, KeyListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Window myWindow;
	private ControlPad controller;
	private int mouseX, mouseY;
	private Grid grid;
	private BufferedImage background;
	private String setupCode;				// each level layout is broken down into a single string
	private Chooser reset, one, two, three;	// buttons
	private final int BLOCK_SIZE = 32;
	private Alarm alarm;					// used in animations
	private boolean inProgress;				// if a game has started
	private long start, elapsed;			// times the game
	protected static char direction;		// will need to be accessed from block class too
	
	/**
	 * Default constructor
	 */
	public Game()
	{
		setTitle("Fort Box");
		setLocation(100, 100);
		setSize(528, 730);
		setBackground(Color.GRAY);

		// allow window to close
		myWindow = new Window();
		addWindowListener(myWindow);
		
		setCode(1);							// default level 1
		
		controller = new ControlPad(183, 540);
		initGrid();
		
		try {								// background image
			background = ImageIO.read(new File("images/background.jpg"));
		} catch (IOException e){
			System.out.println("Image missing.");
		}
		
		reset = new Chooser (40, 520, BLOCK_SIZE, 100, "Reset", 16);
		one = new Chooser (376, 520, BLOCK_SIZE, BLOCK_SIZE, "1", 16);
		two = new Chooser (416, 520, BLOCK_SIZE, BLOCK_SIZE, "2", 16);
		three = new Chooser (456, 520, BLOCK_SIZE, BLOCK_SIZE, "3", 16);
		
		alarm = new Alarm("Timer", this);			// alarm is used to animate
		alarm.setPeriod(10);
		alarm.start();
		
		one.select();
		
		// start our game timer by getting current system time
		start = System.currentTimeMillis();
		
		direction = 'z';					// a "null" value of 'z'
		inProgress = false;					// no moves made yet
		
		addMouseListener(this);
		addKeyListener(this);				// arrows on keyboard can be used as well
		setVisible(true);
	}
	
	/**
	 * Initialize the Grid to begin a new game
	 */
	private void initGrid()
	{
		grid = new Grid();
		
		int tempX = 0;
		int tempY = 0;
		int place = 0;
		char placeVal;
		
		// we will use a 14x14 grid, this loop steps through the setup string
		// and creates each box accordingly
		for (int i = 0; i < 14; i++) {
			for (int j =0; j < 14; j++)
			{
				placeVal = setupCode.charAt(place);
				if (placeVal == 'w')
				{
					Block temp = new Wall(tempX, tempY);
					grid.add(temp);
				} else if (placeVal == 'b')
				{
					Block temp = new Box(tempX, tempY);
					grid.add(temp);
				} else if (placeVal == 'r')
				{
					Block temp = new Robot(tempX, tempY);
					grid.add(temp);
				} else if (placeVal == 'f')
				{
					Block temp = new Finish(tempX, tempY);
					grid.add(temp);
				}
				else if (placeVal == 'o')
				{
					Block temp = new Block(tempX, tempY);
					grid.add(temp);
				}
				tempX+=1;
				place++;
			}
			tempY+=1;
			tempX = 0;
		}
		start = System.currentTimeMillis();
		repaint();
	}
	
	/**
	 * Take any instructions related to the alarm (animations)
	 */
	public void takeNotice()
	{
		repaint();
	}
	
	/**
	 * Paint all our elements (grid, controller, buttons, etc.)
	 */
	public void paint(Graphics g)
	{
		g.drawImage(background, 0, 0, null);
		controller.paint(g);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(40, 60, 448, 448);
		grid.paint(g);
		reset.paint(g);
		one.paint(g);
		two.paint(g);
		three.paint(g);
	}
	
	/**
	 * Determines if a requested move is legal or not. If it is, the Grid class will
	 * execute the move
	 * @param move
	 */
	private void allowMove(char move)
	{
		switch (move)
		{
		case 'u':
			grid.canMoveUp();
			break;
		case 'd':
			grid.canMoveDown();
			break;
		case 'l':
			grid.canMoveLeft();
			break;
		case 'r':
			grid.canMoveRight();
			break;
		default:
			break;
		}
		
		if (grid.checkWinner())			// will check after each move to see if winner
			showWinner();
	}
	
	/**
	 * Displays a winner dialog showing the time it took to complete the level
	 */
	private void showWinner()
	{
		long end = System.currentTimeMillis();
		elapsed = (end - start) / 1000;
			
		// call method to format the time
		String time = formatTime(elapsed);
		repaint();
			
		// show winner dialog box
		JOptionPane.showMessageDialog(null,
			"You finished the puzzle in " + time + "!");
		
		initGrid();				// reset the level after
		inProgress = false;
		direction = 'z';
	}
	
	/**
	 * Formats the raw time data to something more easily readable
	 * @param time
	 * @return
	 */
	private String formatTime(long time)
	{
		String timeString = "";
		int mins, secs;
		
		// no need to format if time is less than 60 seconds
		if (time < 60)
		{
			timeString = String.valueOf(time) + " seconds";
			return timeString;
		}
		
		// determine number of minutes and seconds
		mins = (int)time / 60;
		secs = (int)time % 60;
		
		// create a String in mm:ss format
		timeString = String.valueOf(mins) + ":" + String.valueOf(secs);
		
		return timeString;

	}
	
	/**
	 * Receives which level to set and sets up the corresponding level
	 * Every element in a level is represented by a single char
	 * @param level
	 */
	private void setCode(int level)
	{
		// each level is broken down to a string
		// w = wall
		// r = robot
		// b = box
		// o = open (generic Block)
		//f = finish
		if (level == 1)
			setupCode = "wwwwwwwwwwwfww"
					+ "wooowoobooobow"
					+ "wbobwobobwboow"
					+ "wobowoobowobow"
					+ "wbobbowbowbobw"
					+ "wobobowoooobow"
					+ "wwobowwwwwwwww"
					+ "woooooooboooow"
					+ "wwwwwbooboboow"
					+ "woboobobobobow"
					+ "wobobobowobobw"
					+ "wbobobobwbobow"
					+ "wobwbobowobobw"
					+ "wwrwwwwwwwwwww";
		else if (level == 2)
			setupCode = "wwwwwwwwwwwwww"
					+ "woooboboooobow"
					+ "wbobobobwbobow"
					+ "wobobooowobobw"
					+ "wobwwwwwwbobow"
					+ "wooobobowoboof"
					+ "wbobobobwwwwww"
					+ "woboooboooooow"
					+ "wwwwwwwwwwwwow"
					+ "woobobooboobow"
					+ "wobobobowoboow"
					+ "wbobobobwoobow"
					+ "wobobobowbobow"
					+ "wwrwwwwwwwwwww";
		else if (level == 3)
			setupCode = "wwwwwwwwwwwfww"
					+ "woooooooooooow"
					+ "wwbbbwwwwwwwww"
					+ "woooooooooooow"
					+ "wwwwwwwwwwowww"
					+ "wobowowowobobw"
					+ "wobowooowoboow"
					+ "wobowowowbbobw"
					+ "wowowowowobbow"
					+ "wowobowowoboow"
					+ "wowobowowoobow"
					+ "wowobowowbobbw"
					+ "wowowowobooobw"
					+ "wrwwwwwwwwwwww";
	}
	
	/**
	 * Mouse Pressed
	 */
	public void mousePressed(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
		controller.highlight(mouseX, mouseY);
		direction = controller.getDirection();
		allowMove(direction);
		
		if (direction != 'z')
			inProgress = true;
		
		if (one.isInside(mouseX, mouseY))
		{
			// confirm if a level has been started first to make sure the user really wants to quit
			if (!inProgress)
			{
				one.select();
				two.unselect();
				three.unselect();
				direction = 'z';
				inProgress = false;
				setCode(1);
				initGrid();
			} else {
				// dialog box is shown to confirm user choice if level in progress
				int option = JOptionPane.showConfirmDialog(null,
						"Quit current level?", "Confirm", JOptionPane.YES_NO_OPTION);
				if (option == 0)
				{
					one.select();
					two.unselect();
					three.unselect();
					direction = 'z';
					inProgress = false;
					setCode(1);
					initGrid();
				}
			}

		} else if (two.isInside(mouseX, mouseY))
		{
			// confirm if a level has been started first to make sure the user really wants to quit
			if (!inProgress)
			{
				one.unselect();
				two.select();
				three.unselect();
				direction = 'z';
				inProgress = false;
				setCode(2);
				initGrid();
			} else {
				// dialog box is shown to confirm user choice if level in progress
				int option = JOptionPane.showConfirmDialog(null,
						"Quit current level?", "Confirm", JOptionPane.YES_NO_OPTION);
				if (option == 0)
				{
					one.unselect();
					two.select();
					three.unselect();
					direction = 'z';
					inProgress = false;
					setCode(2);
					initGrid();
				}
			}
		} else if (three.isInside(mouseX, mouseY))
		{
			// confirm if a level has been started first to make sure the user really wants to quit
			if (!inProgress)
			{
				one.unselect();
				two.unselect();
				three.select();
				direction = 'z';
				inProgress = false;
				setCode(3);
				initGrid();
			} else {
				// dialog box is shown to confirm user choice if level in progress
				int option = JOptionPane.showConfirmDialog(null,
						"Quit current level?", "Confirm", JOptionPane.YES_NO_OPTION);
				if (option == 0)
				{
					one.unselect();
					two.unselect();
					three.select();
					direction = 'z';
					inProgress = false;
					setCode(3);
					initGrid();
				}
			}
		} else if (reset.isInside(mouseX, mouseY))
		{
			reset.select();
			if (inProgress)
			{
				int option = JOptionPane.showConfirmDialog(null,
						"Reset current level?", "Confirm", JOptionPane.YES_NO_OPTION);
				if (option == 0)
				{
					initGrid();
					direction = 'z';
					inProgress = false;
				}
			}
		}
		repaint();
	}
	
	/**
	 * Mouse Released
	 */
	public void mouseReleased(MouseEvent e)
	{
		controller.unhighlight();
		reset.unselect();
		repaint();
	}
	
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}

	/**
	 * Detects any keyboard arrow presses and calls corresponding methods
	 */
	public void keyPressed(KeyEvent key)
	{
		if (direction != 'z')
			inProgress = true;
		if (key.getKeyCode() == 39)				// right arrow
		{
			direction = 'r';
			grid.canMoveRight();
			if (grid.checkWinner())
				showWinner();
		}
		else if (key.getKeyCode() == 37)		// left arrow
		{
			direction = 'l';
			grid.canMoveLeft();
			if (grid.checkWinner())
				showWinner();
		}
		else if (key.getKeyCode() == 38)		// up arrow
		{
			direction = 'u';
			grid.canMoveUp();
			if (grid.checkWinner())
				showWinner();
		}
		else if (key.getKeyCode() == 40)		// down arrow
		{
			direction = 'd';
			grid.canMoveDown();
			if (grid.checkWinner())
				showWinner();
		}

	}

	public void keyReleased(KeyEvent key){}

	public void keyTyped(KeyEvent key){}
	
}
