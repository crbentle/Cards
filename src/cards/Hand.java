package cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;

public class Hand {
	
	private ArrayList<Card> hand;
	private HandValue value;
	
	public Hand()
	{
		this.hand = new ArrayList<Card>();
	}
	
	public Hand(ArrayList<Card> hand)
	{
		this.hand = hand;
	}
	
	public void addCard(Card card)
	{
		hand.add(card);
	}
	
	public void addCards(ArrayList<Card> cards)
	{
		hand.addAll(cards);
	}
	
	public ArrayList<Card> getHand()
	{
		return hand;
	}
	
	public void GenerateHandValue()
	{
		Collections.sort(hand, new CardComparator());
		
		//System.out.println("sort 1 : "+ hand);
		
		boolean isFlush = isFlush();
		boolean isStraight = isStraight();
		HashMap<Integer, Integer> pairs = getPairs();
		
		if( isFlush && isStraight )
		{
			int handType = HandValue.STRAIGHT_FLUSH;
			if(hand.get(1).getRankValue() == 11)
				handType = HandValue.ROYAL_FLUSH;
			
			value = new HandValue(handType, getHandRank(handType) );
		}
		else if ( isFlush )
		{
			value = new HandValue(HandValue.FLUSH, getHandRank(HandValue.FLUSH));
		}
		else if ( isStraight )
		{
			value = new HandValue(HandValue.STRAIGHT, getHandRank(HandValue.STRAIGHT));
		}
		else if ( pairs.isEmpty() )
		{
			value = new HandValue(HandValue.HIGH_CARD, getHandRank(HandValue.HIGH_CARD));
		}
		else
		{
			//One pair, three of a kind, or four of a kind
			if(pairs.keySet().size() == 1)
			{
				sortSingleMatch(pairs);
				if(pairs.values().contains(4))
				{
					value = new HandValue(HandValue.FOUR_OF_A_KIND, getHandRank(HandValue.FOUR_OF_A_KIND));
				}
				else if(pairs.values().contains(3))
				{
					value = new HandValue(HandValue.THREE_OF_A_KIND, getHandRank(HandValue.THREE_OF_A_KIND));
				}
				else
				{
					value = new HandValue(HandValue.ONE_PAIR, getHandRank(HandValue.ONE_PAIR));
				}
			}
			//Two pair or full house
			else
			{
				sortDoubleMatch(pairs);
				//Full house
				if( pairs.values().contains(3))
				{
					value = new HandValue(HandValue.FULL_HOUSE, getHandRank(HandValue.FULL_HOUSE));
				}
				//Two pair
				else
				{
					value = new HandValue(HandValue.TWO_PAIR, getHandRank(HandValue.TWO_PAIR));
				}
			}
		}
		
		//if sameCards == 3 check for full house
		//if sameCards == 2 check for two pair
	}
	
	public void printHand()
	{
		GenerateHandValue();
		for(Card card : hand)
		{
			System.out.print(card.getTruncatedString()+", ");
		}
		System.out.println("");
		
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
	
	
	private boolean isFlush()
	{
		for(int i = 0; i<hand.size()-1; i++)
		{
			if(hand.get(i).getSuiteValue() != hand.get(i+1).getSuiteValue())
				return false;
		}
		return true;
	}
	
	private boolean isStraight()
	{
		//A,5,4,3,2
		//A,4,3,2
		for(int i = 0; i<hand.size()-1; i++)
		{
			if(hand.get(i).getRankValue() - 1 != hand.get(i+1).getRankValue())
			{
				if( hand.get(i).getRankValue() == Card.ACE && hand.get(hand.size() - 1).getRankValue() == Card.TWO )
					continue;
				return false;
			}
		}
		return true;
	}
	
	private HashMap<Integer, Integer> getPairs()
	{
		HashMap<Integer, Integer> pairMap = new HashMap<Integer,Integer>();
		int sameCards = 1;
		for(int i=0; i<hand.size()-1; i++)
		{			
			int card1 = hand.get(i).getRankValue();
			
			for(int j=i+1; j<hand.size(); j++)
			{
				if(card1 == hand.get(j).getRankValue())
				{
					if(pairMap.containsKey(card1))
					{
						sameCards = pairMap.get(card1);
					}
					sameCards++;
					i++;
					pairMap.put(card1, sameCards);
				}
				else
				{
					sameCards=1;
					break;
				}
			}		
		}
		
		
		
		
		return pairMap;
	}
	
	private int[] getHandRank(int handType)
	{
		int[] handRank = new int[hand.size()];
//		if(handType == HandValue.STRAIGHT_FLUSH 
//				|| handType == HandValue.ROYAL_FLUSH 
//				|| handType == HandValue.HIGH_CARD
//				|| handType == HandValue.FLUSH 
//				|| handType == HandValue.STRAIGHT 
//				|| handType == HandValue.FOUR_OF_A_KIND )
//		{
		
		if(handType == HandValue.STRAIGHT || handType == HandValue.STRAIGHT_FLUSH)
		{
			//The straight contains an ACE and is not 10-Ace
			//Move the Ace to the end of the hand (5 is now high)
			if(hand.get(0).getRankValue() == 12  && hand.get(1).getRankValue() != 11)
			{
				Card temp = hand.get(0);
				hand.remove(0);
				hand.add(temp);
			}
		}
		
			for(int i=0; i<hand.size(); i++)
			{
				handRank[i] = hand.get(i).getRankValue();
			}
			
			return handRank;
//		}
		
		
		
		
//		return handRank;
	}
	
	
//	private void sortFourOfAKind()
//	{
//		int card1 = -1;
//		for(int i=0;  i< hand.size()-1; i++)
//		{
//			if(hand.get(i).getRankValue() != hand.get(i+1).getRankValue())
//			{
//				if(card1 < 0)
//					card1 = hand.get(i+2).getRankValue();
//
//				//Move i+1 to end
//				if(card1 == hand.get(i).getRankValue())
//				{
//					Card temp = hand.get(i+1);
//					hand.remove(i+1);
//					hand.add(temp);
//				}
//				//Move i to end
//				else
//				{					
//					Card temp = hand.get(i);
//					hand.remove(i);
//					hand.add(temp);
//				}
//				break;
//			}
//			
//		}
//	}
	//Collections.sort(hand, new CardComparator());
	private void sortSingleMatch(HashMap<Integer, Integer> pairs)
	{
		ArrayList<Card> nonPairedCards = new ArrayList<Card>();
		
		Set<Integer> keySet = pairs.keySet();
		for(Card card : hand)
		{
			if(!keySet.contains( card.getRankValue() ) )
			{
				nonPairedCards.add(card);
				//hand.remove(card);
			}
		}
		
		hand.removeAll(nonPairedCards);
		
		Collections.sort(nonPairedCards, new CardComparator());
		
		hand.addAll(nonPairedCards);
		
	}
	
	
	private void sortDoubleMatch(HashMap<Integer, Integer> pairs)
	{
		ArrayList<Card> firstPair = new ArrayList<Card>();
		ArrayList<Card> secondPair = new ArrayList<Card>();
		ArrayList<Card> nonPairedCards = new ArrayList<Card>();
		
		boolean fullHouse = false;
		
		Set<Integer> keySet = pairs.keySet();
		for(Card card : hand)
		{
			if(!keySet.contains( card.getRankValue() ) )
			{
				nonPairedCards.add(card);
				//hand.remove(card);
			}
			else if(pairs.get(card.getRankValue()) == 3 )
			{
				firstPair.add(card);
				//hand.remove(card);
				fullHouse = true;
			}
			else
			{
				if(firstPair.isEmpty())
				{
					firstPair.add(card);
					//hand.remove(card);
				}
				else
				{
					if(firstPair.get(0).getRankValue() == card.getRankValue())
					{
						firstPair.add(card);
						//hand.remove(card);
					}
					else
					{
						secondPair.add(card);
						//hand.remove(card);
					}
				}
			}
			
		}
		hand.clear();
		
		if(fullHouse)
		{
			hand.addAll(firstPair);
			hand.addAll(secondPair);
			hand.addAll(nonPairedCards);
		}
		else
		{
			if(firstPair.get(0).getRankValue() > secondPair.get(0).getRankValue())
			{
				hand.addAll(firstPair);
				hand.addAll(secondPair);
				hand.addAll(nonPairedCards);
			}
			else
			{
				hand.addAll(secondPair);
				hand.addAll(firstPair);
				hand.addAll(nonPairedCards);
			}
		}		
	}
	
	
	/*
//start hand evaluation
if ( sameCards==1 ) {    //if we have no pair...
    value[0]=1;          //this is the lowest type of hand, so it gets the lowest value
    value[1]=orderedRanks[0];  //the first determining factor is the highest card,
    value[2]=orderedRanks[1];  //then the next highest card,
    value[3]=orderedRanks[2];  //and so on
    value[4]=orderedRanks[3];
    value[5]=orderedRanks[4];
}

if (sameCards==2 && sameCards2==1) //if 1 pair
{
    value[0]=2;                //pair ranked higher than high card
    value[1]=largeGroupRank;   //rank of pair
    value[2]=orderedRanks[0];  //next highest cards.
    value[3]=orderedRanks[1];
    value[4]=orderedRanks[2];
}

if (sameCards==2 && sameCards2==2) //two pair
{
    value[0]=3;
    //rank of greater pair
    value[1]= largeGroupRank>smallGroupRank ? largeGroupRank : smallGroupRank;
    //rank of smaller pair
    value[2]= largeGroupRank<smallGroupRank ? largeGroupRank : smallGroupRank;
    value[3]=orderedRanks[0];  //extra card
}

if (sameCards==3 && sameCards2!=2)
//three of a kind (not full house)
{
    value[0]=4;
    value[1]= largeGroupRank;
    value[2]=orderedRanks[0];
    value[3]=orderedRanks[1];
}

if (straight)
{
    value[0]=5;
    value[1]=;
    //if we have two straights, 
    //the one with the highest top cards wins
}

if (flush)   
{
    value[0]=6;
    value[1]=orderedRanks[0]; //tie determined by ranks of cards
    value[2]=orderedRanks[1];
    value[3]=orderedRanks[2];
    value[4]=orderedRanks[3];
    value[5]=orderedRanks[4];
}

if (sameCards==3 && sameCards2==2)  //full house
{
    value[0]=7;
    value[1]=largeGroupRank;
    value[2]=smallGroupRank;
}

if (sameCards==4)  //four of a kind
{
    value[0]=8;
    value[1]=largeGroupRank;
    value[2]=orderedRanks[0];
}

if (straight && flush)  //straight flush
{
    value[0]=9;
    value[1]=;
}
	 */

	public static void main(String[] args)
	{
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(12));
		cards.add(new Card(11));
		cards.add(new Card(10));
		Hand hand = new Hand(cards);
		
		hand.printHand();
		
		System.out.println(hand.getValue());
	}
}

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

