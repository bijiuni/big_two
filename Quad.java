import java.util.ArrayList;

/**
 * This class is used to model a quad in BigTwoGame and is a subclass of Hand
 * 
 * @author Lv Ruyi
 *
 */
public class Quad extends Hand {
	private static final long serialVersionUID = -3711761437629470849L;
	
	/**
	 * a constructor for building a quad with the specified player and list of cards.
	 * 
	 * @param player
	 *        A CardGamePlayer type argument specifying the player
	 * @param cards
	 *        A CardList type argument specifying the list of cards
	 */
	public Quad (CardGamePlayer player, CardList cards){
		super(player,cards);
	}
	
	/**
	 * a method for retrieving the top card of this hand
	 * @return the top card of this quad
	 */
	public Card getTopCard(){
		Card TopCard = this.getCard(0);
		ArrayList<Card> list1 = new ArrayList<Card>();
		ArrayList<Card> list2 = new ArrayList<Card>();
		for (int i=0; i<this.size();i++){
			if (this.getCard(i).getRank()==this.getCard(0).getRank()){
				list1.add(this.getCard(i));
			}
			else{list2.add(this.getCard(i));}
		}
		
		if (list1.size()==4){
			TopCard = list1.get(0);
			for (int j=0; j<4; j++){
				if(list1.get(j).compareTo(TopCard)>0){
					TopCard = list1.get(j);
				}
			}
		}

		else{
			TopCard = list2.get(0);
			for (int j=0; j<4; j++){
				if(list2.get(j).compareTo(TopCard)>0){
					TopCard = list2.get(j);}
		}}
		return TopCard;
	}
	
	/**
	 * a method for checking if this quad beats a specified hand
	 * @param hand
	 *         a specified hand
	 * @return true if it beats, false if doesn't
	 */
	public boolean beats(Hand hand){
		if (hand.getType()=="Straight" || hand.getType()=="Flush" || hand.getType()=="FullHouse"){
			return true;
		}
		else if (hand.getType()=="Quad" && this.getTopCard().compareTo(hand.getTopCard())>0){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * a method for checking if this is a valid quad
	 * @return true if it is and false if it is not
	 */
	public boolean isValid(){
		TempSort();
		boolean firstfour = false;
		boolean lastfour = false;
		
		if (this.tempsort.get(0)==this.tempsort.get(1) && this.tempsort.get(1)==this.tempsort.get(2) && this.tempsort.get(2)==this.tempsort.get(3)){
			firstfour = true;
		}
		if (this.tempsort.get(1)==this.tempsort.get(2) && this.tempsort.get(2)==this.tempsort.get(3) && this.tempsort.get(3)==this.tempsort.get(4)){
			lastfour = true;
		}
		
		if (this.size()==5 && (firstfour == true || lastfour==true)){
			return true;
		}
		else{return false;}
	}
	
	/**
	 * a method for returning a string specifying the type of this hand
	 * @return the string specifying the type of this hand
	 */
	public String getType(){
		return "Quad";
	}

}
