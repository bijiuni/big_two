import java.util.*;
import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;

public class BigTwoClient implements CardGame, NetworkGame{
	
	private int numOfPlayers;
	private Deck deck;
	private ArrayList<CardGamePlayer> playerList;
	private ArrayList<Hand> handsOnTable;
	private int playerID;
	private String playerName;
	private String serverIP;
	private int serverPort;
	private Socket sock;
	private ObjectOutputStream oos;
    private int currentIdx;
    private BigTwoTable table;
    private int lastIdx;
    private ObjectInputStream ois;
	
	public BigTwoClient(){
		CardGamePlayer play1 = new CardGamePlayer();
		CardGamePlayer play2 = new CardGamePlayer();
		CardGamePlayer play3 = new CardGamePlayer();
		CardGamePlayer play4 = new CardGamePlayer();
		this.numOfPlayers = 4;
		this.playerList = new ArrayList<CardGamePlayer> ();
		this.playerList.add(play1);
		this.playerList.add(play2);
		this.playerList.add(play3);
		this.playerList.add(play4);
		String name = JOptionPane.showInputDialog("What's your name?");
		this.playerName = name;
		this.table = new BigTwoTable(this);
		this.makeConnection();
	}
	
	
	//CardGame interface methods
	
	public int getNumOfPlayers(){
		return this.playerList.size();
	}
	
	public Deck getDeck(){
		return this.deck;
	}
	
	public ArrayList<CardGamePlayer> getPlayerList(){
		return this.playerList;
	}
	
	public ArrayList<Hand> getHandsOnTable(){
		return this.handsOnTable;
	}
	
	public int getCurrentIdx(){
		return this.currentIdx;
	}
	
	public int getLastIdx(){
		return this.lastIdx;
	}
	

	public void setLastIdx(int i){
		this.lastIdx = i;
	}
	
    public void start(Deck deck){
		
		this.deck = deck;
		this.handsOnTable = new ArrayList<Hand> ();
	
		for (int p=0; p<4; p++){
			this.playerList.get(p).removeAllCards();
		}

		for (int p=0; p<4; p++){
			for (int i=0; i<13; i++){
				this.playerList.get(p).addCard(deck.getCard(13*p+i));
			}
		}
		
		for (int p=0; p<4; p++){
			this.playerList.get(p).sortCardsInHand();
		}
		
		for (int p=0; p<4; p++){
			if (this.playerList.get(p).getCardsInHand().getCard(0).getSuit()==0 && this.playerList.get(p).getCardsInHand().getCard(0).getRank()==2){
				this.currentIdx =p;
				this.lastIdx=p;
				
			}
		}
		table.setActivePlayer(this.playerID);
		table.resetSelected();
		this.table.repaint();
		this.table.reset();
		this.table.removeLabels();
		this.table.resetLabels();
		this.table.printMsg(this.playerList.get(this.currentIdx).getName() + "'s turn:"+ "\n");
		}

    public void makeMove(int playerID, int[] cardIdx){
	this.sendMessage(new CardGameMessage(CardGameMessage.MOVE,-1,cardIdx));
    }


    public synchronized void checkMove(int playerID, int[] cardIdx){
	boolean legal = false;
	

	Hand ReturnHand = composeHand(this.getPlayerList().get(playerID),this.getPlayerList().get(playerID).play(cardIdx));
	
	
	if (cardIdx.length==0){
		legal = true;
	}
	
	else if (ReturnHand == null){
		legal = true;
	}
	
	else if (this.handsOnTable.size()==0){
		Card diamond3 = new Card(0,2);
		if (ReturnHand.contains(diamond3)){
			legal = true;
		}
	}
	
	else if (this.getLastIdx() == this.currentIdx){
		legal = true;
	}
	
	else if (cardIdx.length == this.handsOnTable.get(this.handsOnTable.size()-1).size() && ReturnHand.beats(this.handsOnTable.get(this.handsOnTable.size()-1))){
		legal = true; 
	}
	
	if (playerID != currentIdx){
		legal = false;
	}
	
	if (cardIdx.length==0 && this.getLastIdx() != this.currentIdx && playerID == currentIdx){
		this.currentIdx ++;
		this.currentIdx = this.currentIdx % 4;
		this.table.printMsg("{pass}"+ "\n");
		if (this.endOfGame()!=true){
			this.table.printMsg(this.playerList.get(this.currentIdx).getName() + "'s turn:"+ "\n");
		}
		this.table.repaint();
	}
	
	else if (cardIdx.length==0){

		this.table.printMsg("Not a legal move"+ "\n");
		this.table.repaint();
	}
	
	else if (ReturnHand == null || legal == false){
		this.table.printMsg("Not a legal move!!!"+ "\n");
		this.table.repaint();

	}
	
	else {
		System.out.print("{" + ReturnHand.getType()+ "} " + ReturnHand.toString()+ "\n");
		this.table.printMsg("{" + ReturnHand.getType()+ "} " + ReturnHand.toString()+ "\n");
		this.playerList.get(this.currentIdx).removeCards(ReturnHand);
		this.handsOnTable.add(ReturnHand);
		this.setLastIdx(this.currentIdx);
		this.currentIdx ++;
		this.currentIdx = this.currentIdx % 4;
		if (this.endOfGame()!=true){
			this.table.printMsg(this.playerList.get(this.currentIdx).getName() + "'s turn:"+ "\n");
		}
		
		this.table.repaint();
	}
	
	if (this.endOfGame()==true){
		this.table.disable();
		this.table.removeLabels();
		String endMSG = "Game ends" + "\n";
		for (int i=0; i<4; i++){
			if (this.playerList.get(i).getNumOfCards()==0){
				endMSG = endMSG + (this.playerList.get(i).getName()+ " wins the game." + "\n" );
			}
			else {
				endMSG = endMSG +(this.playerList.get(i).getName()+ " has " + this.playerList.get(i).getNumOfCards() + " cards in hand." +"\n" );
			}
		}
		int res = JOptionPane.showOptionDialog(null, endMSG, "Result", JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, null, null, null);
		
		if (res == 0){
			this.sendMessage(new CardGameMessage(CardGameMessage.READY,-1,null));
		}
	}
}
    
    public boolean endOfGame(){
		boolean temp = false;
		for (int p=0; p<4; p++){
			if (this.getPlayerList().get(p).getNumOfCards()==0){
				temp=true;
			}
		}
		return temp;
	}
    
    
    //NetworkGame interface methods
    
    public int getPlayerID(){
    	return this.playerID;
    }
	
    
    public void setPlayerID(int playerID){
    	this.playerID = playerID;
    }
    
    public String getPlayerName(){
    	return this.playerName;
    }
    
    public void setPlayerName(String playerName){
    	this.playerName = playerName;
    }
    
    public String getServerIP(){
    	return this.serverIP;
    }
    
    public void setServerIP(String serverIP){
    	this.serverIP = serverIP;
    }
    
    public int getServerPort(){
    	return this.serverPort;
    }
    
    public void setServerPort(int serverPort){
    	this.serverPort = serverPort;
    }
    
    public void makeConnection(){
    	try{
    		this.serverIP = "127.0.0.1";
    		this.serverPort = 2396;
    		this.sock = new Socket(this.serverIP,this.serverPort);
    		this.oos = new ObjectOutputStream(sock.getOutputStream());
    		this.ois = new ObjectInputStream(sock.getInputStream());
    		System.out.println("networking established");
    		
    	   } catch (Exception ex) {
    	     ex.printStackTrace();
    	   }
    	
    	Thread readerThread = new Thread(new ServerHandler());
    	readerThread.start();
    	
    	this.sendMessage(new CardGameMessage(CardGameMessage.JOIN,-1,this.playerName));
    	this.sendMessage(new CardGameMessage(CardGameMessage.READY,-1,null));
    }
    
    public void parseMessage(GameMessage message){
    	
    	switch (message.getType()) {
    	case CardGameMessage.PLAYER_LIST:
    		setPlayerID(message.getPlayerID());
    		for (int p=0; p<4; p++){
    			playerList.get(p).setName(((String[])message.getData())[p]);
    		}
    		break;
		case CardGameMessage.JOIN:
			playerList.get(message.getPlayerID()).setName((String)message.getData());
			this.numOfPlayers++;
			break;
		case CardGameMessage.FULL:
			this.table.printMsg("The server is full and cannot join the game" +"/n");
			break;
		case CardGameMessage.QUIT:
			playerList.get(message.getPlayerID()).setName("");
			if (this.endOfGame()==false){
				this.sendMessage(new CardGameMessage(CardGameMessage.QUIT,-1,null));
				this.table.disable();
				this.table.removeLabels();
			}
			this.sendMessage(new CardGameMessage(CardGameMessage.READY,-1,null));
			break;
		case CardGameMessage.READY:
			this.table.printMsg(this.playerList.get(message.getPlayerID())+"is ready" +"/n");
			break;
		case CardGameMessage.START:
			this.start((Deck)message.getData());
			break;
		case CardGameMessage.MOVE:
			this.checkMove(message.getPlayerID(), (int[])message.getData());
			break;
		case CardGameMessage.MSG:
			this.table.chatMsg((String)message.getData());
			break;
		default:
			this.table.printMsg("Wrong message type: " + message.getType());
			break;
		}
    }
    
    public void sendMessage(GameMessage message){
    	try{
    		this.oos.writeObject(message);
    	}catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	
    }
    
    // inner class
    
    class ServerHandler implements Runnable{
    	public void run() {
			CardGameMessage message;
			try {
				
				while (true) {
					message = (CardGameMessage) ois.readObject();
					if (message != null){
						parseMessage(message);
					}
					
				} 
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
    	}
    
    //public static methods
    
    public static void main(String[] args) {
		BigTwoClient nClient = new BigTwoClient();
	
	}
    
    public static Hand composeHand(CardGamePlayer player, CardList cards){
		if (cards == null){
			return null;
		}
		
		else if (cards.size()==1){
			Single rhand = new Single(player,cards);
			return rhand;
		}
		
		else if (cards.size()==2){
			Pair rhand = new Pair (player,cards);
			if (rhand.isValid()==true){
				return rhand;
			}
			else{return null;}
		}
		
		else if (cards.size()==3){
			Triple rhand = new Triple (player,cards);
			if (rhand.isValid()==true){
				return rhand;
			}
			else{return null;}
		}
		
		else if (cards.size()==5){
			Straight shand = new Straight(player,cards);
			Flush fhand = new Flush(player,cards);
			FullHouse fhhand = new FullHouse(player,cards);
			Quad qhand = new Quad(player,cards);
			StraightFlush sfhand = new StraightFlush(player,cards);
			
			if (sfhand.isValid()==true){
				return sfhand;
			}
			else if (qhand.isValid()==true){
				return qhand;
			}
			else if (fhhand.isValid()==true){
				return fhhand;
			}
			else if (fhand.isValid()==true){
				return fhand;
			}
			else if(shand.isValid()==true){
				return shand;
			}
			else {return null;}
		}
		
		else{
			return null;
		}
		
		
	}

}
