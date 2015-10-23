/**
 * Defines the behavior and general rules that our grid will need to have
 * @author Adam Jilling
 *
 */

import java.awt.Graphics;

public interface GridInterface
{

	/**
	 * Returns T/F if the character is able to move up
	 * @return
	 */
	public boolean canMoveUp();
	
	/**
	 * Returns T/F if the character is able to move down
	 * @return
	 */
	public boolean canMoveDown();
	
	/**
	 * Returns T/F if the character is able to move left
	 * @return
	 */
	public boolean canMoveLeft();
	
	/**
	 * Returns T/F if the character is able to move right
	 * @return
	 */
	public boolean canMoveRight();
	
	/**
	 * Paints the grid
	 * @param pane
	 */
	public void paint(Graphics pane);
	
	/**
	 * adds a block to the grid
	 * @param someBlock
	 */
	public void add(Block someBlock);
	
}
