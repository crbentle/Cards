package cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * This class represents a poker hand
 * @author Chris Bentley
 */
public class Hand {
	
	private ArrayList<Card> hand;
	private HandValue value;
	
	/**
	 * Create a new empty hand
	 */
	public Hand()
	{
		this.hand = new ArrayList<Card>();
	}
	
	public Hand clone( ) {
		Hand newHand = new Hand();
		newHand.addCards( getHand() );
		return newHand;
	}
	
	/**
	 * Adds a Card to the hand
	 * @param card The Card to add
	 */
	public void addCard(Card card)
	{
		hand.add(card);
	}
	
	/**
	 * Adds a list of Cards to the hand
	 * @param cards The list of Cards to add
	 */
	public void addCards(List<Card> cards)
	{
		hand.addAll(cards);
	}
	
	/**
	 * Returns the hand
	 * @return The hand
	 */
	public ArrayList<Card> getHand()
	{
		return hand;
	}
	
	/**
	 * Generate the value of this hand
	 */
	public void generateHandValue() {
		// Sort the cards, placing the highest ranked cards first
		Collections.sort( hand, new CardComparator() );

		boolean isFlush = isFlush();
		boolean isStraight = isStraight();
		HashMap<Integer, Integer> pairs = getPairs();

		if ( isFlush && isStraight ) {
			int handType = HandValue.STRAIGHT_FLUSH;
			// If the second highest card is a King we know this is a royal flush
			// We can't check for an Ace because A,2,3,4,5 would also list the Ace as the highest card
			if ( hand.get( 1 ).getRankValue() == Card.KING ) {
				handType = HandValue.ROYAL_FLUSH;
			}

			value = new HandValue( handType, getHandRank( handType ) );
		} else if ( isFlush ) {
			value = new HandValue( HandValue.FLUSH, getHandRank( HandValue.FLUSH ) );
		} else if ( isStraight ) {
			value = new HandValue( HandValue.STRAIGHT, getHandRank( HandValue.STRAIGHT ) );
		} else if ( pairs.isEmpty() ) {
			value = new HandValue( HandValue.HIGH_CARD, getHandRank( HandValue.HIGH_CARD ) );
		} else {
			// One pair, three of a kind, or four of a kind
			if ( pairs.keySet().size() == 1 ) {
				sortSingleMatch( pairs );
				if ( pairs.values().contains( 4 ) ) {
					value = new HandValue( HandValue.FOUR_OF_A_KIND, getHandRank( HandValue.FOUR_OF_A_KIND ) );
				} else if ( pairs.values().contains( 3 ) ) {
					value = new HandValue( HandValue.THREE_OF_A_KIND, getHandRank( HandValue.THREE_OF_A_KIND ) );
				} else {
					value = new HandValue( HandValue.ONE_PAIR, getHandRank( HandValue.ONE_PAIR ) );
				}
			}
			// Two pair or full house
			else {
				sortDoubleMatch( pairs );
				// Full house
				if ( pairs.values().contains( 3 ) ) {
					value = new HandValue( HandValue.FULL_HOUSE, getHandRank( HandValue.FULL_HOUSE ) );
				}
				// Two pair
				else {
					value = new HandValue( HandValue.TWO_PAIR, getHandRank( HandValue.TWO_PAIR ) );
				}
			}
		}
	}
	
	/**
	 * Get a String representation of the hand
	 * @return The hand
	 */
	public void printHand()
	{
		System.out.println( this );
	}
	
	
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
	 */
	public HandValue getValue()
	{
		return value;
	}
	
	/**
	 * Determine if this hand is a flush
	 * @return True if the hand is a flush
	 */
	protected boolean isFlush() {
		for ( int i = 0; i < hand.size() - 1; i++ ) {
			if ( hand.get( i ).getSuitValue() != hand.get( i + 1 ).getSuitValue() ) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Determine if this hand is a straight.
	 * If the first card is an Ace and the next card is not a King
	 *  check that the last card is a 2. If it is, continue comparing the next cards.
	 * @return True if the hand is a straight
	 */
	protected boolean isStraight() {
		// A,5,4,3,2
		// A,4,3,2
		for ( int i = 0; i < hand.size() - 1; i++ ) {
			if ( hand.get( i ).getRankValue() - 1 != hand.get( i + 1 ).getRankValue() ) {
				if ( hand.get( i ).getRankValue() == Card.ACE
						&& hand.get( hand.size() - 1 ).getRankValue() == Card.TWO ) {
					continue;
				}
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Gets a Map containing the pairs in this hand.
	 * The Map values will be the number of cards that match the key.
	 * @return A Map containing the pairs
	 */
	protected HashMap<Integer, Integer> getPairs() {
		HashMap<Integer, Integer> pairMap = new HashMap<Integer, Integer>();
		int sameCards = 1;
		// loop through all of the cards, looking for matches
		for ( int i = 0; i < hand.size() - 1; i++ ) {
			int card = hand.get( i ).getRankValue();

			// Check if any of the remaining cards are a match to the current card
			for ( int j = i + 1; j < hand.size(); j++ ) {
				// We found a match
				if ( card == hand.get( j ).getRankValue() ) {
					if ( pairMap.containsKey( card ) ) {
						sameCards = pairMap.get( card );
					}
					sameCards++;
					// increment 'i' so we don't look for matches for this card rank again
					i++;
					pairMap.put( card, sameCards );
				} else {
					sameCards = 1;
					break;
				}
			}
		}

		return pairMap;
	}
	
	/**
	 * Get the high-to-low order of the hand.
	 * @param handType The type of this hand
	 * @return An array sorted with the highest card first
	 */
	protected int[] getHandRank( int handType ) {
		int[] handRank = new int[hand.size()];

		if ( handType == HandValue.STRAIGHT || handType == HandValue.STRAIGHT_FLUSH ) {
			// The straight contains an ACE and is not 10-Ace
			// Move the Ace to the end of the hand (5 is now high)
			if ( hand.get( 0 ).getRankValue() == Card.ACE && hand.get( 1 ).getRankValue() != Card.KING ) {
				Card temp = hand.get( 0 );
				hand.remove( 0 );
				hand.add( temp );
			}
		}

		for ( int i = 0; i < hand.size(); i++ ) {
			handRank[i] = hand.get( i ).getRankValue();
		}

		return handRank;
	}
	
	/**
	 * Sort a hand, moving the paired cards to the top
	 * @param pairs The Map of pairs in this hand
	 */
	protected void sortSingleMatch( HashMap<Integer, Integer> pairs ) {
		ArrayList<Card> nonPairedCards = new ArrayList<Card>();

		Set<Integer> keySet = pairs.keySet();
		for ( Card card : hand ) {
			if ( !keySet.contains( card.getRankValue() ) ) {
				nonPairedCards.add( card );
			}
		}

		// Remove the unpaired cards
		hand.removeAll( nonPairedCards );

		// Sort the unpaired cards
		Collections.sort( nonPairedCards, new CardComparator() );

		// Add the unpaired cards to the end of the hand
		hand.addAll( nonPairedCards );
	}
	
	/**
	 * Sort a hand that contains two sets of matching cards (two pair or full house)
	 * @param pairs The Map of pairs in this hand
	 */
	protected void sortDoubleMatch( HashMap<Integer, Integer> pairs ) {
		ArrayList<Card> firstPair = new ArrayList<Card>();
		ArrayList<Card> secondPair = new ArrayList<Card>();
		ArrayList<Card> nonPairedCards = new ArrayList<Card>();

		boolean fullHouse = false;

		Set<Integer> keySet = pairs.keySet();
		for ( Card card : hand ) {
			if ( !keySet.contains( card.getRankValue() ) ) {
				nonPairedCards.add( card );
			} else if ( pairs.get( card.getRankValue() ) == 3 ) {
				// If a card has 3 matches we know it will be the highest rank, regardless of the face value
				firstPair.add( card );
				fullHouse = true;
			} else if( firstPair.isEmpty() && pairs.values().contains( 3 ) ) {
				// We have a full house and the highest card is a 2 pair
				secondPair.add( card );
			} else {
				if ( firstPair.isEmpty() ) {
					firstPair.add( card );
				} else {
					if ( firstPair.get( 0 ).getRankValue() == card.getRankValue() ) {
						firstPair.add( card );
					} else {
						secondPair.add( card );
					}
				}
			}
		}
		hand.clear();

		if ( fullHouse ) {
			hand.addAll( firstPair );
			hand.addAll( secondPair );
			hand.addAll( nonPairedCards );
		} else {
			if ( firstPair.get( 0 ).getRankValue() > secondPair.get( 0 ).getRankValue() ) {
				hand.addAll( firstPair );
				hand.addAll( secondPair );
				hand.addAll( nonPairedCards );
			} else {
				hand.addAll( secondPair );
				hand.addAll( firstPair );
				hand.addAll( nonPairedCards );
			}
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String handStr = "";
		boolean firstCard = true;
		generateHandValue();
		for(Card card : hand)
		{
			if( !firstCard )
			{
				handStr += ", ";
			}
			handStr += card.getTruncatedString();
			firstCard = false;
		}
		return handStr;
	}

	public static void main(String[] args)
	{
		Hand hand = new Hand();
		hand.addCard( new Card(12) );
		hand.addCard( new Card(11) );
		hand.addCard( new Card(10) );
		hand.addCard( new Card(9) );
		hand.addCard( new Card(8) );
		
		System.out.println( hand );
		
		System.out.println(hand.getValue());
	}
}

/**
 * An inner class comparator to use for card sorting
 * @author Chris Bentley
 */
class CardComparator implements Comparator<Card> {
    @Override
    public int compare(Card o1, Card o2) {
    	if( o1.getRankValue() > o2.getRankValue() )
    		return -1;
    	else if (o1.getRankValue() != o2.getRankValue() )
    		return 1;
    	
    	return 0;
    }
}

