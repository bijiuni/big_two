
/**
 * This class is a subclass of the Card class and is used to model a card used in a Big Two card game.
 * 
 * @author Lv Ruyi
 *
 */
public class BigTwoCard extends Card {
	private static final long serialVersionUID = -713898713776577970L;
	
	/**
	 * a constructor for building a card with the specified suit and rank.
	 * 
	 * @param suit
	 *        an integer between 0 and 3
	 *        0 = Diamond, 1 = Club, 2 = Heart, 3 = Spade
	 * @param rank
	 *        an integer between 0 and 12
	 *         0 = 'A', 1 = '2', 2 = '3', ..., 8 = '9', 9 = '0', 10 = 'J', 11
	 *        = 'Q', 12 = 'K'
	 */
	public BigTwoCard(int suit, int rank) {
		super(suit,rank);
	}
	
	/**
	 * Compares this BigTwoCard with the specified BigTwoCard for order.
	 * 
	 * @param card
	 *            the BigTwoCardto be compared
	 * @return a negative integer, zero, or a positive integer as this card is
	 *         less than, equal to, or greater than the specified card
	 */
	public int compareTo(Card card) {
		int tempt = this.rank;
		int tempc = card.rank;
		if (tempt == 0){
			tempt = 13;
		}
		if (tempc == 0){
			tempc = 13;
		}
		if (tempt == 1){
			tempt = 14;
		}
		if (tempc == 1){
			tempc = 14;
		}
		
		if (tempt > tempc) {
			return 1;
		} else if (tempt < tempc) {
			return -1;
		} else if (this.suit > card.suit) {
			return 1;
		} else if (this.suit < card.suit) {
			return -1;
		} else {
			return 0;
		}
	}

}
