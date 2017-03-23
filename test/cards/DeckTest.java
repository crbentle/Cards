package cards;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

/**
 * @author Chris
 *
 */
public class DeckTest {
	
	@Test
	public void testDeck() {
		Deck deck = new Deck();
		Card[] cards = deck.getDeck();
		assertEquals( 52, cards.length );
		
		for( int i = 0; i < cards.length; i++ ) {
			assertEquals( i, cards[i].getValue() );
		}
	}
	
	@Test
	public void testShuffle() {
		Deck deck = new Deck();
		Card[] cards = deck.getDeck();
		Set<String> orderedSet = new LinkedHashSet<String>();
		for( Card card : cards ) {
			orderedSet.add( card.getTruncatedString() );
		}
		// Verify all cards in the deck are unique
		assertEquals( 52, orderedSet.size() );
		
		Set<String> shuffledSet = new LinkedHashSet<String>();
		deck.shuffle();
		for( Card card : cards ) {
			shuffledSet.add( card.getTruncatedString() );
		}
		assertEquals( orderedSet, shuffledSet );
		
		List<String> orderedList = new ArrayList<String>( orderedSet );
		List<String> shuffledList = new ArrayList<String>( shuffledSet );
		
		boolean sameOrder = true;
		// verify that the lists are not in the same order
		for( int i = 0; i < orderedList.size(); i++ ){
			if( !orderedList.get( i ).equals( shuffledList.get( i ) ) ) {
				sameOrder = false;
				break;
			}
		}
		assertTrue( !sameOrder );
	}
	
	@Test
	public void testDeal() {
		Deck deck = new Deck();
		deck.deal( 3 );
		deck.deal( 1 );
		assertEquals( 4, deck.getCardsDealt() );
		deck.deal( 1 );
		assertEquals( 5, deck.getCardsDealt() );
		
		deck = new Deck();
		ArrayList<Card> cards = deck.deal( 52 );
		Set<String> uniqueCards = new TreeSet<String>();
		for( Card card : cards ) {
			uniqueCards.add( card.getTruncatedString() );
		}
		// confirm all dealt cards are unique
		assertEquals( 52, uniqueCards.size() );
	}

}
