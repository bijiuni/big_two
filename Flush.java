
/**
 * This class is used to model a flush in BigTwoGame and is a subclass of Hand
 * 
 * @author Zach Lyu
 *
 */
public class Flush extends Hand{
	private static final long serialVersionUID = -3711761437629470849L;
	
	/**
	 * a constructor for building a flush with the specified player and list of cards.
	 * 
	 * @param player
	 *        A CardGamePlayer type argument specifying the player
	 * @param cards
	 *        A CardList type argument specifying the list of cards
	 */
	public Flush (CardGamePlayer player, CardList cards){
		super(player,cards);
	}
	
	
	/**
	 * a method for checking if this flush beats a specified hand
	 * @param hand
	 *         a specified hand
	 * @return true if it beats, false if doesn't
	 */
	public boolean beats(Hand hand){
		if (hand.getType()=="Straight"){
			return true;
		}
		else if (hand.getType()=="Flush" && this.getTopCard().getSuit()>hand.getTopCard().getSuit()){
			return true;
		}
		else if (hand.getType()=="Flush" && this.getTopCard().getSuit()<hand.getTopCard().getSuit()){
			return false;
		}
		else if (hand.getType()=="Flush" && this.getTopCard().getSuit()==hand.getTopCard().getSuit()){
			if (this.getTopCard().compareTo(hand.getTopCard())>0){
				return true;}
			else{return false;}
		}
		else{
			return false;
		}
	}
	
	/**
	 * a method for checking if this is a valid flush
	 * @return true if it is and false if it is not
	 */
	public boolean isValid(){
		if (this.getCard(0).getSuit()==this.getCard(1).getSuit() && this.getCard(1).getSuit()==this.getCard(2).getSuit() && this.getCard(2).getSuit()==this.getCard(3).getSuit() && this.getCard(3).getSuit()==this.getCard(4).getSuit()){
			return true;
		}
		else{return false;}
	}
	
	/**
	 * a method for returning a string specifying the type of this hand
	 * @return the string specifying the type of this hand
	 */
	public String getType(){
		return "Flush";
	}

}
