/**
 * Defines a finish element and inherits from Block.
 */

import java.io.*;
import javax.imageio.*;

public class Finish extends Block
{
	/**
	 * Constructor will set appropriate image for a box
	 * @param someX
	 * @param someY
	 */
	public Finish(int someX, int someY)
	{
		xRank = someX;
		yRank = someY;
		
		try
		{
			image = ImageIO.read(new File("images/finish.png"));
			
		} catch (IOException e){
			System.out.println("Image missing.");
		}
	}
}