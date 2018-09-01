
/**
 * This class is used to model a triple in BigTwoGame and is a subclass of Hand
 * 
 * @author Zach Lyu
 *
 */
public class Triple extends Hand{
	private static final long serialVersionUID = -3711761437629470849L;
	
	/**
	 * a constructor for building a triple with the specified player and list of cards.
	 * 
	 * @param player
	 *        A CardGamePlayer type argument specifying the player
	 * @param cards
	 *        A CardList type argument specifying the list of cards
	 */
	public Triple (CardGamePlayer player, CardList cards){
		super(player,cards);
	}
	
	/**
	 * a method for checking if this is a valid triple
	 * @return true if it is and false if it is not
	 */
	public boolean isValid(){
		if (this.size()==3 && (this.getCard(0).getRank()==this.getCard(1).getRank()) && (this.getCard(1).getRank()==this.getCard(2).getRank())){
			return true;
		}
		else{return false;}
	}
	
	
	/**
	 * a method for returning a string specifying the type of this hand
	 * @return the string specifying the type of this hand
	 */
	public String getType(){
		return "Triple";
	}

}
