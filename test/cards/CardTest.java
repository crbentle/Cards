package cards;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Chris Bentley
 */
public class CardTest {
	
	@Test
	public void testGetRankValue() {

		Card card;
		int expectedValue = 0;
		for ( int i = 0; i < 52; i++, expectedValue++ ) {
			card = new Card( i );
			if ( i % 13 == 0 && i > 0 ) {
				expectedValue = 0;
			}
			assertEquals( expectedValue, card.getRankValue() );
		}
	}
	
	@Test
	public void testGetRank() {
		String[] ranks = {"Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace"};
		Card card;
		int expectedValue = 0;
		for ( int i = 0; i < 52; i++, expectedValue++ ) {
			card = new Card( i );
			if ( i % 13 == 0 && i > 0 ) {
				expectedValue = 0;
			}
			assertEquals( ranks[expectedValue], card.getRank() );
		}
	}
	
	@Test
	public void testGetSuitValue() {
		Card card;
		int expectedSuit = -1;
		for ( int i = 0; i < 52; i++ ) {
			card = new Card( i );
			if ( i < 13 ) {
				expectedSuit = 0;
			} else if( i < 26 ) {
				expectedSuit = 1;
			} else if( i < 39 ) {
				expectedSuit = 2;
			} else if( i < 52 ) {
				expectedSuit = 3;
			}
			assertEquals( expectedSuit, card.getSuitValue() );
		}
	}
	
	@Test
	public void testGetSuit() {
		Card card;
		String expectedSuit = null;
		for ( int i = 0; i < 52; i++ ) {
			card = new Card( i );
			if ( i < 13 ) {
				expectedSuit = "Clubs";
			} else if( i < 26 ) {
				expectedSuit = "Diamonds";
			} else if( i < 39 ) {
				expectedSuit = "Hearts";
			} else if( i < 52 ) {
				expectedSuit = "Spades";
			}
			assertEquals( expectedSuit, card.getSuit() );
		}
	}
	
	@Test
	public void testToString() {
		
		Card card;
		
		// 2c
		card = new Card(0);
		assertEquals( "Two of Clubs", card.toString() );
		
		// Ac
		card = new Card(12);
		assertEquals( "Ace of Clubs", card.toString() );
		
		// 2d
		card = new Card(13);
		assertEquals( "Two of Diamonds", card.toString() );
		
		// Ad
		card = new Card(25);
		assertEquals( "Ace of Diamonds", card.toString() );
		
		// 2h
		card = new Card(26);
		assertEquals( "Two of Hearts", card.toString() );
		
		// Ah
		card = new Card(38);
		assertEquals( "Ace of Hearts", card.toString() );
		
		// 2s
		card = new Card(39);
		assertEquals( "Two of Spades", card.toString() );
		
		// As
		card = new Card(51);
		assertEquals( "Ace of Spades", card.toString() );
	}
	
	@Test
	public void testGetTruncatedString() {
		
		Card card;
		
		// 2c
		card = new Card(0);
		assertEquals( "2c", card.getTruncatedString() );
		
		// Kc
		card = new Card(11);
		assertEquals( "Kc", card.getTruncatedString() );
		
		// Ac
		card = new Card(12);
		assertEquals( "Ac", card.getTruncatedString() );
		
		// 2d
		card = new Card(13);
		assertEquals( "2d", card.getTruncatedString() );
		
		// Ad
		card = new Card(25);
		assertEquals( "Ad", card.getTruncatedString() );
		
		// 2h
		card = new Card(26);
		assertEquals( "2h", card.getTruncatedString() );
		
		// Ah
		card = new Card(38);
		assertEquals( "Ah", card.getTruncatedString() );
		
		// 2s
		card = new Card(39);
		assertEquals( "2s", card.getTruncatedString() );
		
		// As
		card = new Card(51);
		assertEquals( "As", card.getTruncatedString() );
	}
	@Test
	public void testGetRankPlural()
	{
		Card card;
		String expected = null;
		for( int i = 0; i < 13; i++ ) {
			card = new Card( i );
			switch( i ) {
				case 0:
					expected = "Twos";
					break;
				case 1:
					expected = "Threes";
					break;
				case 2:
					expected = "Fours";
					break;
				case 3:
					expected = "Fives";
					break;
				case 4:
					expected = "Sixes";
					break;
				case 5:
					expected = "Sevens";
					break;
				case 6:
					expected = "Eights";
					break;
				case 7:
					expected = "Nines";
					break;
				case 8:
					expected = "Tens";
					break;
				case 9:
					expected = "Jacks";
					break;
				case 10:
					expected = "Queens";
					break;
				case 11:
					expected = "Kings";
					break;
				case 12:
					expected = "Aces";
					break;
			}
			assertEquals( expected, card.getRankPlural() );
		}
	}

}
