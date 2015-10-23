/**
 * Defines a ControlPad object made up of images of arrows that will allow the
 * character to be moved around the puzzle grid
 */

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class ControlPad
{
	private int x, y;
	private final int SPACER = 50;
	
	/**
	 * Images contained with ControlPad
	 */
	private BufferedImage greenUp, greenDown, greenLeft, greenRight,
							redUp, redDown, redLeft, redRight,
							up, down, left, right;
	
	/**
	 * The currently set direction
	 */
	private char direction;
	
	/**
	 * Default constructor
	 */
	public ControlPad()
	{
		this(0, 0);
	}
	
	/**
	 * Constructor that receives x and y coordinates
	 * @param someX
	 * @param someY
	 */
	public ControlPad(int someX, int someY)
	{
		x = someX;
		y = someY;
		try					// loads green and red images to be able to switch between quickly
		{
			greenUp = ImageIO.read(new File("images/green_up.png"));
			greenDown = ImageIO.read(new File("images/green_down.png"));
			greenLeft = ImageIO.read(new File("images/green_left.png"));
			greenRight = ImageIO.read(new File("images/green_right.png"));
			redUp = ImageIO.read(new File("images/red_up.png"));
			redDown = ImageIO.read(new File("images/red_down.png"));
			redLeft = ImageIO.read(new File("images/red_left.png"));
			redRight = ImageIO.read(new File("images/red_right.png"));
			up = redUp;
			down = redDown;
			left = redLeft;
			right = redRight;
			
		} catch (IOException e){
			System.out.println("Image missing.");
		}
	}
	
	/**
	 * Highlight the appropriate arrow green depending on where click occurred
	 * Sets direction to the appropriate value
	 * @param mouseX
	 * @param mouseY
	 */
	public void highlight(int mouseX, int mouseY)
	{
		if (mouseY < (y + up.getHeight()) && mouseY > y && mouseX
				< (x + SPACER + up.getWidth()) && mouseX > x + SPACER)
		{
			up = greenUp;
			direction = 'u';
		}
		
		else if (mouseY < (y + 2*SPACER + down.getHeight()) && mouseY > y + 2*SPACER
				&& mouseX < (x + SPACER + down.getWidth()) && mouseX > x + SPACER)
		{
			down = greenDown;
			direction = 'd';
		}
		
		else if (mouseY < (y + SPACER + left.getHeight()) && mouseY > y + SPACER
				&& mouseX < (x + left.getWidth()) && mouseX > x)
		{
			left = greenLeft;
			direction = 'l';
		}
		
		else if (mouseY < (y + SPACER + right.getHeight()) && mouseY > y + SPACER
				&& mouseX < (x + 2*SPACER + right.getWidth()) && mouseX > x + 2*SPACER)
		{
			right = greenRight;
			direction = 'r';
		}
		
		else
			direction = 'z';				// default "null" direction
	}
	
	/**
	 * Returns a char indicating which direction the character is set to move
	 * u = up, d = down, l = left, r = right, z = no direction
	 * @return
	 */
	public char getDirection()
	{
		return direction;
	}
	
	/**
	 * changes all arrows back to default state of red
	 */
	public void unhighlight()
	{
		up = redUp;
		down = redDown;
		left = redLeft;
		right = redRight;
	}
	
	/**
	 * Paints the four arrows that make up the control pad
	 * @param pane
	 */
	public void paint(Graphics pane)
	{

		pane.drawImage(up, x+SPACER, y, null);
		pane.drawImage(down, x+SPACER, y+2*SPACER, null);
		pane.drawImage(left, x, y+SPACER, null);
		pane.drawImage(right, x+2*SPACER, y+SPACER, null);
		
	}

}
