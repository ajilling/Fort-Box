/**
 * @author Adam Jilling
 * 
 * This class serves as the framework for all buttons
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Chooser
{
	/**
	 * int x, y: location of button
	 * int height, width: size of button
	 * int fontSize: size of font to use, which will vary between buttons
	 * final int CORNER: the rounded corner on each button will not vary
	 * int centerX, centerY: calculate these values to keep everything centered
	 * Color borderColor, backColor: the border and back color of each button will be
	 *   changed dynamically
	 * boolean isSelected: state of the button
	 */
	private int x, y, height, width, fontSize;
	private String label;
	private final int CORNER = 15;
	private int centerX, centerY;
	private Color borderColor = Color.ORANGE;
	private Color backColor = Color.LIGHT_GRAY;
	private boolean isSelected;
	
	/**
	 * Constructor will initialize our default values
	 * @param someX: x location
	 * @param someY: y location
	 * @param someHeight: height of button
	 * @param someWidth: width of button
	 * @param someLabel: button's label
	 * @param someFont: font size
	 * centerX and centerY will be calculated and initialized here as well
	 */
	public Chooser(int someX, int someY, int someHeight, int someWidth, String someLabel, int someFont)
	{
		x = someX;
		y = someY;
		height = someHeight;
		width = someWidth;
		label = someLabel;
		fontSize = someFont;
		centerX = x + (width / 2);
		centerY = y + (height / 2);
	}
	
	/**
	 * Paint each button object
	 * @param pane
	 */
	public void paint(Graphics pane)
	{
		// fill in the background of the button
		pane.setColor(backColor);
		pane.fillRoundRect(x, y, width, height, CORNER, CORNER);
		
		// draw the border of the button
		pane.setColor(borderColor);
		pane.drawRoundRect(x, y, width, height, CORNER, CORNER);
		
		// set the font color, font size, and font
		pane.setColor(Color.BLACK);
		pane.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, fontSize));
		
		// get size of label to be able to center it
		int labelWidth = pane.getFontMetrics().stringWidth(label);
		int labelHeight = pane.getFontMetrics().getMaxAscent();
		
		// draw label in center of button
		pane.drawString(label, centerX - labelWidth / 2, centerY + labelHeight / 2);
	}
	
	/**
	 * To determine if a mouse click is within the bounds of a button
	 * @param someX
	 * @param someY
	 * @return true if inside, false if not
	 */
	public boolean isInside(int someX, int someY)
	{
		return (someX >= x && someX <= (x+width) && someY >= y && someY <= (y+height));
	}
	
	/**
	 * Method to put a button in a selected state
	 */
	public void select()
	{
		borderColor = Color.red;
		backColor = new Color(250, 250, 250);
		isSelected = true;
	}
	
	/**
	 * To put a button in unselected state
	 */
	public void unselect()
	{
		borderColor = Color.orange;
		backColor = Color.LIGHT_GRAY;
		isSelected = false;
	}
	
	/**
	 * Determine if a button is selected or not
	 * @return true if selected, false if not
	 */
	public boolean isSelected()
	{
		return isSelected;
	}
}
