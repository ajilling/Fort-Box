/**
 * Defines a box element and inherits from Block. A box is a MOVABLE boundary
 * that will have to be pushed around in order to navigate through the maze
 */

import java.io.*;
import javax.imageio.*;

public class Box extends Block
{
	/**
	 * Constructor will set appropriate image for a box
	 * @param someX
	 * @param someY
	 */
	public Box(int someX, int someY)
	{
		xRank = someX;
		yRank = someY;
		
		try
		{
			image = ImageIO.read(new File("images/box.png"));
			
		} catch (IOException e){
			System.out.println("Image missing.");
		}
	}
}
