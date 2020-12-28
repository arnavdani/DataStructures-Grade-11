import java.util.*;

/**
 * Provides the methods for the display to play the game
 * solitaire which is about getting all cards in order
 * seperated by suit
 * 
 * @author Arnav Dani
 * @version 12.6.20
 */
public class Solitaire
{

    /**
     * runs the game by creating a new instance of it
     * 
     * @param args input arguments passed in
     */
    public static void main(String[] args)
    {
        new Solitaire();
    }

    private Stack<Card> stock;
    private Stack<Card> waste;
    private Stack<Card>[] foundations;
    private Stack<Card>[] piles;
    private SolitaireDisplay display;

    /**
     * constructor for solitaire
     *  sets up all parts of the game
     */
    public Solitaire()
    {
        foundations = new Stack[4];
        piles = new Stack[7];
        stock = new Stack<Card>();
        waste = new Stack<Card>();

        //initializing all stacks
        for (int i = 0; i < 7; i++)
        {
            if (i < 4)
                foundations[i] = new Stack<Card>();
            piles[i] = new Stack<Card>();
        }

        createStock();
        deal();

        display = new SolitaireDisplay(this);

    }

    /**
     * returns the top card on the stock
     * @return top card or null if empty
     */
    public Card getStockCard()
    {
        if (!stock.isEmpty())
            return stock.peek();
        return null;
    }

    //returns the card on top of the waste,
    //or null if the waste is empty
    /**
     * returns the top card on the waste
     *  or null if empty
     * @return the card or null if waste is empty
     */
    public Card getWasteCard()
    {
        if (!waste.isEmpty())
            return waste.peek();
        return null;
    }

    /**
    public Stack<Card> getWasteStack()
    {
    Stack<Card> wasteStack = new Stack<Card>();
    while (!waste.isEmp
    }
     */

    /**
     * gets the card on the top of the foundation
     * @param index index of foundation
     * @precondition index >= 0 < 4
     * @return card at the top or null if empty
     */
    public Card getFoundationCard(int index)
    {
        if (!foundations[index].isEmpty())
            return foundations[index].peek();
        return null;
    }

    /**
     * returns a reference to a pile
     * @param index index of specific pile
     * @precondition  0 <= index < 7
     * @return the stack at an index
     */
    public Stack<Card> getPile(int index)
    {
        return piles[index];
    }

    /**
     * tells whether the player has won the 
     * game by checking if every foundation has a king on top
     * @return true if piles are empty;
     *              otherwise, false
     */
    public boolean hasWon()
    {
        for (int i = 0; i < 4; i++)
        {
            if (foundations[i].isEmpty() || 
                foundations[i].peek().getRank() != 13)
                return false;
        }
        return true;
    }

    /**
     * creates all of the cards and shuffles them onto the stock
     */
    public void createStock()
    {
        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < 4; i++)
        {
            String s = "";
            if (i == 0)
                s = "c";
            else if (i == 1)
                s = "d";
            else if (i == 2)
                s = "h";
            else if (i == 3)
                s = "s";

            cards.add(new Card(1, s));
            cards.add(new Card(2, s));
            cards.add(new Card(3, s));
            cards.add(new Card(4, s));
            cards.add(new Card(5, s));
            cards.add(new Card(6, s));
            cards.add(new Card(7, s));
            cards.add(new Card(8, s));
            cards.add(new Card(9, s));
            cards.add(new Card(10, s));
            cards.add(new Card(11, s));
            cards.add(new Card(12, s));
            cards.add(new Card(13, s));
        }

        for (int i = 51; i >= 0; i--)
        {
            int rand = (int)(Math.random() * cards.size());
            Card removed = cards.remove(rand);
            stock.push(removed);

        }
    }

    /**
     * deals a set amount of cards from the stock
     * onto all of the piles
     */
    public void deal()
    {
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < i + 1; j++)
            {   
                Card popped = stock.pop();
                piles[i].push(popped);
            }
            piles[i].peek().turnUp();
        }
    }

    /**
     * deals 3 cards from the stock to the waste
     */
    public void dealThreeCards()
    {
        int numCards = Math.min(3, stock.size());
        for (int i = 0; i < numCards; i++)
        {
            Card popped = stock.pop();
            popped.turnUp();
            waste.push(popped);
        }
    }

    /**
     * takes all the cards from the waste
     * and puts them on the stock
     */
    public void resetStock()
    {
        while (!waste.isEmpty())
        {
            Card current = waste.pop();
            current.turnDown();
            stock.push(current);
        }
    }

    /**
     * determines whether a card can be added to a pile
     * 
     * 
     * @param card the card being checked
     * @param index the pile being checked
     * 
     * All cards that are added must be face up
     * A card can be added to an empty pile if it is a king
     * Otherwise, if the pile is not empty, a card can be
     * added if it is of an opposite color and 1 rank lesser
     * 
     * @return if pile is empty if card is king, if pile is not empty
     *          if card is 1 rank lesser of opposite color;
     *                  otherwise, false
     */
    public boolean canAddToPile(Card card, int index)
    {        
        if (piles[index].isEmpty())
            return card.getRank() == 13;

        Card top = piles[index].peek();
        if (top.isFaceUp())
        {
            boolean topRed = top.isRed();
            if (card.isRed() != topRed)
                if (card.getRank() + 1 == top.getRank())
                    return true;
        }
        return false;
    }

    /**
     * removes all face up cards from a pile
     * 
     * @param index pile to remove from
     * @return a stack of all removed cards
     */
    public Stack<Card> removeFaceUpCards(int index)
    {
        Stack<Card> removed = new Stack<Card>();
        while (!piles[index].isEmpty() && piles[index].peek().isFaceUp())
        {
            removed.push(piles[index].pop());
        }
        return removed;
    }

    /**
     * adds a stack of cards to a specified pile
     * @param cards stack of cards to ad
     * @param index index of the pile to add to
     */
    public void addToPile(Stack<Card> cards, int index)
    {
        while (!cards.isEmpty())
        {
            piles[index].push(cards.pop());
        }
    }

    /**
     * determines whether a card can be added to a foundation
     * 
     * @param card the card being checked
     * @param index index of the foundation checked
     * 
     * a card can only be added to an empty foundation if
     * it is an ace, otherwise the card must have the same
     * suit and 1 higher rank than the previous card
     * 
     * @return if card is ace if empty and if card is same suit
     *  with higher rank if not empty;
     *                  otherwise, false
     */
    public boolean canAddToFoundation(Card card, int index)
    {
        if (foundations[index].isEmpty())

            return (card.getRank() == 1);
        else
        {    
            Card top = foundations[index].peek();
            return (card.getSuit().equals(top.getSuit())
                && card.getRank() == top.getRank() + 1);
        }
    }

    /**
     * determines the actions that should be taken
     * when the stock is clicked
     * 
     * if nothing else is selected, either 
     *  transfer all the cards from waste to stock
     *      if the stock is empty
     *  or deal 3 cards to the waste
     *  
     */
    public void stockClicked()
    {
        if (!display.isWasteSelected() && !display.isPileSelected())
        {
            if (stock.isEmpty())
            {
                resetStock();
            }
            else
            { 
                dealThreeCards();
                display.addPoints(-5);
            }
        }
    }

    /**
     * determines the action that should be taken
     * if the waste is clicked
     * 
     * if the waste is not empty and its not selected
     *  it should be selected
     *  
     * if it is already selected, it should be unelected
     * 
     * if the waste is empty, everything should be unselected
     */
    public void wasteClicked()
    {
        if (!waste.isEmpty())
        {
            if (!display.isWasteSelected()) 
            {
                if (!display.isPileSelected())
                {
                    display.selectWaste();
                }
            }
            else
                display.unselect();
        }
        else
            display.unselect();
    }

    //precondition:  0 <= index < 4
    //called when given foundation is clicked
    /**
     * determines the actions that need to be taken if
     * foundation is clicked
     * 
     * @param index index of foundation between 0 and <4
     * 
     * if the waste is already selected
     *  if the card can go on the foundation, it should be added
     *      and the user should get 50 points
     * 
     * if a pile is already selected, the top card of the pile
     * should be checked if it can be added to the foundation
     * and the user should get 50 points if it can be added
     * the new top card of the pile should be automatically 
     * flipped over
     * 
     * if only the foundation is selected, nothing happens
     */
    public void foundationClicked(int index)
    {
        if (display.isWasteSelected())
        {
            Card removed = waste.peek();
            if (canAddToFoundation(removed, index))
            {
                foundations[index].push(removed);
                waste.pop();
                display.addPoints(50);
            }
        }
        else if (display.isPileSelected())
        {
            Card removed = piles[display.selectedPile()].peek();
            if (canAddToFoundation(removed, index))
            {
                foundations[index].push(removed);
                piles[display.selectedPile()].pop();
                if (!piles[display.selectedPile()].isEmpty())
                    piles[display.selectedPile()].peek().turnUp();
            }
            display.addPoints(50);
        }
        display.unselect();
    }

    //precondition:  0 <= index < 7
    //called when given pile is clicked
    /**
     * determines action that should be taken if a pile is clicked
     * @param index index of clicked piles between 0 and < 7
     * 
     * if the waste is selected and its not empty
     * the top card of the waste should be checked if it can
     * be moved to pile and should be if it is valid
     *  user should get 5 points
     * 
     * if nothing is selected, the current pile should be selected
     *  if the top card is face up, otherwise the top card should be
     *  turned up
     *  
     * if a different pile is selected and its not the current pile,
     * all the face up cards from the different pile should be attempted
     * to be transfered to the current pile and the user should get 10 
     * points is this action is possible
     * 
     * otherwise nothing happens and everything is unselected
     */
    public void pileClicked(int index)
    {
        if (display.isWasteSelected() && !waste.isEmpty())
        {
            Card removed = waste.peek();
            if (canAddToPile(removed, index))
            {
                piles[index].push(removed);
                waste.pop();
                display.addPoints(5);
            }
            display.unselect();
        }
        else
        {
            if (!display.isPileSelected())
            {
                if (!piles[index].isEmpty())
                    if (piles[index].peek().isFaceUp())
                    {
                        display.selectPile(index);
                    }
                    else
                    {
                        piles[index].peek().turnUp();
                    }
            }
            else
            {               
                if (index != display.selectedPile())
                {
                    Stack<Card> faceUps = 
                        removeFaceUpCards(display.selectedPile());
                    if (!faceUps.isEmpty() && 
                        canAddToPile(faceUps.peek(), index))
                    {
                        addToPile(faceUps, index);
                        if (!piles[display.selectedPile()].isEmpty())
                            piles[display.selectedPile()].peek().turnUp();
                        display.addPoints(10);
                    }
                    else
                        addToPile(faceUps, display.selectedPile());    
                }
                display.unselect();
            }

        }
    }
}
