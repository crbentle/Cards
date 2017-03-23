package games.letitride;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import player.Player;
import cards.Card;
import cards.Deck;
import cards.Hand;
import cards.HandValue;

/**
 * @author Chris Bentley
 *
 */
public class GameTest {
	
	@Test
	public void testDealCards() {
		Deck deck = new Deck();
		Player player = new Player();
		Game game = new Game( player, deck );
		deck.shuffle();
		Hand hand = new Hand();
		game.dealCards( hand, 3, false );
		assertEquals( 3, deck.getCardsDealt() );
		for( int i = 0; i < hand.getHand().size(); i++ ) {
			assertTrue( hand.getHand().contains( deck.getDeck()[i] ) );
		}
	}
	
	@Test
	public void testHandleSideBet() {
		Player player = new Player();
		player.setMoney( 0 );
		Game game = new Game( player );
		Hand hand = new Hand();
		hand.addCards( Arrays.asList( new Card(12), new Card(11), new Card(10), new Card(9), new Card(8) ) );
		hand.generateHandValue();
		game.handleSideBet( hand );
		assertEquals( 50 * player.getSideBet(), player.getMoney() );
		
		hand.getHand().clear();
		player.setMoney( 0 );
		hand.addCards( Arrays.asList( new Card(0), new Card(13), new Card(10), new Card(9), new Card(8) ) );
		hand.generateHandValue();
		game.handleSideBet( hand );
		assertEquals( 1 * player.getSideBet(), player.getMoney() );
		
		hand.getHand().clear();
		player.setMoney( 0 );
		hand.addCards( Arrays.asList( new Card(0), new Card(14), new Card(10), new Card(9), new Card(8) ) );
		hand.generateHandValue();
		game.handleSideBet( hand );
		assertEquals( -1 * player.getSideBet(), player.getMoney() );
	}
	
	@Test
	public void testProcessPayout() {
		Player player = new Player();
		player.setMoney( 0 );
		Game game = new Game( player );
		Hand hand = new Hand();
		hand.addCards( Arrays.asList( new Card(12), new Card(11), new Card(10), new Card(9), new Card(8) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		game.processPayout( player.getBet(), hand, true );
		assertEquals( 1000 * player.getBet(), player.getMoney() );
		
		hand.getHand().clear();
		player.setMoney( 0 );
		hand.addCards( Arrays.asList( new Card(0), new Card(13), new Card(10), new Card(9), new Card(8) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		game.processPayout( player.getBet(), hand, true );
		assertEquals( -1 * player.getBet(), player.getMoney() );
		
		hand.getHand().clear();
		player.setMoney( 0 );
		hand.addCards( Arrays.asList( new Card(0), new Card(8), new Card(21), new Card(9), new Card(7) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		game.processPayout( player.getBet(), hand, true );
		assertEquals( 1 * player.getBet(), player.getMoney() );
		
		hand.getHand().clear();
		player.setMoney( 0 );
		hand.addCards( Arrays.asList( new Card(0), new Card(14), new Card(10), new Card(9), new Card(8) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		game.processPayout( player.getBet(), hand, true );
		assertEquals( -1 * player.getBet(), player.getMoney() );
	}
	
	@Test
	public void testAdjustHandValueForTensOrBetter() {
		Game game = new Game( new Player() );
		Hand hand = new Hand();
		hand.addCards( Arrays.asList( new Card(21), new Card(11), new Card(10), new Card(9), new Card(8) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertEquals( HandValue.ONE_PAIR, hand.getValue().getHandType() );
		
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(0), new Card(13), new Card(10), new Card(9), new Card(8) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertEquals( HandValue.HIGH_CARD, hand.getValue().getHandType() );
		
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(4), new Card(3), new Card(2), new Card(1), new Card(0) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertEquals( HandValue.STRAIGHT_FLUSH, hand.getValue().getHandType() );
	}
	
	/**
	 * With three cards you should only "let it ride" if you have:
	 * <ul>
	 * <li>Any paying hand (tens or better, three of a kind)</li>
	 * <li>Any three to a royal flush</li>
	 * <li>Three suited cards in a row except 2-3-4, and ace-2-3</li>
	 * <li>Three to a straight flush, spread 4, with at least one high card (ten or greater)</li>
	 * <li>Three to a straight flush, spread 5, with at least two high cards</li>
	 * </ul>
	 * 
	 * With four cards you should only "let it ride" if you have:
	 * <ul>
	 * <li>Any paying hand (tens or better, two pair, three of a kind)</li>
	 * <li>Any four cards of the same suit</li>
	 * <li>Any four to an outside straight with at least one high card</li>
	 * <li>Any four to an outside straight with no high cards (zero house edge)</li>
	 * <li>Any four to an inside straight with 4 high cards (zero house edge)</li>
	 * </ul>
	 */
	@Test
	public void testLetItRide() {
		/* 3 card tests */
		// Pair of tens
		Game game = new Game( new Player() );
		Hand hand = new Hand();
		hand.addCards( Arrays.asList( new Card(21), new Card(11), new Card(8) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertTrue( Game.letItRide( hand.getValue(), 3 ) );
		
		// Pair of nines
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(20), new Card(11), new Card(7) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertFalse( Game.letItRide( hand.getValue(), 3 ) );
		
		// No pair
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(21), new Card(11), new Card(7) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertFalse( Game.letItRide( hand.getValue(), 3 ) );
		
		// 3 of a kind
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(20), new Card(33), new Card(7) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertTrue( Game.letItRide( hand.getValue(), 3 ) );
		
		// 3 to a Royal Flush (not consecutive)
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(12), new Card(11), new Card(9) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertTrue( Game.letItRide( hand.getValue(), 3 ) );
		
		// 3 to a Royal Flush (consecutive)
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(12), new Card(11), new Card(10) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertTrue( Game.letItRide( hand.getValue(), 3 ) );
		
		// 3 consecutive suited cards
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(5), new Card(4), new Card(3) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertTrue( Game.letItRide( hand.getValue(), 3 ) );
		
		// 3 consecutive suited cards (2-3-4)
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(2), new Card(1), new Card(0) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertFalse( Game.letItRide( hand.getValue(), 3 ) );
		
		// 3 consecutive suited cards (A-2-3)
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(12), new Card(1), new Card(0) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertFalse( Game.letItRide( hand.getValue(), 3 ) );
		
		// 3 to a straight flush, spread 4 - 10 high
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(8), new Card(7), new Card(5) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertTrue( Game.letItRide( hand.getValue(), 3 ) );
		
		// 3 to a straight flush, spread 4 - 9 high
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(7), new Card(6), new Card(4) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertFalse( Game.letItRide( hand.getValue(), 3 ) );
		
		// 3 to a straight flush, spread 4 - 2 high cards
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(10), new Card(9), new Card(7) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertTrue( Game.letItRide( hand.getValue(), 3 ) );
		
		// 3 to a straight flush, spread 5 - 1 high card
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(8), new Card(7), new Card(4) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertFalse( Game.letItRide( hand.getValue(), 3 ) );
		
		// 3 to a straight flush, spread 5 - 0 high cards
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(7), new Card(6), new Card(3) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertFalse( Game.letItRide( hand.getValue(), 3 ) );
		
		// 3 to a straight flush, spread 5 - 2 high cards
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(9), new Card(8), new Card(5) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertTrue( Game.letItRide( hand.getValue(), 3 ) );
		
		// non-suited straight
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(12), new Card(11), new Card(23) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertFalse( Game.letItRide( hand.getValue(), 3 ) );
		
		// non-straight flush
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(0), new Card(5), new Card(10) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertFalse( Game.letItRide( hand.getValue(), 3 ) );
		
		/* 4 card tests */
		// Pair of tens
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(21), new Card(11), new Card(8), new Card(5) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertTrue( Game.letItRide( hand.getValue(), 4 ) );
		
		// Pair of nines
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(20), new Card(11), new Card(7), new Card(5) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertFalse( Game.letItRide( hand.getValue(), 4 ) );
		
		// No pair
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(22), new Card(11), new Card(7), new Card(8) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertFalse( Game.letItRide( hand.getValue(), 4 ) );
		
		// 3 of a kind
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(20), new Card(33), new Card(7), new Card(8) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertTrue( Game.letItRide( hand.getValue(), 4 ) );
		
		// 2 pair
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(13), new Card(0), new Card(1), new Card(14) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertTrue( Game.letItRide( hand.getValue(), 4 ) );
		
		// flush
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(7), new Card(5), new Card(3), new Card(0) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertTrue( Game.letItRide( hand.getValue(), 4 ) );
		
		// almost flush
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(13), new Card(5), new Card(3), new Card(1) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertFalse( Game.letItRide( hand.getValue(), 4 ) );
		
		// outside straight - 10 high
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(8), new Card(7), new Card(6), new Card(18) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertTrue( Game.letItRide( hand.getValue(), 4 ) );
		
		// outside straight - 9 high
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(7), new Card(6), new Card(5), new Card(17) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertTrue( Game.letItRide( hand.getValue(), 4 ) );
		
		// straight - A-4
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(12), new Card(2), new Card(1), new Card(13) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertFalse( Game.letItRide( hand.getValue(), 4 ) );
		
		// inside straight - 10-A
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(12), new Card(11), new Card(10), new Card(21) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertTrue( Game.letItRide( hand.getValue(), 4 ) );
		
		// straight - J-A
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(12), new Card(11), new Card(10), new Card(22) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertTrue( Game.letItRide( hand.getValue(), 4 ) );
		
		// inside straight - 3 high cards
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(11), new Card(10), new Card(9), new Card(20) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertFalse( Game.letItRide( hand.getValue(), 4 ) );
		
		// inside straight - 0 high cards
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(7), new Card(6), new Card(5), new Card(16) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertFalse( Game.letItRide( hand.getValue(), 4 ) );
		
		// inside straight - 4 high cards
		hand = new Hand();
		hand.addCards( Arrays.asList( new Card(12), new Card(11), new Card(10), new Card(21) ) );
		hand.generateHandValue();
		game.adjustHandValueForTensOrBetter( hand );
		assertTrue( Game.letItRide( hand.getValue(), 4 ) );
		
		/*
	 * With four cards you should only "let it ride" if you have:
	 * <ul>
	 * 	<li>Any paying hand (tens or better, two pair, three of a kind)</li>
	 * 	<li>Any four cards of the same suit</li>
	 * 	<li>Any four to an outside straight with at least one high card</li>
	 * 	<li>Any four to an outside straight with no high cards (zero house edge)</li>
	 * 	<li>Any four to an inside straight with 4 high cards (zero house edge)</li>
	 * </ul>
		 */
	}

}
