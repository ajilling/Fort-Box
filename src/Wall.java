/**
 * Defines a wall element and inherits from Block. A wall will be a boundary
 * that cannot be moved or passed through
 */

import java.io.*;
import javax.imageio.*;

public class Wall extends Block
{
	/**
	 * Constructor will set appropriate image for a wall
	 * @param someX
	 * @param someY
	 */
	public Wall(int someX, int someY)
	{
		xRank = someX;
		yRank = someY;
		try
		{
			image = ImageIO.read(new File("images/wall.png"));
			
		} catch (IOException e){
			System.out.println("Image missing.");
		}
	}
	
}
