package games.letitride;

import java.util.HashMap;

import cards.HandValue;

/**
 * This class maps the payout multiplier to the hand type
 * @author Chris Bentley
 */
public class Payout {
	
	// The payout multipliers for a full hand
	static HashMap<Integer, Integer> MAIN_BET_PAYOUT = new HashMap<Integer, Integer>();
	
	// The payout multipliers for a 3 card hand
	static HashMap<Integer, Integer> THREE_CARD_BET_PAYOUT = new HashMap<Integer, Integer>();
	
	static
	{
		MAIN_BET_PAYOUT.put(HandValue.ROYAL_FLUSH, 1000);
		MAIN_BET_PAYOUT.put(HandValue.STRAIGHT_FLUSH, 200);
		MAIN_BET_PAYOUT.put(HandValue.FOUR_OF_A_KIND, 50);
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
	
	/**
	 * Determine the payout multiplier for the hand type
	 * @param handType The type of hand.
	 * @param fullHand Flag indicating if the hand is a full, 5 card hand.
	 * @return
	 */
	public static int getPayout(int handType, boolean fullHand )
	{
		int payout = 0;
		
		if (fullHand) {
			Integer mainPayout = MAIN_BET_PAYOUT.get(handType);
			payout = (mainPayout == null ? 0 : mainPayout);
		} else {
			Integer sidePayout = THREE_CARD_BET_PAYOUT.get( handType );
			payout = (sidePayout == null ? 0 : sidePayout);
		}
		return payout;
	}

}
