package games.letitride;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cards.HandValue;

/**
 * @author Chris Bentley
 *
 */
public class PayoutTest {
	
	@Test
	public void testGetPayout() {
		assertEquals( 1000, Payout.getPayout( HandValue.ROYAL_FLUSH, true ) );
		assertEquals( 200, Payout.getPayout( HandValue.STRAIGHT_FLUSH, true ) );
		assertEquals( 50, Payout.getPayout( HandValue.FOUR_OF_A_KIND, true ) );
		assertEquals( 11, Payout.getPayout( HandValue.FULL_HOUSE, true ) );
		assertEquals( 8, Payout.getPayout( HandValue.FLUSH, true ) );
		assertEquals( 5, Payout.getPayout( HandValue.STRAIGHT, true ) );
		assertEquals( 3, Payout.getPayout( HandValue.THREE_OF_A_KIND, true ) );
		assertEquals( 2, Payout.getPayout( HandValue.TWO_PAIR, true ) );
		assertEquals( 1, Payout.getPayout( HandValue.ONE_PAIR, true ) );
		assertEquals( 0, Payout.getPayout( HandValue.HIGH_CARD, true ) );
		assertEquals( 0, Payout.getPayout( -1, true ) );
		
		assertEquals( 50, Payout.getPayout( HandValue.ROYAL_FLUSH, false ) );
		assertEquals( 40, Payout.getPayout( HandValue.STRAIGHT_FLUSH, false ) );
		assertEquals( 30, Payout.getPayout( HandValue.THREE_OF_A_KIND, false ) );
		assertEquals( 6, Payout.getPayout( HandValue.STRAIGHT, false ) );
		assertEquals( 3, Payout.getPayout( HandValue.FLUSH, false ) );
		assertEquals( 1, Payout.getPayout( HandValue.ONE_PAIR, false ) );
		assertEquals( 0, Payout.getPayout( HandValue.HIGH_CARD, false ) );
		assertEquals( 0, Payout.getPayout( -1, false ) );
	}

}
