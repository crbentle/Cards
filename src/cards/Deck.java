package cards;

import java.util.ArrayList;

/**
 * This class is used to manipulate a deck of cards
 * @author Chris Bentley
 */
public class Deck {
	
	// An array to contain the deck of cards
	private Card[] deck = new Card[52];
	
	// How many cards have been dealt from the deck
	private int cardsDealt = 0;
	
	/**
	 * Create a new deck of 52 cards
	 */
	public Deck() {
		for ( int i = 0; i < deck.length; i++ )
			deck[i] = new Card( i );
	}
	
	/**
	 * Prints out the deck of cards
	 */
	public void printDeck() {
		for ( int i = 0; i < deck.length; i++ ) {
			if ( i % 13 == 0 && i > 0 )
				System.out.println( "" );
			System.out.print( deck[i].getTruncatedString() );
			if ( ( i + 1 ) % 13 != 0 )
				System.out.print( ", " );
		}
		System.out.println( "" );
	}
	
	/**
	 * Randomly shuffle the deck of cards
	 */
	public void shuffle() {
		cardsDealt = 0;

		for ( int i = deck.length - 1; i > 0; i-- ) {
			int j = (int) ( Math.random() * i );
			Card temp = deck[j];
			deck[j] = deck[i];
			deck[i] = temp;
		}
	}
	
	/**
	 * Deal from the deck of cards
	 * @param numToDeal The number of cards to deal
	 * @return The list of dealt cards
	 */
	public ArrayList<Card> deal( int numToDeal ) {
		ArrayList<Card> cards = new ArrayList<Card>();

		for ( int i = 0; i < numToDeal; i++ ) {
			cards.add( deck[cardsDealt++] );
		}
		return cards;
	}
	
	/**
	 * Gets the deck
	 * @return the deck
	 */
	public Card[] getDeck() {
		return deck;
	}

	/**
	 * Gets the cardsDealt
	 * @return the cardsDealt
	 */
	public int getCardsDealt() {
		return cardsDealt;
	}

	public static void main(String[] args){
		Deck newDeck = new Deck();
		//newDeck.printDeck();
		
		newDeck.shuffle();
		newDeck.printDeck();
		
		System.out.println(newDeck.deal(3));
		System.out.println(newDeck.deal(3));
		
		System.out.println("------------------------------------------------------");
		
		newDeck.shuffle();
		newDeck.printDeck();
		
		System.out.println(newDeck.deal(3));
		System.out.println(newDeck.deal(3));
	}

}
