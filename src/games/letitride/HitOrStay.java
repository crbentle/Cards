package games.letitride;

import cards.Card;
import cards.HandValue;

public class HitOrStay {

	/*
With three cards you should only "let it ride" if you have:

-Any paying hand (tens or better, three of a kind)
-Any three to a royal flush
-Three suited cards in a row except 2-3-4, and ace-2-3
-Three to a straight flush, spread 4, with at least one high card (ten or greater)
-Three to a straight flush, spread 5, with at least two high cards

With four cards you should only "let it ride" if you have:

Any paying hand (tens or better, two pair, three of a kind)
Any four cards of the same suit
Any four to an outside straight with at least one high card
Any four to an outside straight with no high cards (zero house edge)
Any four to an inside straight with 4 high cards (zero house edge)
	 */
	
	public static boolean LetItRide( HandValue handValue, int handSize )
	{
		//We have a paying hand
		if(handValue.getHandType() == HandValue.ONE_PAIR || handValue.getHandType() == HandValue.THREE_OF_A_KIND)
			return true;
		
		if(handSize == 3)
		{
			//Any three to a royal flush
			if(handValue.getHandType() == HandValue.FLUSH 
					|| handValue.getHandType() == HandValue.STRAIGHT_FLUSH
					|| handValue.getHandType() == HandValue.ROYAL_FLUSH)
			{
				boolean allFaceCards = true;
				for(int i=0; i<handValue.getRank().length; i++)
				{
					if( handValue.getRank()[i] < Card.TEN )
					{
						allFaceCards = false;
						break;
					}
				}
				if(allFaceCards)
					return true;
			}
			
			//Three suited cards in a row except 2-3-4, and ace-2-3
			if(handValue.getHandType() == HandValue.STRAIGHT_FLUSH )
			{
				if(handValue.getRank()[0] > 2)
					return true;
				else
					return false;
			}
			
			//Three to a straight flush, spread 4, with at least one high card (ten or greater)
			//Three to a straight flush, spread 5, with at least two high cards
			if(handValue.getHandType() == HandValue.FLUSH )
			{
				int highCard = handValue.getRank()[0];
				int lowCard = handValue.getRank()[handValue.getRank().length-1];
				
				//4 spread = 5,6,8 - 3,5,6
				//Three to a straight flush, spread 4, with at least one high card (ten or greater)
				if(highCard - lowCard == 3 && highCard>=Card.TEN)
					return true;
				
				//5 spread = 5,7,9 - 3,4,7
				//Three to a straight flush, spread 5, with at least two high cards
				if(highCard - lowCard == 4 && highCard>=Card.TEN)
				{
					if(handValue.getRank()[1]>Card.TEN)
						return true;
				}
			}
			return false;
		}
		else
		{
			/*
-Any paying hand (tens or better, two pair, three of a kind)
-Any four cards of the same suit
-Any four to an outside straight with at least one high card
-Any four to an outside straight with no high cards (zero house edge)
Any four to an inside straight with 4 high cards (zero house edge)
			 */
			
			//Any paying hand (tens or better, two pair, three of a kind).  We already tested for 1 pair and 3 of a kind
			if(handValue.getHandType() == HandValue.TWO_PAIR)
				return true;
			
			//Any four cards of the same suit
			if(handValue.getHandType() == HandValue.FLUSH)
				return true;
			
			//Any four to an outside straight with no high cards
			if(handValue.getHandType() == HandValue.STRAIGHT)
			{
				//J,Q,K,A is not an outside straight
				if(handValue.getRank()[0]==Card.ACE)
					return false;
				//A,2,3,4 is not an outside straight
				if(handValue.getRank()[0]==Card.FOUR)
					return false;
				
				return true;
			}
			
			//Any four to an inside straight with 4 high cards (zero house edge)
			//10,J,K,A - J,Q,K,A
			if(handValue.getHandType() == HandValue.HIGH_CARD)
			{
				for(int i =0; i<handValue.getRank().length; i++)
				{
					if(handValue.getRank()[i]<Card.TEN)
					{
						return false;
					}
				}
				
				int highCard = handValue.getRank()[0];
				int lowCard = handValue.getRank()[handValue.getRank().length-1];
				
				if(highCard - lowCard == 4)
					return true;
				
				
			}
		}
		
		
		return false;
	}
}
