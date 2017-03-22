package cards;

/**
 * A class to represent the type and ranking of a hand
 * @author Chris Bentley
 */
public class HandValue {
	
	public static final int HIGH_CARD = 1;
	public static final int ONE_PAIR = 2;
	public static final int TWO_PAIR = 3;
	public static final int THREE_OF_A_KIND = 4;
	public static final int STRAIGHT = 5;
	public static final int FLUSH = 6;
	public static final int FULL_HOUSE = 7;
	public static final int FOUR_OF_A_KIND = 8;
	public static final int STRAIGHT_FLUSH = 9;
	public static final int ROYAL_FLUSH = 10;
	
	private int handType;	
	private int[] rank = new int[5];
	
	public HandValue(){}
	
	/**
	 * Creates a new HandValue
	 * @param handType The type of hand
	 * @param rank The ordered cards of a hand
	 */
	public HandValue(int handType, int[] rank)
	{
		this.handType = handType;
		this.rank = rank;
	}

	/**
	 * Gets the handType
	 * @return the handType
	 */
	public int getHandType() {
		return handType;
	}

	/**
	 * Sets the handType
	 * @param handType the handType to set
	 */
	public void setHandType( int handType ) {
		this.handType = handType;
	}

	/**
	 * Gets the rank
	 * @return the rank
	 */
	public int[] getRank() {
		return rank;
	}

	/**
	 * Sets the rank
	 * @param rank the rank to set
	 */
	public void setRank( int[] rank ) {
		this.rank = rank;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		String valueStr = "";
		
		Card highCard, highPair, lowPair;
		switch ( handType ) {
			case ROYAL_FLUSH:
				valueStr = "Royal Flush";
				break;
			case STRAIGHT_FLUSH:
				highCard = new Card(rank[0]);
				valueStr = "Straight Flush, "+highCard.getRank() + " high";
				break;
			case FOUR_OF_A_KIND:
				highCard = new Card(rank[0]);
				valueStr = "Four "+ highCard.getRankPlural();
				break;
			case FULL_HOUSE:
				highPair = new Card(rank[0]);
				lowPair = new Card(rank[3]);
				valueStr = "Full house.  "+highPair.getRankPlural()+" over "+lowPair.getRankPlural();
				break;
			case FLUSH:
				highPair = new Card(rank[0]);
				valueStr = "Flush. "+highPair.getRank()+" high.";
				break;
			case STRAIGHT:
				highCard = new Card(rank[0]);
				valueStr = "Straight. "+highCard.getRank()+" high.";
				break;
			case THREE_OF_A_KIND:
				highCard = new Card(rank[0]);
				valueStr = "Three "+ highCard.getRankPlural();
				break;
			case TWO_PAIR:
				highPair = new Card(rank[0]);
				lowPair = new Card(rank[2]);
				valueStr = "Two pair.  "+highPair.getRankPlural()+" over "+lowPair.getRankPlural();
				break;
			case ONE_PAIR:
				highPair = new Card(rank[0]);
				lowPair = new Card(rank[2]);
				valueStr = "Pair of "+highPair.getRankPlural()+", "+lowPair.getRank()+" kicker.";
				break;
			case HIGH_CARD:
				highCard = new Card(rank[0]);
				valueStr = highCard.getRank()+" high.";
				break;
			default:
				valueStr = "Unknown hand";
		}
		
		return valueStr;
	}
}
