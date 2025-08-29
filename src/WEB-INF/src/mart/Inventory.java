package mart;

public class Inventory 
{
	private static String[] GETTypes = {"general", "ball", "heal", "status"};
	private static String[][] GETNames = 
		{
			{"rope", "repel", "srepel", "mrepel", "revive"},
			{"pball", "gball", "uball", "mball"},
			{"potion", "spotion", "hpotion", "mpotion", "fullrestore"},
			{"antidote", "paralyze", "awakening", "burnheal", "iceheal", "fullheal"}
		};
	
	private static String[] displayTypes = {"General", "Pok&eacute; balls", "Healing items", "Status-condition items"};
	private static String[][] displayNames =
		{
			{"Escape Rope", "Repel", "Super Repel", "Max Repel", "Revive"},
			{"Pok&eacute;ball", "Great Ball", "Ultra Ball", "Master Ball"},
			{"Potion", "Super Potion", "Hyper Potion", "Max Potion", "Full Restore"},
			{"Antidote", "Paralyze Heal", "Awakening", "Burn Heal", "Ice Heal", "Full Heal"}
		};
	
	public static String[] GETTypes()
	{
		return GETTypes;
	}
	
	public static String[] displayTypes()
	{
		return displayTypes;
	}
	
	public static String[] GETNames(int type)
	{
		return GETNames[type];
	}
	
	public static String[] displayNames(int type)
	{
		return displayNames[type];
	}
	
	public static String getGETType(int type)
	{
		if (type >= GETTypes.length)
			return null;
		
		return GETTypes[type];
	}
	
	public static String getGETName(int type, int name)
	{
		if (type >= GETTypes.length)
			return null;
		
		if (name >= GETNames[type].length)
			return null;
		
		return GETNames[type][name];
	}
	
	public static String getDisplayType(int type)
	{
		if (type >= displayTypes.length)
			return null;
		
		return displayTypes[type];
	}
	
	public static String getDisplayName(int type, int name)
	{
		if (type >= displayTypes.length)
			return null;
		
		if (name >= displayNames[type].length)
			return null;
		
		return displayNames[type][name];
	}
	
	public static int getINTType(String type)
	{
		for (int i = 0; i < GETTypes.length; i++)
			if (GETTypes[i].equals(type) || displayTypes[i].equals(type))
				return i;
		
		return -1;
	}
	
	public static int getINTName(String type, String name)
	{		
		int t;
		if ((t = getINTType(type)) != -1)
			for (int i = 0; i < GETNames[t].length; i++)
				if (GETNames[t][i].equals(name) || displayNames[t][i].equals(name))
					return i;
		
		return -1;
	}
}
