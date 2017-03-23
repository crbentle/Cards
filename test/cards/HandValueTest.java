package cards;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Chris Bentley
 *
 */
public class HandValueTest {
	
	@Test
	public void testToString() {
		HandValue value = new HandValue( HandValue.ROYAL_FLUSH, null );
		assertEquals( "Royal Flush", value.toString() );
		
		int[] rank = {11,10,9,8,7};
		value = new HandValue( HandValue.STRAIGHT_FLUSH, rank );
		assertEquals( "Straight Flush, King high", value.toString() );
		
		rank = new int[]{3,2,1,0,12};
		value = new HandValue( HandValue.STRAIGHT_FLUSH, rank );
		assertEquals( "Straight Flush, Five high", value.toString() );
		
		rank = new int[]{12,25,38,51,0};
		value = new HandValue( HandValue.FOUR_OF_A_KIND, rank );
		assertEquals( "Four Aces", value.toString() );
		
		rank = new int[]{4,17,30,43,0};
		value = new HandValue( HandValue.FOUR_OF_A_KIND, rank );
		assertEquals( "Four Sixes", value.toString() );
		
		rank = new int[]{7,20,33,5,18};
		value = new HandValue( HandValue.FULL_HOUSE, rank );
		assertEquals( "Full house. Nines over Sevens", value.toString() );
		
		rank = new int[]{9,3,2,1,0};
		value = new HandValue( HandValue.FLUSH, rank );
		assertEquals( "Flush. Jack high.", value.toString() );
		
		rank = new int[]{4,3,2,1,0};
		value = new HandValue( HandValue.STRAIGHT, rank );
		assertEquals( "Straight. Six high.", value.toString() );
		
		rank = new int[]{3,2,1,0,25};
		value = new HandValue( HandValue.STRAIGHT, rank );
		assertEquals( "Straight. Five high.", value.toString() );
		
		rank = new int[]{25,11,10,9,8};
		value = new HandValue( HandValue.STRAIGHT, rank );
		assertEquals( "Straight. Ace high.", value.toString() );
		
		rank = new int[]{4,17,30,9,8};
		value = new HandValue( HandValue.THREE_OF_A_KIND, rank );
		assertEquals( "Three Sixes", value.toString() );
		
		rank = new int[]{12,25,5,18,33};
		value = new HandValue( HandValue.TWO_PAIR, rank );
		assertEquals( "Two pair. Aces over Sevens", value.toString() );
		
		rank = new int[]{8,21,12,5,4};
		value = new HandValue( HandValue.ONE_PAIR, rank );
		assertEquals( "Pair of Tens, Ace kicker.", value.toString() );
		
		rank = new int[]{10,6,4,2,0};
		value = new HandValue( HandValue.HIGH_CARD, rank );
		assertEquals( "Queen high.", value.toString() );
		
		value = new HandValue( -1, null );
		assertEquals( "Unknown hand", value.toString() );
	}

}
