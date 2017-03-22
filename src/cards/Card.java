package cards;

/**
 * A helper class to represent the value of a card
 * @author Chris Bentley
 */
public class Card {
	
	final static String[] SUITE = new String[]{"Clubs", "Diamonds", "Hearts", "Spades"};
	final static String[] RANK = new String[]{"Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace"};
	final static String[] TRUNCATED_RANK = new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
	
	public final static int TEN = 8;
	public final static int KING = 11;
	public final static int ACE = 12;
	public final static int TWO = 0;
	public final static int FOUR = 2;
	public final static int FIVE = 3;
	
	private int value;
	
	public Card(int value)
	{
		this.value = value;
	}

	public int getRankValue()
	{
		return value % 13;
	}
	
	public int getSuiteValue()
	{
		return value / 13;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public String toString()
	{
		return RANK[getRankValue()]+" of "+SUITE[getSuiteValue()];
	}
	
	public String getTruncatedString()
	{
		return TRUNCATED_RANK[getRankValue()]+""+SUITE[getSuiteValue()].toLowerCase().charAt(0);
	}
	
	public String getRank()
	{
		return RANK[getRankValue()];
	}
	
	public String getRankPlural()
	{
		String plural = "s";
		if(getRankValue() == 4)
			plural = "es";
		
		return RANK[getRankValue()] + plural;
	}
	
	public static void main(String[] args)
	{
		for ( int i = 0; i < 52; i++ )
		{
			if(i%13 == 0 && i>0) {
				System.out.println("");
			}
			System.out.print(new Card(i).getTruncatedString()+", ");
		}
	}

}
