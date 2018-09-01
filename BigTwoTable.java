import javax.swing.*;
import javax.swing.text.DefaultCaret;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;

/**
 * @author Zach Lyu
 * 
 * The BigTwoTable class implements the CardGameTable interface. It is used to build a GUI
 * for the Big Two card game and handle all user actions
 *
 */
public class BigTwoTable implements CardGameTable{
	
	private BigTwoClient game;  //– a card game associates with this table
	private boolean[] selected;  //a boolean array indicating which cards are being selected
	private int activePlayer;  //an integer specifying the index of the active player
	private JFrame frame;  //the main window of the application
	private JPanel bigTwoPanel;  //a panel for showing the cards of each player and the cards played on the table
	private JButton playButton = new JButton("play");  //a “Play” button for the active player to play the selected cards
	private JButton passButton = new JButton("pass");  //a “Pass” button for the active player to pass his/her turn to the next player
	private JTextArea msgArea;  //a text area for showing the current game status as well as end of game messages
	private JTextArea chatArea;//a text area for showing the chat messages;
	private Image[][] cardImages;  //a 2D array storing the images for the faces of the cards
	private Image cardBackImage = new ImageIcon("b.gif").getImage();  //an image for the backs of the cards
	private Image[] avatars = new Image[4];  //an array storing the images for the avatars
	private JLabel[] labels = new JLabel[4];  //an array storing the labels of the players
	private JLabel currentlabel; //the label shows the player who played the last hand
	private JLabel[] avatarl = new JLabel[4];  //an array storing the the avatar labels
	private JMenuItem connect = new JMenuItem("connect"); //for connecting
	private JMenuItem quit = new JMenuItem("quit"); //the quit menu item
	private PlayButtonListener currentPlayListener; //the current playlistener
	private PassButtonListener currentPassListener; //the current passlistener
	private JTextField inputtext;
	private EnterListener currentEnterListener;


	
	/**
	 * a constructor for creating a BigTwoTable
	 * 
	 * @param game  
	 *         a reference to a card game associates with this table
	 */
	public BigTwoTable(BigTwoClient game){
		this.game = game;
		this.frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		avatars[0]= new ImageIcon("batman_128.png").getImage().getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH );;
	    avatars[1]= new ImageIcon("flash_128.png").getImage().getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH );
	    avatars[2]= new ImageIcon("green_lantern_128.png").getImage().getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH );
	    avatars[3]= new ImageIcon("superman_128.png").getImage().getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH );
	    
	    for (int i=0; i<4; i++){
	    	labels[i]=new JLabel(game.getPlayerList().get(i).getName());
	    }
	    

	    connect.addActionListener(new connectMenuItemListener());
	    quit.addActionListener(new QuitMenuItemListener());


	    currentlabel = new JLabel();
		
	    cardImages = new Image[4][13];
		for (int s=0; s<4; s++){
			for (int r=0; r<13; r++){
				String suitname = new String();
				String rankname = new String();
				String Stringname = new String();
				if (s==0){suitname="d";}
				else if (s==1){suitname="c";}
				else if (s==2){suitname="h";}
				else if (s==3){suitname="s";}
				
				if (r==0){
					rankname="a";
				}
				else if (r>0 && r<9){
					rankname=Integer.toString(r+1);
				}
				else if (r==9){
					rankname="t";
				}
				else if (r==10){
					rankname="j";
				}
				else if (r==11){
					rankname="q";
				}
				else if (r==12){
					rankname="k";
				}
				
				Stringname = rankname + suitname;
				
				cardImages[s][r]=new ImageIcon(Stringname+".gif").getImage();
				
				for (int a=0; a<4; a++){
					avatarl[a] = new JLabel("", new ImageIcon(avatars[a]), JLabel.CENTER);
				}
				
			}
		}
		
		
		this.msgArea = new JTextArea();

		this.msgArea.setPreferredSize(new Dimension(380,100000));
		this.msgArea.setMaximumSize(new Dimension(380,10));
		this.msgArea.setEditable(false);
		this.msgArea.setLineWrap(true);
		this.msgArea.setWrapStyleWord(true);
		JScrollPane BTscrollPane = new JScrollPane(msgArea);
		
		
		this.chatArea = new JTextArea();
		this.chatArea.setPreferredSize(new Dimension(380,100000));
		this.chatArea.setEditable(false);
		this.chatArea.setLineWrap(true);
		this.chatArea.setWrapStyleWord(true);
		JScrollPane chatScrollPane = new JScrollPane(chatArea);
		this.chatArea.setMaximumSize(new Dimension(380,100));
		
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new BoxLayout(textPanel,BoxLayout.Y_AXIS));
		textPanel.add(BTscrollPane,BorderLayout.NORTH);

		textPanel.add(chatScrollPane,BorderLayout.SOUTH);
		
		JPanel buttonPanel = new JPanel();
		inputtext = new JTextField(30);
		JLabel messagelabel = new JLabel("message:");
		
		buttonPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.weightx=0.5;
		buttonPanel.add(playButton,c);
		c.gridx=1;
		buttonPanel.add(passButton,c);
		c.gridx=2;
		c.weightx=0;
		c.insets = new Insets(0,50,0,0);
		buttonPanel.add(messagelabel,c);
		c.gridx=3;
		c.anchor = GridBagConstraints.EAST;
		buttonPanel.add(inputtext,c);
		
		frame.add(textPanel, BorderLayout.EAST);
		frame.add(buttonPanel,BorderLayout.SOUTH);
		
		JMenuBar jmb = new JMenuBar();
		frame.setJMenuBar(jmb);
		JMenu option = new JMenu("Game");
		JMenu message = new JMenu("Message");
		jmb.add(option);
		jmb.add(message);
		option.add(connect);
		option.addSeparator();
		option.add(quit);

		
		currentPlayListener = new PlayButtonListener();
		currentPassListener = new PassButtonListener();
		currentEnterListener = new EnterListener();
		
		frame.setSize(1000,900);
		frame.setVisible(true);
	}
	
	public void resetLabels(){
		for (int i=0; i<4; i++){
			bigTwoPanel.remove(labels[i]);
	    }
		for (int i=0; i<4; i++){
	    	labels[i]=new JLabel(game.getPlayerList().get(i).getName());
	    }
		bigTwoPanel.revalidate();
		bigTwoPanel.repaint();
	}
	
	public void removeLabels(){
		for (int i=0; i<4; i++){
			bigTwoPanel.remove(labels[i]);
	    }
		bigTwoPanel.revalidate();
		bigTwoPanel.repaint();
	}
	
	
	/**
	 * Sets the index of the active player (i.e., the current player).
	 * 
	 * @param activePlayer
	 *            an int value representing the index of the active player
	 */
	public void setActivePlayer(int activePlayer){
		this.activePlayer = activePlayer;
	}
	
	/**
	 * Returns an array of indices of the cards selected.
	 * 
	 * @return an array of indices of the cards selected
	 */
	public int[] getSelected(){
		int cardNum = 0;
		int index = 0;
		for (int i=0; i<this.selected.length; i++){
			if (this.selected[i] == true){
				cardNum++;
			}
		}
		
		int[] selectedList = new int[cardNum];
		
		for (int i=0; i<this.selected.length; i++){
			if (this.selected[i] == true){
				selectedList[index]=i;
				index++;
			}
		}
		return selectedList;
		
	}
	
	/**
	 * Resets the list of selected cards to a list of length of current player.
	 */
	public void resetSelected(){
		this.selected = new boolean[game.getPlayerList().get(activePlayer).getNumOfCards()];
		for (int i=0; i<this.selected.length; i++){
			this.selected[i]=false;
		}
	}
	
	
	/**
	 * Repaints the GUI.
	 */
	public void repaint(){
		frame.removeMouseListener((MouseListener)bigTwoPanel);
		bigTwoPanel = new BigTwoPanel();
		frame.addMouseListener((MouseListener)bigTwoPanel);
		bigTwoPanel.setBackground(Color.orange);
		bigTwoPanel.setPreferredSize(new Dimension(600, 200));
		
		frame.add(bigTwoPanel, BorderLayout.WEST);
		frame.setSize(1000,900);
		frame.setVisible(true);
	}
	
	
	/**
	 * Prints the specified string to the message area of the big two table.
	 * 
	 * @param msg
	 *            the string to be printed to the message area of the big two
	 *            table
	 */
	public void printMsg(String msg){
		this.msgArea.append(msg + "\n");
		this.msgArea.setCaretPosition(this.msgArea.getDocument().getLength());
	}
	
	public void chatMsg(String msg){
		this.chatArea.append(msg + "\n");
		this.chatArea.setCaretPosition(this.chatArea.getDocument().getLength());
	}
	
	
	/**
	 * Clears the message area of the big two table.
	 */
	public void clearMsgArea(){
		this.msgArea.setText("");
		this.chatArea.setText("");
	}
	
	/**
	 * Resets the GUI.
	 */
	public void reset(){
		this.resetSelected();
		this.clearMsgArea();
		this.enable();
		
	}
	
	/**
	 * Enables user interactions.
	 */
	public void enable(){
		playButton.removeActionListener(currentPlayListener);
		passButton.removeActionListener(currentPassListener);
		inputtext.removeActionListener(currentEnterListener);
		
		playButton.addActionListener(currentPlayListener);
		passButton.addActionListener(currentPassListener);
		inputtext.addActionListener(currentEnterListener);
		
	}
	
	/**
	 * Disables user interactions.
	 */
	public void disable(){
		playButton.removeActionListener(currentPlayListener);
		passButton.removeActionListener(currentPassListener);
		inputtext.removeActionListener(currentEnterListener);
		frame.removeMouseListener((MouseListener)bigTwoPanel);
	}
	
	/**
	 * @author Lv Ruyi
	 * 
	 * an inner class that extends the JPanel class and implements the MouseListener interface
	 *
	 */
	class BigTwoPanel extends JPanel implements MouseListener{
		
		
		/**
		  * Overrides the paintComponent() method inherited from the JPanel class to draw the card game table
		  */
		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			
			for (int p=0; p<4; p++){
				labels[p].setLocation(70 , 25+ 160*p);
				this.add(labels[p]);
				
				avatarl[p].setLocation(50, 50+ 160*p);
				this.add(avatarl[p]);
				
				drawCards(g, p);
			}
			

			currentlabel.setText("Played by "+ game.getPlayerList().get(((BigTwoClient)game).getLastIdx()).getName());
			currentlabel.setPreferredSize(new Dimension(150, 50));
			currentlabel.setLocation(50 , 630);
			this.add(currentlabel);
			
			revalidate();
			if (game.getHandsOnTable().size() ==0){
				g.drawImage(cardBackImage, 60 , 670, 80, 100, this);
			}
			
			else{
				int lasthandsize = game.getHandsOnTable().get(game.getHandsOnTable().size()-1).size();
				for (int i=0; i<lasthandsize; i++){
					int ss = game.getHandsOnTable().get(game.getHandsOnTable().size()-1).getCard(i).getSuit();
					int rr = game.getHandsOnTable().get(game.getHandsOnTable().size()-1).getCard(i).getRank();
					
					g.drawImage(cardImages[ss][rr], 60+ 20*i , 670, 80, 100, this);
				}
			}

		}
		
		/**
		 * Draw the cards on the table given a specific player
		 * 
		 * @param g
		 *         the graphics item
		 * @param player
		 *         the player index
		 */       
		 
		public void drawCards(Graphics g, int player){
			
			if (player == activePlayer && game.endOfGame()==false){
				for (int i=0; i < game.getPlayerList().get(player).getNumOfCards(); i++){
					int ss = game.getPlayerList().get(player).getCardsInHand().getCard(i).getSuit();
					int rr = game.getPlayerList().get(player).getCardsInHand().getCard(i).getRank();
					

					if (player == activePlayer && selected[i]==true){
						g.drawImage(cardImages[ss][rr], 180+ 20*i , 25+ 160*player, 80, 100, this);
					}
					else{
						g.drawImage(cardImages[ss][rr], 180+ 20*i , 45+ 160*player, 80, 100, this);
					}
					
				}
			}
			else{
				for (int i=0; i < game.getPlayerList().get(player).getNumOfCards(); i++){
					g.drawImage(cardBackImage, 180+ 20*i , 45+ 160*player, 80, 100, this);
				}
			}
			
		}
		
		/**
		 * Do actions when mouse clicked
		 * 
		 * @param e
		 *         Mouse event item
		 * 
		 */ 
		 @Override
		 public void mouseClicked(MouseEvent e){
			 
			 int x = e.getX();
			 int y = e.getY();
				
				 
				
			 int Numofcards = game.getPlayerList().get(activePlayer).getNumOfCards();
			 if (y>=160*activePlayer+45 && y<=160*activePlayer+145 && x>=180 && x<=240+20*Numofcards){
				 
				 if (x<=240+20*Numofcards && x>=160+20*Numofcards){
					 
					 if (selected[Numofcards-1]==true){
							 selected[Numofcards-1]=false;
							 }
					 else{
							 selected[Numofcards-1]=true;
						 }
					 }
				 else{
					 int index = (x-180)/20;
					 if (selected[index]==true){
							 selected[index]=false;
						 }
					 else{
							 selected[index]=true;
						 }}
				 revalidate();
				 repaint();
				 }
			 
		 }


		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

	
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/**
	 * @author Lv Ruyi
	 * 
	 * an inner class specifying the play button listener that implements the ActionListener interface
	 *
	 */
	class PlayButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event){
			game.makeMove(activePlayer, getSelected());
			resetSelected();
			
		}
	}
	/**
	 * @author Lv Ruyi
	 * 
	 * an inner class specifying the pass button listener that implements the ActionListener interface
	 *
	 */
	class PassButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event){
			game.makeMove(activePlayer, getSelected());
			resetSelected();
			
		}
	}
	
	/**
	 * @author Lv Ruyi
	 * 
	 * an inner class specifying the connect menu listener that implements the ActionListener interface
	 *
	 */
	class connectMenuItemListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event){
			game.makeConnection();
		}
	}
	
	/**
	 * @author Lv Ruyi
	 * 
	 * an inner class specifying the quit menu listener that implements the ActionListener interface
	 *
	 */
	class QuitMenuItemListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event){;
			System.exit(0);
		}
	}
	

	
	class EnterListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
        	try{
        		game.sendMessage(new CardGameMessage(CardGameMessage.MSG,activePlayer,inputtext.getText()));
        	}catch (Exception ex) {
        		ex.printStackTrace();
        		
        	}
        	inputtext.setText("");
        	inputtext.requestFocus();
        	}
        	
		}

}
