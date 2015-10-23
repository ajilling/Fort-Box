/**
 * Parent class of all block elements the make up the playing grid
 */

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Block
{
	/**
	 * Numerical rank of where Blocks are located
	 */
	protected int xRank, yRank;
	private int moveX, moveY;
	private int startX, startY; 	// used for animations
	private int endX, endY;			// used for animations
	private boolean animated;
	
	/**
	 * The standard size of a block (unchangeable)
	 */
	public static final int SIZE = 32;
	
	/**
	 * The image the block will contain
	 */
	protected BufferedImage image;
	
	/**
	 * Default constructor
	 */
	public Block()
	{
		moveX = 40;
		moveY = 60;
		startX = 0;
		startY = 0;
		animated = false;
	}
	
	/**
	 * sets animated value to true
	 */
	public void animate()
	{
		animated = true;
	}
	
	/**
	 * Constructor will set a rank for each block, which is two values
	 * defining its initial position on the grid
	 * @param someX
	 * @param someY
	 */
	public Block(int someX, int someY)
	{
		xRank = someX;
		yRank = someY;
		endX = xRank*SIZE+moveX;
		endY = yRank*SIZE+moveY;
	}
	
	/**
	 * Paints the block
	 * @param pane
	 */
	public void paint(Graphics pane)
	{
		if (!animated)
			pane.drawImage(image, xRank*SIZE+moveX, yRank*SIZE+moveY, null);
		
		else if (animated)				// this will give the blocks a sliding motion when moved
										// this includes the robot block which will slide in sync
										// with the block being moved
		{
			pane.drawImage(image, startX, startY, null);
			
			if (Game.direction == 'u')
			{
				startY-=3;
				if (startY <= endY) animated = false;
			} else if (Game.direction == 'd')
			{
				startY+=3;
				if (startY >= endY) animated = false;
			} else if (Game.direction == 'l')
			{
				startX-=3;
				if (startX <= endX) animated = false;
			} else if (Game.direction == 'r')
			{
				startX+=3;
				if (startX >= endX) animated = false;
			}
		}
	
	}
	
	/**
	 * @return the x coordinate of a Block
	 */
	public int getX()
	{
		return xRank;
	}
	
	/**
	 * @return the y coordinate of a Block
	 */
	public int getY()
	{
		return yRank;
	}
	
	/**
	 * sets the x coordinate of a Block
	 */
	public void setX(int someX)
	{
		xRank = someX;
	}
	
	/**
	 * sets the y coordinate of a Block
	 */
	public void setY(int someY)
	{
		yRank = someY;
	}
	
	/**
	 * sets the starting x and y coordinates of a Block (for animations)
	 */
	public void setStartPos(int someX, int someY)
	{
		startX = someX*SIZE+moveX;
		startY = someY*SIZE+moveY;
	}
	
	/**
	 * sets the ending x and y coordinates of a Block (for animations)
	 */
	public void setEndPos(int someX, int someY)
	{
		endX = someX*SIZE+moveX;
		endY = someY*SIZE+moveY;
	}
	
}
