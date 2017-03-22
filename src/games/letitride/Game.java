package games.letitride;

import player.Player;
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
	
	private Deck deck = new Deck();
	private Player player = null;
	
	public Game( Player player ){
		this.player = player;
	}
	
	/**
	 * Simulate a game of Let It Ride
	 * The game flows as follows:
	 * <ul>
	 * 	<li>Deal 3 cards</li>
	 * 	<li>Determine if the 3 cards earned a payout</li>
	 * 	<li>Choose to take back a bet or let it ride</li>
	 * 	<li>Deal 1 card</li>
	 * 	<li>Choose to take back a bet or let it ride</li>
	 * 	<li>Deal 1 card</li>
	 * 	<li>Determine if the full hand earned a payout</li>
	 *	<li>Add or subtract money from the player</li>
	 * </ul>
	 */
	public void Play()
	{
		deck.shuffle();
		
		int bet = player.getBet() * 3;
		
		Hand hand = new Hand();
		
		dealCards( hand, 3, false );
		
		handleSideBet( hand );

		// For the 3 card hand any pair is a winner. For a full hand only 10s or better win
		adjustHandValueForTensOrBetter( hand );
		
		// Let it ride?
		// Pull back a bet if we aren't letting it ride
		if ( !letItRide( hand.getValue(), 3 ) ) {
			bet -= player.getBet();
		}
		
		dealCards( hand, 1, true );
		
		// Let it ride?
		// Pull back a bet if we aren't letting it ride
		if ( !letItRide( hand.getValue(), 4 ) ) {
			bet -= player.getBet();
		}
		
		dealCards( hand, 1, true );
		
		processPayout( bet, hand, true );
	}
	
	/**
	 * Deal cards and generate the new hand value.
	 * For the initial 3 card hand we cannot adjust the hand for 10s or better
	 *  because we need to check the 3 card side bet first.
	 * @param hand The hand to deal to
	 * @param numCards The number of cards to deal
	 * @param fullHand Flag indicating if this deal should adjust for 10s or better
	 */
	private void dealCards( Hand hand, int numCards, boolean fullHand ) {
		hand.addCards( deck.deal( numCards ) );
		hand.GenerateHandValue();

		if ( fullHand ) {
			adjustHandValueForTensOrBetter( hand );
		}
	}

	/**
	 * Determine if the player made a side bet and if the initial 3 card hand won
	 * @param hand The initial 3 card hand.
	 */
	private void handleSideBet( Hand hand ) {
		if ( player.getSideBet() > 0 ) {
			processPayout( player.getSideBet(), hand, false );
		}
	}
	
	/**
	 * Determine if the hand won and add or subtract money from the player accordingly.
	 * @param bet The amount bet
	 * @param hand The hand
	 * @param fullHand Flag indicating if this was a full hand or a 3 card hand
	 */
	private void processPayout( int bet, Hand hand, boolean fullHand ) {
		int payoutMultiplyer = Payout.getPayout( hand.getValue().getHandType(), fullHand );

		int payout = ( payoutMultiplyer * bet );
		if ( payout > 0 ) {
			player.addMoney( payout );
			if ( payout > player.getBiggestPayout() ) {
				player.setBiggestPayout( payout );
				player.setBiggestHand( hand );
			}
		} else {
			player.subtractMoney( bet );
		}
	}
	
	/**
	 * If the hand has a single pair less than 10s update the hand type to HIGH_CARD.
	 * Any pair on the first 3 cards will payout the side bet.
	 * Only pairs of 10s or better will payout for the full hand.
	 * @param hand The current hand
	 */
	private void adjustHandValueForTensOrBetter( Hand hand )
	{	
		if( hand.getValue().getHandType() == HandValue.ONE_PAIR && ( hand.getValue().getRank()[0] < Card.TEN ) ) {
			hand.getValue().setHandType( HandValue.HIGH_CARD );
		}
	}
	
	/**
	 * Determine if the player should let the bet ride or pull back a bet.
	 * 
	 * With three cards you should only "let it ride" if you have:
	 * <ul>
	 * 	<li>Any paying hand (tens or better, three of a kind)</li>
	 * 	<li>Any three to a royal flush</li>
	 * 	<li>Three suited cards in a row except 2-3-4, and ace-2-3</li>
	 * 	<li>Three to a straight flush, spread 4, with at least one high card (ten or greater)</li>
	 * 	<li>Three to a straight flush, spread 5, with at least two high cards</li>
	 * </ul>
	 * 
	 * With four cards you should only "let it ride" if you have:
	 * <ul>
	 * 	<li>Any paying hand (tens or better, two pair, three of a kind)</li>
	 * 	<li>Any four cards of the same suit</li>
	 * 	<li>Any four to an outside straight with at least one high card</li>
	 * 	<li>Any four to an outside straight with no high cards (zero house edge)</li>
	 * 	<li>Any four to an inside straight with 4 high cards (zero house edge)</li>
	 * </ul>
	 * 
	 * @param handValue The value of the hand
	 * @param handSize The number of cards currently in the hand
	 * @return A flag indicating if the player should let the bet ride
	 */
	public static boolean letItRide( HandValue handValue, int handSize ) {
		// We have a paying hand
		if ( handValue.getHandType() == HandValue.ONE_PAIR
		  || handValue.getHandType() == HandValue.THREE_OF_A_KIND ) {
			return true;
		}

		if ( handSize == 3 ) {
			// Any three to a royal flush
			if ( handValue.getHandType() == HandValue.FLUSH
				|| handValue.getHandType() == HandValue.STRAIGHT_FLUSH
				|| handValue.getHandType() == HandValue.ROYAL_FLUSH ) {
				boolean allFaceCards = true;
				for ( int i = 0; i < handValue.getRank().length; i++ ) {
					if ( handValue.getRank()[i] < Card.TEN ) {
						allFaceCards = false;
						break;
					}
				}
				if ( allFaceCards ) {
					return true;
				}
			}

			// Three suited cards in a row except 2-3-4, and ace-2-3
			if ( handValue.getHandType() == HandValue.STRAIGHT_FLUSH ) {
				if ( handValue.getRank()[0] > 2 )
					return true;
				else
					return false;
			}

			// Three to a straight flush, spread 4, with at least one high card (ten or greater)
			// Three to a straight flush, spread 5, with at least two high cards
			if ( handValue.getHandType() == HandValue.FLUSH ) {
				int highCard = handValue.getRank()[0];
				int lowCard = handValue.getRank()[handValue.getRank().length - 1];

				// 4 spread = 5,6,8 - 3,5,6
				// Three to a straight flush, spread 4, with at least one high card (ten or greater)
				if ( highCard - lowCard == 3 && highCard >= Card.TEN ) {
					return true;
				}

				// 5 spread = 5,7,9 - 3,4,7
				// Three to a straight flush, spread 5, with at least two high cards
				if ( highCard - lowCard == 4 && highCard >= Card.TEN ) {
					if ( handValue.getRank()[1] > Card.TEN ) {
						return true;
					}
				}
			}
			return false;
		} else {
			// Any paying hand (tens or better, two pair, three of a kind). 
			// We already tested for 1 pair and 3 of a kind
			if ( handValue.getHandType() == HandValue.TWO_PAIR )
				return true;

			// Any four cards of the same suit
			if ( handValue.getHandType() == HandValue.FLUSH )
				return true;

			// Any four to an outside straight
			if ( handValue.getHandType() == HandValue.STRAIGHT ) {
				// J,Q,K,A is not an outside straight
				if ( handValue.getRank()[0] == Card.ACE )
					return false;
				// A,2,3,4 is not an outside straight
				if ( handValue.getRank()[0] == Card.FOUR )
					return false;

				return true;
			}

			// Any four to an inside straight with 4 high cards (zero house edge)
			// 10,J,K,A - J,Q,K,A
			if ( handValue.getHandType() == HandValue.HIGH_CARD ) {
				for ( int i = 0; i < handValue.getRank().length; i++ ) {
					if ( handValue.getRank()[i] < Card.TEN ) {
						return false;
					}
				}
				return true;
			}
		}

		return false;
	}
	
	/**
	 * Play the game 100 times and print the final result.
	 * @param args
	 */
	public static void main(String[] args)
	{
		Player player = new Player( 15, 15 );
		
		Game game = new Game( player );
		for(int i=0; i<100; i++){
			game.Play();
		}
		System.out.println(player.getResult());
	}

}
