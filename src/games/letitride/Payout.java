package games.letitride;

import java.util.HashMap;

import cards.HandValue;

public class Payout {
	
	static HashMap<Integer, Integer> MAIN_BET_PAYOUT = new HashMap<Integer, Integer>();
	static HashMap<Integer, Integer> THREE_CARD_BET_PAYOUT = new HashMap<Integer, Integer>();
	
	static
	{
		MAIN_BET_PAYOUT.put(HandValue.ROYAL_FLUSH, 1000);
		MAIN_BET_PAYOUT.put(HandValue.STRAIGHT_FLUSH, 200);
		MAIN_BET_PAYOUT.put(HandValue.FOUR_OF_A_KIND, 1);
		MAIN_BET_PAYOUT.put(HandValue.FULL_HOUSE, 11);
		MAIN_BET_PAYOUT.put(HandValue.FLUSH, 8);
		MAIN_BET_PAYOUT.put(HandValue.STRAIGHT, 5);
		MAIN_BET_PAYOUT.put(HandValue.THREE_OF_A_KIND, 3);
		MAIN_BET_PAYOUT.put(HandValue.TWO_PAIR, 2);
		MAIN_BET_PAYOUT.put(HandValue.ONE_PAIR, 1);
		
		THREE_CARD_BET_PAYOUT.put(HandValue.ROYAL_FLUSH, 50);
		THREE_CARD_BET_PAYOUT.put(HandValue.STRAIGHT_FLUSH, 40);
		THREE_CARD_BET_PAYOUT.put(HandValue.THREE_OF_A_KIND, 30);
		THREE_CARD_BET_PAYOUT.put(HandValue.STRAIGHT, 6);
		THREE_CARD_BET_PAYOUT.put(HandValue.FLUSH, 3);
		THREE_CARD_BET_PAYOUT.put(HandValue.ONE_PAIR, 1);
	}
	
	public static int getPayout(int handType, boolean fullHand )
	{
		//System.out.println("handType = "+handType+", fullHand = "+fullHand);
		
		if(!MAIN_BET_PAYOUT.keySet().contains(handType))
		{
			//System.out.println("No payout.");
			return 0;
		}
		
		if( fullHand )
		{
			//System.out.println("Full payout of "+MAIN_BET_PAYOUT.get(handType));
			return MAIN_BET_PAYOUT.get(handType);
		}
		
		//System.out.println("3 card payout of "+THREE_CARD_BET_PAYOUT.get(handType));
		return THREE_CARD_BET_PAYOUT.get( handType );
	}

}
