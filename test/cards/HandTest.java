package cards;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

/**
 * @author Chris Bentley
 */
public class HandTest {
	
	@Test
	public void testAddCard() {
		Hand hand = new Hand();
		Card card = new Card(10);
		hand.addCard( card );
		assertTrue( hand.getHand().contains( card ) );
	}
	
	@Test
	public void testAddCards() {
		Hand hand = new Hand();
		ArrayList<Card> cardList = new ArrayList<Card>( Arrays.asList( new Card(10), new Card(25), new Card(40) ) );
		hand.addCards( cardList );
		for( Card card : cardList ) {
			assertTrue( hand.getHand().contains( card ) );
		}
	}
	
	@Test
	public void testClone() {
		Hand hand1 = new Hand();
		Card card1 = new Card(25);
		hand1.addCard( card1 );
		Hand hand2 = hand1.clone();
		assertEquals( hand1.getHand(), hand2.getHand() );
		
		hand1.addCard( new Card(5) );
		assertNotEquals( hand1.getHand(), hand2.getHand() );
	}
	
	@Test
	public void testGenerateHandValue() {

		// Club flush - Kind high
		Hand hand = new Hand();
		hand.addCards( Arrays.asList( new Card(0), new Card(5), new Card(10), new Card(11), new Card(6) ) );
		hand.generateHandValue();
		HandValue value = hand.getValue();
		assertEquals( HandValue.FLUSH, value.getHandType() );
		assertEquals( 11, value.getRank()[0] );

		// Diamond flush - Ten high
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(13), new Card(18), new Card(21), new Card(14), new Card(19) ) );
		hand.generateHandValue();
		value = hand.getValue();
		assertEquals( HandValue.FLUSH, value.getHandType() );
		assertEquals( 8, value.getRank()[0] );

		// Heart flush - Ace high
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(38), new Card(31), new Card(34), new Card(27), new Card(32) ) );
		hand.generateHandValue();
		value = hand.getValue();
		assertEquals( HandValue.FLUSH, value.getHandType() );
		assertEquals( 12, value.getRank()[0] );

		// Spade flush - Ace high
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(51), new Card(44), new Card(47), new Card(40), new Card(45) ) );
		hand.generateHandValue();
		value = hand.getValue();
		assertEquals( HandValue.FLUSH, value.getHandType() );
		assertEquals( 12, value.getRank()[0] );

		// Not a flush
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(12), new Card(14), new Card(0), new Card(5), new Card(7) ) );
		hand.generateHandValue();
		value = hand.getValue();
		assertNotEquals( HandValue.FLUSH, value.getHandType() );
		assertEquals( 12, value.getRank()[0] );

		// Straight - Ace High
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(25), new Card(11), new Card(10), new Card(9), new Card(8) ) );
		hand.generateHandValue();
		value = hand.getValue();
		assertEquals( HandValue.STRAIGHT, value.getHandType() );
		assertEquals( 12, value.getRank()[0] );

		// Straight - 5 High
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(25), new Card(3), new Card(2), new Card(1), new Card(0) ) );
		hand.generateHandValue();
		value = hand.getValue();
		assertEquals( HandValue.STRAIGHT, value.getHandType() );
		assertEquals( 3, value.getRank()[0] );

		// Straight - 9 High
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(20), new Card(6), new Card(5), new Card(4), new Card(3) ) );
		hand.generateHandValue();
		value = hand.getValue();
		assertEquals( HandValue.STRAIGHT, value.getHandType() );
		assertEquals( 7, value.getRank()[0] );

		// Not a straight - Consecutive cards spanning 2 suits
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(13), new Card(12), new Card(11), new Card(10), new Card(9) ) );
		hand.generateHandValue();
		value = hand.getValue();
		assertNotEquals( HandValue.STRAIGHT, value.getHandType() );
		assertEquals( 12, value.getRank()[0] );

		// Straight Flush - 5 High
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(12), new Card(3), new Card(2), new Card(1), new Card(0) ) );
		hand.generateHandValue();
		value = hand.getValue();
		assertEquals( HandValue.STRAIGHT_FLUSH, value.getHandType() );
		assertEquals( 3, value.getRank()[0] );

		// Straight Flush - 10 High
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(21), new Card(20), new Card(19), new Card(18), new Card(17) ) );
		hand.generateHandValue();
		value = hand.getValue();
		assertEquals( HandValue.STRAIGHT_FLUSH, value.getHandType() );
		assertEquals( 8, value.getRank()[0] );
		
		// Royal Flush
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(12), new Card(11), new Card(10), new Card(9), new Card(8) ) );
		hand.generateHandValue();
		value = hand.getValue();
		assertEquals( HandValue.ROYAL_FLUSH, value.getHandType() );
		assertEquals( 12, value.getRank()[0] );
		
		// High Card - Ace high
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(12), new Card(11), new Card(10), new Card(9), new Card(17) ) );
		hand.generateHandValue();
		value = hand.getValue();
		assertEquals( HandValue.HIGH_CARD, value.getHandType() );
		assertEquals( 12, value.getRank()[0] );
		
		// High Card - seven high
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(18), new Card(3), new Card(2), new Card(1), new Card(0) ) );
		hand.generateHandValue();
		value = hand.getValue();
		assertEquals( HandValue.HIGH_CARD, value.getHandType() );
		assertEquals( 5, value.getRank()[0] );
		
		// One Pair - low card pair
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(10), new Card(9), new Card(8), new Card(2), new Card(15) ) );
		hand.generateHandValue();
		value = hand.getValue();
		assertEquals( HandValue.ONE_PAIR, value.getHandType() );
		assertEquals( 2, value.getRank()[0] );
		assertEquals( 2, value.getRank()[1] );
		assertEquals( 10, value.getRank()[2] );
		
		// One Pair - high card pair
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(10), new Card(9), new Card(8), new Card(2), new Card(23) ) );
		hand.generateHandValue();
		value = hand.getValue();
		assertEquals( HandValue.ONE_PAIR, value.getHandType() );
		assertEquals( 10, value.getRank()[0] );
		assertEquals( 10, value.getRank()[1] );
		assertEquals( 9, value.getRank()[2] );
		
		// Two Pair - unpaired high card 
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(10), new Card(9), new Card(22), new Card(2), new Card(15) ) );
		hand.generateHandValue();
		value = hand.getValue();
		assertEquals( HandValue.TWO_PAIR, value.getHandType() );
		assertEquals( 9, value.getRank()[0] );
		assertEquals( 9, value.getRank()[1] );
		assertEquals( 2, value.getRank()[2] );
		assertEquals( 2, value.getRank()[3] );
		assertEquals( 10, value.getRank()[4] );
		
		// Two Pair - unpaired middle card 
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(5), new Card(9), new Card(22), new Card(2), new Card(15) ) );
		hand.generateHandValue();
		value = hand.getValue();
		assertEquals( HandValue.TWO_PAIR, value.getHandType() );
		assertEquals( 9, value.getRank()[0] );
		assertEquals( 9, value.getRank()[1] );
		assertEquals( 2, value.getRank()[2] );
		assertEquals( 2, value.getRank()[3] );
		assertEquals( 5, value.getRank()[4] );
		
		// Two Pair - unpaired low card 
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(0), new Card(9), new Card(2), new Card(22), new Card(15) ) );
		hand.generateHandValue();
		value = hand.getValue();
		assertEquals( HandValue.TWO_PAIR, value.getHandType() );
		assertEquals( 9, value.getRank()[0] );
		assertEquals( 9, value.getRank()[1] );
		assertEquals( 2, value.getRank()[2] );
		assertEquals( 2, value.getRank()[3] );
		assertEquals( 0, value.getRank()[4] );
		
		// Three of a kind - high card pair
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(25), new Card(38), new Card(12), new Card(2), new Card(3) ) );
		hand.generateHandValue();
		value = hand.getValue();
		assertEquals( HandValue.THREE_OF_A_KIND, value.getHandType() );
		assertEquals( 12, value.getRank()[0] );
		assertEquals( 12, value.getRank()[1] );
		assertEquals( 12, value.getRank()[2] );
		assertEquals( 3, value.getRank()[3] );
		assertEquals( 2, value.getRank()[4] );
		
		// Three of a kind - middle card pair
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(4), new Card(17), new Card(30), new Card(2), new Card(5) ) );
		hand.generateHandValue();
		value = hand.getValue();
		assertEquals( HandValue.THREE_OF_A_KIND, value.getHandType() );
		assertEquals( 4, value.getRank()[0] );
		assertEquals( 4, value.getRank()[1] );
		assertEquals( 4, value.getRank()[2] );
		assertEquals( 5, value.getRank()[3] );
		assertEquals( 2, value.getRank()[4] );
		
		// Three of a kind - low card pair
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(12), new Card(11), new Card(2), new Card(15), new Card(28) ) );
		hand.generateHandValue();
		value = hand.getValue();
		assertEquals( HandValue.THREE_OF_A_KIND, value.getHandType() );
		assertEquals( 2, value.getRank()[0] );
		assertEquals( 2, value.getRank()[1] );
		assertEquals( 2, value.getRank()[2] );
		assertEquals( 12, value.getRank()[3] );
		assertEquals( 11, value.getRank()[4] );
		
		// Four of a kind - high card pair
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(12), new Card(2), new Card(25), new Card(38), new Card(51) ) );
		hand.generateHandValue();
		value = hand.getValue();
		assertEquals( HandValue.FOUR_OF_A_KIND, value.getHandType() );
		assertEquals( 12, value.getRank()[0] );
		assertEquals( 12, value.getRank()[1] );
		assertEquals( 12, value.getRank()[2] );
		assertEquals( 12, value.getRank()[3] );
		assertEquals( 2, value.getRank()[4] );
		
		// Four of a kind - low card pair
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(12), new Card(2), new Card(15), new Card(28), new Card(41) ) );
		hand.generateHandValue();
		value = hand.getValue();
		assertEquals( HandValue.FOUR_OF_A_KIND, value.getHandType() );
		assertEquals( 2, value.getRank()[0] );
		assertEquals( 2, value.getRank()[1] );
		assertEquals( 2, value.getRank()[2] );
		assertEquals( 2, value.getRank()[3] );
		assertEquals( 12, value.getRank()[4] );
		
		// Full house - high card trips
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(10), new Card(2), new Card(23), new Card(15), new Card(36) ) );
		hand.generateHandValue();
		value = hand.getValue();
		assertEquals( HandValue.FULL_HOUSE, value.getHandType() );
		assertEquals( 10, value.getRank()[0] );
		assertEquals( 10, value.getRank()[1] );
		assertEquals( 10, value.getRank()[2] );
		assertEquals( 2, value.getRank()[3] );
		assertEquals( 2, value.getRank()[4] );
		
		// Full house - low card trips
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(28), new Card(2), new Card(23), new Card(15), new Card(36) ) );
		hand.generateHandValue();
		value = hand.getValue();
		assertEquals( HandValue.FULL_HOUSE, value.getHandType() );
		assertEquals( 2, value.getRank()[0] );
		assertEquals( 2, value.getRank()[1] );
		assertEquals( 2, value.getRank()[2] );
		assertEquals( 10, value.getRank()[3] );
		assertEquals( 10, value.getRank()[4] );
	}
	
	@Test
	public void testIsFlush() {
		Hand hand = new Hand();
		hand.addCards( Arrays.asList( new Card(0), new Card(5), new Card(10), new Card(12) ) );
		assertTrue( hand.isFlush() );
		
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(13), new Card(5), new Card(10), new Card(12) ) );
		assertFalse( hand.isFlush() );
	}
	
	@Test
	public void testIsStraight() {
		Hand hand = new Hand();
		hand.addCards( Arrays.asList( new Card(5), new Card(4), new Card(3), new Card(2), new Card(1) ) );
		assertTrue( hand.isStraight() );
		
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(12), new Card(3), new Card(2), new Card(1), new Card(0) ) );
		assertTrue( hand.isStraight() );
		
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(25), new Card(3), new Card(2), new Card(1), new Card(0) ) );
		assertTrue( hand.isStraight() );
		
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(25), new Card(11), new Card(10), new Card(9), new Card(8) ) );
		assertTrue( hand.isStraight() );
		
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(13), new Card(12), new Card(11), new Card(10), new Card(9) ) );
		assertFalse( hand.isStraight() );
		
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(12), new Card(25), new Card(38), new Card(0), new Card(13) ) );
		assertFalse( hand.isStraight() );
	}
	
	@Test
	public void testGetPairs() {
		Hand hand = new Hand();
		// no pairs
		hand.addCards( Arrays.asList( new Card(5), new Card(4), new Card(3), new Card(2), new Card(1) ) );
		assertEquals( 0, hand.getPairs().size() );
		
		hand = new Hand();
		// 1 pair
		hand.addCards( Arrays.asList( new Card(5), new Card(18), new Card(3), new Card(2), new Card(1) ) );
		assertEquals( 1, hand.getPairs().size() );
		assertEquals( 2, hand.getPairs().get( 5 ).intValue() );
		
		hand = new Hand();
		// 3 of a kind
		hand.addCards( Arrays.asList( new Card(5), new Card(18), new Card(31), new Card(2), new Card(1) ) );
		assertEquals( 1, hand.getPairs().size() );
		assertEquals( 3, hand.getPairs().get( 5 ).intValue() );
		
		hand = new Hand();
		// 4 of a kind
		hand.addCards( Arrays.asList( new Card(5), new Card(18), new Card(31), new Card(44), new Card(1) ) );
		assertEquals( 1, hand.getPairs().size() );
		assertEquals( 4, hand.getPairs().get( 5 ).intValue() );
		
		hand = new Hand();
		// two pair
		hand.addCards( Arrays.asList( new Card(5), new Card(18), new Card(3), new Card(16), new Card(1) ) );
		assertEquals( 2, hand.getPairs().size() );
		assertEquals( 2, hand.getPairs().get( 5 ).intValue() );
		assertEquals( 2, hand.getPairs().get( 3 ).intValue() );
		
		hand = new Hand();
		// full house
		hand.addCards( Arrays.asList( new Card(5), new Card(18), new Card(31), new Card(16), new Card(3) ) );
		assertEquals( 2, hand.getPairs().size() );
		assertEquals( 3, hand.getPairs().get( 5 ).intValue() );
		assertEquals( 2, hand.getPairs().get( 3 ).intValue() );
	}
	
	@Test
	public void testGetHandRank() {
		Hand hand = new Hand();
		// 2-6 straight
		hand.addCards( Arrays.asList( new Card(5), new Card(4), new Card(3), new Card(2), new Card(1) ) );
		assertEquals( 5, hand.getHandRank( HandValue.STRAIGHT )[0] );
		
		hand = new Hand();
		// 10-A straight
		hand.addCards( Arrays.asList( new Card(12), new Card(11), new Card(10), new Card(9), new Card(8) ) );
		assertEquals( 12, hand.getHandRank( HandValue.STRAIGHT )[0] );
		
		hand = new Hand();
		// A-5 straight
		hand.addCards( Arrays.asList( new Card(12), new Card(3), new Card(2), new Card(1), new Card(0) ) );
		assertEquals( 3, hand.getHandRank( HandValue.STRAIGHT )[0] );
		
		hand = new Hand();
		// Random hand. Should return first card
		hand.addCards( Arrays.asList( new Card(11), new Card(7), new Card(4), new Card(2), new Card(0) ) );
		assertEquals( 11, hand.getHandRank( 0 )[0] );
	}
	
	@Test
	public void testSortSingleMatch() {
		Hand hand = new Hand();
		// high card pair
		hand.addCards( Arrays.asList( new Card(24), new Card(11), new Card(3), new Card(2), new Card(1) ) );
		hand.sortSingleMatch( hand.getPairs() );
		assertEquals( 11, hand.getHand().get( 0 ).getRankValue() );hand = new Hand();
		
		hand = new Hand();
		// low card pair
		hand.addCards( Arrays.asList( new Card(14), new Card(1), new Card(3), new Card(2), new Card(11) ) );
		hand.sortSingleMatch( hand.getPairs() );
		assertEquals( 1, hand.getHand().get( 0 ).getRankValue() );
	}
	
	@Test
	public void testSortDoubleMatch() {
		
		Hand hand = new Hand();
		// two pair - correct order
		hand.addCards( Arrays.asList( new Card(5), new Card(18), new Card(3), new Card(16), new Card(1) ) );
		hand.sortDoubleMatch( hand.getPairs() );
		assertEquals( 5, hand.getHand().size() );
		assertEquals( 5, hand.getHand().get( 0 ).getRankValue() );
		assertEquals( 5, hand.getHand().get( 1 ).getRankValue() );
		assertEquals( 3, hand.getHand().get( 2 ).getRankValue() );
		assertEquals( 3, hand.getHand().get( 3 ).getRankValue() );
		assertEquals( 1, hand.getHand().get( 4 ).getRankValue() );
		
		// two pair - high card is not a pair
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(12), new Card(5), new Card(18), new Card(3), new Card(16) ) );
		hand.sortDoubleMatch( hand.getPairs() );
		assertEquals( 5, hand.getHand().size() );
		assertEquals( 5, hand.getHand().get( 0 ).getRankValue() );
		assertEquals( 5, hand.getHand().get( 1 ).getRankValue() );
		assertEquals( 3, hand.getHand().get( 2 ).getRankValue() );
		assertEquals( 3, hand.getHand().get( 3 ).getRankValue() );
		assertEquals( 12, hand.getHand().get( 4 ).getRankValue() );
		
		// two pair - non-pair in the middle
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(5), new Card(18), new Card(4), new Card(3), new Card(16) ) );
		hand.sortDoubleMatch( hand.getPairs() );
		assertEquals( 5, hand.getHand().size() );
		assertEquals( 5, hand.getHand().get( 0 ).getRankValue() );
		assertEquals( 5, hand.getHand().get( 1 ).getRankValue() );
		assertEquals( 3, hand.getHand().get( 2 ).getRankValue() );
		assertEquals( 3, hand.getHand().get( 3 ).getRankValue() );
		assertEquals( 4, hand.getHand().get( 4 ).getRankValue() );
		
		// full house - high card 3 of a kind
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(5), new Card(18), new Card(31), new Card(16), new Card(3) ) );
		hand.sortDoubleMatch( hand.getPairs() );
		assertEquals( 5, hand.getHand().size() );
		assertEquals( 5, hand.getHand().get( 0 ).getRankValue() );
		assertEquals( 5, hand.getHand().get( 1 ).getRankValue() );
		assertEquals( 5, hand.getHand().get( 2 ).getRankValue() );
		assertEquals( 3, hand.getHand().get( 3 ).getRankValue() );
		assertEquals( 3, hand.getHand().get( 4 ).getRankValue() );
		
		// full house - low card 3 of a kind
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(5), new Card(18), new Card(29), new Card(16), new Card(3) ) );
		hand.sortDoubleMatch( hand.getPairs() );
		assertEquals( 5, hand.getHand().size() );
		assertEquals( 3, hand.getHand().get( 0 ).getRankValue() );
		assertEquals( 3, hand.getHand().get( 1 ).getRankValue() );
		assertEquals( 3, hand.getHand().get( 2 ).getRankValue() );
		assertEquals( 5, hand.getHand().get( 3 ).getRankValue() );
		assertEquals( 5, hand.getHand().get( 4 ).getRankValue() );
	}

}
