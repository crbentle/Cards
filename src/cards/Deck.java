package cards;

import java.util.ArrayList;


public class Deck {
	
	private static Card[] deck = new Card[52];
	private static int cardsDealt = 0;
	
	public Deck()
	{
		for ( int i = 0; i < deck.length; i++ )
			deck[ i ] = new Card( i );
	}
	
	public void printDeck()
	{
		for ( int i = 0; i < deck.length; i++ )
		{
			if(i%13 == 0 && i>0)
				System.out.println("");
			System.out.print(deck[i].getTruncatedString());
			if((i+1)%13!=0)
				System.out.print(", ");
		}
		System.out.println("");
	}
	
	public void shuffle()
	{
		cardsDealt = 0;
		
		for(int i=deck.length-1; i>0; i--)
		{
			int j = (int)(Math.random() * i );
			Card temp = deck[j];
			deck[j] = deck[i];
			deck[i] = temp;
		}
	}
	
	public ArrayList<Card> deal(int numToDeal)
	{
		ArrayList<Card> cards = new ArrayList<Card>();
		
		for(int i = 0;i<numToDeal; i++)
		{
			cards.add(deck[cardsDealt++]);
		}
		return cards;
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
