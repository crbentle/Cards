/**
 * 
 */
package player;

import cards.Hand;

/**
 * This class holds information about a player
 * @author Chris Bentley
 */
public class Player {
	
	// How much money the player has
	private int money = 0;
	
	// How large the biggest win was
	private int biggestPayout = 0;
	
	// Which hand paid out the most
	private Hand biggestHand;
	
	// The size of the main bet
	private int bet = 15;
	
	// The size of the 3 card side bet
	private int sideBet = 15;
	
	/**
	 * Create a new Player with default values
	 */
	public Player(){}
	
	/**
	 * Create a new Player with a non-default bet and sideBet
	 * @param bet The size of the main bet
	 * @param sideBet The size of the 3 card side bet
	 */
	public Player( int bet, int sideBet ){
		this.bet = bet;
		this.sideBet = sideBet;
	}
	
	/**
	 * Add money to the player's total
	 * @param addition The amount of money to add
	 */
	public void addMoney( int addition )
	{
		money += addition;
	}
	
	/**
	 * Subtract money from the player's total
	 * @param subtraction The amount of money to subtract
	 */
	public void subtractMoney( int subtraction )
	{
		money -= subtraction;
	}

	/**
	 * @return the money
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * @param money the money to set
	 */
	public void setMoney(int money) {
		this.money = money;
	}

	/**
	 * @return the biggestPayout
	 */
	public int getBiggestPayout() {
		return biggestPayout;
	}

	/**
	 * @param biggestPayout the biggestPayout to set
	 */
	public void setBiggestPayout(int biggestPayout) {
		this.biggestPayout = biggestPayout;
	}

	/**
	 * @return the biggestHand
	 */
	public Hand getBiggestHand() {
		return biggestHand;
	}

	/**
	 * @param biggestHand the biggestHand to set
	 */
	public void setBiggestHand(Hand biggestHand) {
		this.biggestHand = biggestHand;
	}
	
	/**
	 * @return the bet
	 */
	public int getBet() {
		return bet;
	}

	/**
	 * @param bet the bet to set
	 */
	public void setBet(int bet) {
		this.bet = bet;
	}

	/**
	 * @return the sideBet
	 */
	public int getSideBet() {
		return sideBet;
	}

	/**
	 * @param sideBet the sideBet to set
	 */
	public void setSideBet(int sideBet) {
		this.sideBet = sideBet;
	}

	/**
	 * Print the final game results
	 * @return The print out of the game results
	 */
	public String getResult() {
		
		return "Money: " + money + "\nBiggest payout: " + biggestPayout
				+ "\nBiggest hand: " + ( biggestHand == null ? null : biggestHand );
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Player [money=" + money + ", biggestPayout=" + biggestPayout
				+ ", biggestHand=" + biggestHand + ", bet=" + bet
				+ ", sideBet=" + sideBet + "]";
	}
	
	

}
