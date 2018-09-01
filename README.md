# Big Two (Chinese Card Game)

This project implement a Chinese card game called Big Two. You can refer to the [rules here](https://www.pagat.com/climbing/bigtwo.html).

The project makes use of OO design, GUI design, networking and multi-threading.

## The following rules are adopted in this implementation:

• A standard 52 card pack is used.  
• The order of ranks from high to low is 2, A, K, Q, J, 10, 9, 8, 7, 6, 5, 4, 3.  
• The order of suits from high to low is Spades, Hearts, Clubs, Diamonds.  
• There are always four players in a game.  
• Each player holds 13 (randomly assigned) cards at the beginning of the game.  
• The player holding the Three of Diamonds will begin the game by playing a hand of legal  
combination of cards that includes the Three of Diamonds.  
• Players take turns to play by either playing a hand of legal combination of cards that
beats the last hand of cards played on the table, or by passing his turn to the next player.  
• A hand of legal combination of cards can only be beaten by another better hand of legal
combination of cards with the same number of cards.  
• A player cannot pass his turn to the next player if he is the one who played the last hand
of cards on the table. In this case, he can play a hand of any legal combination of cards
regardless of the last hand he played on the table.  
• The game ends when any of the players has no more cards in his hand.  

## Legal combinations of Cards

• Single. This hand consists of only one single card. The only card in a single is referred to
as the top card of this single. A single with a higher rank beats a single with a lower rank.
For singles with the same rank, the one with a higher suit beats the one with a lower suit.  
• Pair. This hand consists of two cards with the same rank. The card with a higher suit in a
pair is referred to as the top card of this pair. A pair with a higher rank beats a pair with a
lower rank. For pairs with the same rank, the one containing the highest suit beats the
other.  
• Triple. This hand consists of three cards with the same rank. The card with the highest
suit in a triple is referred to as the top card of this triple. A triple with a higher rank beats
a triple with a lower rank.  
• Straight. This hand consists of five cards with consecutive ranks. For the sake of
simplicity, 2 and A can only form a straight with K but not with 3. The card with the
highest rank in a straight is referred to as the top card of this straight. A straight having a
top card with a higher rank beats a straight having a top card with a lower rank. For
straights having top cards with the same rank, the one having a top card with a higher suit
beats the one having a top card with a lower suit.  
• Flush. This hand consists of five cards with the same suit. The card with the highest rank
in a flush is referred to as the top card of this flush. A flush always beats any straights. A
flush with a higher suit beats a flush with a lower suit. For flushes with the same suit, the
one having a top card with a higher rank beats the one having a top card with a lower
rank.  
• Full House. This hand consists of five cards, with two having the same rank and three
having another same rank. The card in the triplet with the highest suit in a full house is
referred to as the top card of this full house. A full house always beats any straights and
flushes. A full house having a top card with a higher rank beats a full house having a top
card with a lower rank.  
• Quad. This hand consists of five cards, with four having the same rank. The card in the
quadruplet with the highest suit in a quad is referred to as the top card of this quad. A
quad always beats any straights, flushes and full houses. A quad having a top card with a
higher rank beats a quad having a top card with a lower rank.  
• Straight Flush. This hand consists of five cards with consecutive ranks and the same suit.
For the sake of simplicity, 2 and A can only form a straight flush with K but not with 3.
The card with the highest rank in a straight flush is referred to as the top card of this
straight flush. A straight flush always beats any straights, flushes, full houses and quads.
A straight flush having a top card with a higher rank beats a straight flush having a top
card with a lower rank. For straight flushes having top cards with the same rank, the one
having a top card with a higher suit beats one having a top card with a lower suit.  


### Graphical User Interface

• A panel showing the cards of each player as well as the cards played on the table.  
• For each player, the panel shows his/her name and an avatar for him/her.  
• For the active player, the panel shows the faces of his/her cards.  
• The program allow the active player to select and deselect his/her cards by mouse clicks. The
selected cards are in a “raised” position.  
• “Play” button for the active player to play the selected cards.  
• “Pass” button for the active player to pass his/her turn to the next player.  
• A text area shows the current game status as well as end of game messages.  
• “Restart” menu item for restarting the game.  
• “Quit” menu item for quitting the game.  



## Deployment

A sample window is shown below.

![Graphical Interface](https://github.com/bijiuni/big_two/blob/master/card_img/2h.gif)

## Authors

* **Kennoth Wong** - *Initial work*

* **Zach Lyu (Lv Ruyi)** - *Details are commented in the files*

## Acknowledgments

* The card images were downloaded from [https://www.waste.org/~oxymoron/cards/](https://www.waste.org/~oxymoron/cards/)
* The avatar icons were downloaded from [http://www.123freeicons.com/super-heroes-sigma-style-icons/#.Vuf0DOJ95aQ](http://www.123freeicons.com/super-heroes-sigma-style-icons/#.Vuf0DOJ95aQ)
