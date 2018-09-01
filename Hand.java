import java.util.ArrayList;
import java.util.Collections;

/**
 * The Hand class is a subclass of the CardList class, and is used to model a hand of cards.
 * 
 * @author Lv Ruyi
 *
 */
abstract class Hand extends CardList{
	private static final long serialVersionUID = -3711761437629470849L;
	private CardGamePlayer player;
	public ArrayList<Integer> tempsort = new ArrayList<Integer>();
	
	/**
	 * a constructor for building a hand with the specified player and list of cards.
	 * 
	 * @param player
	 *        A CardGamePlayer type argument specifying the player
	 * @param cards
	 *        A CardList type argument specifying the list of cards
	 */
	public Hand (CardGamePlayer player, CardList cards){
		this.player = player;
		for (int i=0; i< cards.size(); i++) {
			this.addCard(cards.getCard(i));}
	}
	
	/**
	 * a method for retrieving the player of this hand
	 * @return the player of this hand
	 */
	public CardGamePlayer getPlayer(){
		return this.player;
	}
	
	/**
	 * a method for retrieving the top card of this hand
	 * @return the top card of this hand
	 */
	public Card getTopCard(){
		Card TopCard = this.getCard(0);
		for (int i=0; i<this.size();i++){
			if (this.getCard(i).compareTo(TopCard)>0){
				TopCard = this.getCard(i);
			}
		}
		return TopCard;
	}
	
	/**
	 * a method for checking if this hand beats a specified hand
	 * @param hand
	 *         a specified hand
	 * @return true if it beats, false if doesn't
	 */
	public boolean beats(Hand hand){
		if (this.getTopCard().compareTo(hand.getTopCard())>0){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * sort the hand with only rank values for convenience in isValid functions
	 */
	public void TempSort(){
		for (int i=0; i<this.size();i++){
			tempsort.add(this.getCard(i).getRank());
		}
		
		for (int i=0; i<tempsort.size();i++){
			if (tempsort.get(i)==0){
				tempsort.set(i, 13);
			}
			if (tempsort.get(i)==1){
				tempsort.set(i, 14);
			}
		}

		Collections.sort(tempsort);
	}
	
	
	/**
	 * a method for checking if this is a valid hand
	 * @return
	 */
	public abstract boolean isValid();
	
	/**
	 * a method for returning a string specifying the type of this hand
	 * @return
	 */
	public abstract String getType();

}
