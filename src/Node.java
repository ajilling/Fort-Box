/**
 * Used to construct a linked list collection of Block objects
 * @author Adam Jilling
 *
 */

public class Node
{
	private Block item;
	private Node next;
	
	/**
	 * Default constructor
	 */
	public Node()
	{
		item = null;
		next = null;
	}

	/**
	 * Getter - returns a Block
	 * @return
	 */
	public Block getItem()
	{
		return item;
	}

	/**
	 * Sets a Block that is passed
	 * @param someItem
	 */
	public void setItem(Block someItem)
	{
		item = someItem;
	}

	/**
	 * Getter - returns a Node
	 * @return
	 */
	public Node getNext()
	{
		return next;
	}

	/**
	 * Sets a Node that is passed
	 * @param someNext
	 */
	public void setNext(Node someNext)
	{
		next = someNext;
	}
}