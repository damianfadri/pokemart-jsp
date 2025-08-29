package mart;

/**
 * A representation of the items for sale.
 * 
 * An item holds properties such as its type of item,
 * the name of the item, its price, and description.
 * 
 * Additional information include a link to its image,
 * echos, and other fuckshits.
 */
public class Item implements Comparable<Item>
{	
	// Item types
	public static final int GENERAL = 0;
	public static final int BALL = 1;
	public static final int HEAL = 2;
	public static final int STATUS = 3;
	
	// General
	public static final int ROPE = 0;
	public static final int REPEL = 1;
	public static final int SUPER_REPEL = 2;
	public static final int MAX_REPEL = 3;
	public static final int REVIVE = 4;
	public static final int MAX_REVIVE = 5;
	
	// Pokeball
	public static final int POKEBALL = 0;
	public static final int GREAT_BALL = 1;
	public static final int ULTRA_BALL = 2;
	public static final int MASTER_BALL = 3;
	
	// Healing items
	public static final int POTION = 0;
	public static final int SUPER_POTION = 1;
	public static final int HYPER_POTION = 2;
	public static final int MAX_POTION = 3;
	public static final int FULL_RESTORE = 4;
	
	// Status items
	public static final int ANTIDOTE = 0;
	public static final int PARALYZE = 1;
	public static final int AWAKENING = 2;
	public static final int BURN_HEAL = 3;
	public static final int ICE_HEAL = 4;
	public static final int FULL_HEAL = 5;
	
	
	
	private int type;
	private int name;
	private int price;
	private int stock;
	
	private String description;
	private String rarity;
	private String thumbURL;
	private String fullURL;
	private String spriteURL;
	
	public Item(int type, int name)
	{
		this.type = type;
		this.name = name;
		
		stock = 0;
		price = 0;
		description = null;
		rarity = null;
		thumbURL = null;
		fullURL = null;
		spriteURL = null;
	}
	
	public Item(int type, int name, int stock, int price, String description, String rarity)
	{
		this.type = type;
		this.name = name;
		this.stock = stock;
		this.price = price;
		this.description = description;
		this.rarity = rarity;
		
		thumbURL = "images/thumb/" + Inventory.getGETName(type, name) + ".png";
		spriteURL = "images/sprite/" + Inventory.getGETName(type, name) + ".png";
		fullURL = "images/full/" + Inventory.getGETName(type, name) + ".png"; 
	}
	
	/**
	 * Returns the name of the item.
	 * @return				name of the item
	 */
	public int name()
	{
		return name;
	}
	
	/**
	 * Returns the type of the item.
	 * @return				type of the item
	 */
	public int type()
	{
		return type;
	}
	
	/**
	 * Returns the price of the item.
	 * @return				price of the item
	 */
	public int price()
	{
		return price;
	}
	
	/**
	 * Returns the stock count of the item
	 * @return				stock count of the item
	 */
	public int stock()
	{
		return stock;
	}
	
	/**
	 * Returns the description of the item.
	 * 
	 * @return				description of the item
	 */
	public String description()
	{
		return description;
	}
	
	public String rarity()
	{
		return rarity;
	}
	
	/**
	 * Returns the image URL of the item.
	 * 
	 * @return				image URL of the item
	 */
	public String fullURL()
	{
		return fullURL;
	}
	
	public String thumbURL()
	{
		return thumbURL;
	}
	
	/**
	 * Returns the sprite image URL of the item.
	 * 
	 * @return				sprite image URL of the item
	 */
	public String spriteURL()
	{
		return spriteURL; 
	}
	
	/**
	 * Subtracts the value of this item's stock with another value.
	 * 
	 * @param num				number to be subtracted
	 * @return					true if the number is positive and 
	 * 							less than or equal to the stock,
	 * 							false otherwise
	 */
	public boolean decrement(int num)
	{
		if (num <= 0 || num > stock)
			return false;
		
		stock -= num;
		return true;
	}
	
	/**
	 * Adds the value of this item's stock with another value.
	 * 
	 * @param num				number to be added
	 * @return					true if the number is positive,
	 * 							false otherwise
	 */
	public boolean increment(int num)
	{
		if (num <= 0)
			return false;
		
		stock += num;
		return true;
	}

	@Override
	public int compareTo(Item o) 
	{
		if (type != o.type)
			return (type - o.type);
		
		return (name - o.name);
	}
}
