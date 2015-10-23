/**
 * Defines a robot element and inherits from Block. A robot will move around the
 * grid independently
 */

import java.io.*;
import javax.imageio.*;

public class Robot extends Block
{
	/**
	 * Constructor will set appropriate image for a box
	 * @param someX
	 * @param someY
	 */
	public Robot(int someX, int someY)
	{
		xRank = someX;
		yRank = someY;
		
		try
		{
			image = ImageIO.read(new File("images/robot.png"));
			
		} catch (IOException e){
			System.out.println("Image missing.");
		}
	}
	
	public int getRobotX()
	{
		return xRank;
	}
	
	public int getRobotY()
	{
		return yRank;
	}
	
	public void setRobotX(int someX)
	{
		xRank = someX;
	}
	
	public void setRobotY(int someY)
	{
		yRank = someY;
	}
}