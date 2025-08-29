package mart;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * The stock room has the information about the stock of items,
 * sorting it by its specified item type and item name and
 * maintaining its stock count.
 * 
 * The stock room also manages the changes pertaining to
 * the stock of its items.
 */
public class Stockroom
{
	// Filename of the database file
	private static String db = "./webapps/PokeMart/META-INF/item.db";
	
	// Only instance of the Stockroom
	private static Stockroom instance = null;
	
	// Data representation of the Stockroom
	private ArrayList<Item> stockroom;
	
	/**
	 * Creates an instance of a stock room.
	 * 
	 * Reads from a file to retrieve existing stock room data
	 * and places it in store for manipulation.
	 */
	private Stockroom()
	{
		stockroom = new ArrayList<Item>();
		
		try 
		{
			BufferedReader itemDB = new BufferedReader(new FileReader(db));
		
			String line;
			while ((line = itemDB.readLine()) != null)
			{
				StringTokenizer st = new StringTokenizer(line, "|");
			
				int type = new Integer(st.nextToken());
				int name = new Integer(st.nextToken());
				int stock = new Integer(st.nextToken());
				int price = new Integer(st.nextToken());
				
				String description = st.nextToken();
				String rarity = st.nextToken();
					
				Item s = new Item(type, name, stock, price, description, rarity);
				stockroom.add(s);
			}
			
			itemDB.close();
		}
		catch (java.io.FileNotFoundException e) {}
		catch (java.io.IOException e1) {}
	}
	
	/**
	 * Returns an instance of the current stockroom to ensure
	 * only one instance exists at a time.
	 * 
	 * @return					instance of the stockroom
	 */
	public static Stockroom getInstance()
	{
		if (instance == null) {
			instance = new Stockroom();
		}
		
		return instance;
	}
	
	/**
	 * Returns the current stock of a specific item.
	 * 
	 * @param type				type of the item
	 * @param name				name of the item
	 * @return					current stock of the item
	 */
	public int getStockOf(int type, int name)
	{
		Item j = get(type, name);
		
		if (j == null)
			return -1;
		return j.stock();
	}
	
	/**
	 * Gets a specific number of stock of an item from the stockroom.
	 * 
	 * Subtracts the retrieved stock from an item's stock and 
	 * updates the file.
	 * 
	 * @param type				type of item
	 * @param name				name of item
	 * @param num				number of stock
	 * @return					true if the item type and name exists,
	 * 							false otherwise
	 */
	public boolean getStock(int type, int name, int num)
	{
		Item j = get(type, name);
		
		if (j == null)
			return false;
		
		return j.decrement(num) ? writeToFile() : false;
	}
	
	/**
	 * Returns a specific number of stock of an item to the stockroom.
	 * 
	 * Adds the returned stock from an item's stock
	 * and updates the file.
	 * 
	 * @param type				type of item
	 * @param name				name of item
	 * @param num				number of stock
	 * @return					true if the item type and name exists,
	 * 							false otherwise
	 */
	public boolean returnStock(int type, int name, int num)
	{
		Item j = get(type, name);
		
		if (j == null)
			return false;
		
		return j.increment(num) ? writeToFile() : false;
	}
	
	public Item get(int type, int name)
	{
		Item temp = new Item(type, name);	// dummy stock
		
		for (Item j : stockroom)
			if (temp.compareTo(j) == 0)
				return j;
		
		return null;
	}
	
	private boolean writeToFile()
	{
		try
		{
			FileWriter itemDB = new FileWriter(db);			
			
			for (Item j : stockroom)
				itemDB.write(toString(j));
			
			itemDB.close();
		}
		catch (Exception e)
		{
			return false;
		}
		
		return true;
	}
	
	private String toString(Item s)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append(s.type());
		sb.append("|");
		sb.append(s.name());
		sb.append("|");
		sb.append(s.stock());
		sb.append('|');
		sb.append(s.price());
		sb.append('|');
		sb.append(s.description());
		sb.append('|');
		sb.append(s.rarity());
		sb.append("\n");
		
		return sb.toString();
	}
	
	public int size()
	{
		return stockroom.size();
	}
}
