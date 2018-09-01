/**
 * This class is used to model a straightflush in BigTwoGame and is a subclass of Hand
 * 
 * @author Zach Lyu
 *
 */
public class StraightFlush extends Hand{
	private static final long serialVersionUID = -3711761437629470849L;
	
	/**
	 * a constructor for building a straightflush with the specified player and list of cards.
	 * 
	 * @param player
	 *        A CardGamePlayer type argument specifying the player
	 * @param cards
	 *        A CardList type argument specifying the list of cards
	 */
	public StraightFlush (CardGamePlayer player, CardList cards){
		super(player,cards);
	}
	
	/**
	 * a method for checking if this straightflush beats a specified hand
	 * @param hand
	 *         a specified hand
	 * @return true if it beats, false if doesn't
	 */
	public boolean beats(Hand hand){
		if (hand.getType()=="Straight" || hand.getType()=="Flush" || hand.getType()=="FullHouse" || hand.getType()=="Quad"){
			return true;
		}
		else if (hand.getType()=="StraightFlush" && this.getTopCard().compareTo(hand.getTopCard())>0){
			return true;
		}
		else{
			return false;
		}
		}
	
	
	/**
	 * a method for checking if this is a valid straightflush
	 * @return true if it is and false if it is not
	 */
	public boolean isValid(){
		boolean isStraight = false;
		boolean isFlush = false;
		TempSort();
	
		if ((this.tempsort.get(0)+1==this.tempsort.get(1)) && (this.tempsort.get(1)+1==this.tempsort.get(2)) && (this.tempsort.get(2)+1==this.tempsort.get(3)) && (this.tempsort.get(3)+1==this.tempsort.get(4))){
			isStraight=true;
		}
		
		if (this.getCard(0).getSuit()==this.getCard(1).getSuit() && this.getCard(1).getSuit()==this.getCard(2).getSuit() && this.getCard(2).getSuit()==this.getCard(3).getSuit() && this.getCard(3).getSuit()==this.getCard(4).getSuit()){
			isFlush=true;
		}
		
		if (this.size()==5 && isStraight==true && isFlush==true){
			return true;
		}
		else{return false;}
	}
	
	/**
	 * a method for returning a string specifying the type of this hand
	 * @return the string specifying the type of this hand
	 */
	public String getType(){
		return "StraightFlush";
	}

}
