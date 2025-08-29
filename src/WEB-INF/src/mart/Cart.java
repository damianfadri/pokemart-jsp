package mart;

import java.util.ArrayList;
import java.util.Iterator;


public class Cart
{
	private ArrayList<Item> cart;
	private ArrayList<Integer> quantities;
	private int total;
	
	public Cart()
	{
		cart = new ArrayList<Item>();
		quantities = new ArrayList<Integer>();
		total = 0;
	}
	
	/**
	 * Adds an item to the shopping cart.
	 * 
	 * @param item				item to be added
	 * @return					TRUE if adding is successful,
	 * 							FALSE otherwise
	 */
	public boolean addToCart(Item item, int amount)
	{		
		if (contains(item))
		{
			for (Item i : cart)
				if (i.compareTo(item) == 0)
				{
					int index = cart.indexOf(i);
					int q = quantities.get(index);
					quantities.set(index, (q + amount));
					
					break;
				}
		}
		else
		{
			cart.add(item);
			quantities.add(amount);
		}
		
		total += (item.price()*amount);
		return true;
	}
	
	/**
	 * Removes an item from the shopping cart.
	 * 
	 * @param item				item to be removed
	 * @return					TRUE if removing is successful,
	 * 							FALSE otherwise
	 */
	public boolean removeToCart(Item item)
	{
		if (!contains(item))
			return false;
		
		for (Item i : cart)
		{
			if (i.compareTo(item) == 0)
			{
				int index = cart.indexOf(i);
				int amount = quantities.get(index);
				
				cart.remove(index);
				quantities.remove(index);
				
				total -= (item.price()*amount);
				return true;
			}
		}

		return false;
	}
	
	public int quantityOf(Item item)
	{
		if (!contains(item))
			return 0;
		
		return quantities.get(cart.indexOf(item));
	}
	
	public boolean contains(Item item)
	{
		for (Item i : cart)
			if (i.compareTo(item) == 0)
				return true;
		return false;
	}
	
	public ArrayList<Item> iterator()
	{
		return cart;
	}
	
	/**
	 * Returns the current size of the cart.
	 * 
	 * @return					size of the cart
	 */
	public int size()
	{
		return cart.size();
	}
	
	/**
	 * Returns the total amount of items currently in the cart.
	 * 
	 * @return					total amount of the cart
	 */
	public int total()
	{
		return total;
	}
}
