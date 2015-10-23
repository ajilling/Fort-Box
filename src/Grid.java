/**
 * A linked list collection of all of our grid elements
 */
import java.awt.Graphics;

public class Grid implements GridInterface
{
	private Node head;
	private int finishX, finishY;	// the coordinates of the finish are stored
									// independently for faster access
	
	/**
	 * Default constructor
	 */
	public Grid ()
	{
		head = null;
	}
	
	/**
	 * Returns the node where the robot is currently located
	 * @return
	 */
	private Node findRobot()
	{
		Node current = head;
		while (current.getNext() != null)		// step through list until we find a block
		{										// that is an instance of a robot
			if (current.getItem() instanceof Robot)
				return current;
			else
				current = current.getNext();
		}
		return current;							// returns the node containing the robot
	}
	
	/**
	 * Adds a block that is passed into the Grid linked list
	 */
	public void add(Block someBlock)
	{
		Node placeHolder = new Node();			// add a new block and update our list
		placeHolder.setItem(someBlock);			
		placeHolder.setNext(head);				
		head = placeHolder;
		
		if (someBlock instanceof Finish)		// when the finish block is found, coordinates are saved
		{
			finishX = someBlock.getX();
			finishY = someBlock.getY();
		}
	}
	
	/**
	 * Paint the entire grid
	 */
	public void paint(Graphics pane)
	{
		Node current = head;
		
		while (current != null)	
		{
			current.getItem().paint(pane);
			current = current.getNext();
		}
		
	}

	/**
	 * Returns a requested Node given an x and y coordinate
	 * @param someX
	 * @param someY
	 * @return
	 */
	private Node getNode(int someX, int someY)
	{
		Node current = head;
		while (current.getNext() != null)
		{
			if ((current.getItem().getX() == someX) && (current.getItem().getY() == someY))
				return current;
			else
				current = current.getNext();
		}
		return current;
	}
	
	/**
	 * returns if the robot has entered the finish coordinates
	 * @return
	 */
	public boolean checkWinner()
	{
		Node robot = findRobot();
		int x = robot.getItem().getX();
		int y = robot.getItem().getY();
		
		if ((x == finishX) && (y == finishY))	// check if the robots coordinates match
			return true;
		
		return false;
	}
	
	/**
	 * return T/F is character is able to move up
	 * and perform the move
	 */
	public boolean canMoveUp()
	{
		Node robot = findRobot();
		int x = ((Robot)robot.getItem()).getRobotX();
		int y = ((Robot)robot.getItem()).getRobotY();
		Node first = getNode(x, y-1);		// find the 2 blocks ahead of the desired move
		Node second = getNode(x, y-2);
		
		if (first.getItem() instanceof Wall)		// determine if move is legal
			return false;
		else if ((first.getItem() instanceof Box) && (second.getItem() instanceof Box))
			return false;
		else if ((first.getItem() instanceof Box) && (second.getItem() instanceof Wall))
			return false;
		else if ((first.getItem() instanceof Box) && (second.getItem() instanceof Block))
		{
			// if it is legal, execute the move and animations
			Block temp1 = new Block(x, y);					// create a new "blank" Block
			Block temp2 = first.getItem();
			temp1.setY(y-1);								// adjust for the new coordinates
			temp2.setY(y-2);
			
			first.getItem().setStartPos(x, y-1);			// prepare for animations
			first.getItem().setEndPos(x, y-2);				// prepare for animations
			first.getItem().animate();						// perform animations
			
			first.setItem(temp1);							// send updated blocks back
			second.setItem(temp2);
			
			((Robot)robot.getItem()).animate();				// perform animations
			((Robot)robot.getItem()).setStartPos(x, y);
			((Robot)robot.getItem()).setEndPos(x, y-1);
			((Robot)robot.getItem()).setRobotY(y-1);
			return true;
		}
		else
		{
			((Robot)robot.getItem()).animate();				// perform animations
			((Robot)robot.getItem()).setStartPos(x, y);
			((Robot)robot.getItem()).setEndPos(x, y-1);
			((Robot)robot.getItem()).setRobotY(y-1);
			return true;
		}
	}

	/**
	 * return T/F is character is able to move down
	 * and perform the move
	 */
	public boolean canMoveDown()
	{
		Node robot = findRobot();
		int x = ((Robot)robot.getItem()).getRobotX();
		int y = ((Robot)robot.getItem()).getRobotY();
		Node first = getNode(x, y+1);
		Node second = getNode(x, y+2);
		
		if (first.getItem() instanceof Wall)
			return false;
		else if ((first.getItem() instanceof Box) && (second.getItem() instanceof Box))
			return false;
		else if ((first.getItem() instanceof Box) && (second.getItem() instanceof Wall))
			return false;
		else if ((first.getItem() instanceof Box) && (second.getItem() instanceof Block))
		{
			Block temp1 = new Block(x, y);
			Block temp2 = first.getItem();
			temp1.setY(y+1);
			temp2.setY(y+2);
			
			first.getItem().setStartPos(x, y+1);
			first.getItem().setEndPos(x, y+2);
			first.getItem().animate();
			
			((Robot)robot.getItem()).animate();
			((Robot)robot.getItem()).setStartPos(x, y);
			((Robot)robot.getItem()).setEndPos(x, y+1);
			
			first.setItem(temp1);
			second.setItem(temp2);
			((Robot)robot.getItem()).setRobotY(y+1);
			return true;
		}
		else
		{
			((Robot)robot.getItem()).animate();
			((Robot)robot.getItem()).setStartPos(x, y);
			((Robot)robot.getItem()).setEndPos(x, y+1);
			((Robot)robot.getItem()).setRobotY(y+1);
			return true;
		}
	}

	/**
	 * return T/F is character is able to move left
	 * and perform the move
	 */
	public boolean canMoveLeft()
	{
		Node robot = findRobot();
		int x = ((Robot)robot.getItem()).getRobotX();
		int y = ((Robot)robot.getItem()).getRobotY();
		Node first = getNode(x-1, y);
		Node second = getNode(x-2, y);
		
		if (first.getItem() instanceof Wall)
			return false;
		else if ((first.getItem() instanceof Box) && (second.getItem() instanceof Box))
			return false;
		else if ((first.getItem() instanceof Box) && (second.getItem() instanceof Wall))
			return false;
		else if ((first.getItem() instanceof Box) && (second.getItem() instanceof Block))
		{
			Block temp1 = new Block(x, y);
			Block temp2 = first.getItem();
			temp1.setX(x-1);
			temp2.setX(x-2);
			
			first.getItem().animate();
			first.getItem().setStartPos(x-1, y);
			first.getItem().setEndPos(x-2, y);
			
			((Robot)robot.getItem()).animate();
			((Robot)robot.getItem()).setStartPos(x, y);
			((Robot)robot.getItem()).setEndPos(x-1, y);
			
			first.setItem(temp1);
			second.setItem(temp2);
			((Robot)robot.getItem()).setRobotX(x-1);
			return true;
		}
		else
		{
			((Robot)robot.getItem()).animate();
			((Robot)robot.getItem()).setStartPos(x, y);
			((Robot)robot.getItem()).setEndPos(x-1, y);
			((Robot)robot.getItem()).setRobotX(x-1);
			return true;
		}
	}

	/**
	 * return T/F is character is able to move right
	 * and perform the move
	 */
	public boolean canMoveRight()
	{
		Node robot = findRobot();
		int x = ((Robot)robot.getItem()).getRobotX();
		int y = ((Robot)robot.getItem()).getRobotY();
		Node first = getNode(x+1, y);
		Node second = getNode(x+2, y);
		
		if (first.getItem() instanceof Wall)
			return false;
		else if ((first.getItem() instanceof Box) && (second.getItem() instanceof Box))
			return false;
		else if ((first.getItem() instanceof Box) && (second.getItem() instanceof Wall))
			return false;
		else if ((first.getItem() instanceof Box) && (second.getItem() instanceof Block))
		{
			Block temp1 = new Block(x, y);
			Block temp2 = first.getItem();
			temp1.setX(x+1);
			temp2.setX(x+2);
			
			first.getItem().animate();
			first.getItem().setStartPos(x+1, y);
			first.getItem().setEndPos(x+2, y);
			
			((Robot)robot.getItem()).animate();
			((Robot)robot.getItem()).setStartPos(x, y);
			((Robot)robot.getItem()).setEndPos(x+1, y);
			
			first.setItem(temp1);
			second.setItem(temp2);
			((Robot)robot.getItem()).setRobotX(x+1);
			return true;
		}
		else
		{
			((Robot)robot.getItem()).animate();
			((Robot)robot.getItem()).setStartPos(x, y);
			((Robot)robot.getItem()).setEndPos(x+1, y);
			((Robot)robot.getItem()).setRobotX(x+1);
			return true;
		}
	}
}
