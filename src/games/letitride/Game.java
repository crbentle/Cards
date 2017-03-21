package games.letitride;

import cards.Card;
import cards.Deck;
import cards.Hand;
import cards.HandValue;

/**
 * Plays a game of Let It Ride and determines when to hit or stand.
 * Let It Ride is a five card poker variant.
 * The player initially makes 3 equal bets and has a chance to pull back a bet
 *  after the 3rd and 4th cards are dealt.
 * 
 * @author Chris Bentley
 *
 */
public class Game {
	
	//Deal 3
	//Determine 3 card payout
	//hit or stay
	//Deal 1
	//hit or stay
	//Deal 1
	//Determine 5 card payout
	
	static int money = 0;
	static int biggestPayout = 0;
	static Hand biggestHand;
	
	int mainBet = 15;
	int threeCardBet = 15;
	static Deck deck = new Deck();
	
	public Game()
	{
	}
	public int getMoney()
	{
		return money;
	}
	public int getBiggestPayout()
	{
		return biggestPayout;
	}
	
	public void Play()
	{
		deck.shuffle();
		
		int bet = mainBet * 3;
		
		Hand hand = new Hand();
		hand.addCards(deck.deal(3));
		
		hand.GenerateHandValue();
		//hand.printHand();
		
		int threeCardMultiplier = Payout.getPayout(hand.getValue().getHandType(), false);
		
		int sidePayout = ( threeCardMultiplier * threeCardBet );
		if(sidePayout > 0)
		{
			//System.out.println("3 card payout: "+ sidePayout );
			money+=sidePayout;
			if(sidePayout > biggestPayout)
			{
				biggestPayout = sidePayout;
				biggestHand = new Hand();
				for(Card card : hand.getHand())
				{
					biggestHand.addCard(card);
				}
			}
		}
		else
		{
			//System.out.println("3 card payout: "+ (0-threeCardBet) );
			money-=threeCardBet;
		}
		
		
		hand.getValue().setHandType( adjustHandValueForTensOrBetter( hand.getValue() ) );
		
		//Let it ride?
		if(!LetItRide(hand.getValue(), 3))
		{
			bet -= mainBet;
			//System.out.println("Pull back.");
		}
		else
		{
			//System.out.println("Let it Ride!");
		}
			
		
		hand.addCards(deck.deal(1));
		hand.GenerateHandValue();
		//hand.printHand();
		
		hand.getValue().setHandType( adjustHandValueForTensOrBetter( hand.getValue() ) );
		
		//Let it ride?
		if(!LetItRide(hand.getValue(), 4))
		{
			bet -= mainBet;
			//System.out.println("Pull back.");
		}
		else
		{
			//System.out.println("Let it Ride!");
		}
		
		
		
		hand.addCards(deck.deal(1));
		
		hand.GenerateHandValue();
		//hand.printHand();
		
		hand.getValue().setHandType( adjustHandValueForTensOrBetter( hand.getValue() ) );
		
		int fullHandMultiplyer = Payout.getPayout(hand.getValue().getHandType(), true);
		
		
		int mainPayout = ( fullHandMultiplyer * bet );
		if(mainPayout > 0)
		{
			//System.out.println("Main payout: "+ ( fullHandMultiplyer * bet ) );
			money+=mainPayout;
			if(mainPayout > biggestPayout)
			{
				biggestPayout = mainPayout;
				biggestHand = hand;
			}
		}
		else
		{
			//System.out.println("Main payout: "+ (0- bet ) );
			money-=bet;
		}
	}
	
	private int adjustHandValueForTensOrBetter(HandValue handValue)
	{
		if( handValue.getHandType() != HandValue.ONE_PAIR )
			return handValue.getHandType();
		
		if( handValue.getRank()[0] < Card.TEN )
			return HandValue.HIGH_CARD;
		
		return handValue.getHandType();
		
	}
	
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
	
	public static void main(String[] args)
	{
		Game game = new Game();
		for(int i=0; i<100; i++){
			game.Play();
		}
		System.out.println("\nFinal: "+game.getMoney());
		System.out.println("Biggest payout: "+game.getBiggestPayout());
		System.out.println("Biggest hand: ");game.biggestHand.printHand();
	}

}
