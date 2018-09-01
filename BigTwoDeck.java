
/**
 * The class is a subclass of the Deck class, and is used to model a deck of cards used in a Big Two card game.
 * 
 * @author Lv Ruyi
 *
 */
public class BigTwoDeck extends Deck {
	private static final long serialVersionUID = -3886066435694112173L;
	
	/**
	 * Initialize the deck of BigTwoCards.
	 */	
	public void initialize (){
		removeAllCards();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				BigTwoCard card = new BigTwoCard(i, j);
				addCard(card);
			}
		}
	}

}
