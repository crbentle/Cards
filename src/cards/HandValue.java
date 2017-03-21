package cards;

public class HandValue {
	
	/*
	 * High card 0-50 (a,k,q,j,9 = 50)
	 * Pair 51-89 (2,2,3,4,5 = 16 , a,a,k,q,j = 54) + 35
	 * Two Pair 14-56
	 * Three of a kind
	 * Straight
	 * Flush
	 * Full House
	 * Four of a kind
	 * Straight flush
	 * Royal Flush
	 * 
	 *     value[0]=1;          //this is the lowest type of hand, so it gets the lowest value
    value[1]=orderedRanks[0];  //the first determining factor is the highest card,
    value[2]=orderedRanks[1];  //then the next highest card,
    value[3]=orderedRanks[2];  //and so on
    value[4]=orderedRanks[3];
    value[5]=orderedRanks[4];
	 */
	
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
	
	public HandValue()
	{		
	}
	
	public HandValue(int handType, int[] rank)
	{
		this.handType = handType;
		this.rank = rank;
	}
	
	public int[] getRank()
	{
		return rank;
	}
	public int getHandType()
	{
		return handType;
	}
	public void setHandType(int handType)
	{
		this.handType = handType;
	}
	public void setRank(int[] rank)
	{
		this.rank = rank;
	}
	
	public String toString()
	{
		String valueStr = "";
		
		if(handType == HandValue.ROYAL_FLUSH)
			return "Royal Flush";
		
		if(handType == HandValue.STRAIGHT_FLUSH)
		{
			Card highCard = new Card(rank[0]);
			return "Straight Flush, "+highCard.getRank() + " high";
		}
		
		if(handType == HandValue.FOUR_OF_A_KIND)
		{
			
			Card highCard = new Card(rank[0]);
			return "Four "+ highCard.getRankPlural();
		}
		
		if(handType == HandValue.FULL_HOUSE)
		{			
			Card highPair = new Card(rank[0]);
			Card lowPair = new Card(rank[3]);
			
			return "Full house.  "+highPair.getRankPlural()+" over "+lowPair.getRankPlural();
		}
		
		if(handType == HandValue.FLUSH)
		{
			Card highPair = new Card(rank[0]);
			return "Flush. "+highPair.getRank()+" high.";
		}
		
		if(handType == HandValue.STRAIGHT)
		{
			Card highPair = new Card(rank[0]);
			return "Straight. "+highPair.getRank()+" high.";
		}
		
		if(handType == HandValue.THREE_OF_A_KIND)
		{
			
			Card highCard = new Card(rank[0]);
			return "Three "+ highCard.getRankPlural();
		}
		
		if(handType == HandValue.TWO_PAIR)
		{			
			Card highPair = new Card(rank[0]);
			Card lowPair = new Card(rank[2]);
			return "Two pair.  "+highPair.getRankPlural()+" over "+lowPair.getRankPlural();
		}
		
		if(handType == HandValue.ONE_PAIR)
		{			
			Card highPair = new Card(rank[0]);
			Card lowPair = new Card(rank[2]);
			return "Pair of "+highPair.getRankPlural()+", "+lowPair.getRank()+" kicker.";
		}
		
		if(handType == HandValue.HIGH_CARD)
		{
			Card highPair = new Card(rank[0]);
			return highPair.getRank()+" high.";
		}
		
		return "Unknown hand";
	}

}
