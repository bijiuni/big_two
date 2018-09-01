/**
 * This class is used to model a straight in BigTwoGame and is a subclass of Hand
 * 
 * @author Lv Ruyi
 *
 */
public class Straight extends Hand{
	private static final long serialVersionUID = -3711761437629470849L;
	
	/**
	 * a constructor for building a straight with the specified player and list of cards.
	 * 
	 * @param player
	 *        A CardGamePlayer type argument specifying the player
	 * @param cards
	 *        A CardList type argument specifying the list of cards
	 */
	public Straight (CardGamePlayer player, CardList cards){
		super(player,cards);
	}
	
	/**
	 * a method for checking if this straight beats a specified hand
	 * @param hand
	 *         a specified hand
	 * @return true if it beats, false if doesn't
	 */
	public boolean beats(Hand hand){
		if (hand.getType()=="Straight" && this.getTopCard().compareTo(hand.getTopCard())>0){
				return true;}
			else{return false;}
		}
	
	
	/**
	 * a method for checking if this is a valid straight
	 * @return true if it is and false if it is not
	 */
	public boolean isValid(){
		boolean isStraight = false;
		TempSort();
	
		if ((this.tempsort.get(0)+1==this.tempsort.get(1)) && (this.tempsort.get(1)+1==this.tempsort.get(2)) && (this.tempsort.get(2)+1==this.tempsort.get(3)) && (this.tempsort.get(3)+1==this.tempsort.get(4))){
			isStraight=true;
		}
		
		if (this.size()==5 && isStraight==true){
			return true;
		}
		else{return false;}
	}
	
	
	/**
	 * a method for returning a string specifying the type of this hand
	 * @return the string specifying the type of this hand
	 */
	public String getType(){
		return "Straight";
	}


}
