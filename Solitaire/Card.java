
/**
 * Defines the properties and actions of each card
 * for the game solitaire
 * 
 * @author Arnav Dani
 * @version 12.6.20
 */
public class Card
{
    // instance variables - replace the example below with your own
    private int rank;
    private String suit;
    private boolean isFaceUp;

    /**
     * Constructor for objects of class Card
     * @param r the rank of the card
     * @param s suit of the card
     */
    public Card(int r, String s)
    {
        rank = r;
        suit = s;
        isFaceUp = false;
    }
    
    /**
     * gets the rank of the card
     * @return rank
     */
    public int getRank()
    {
        return rank;
    }
    
    /**
     * gets the suit of the card
     * @return suit
     */
    public String getSuit()
    {
        return suit;
    }
    
    /**
     * says whether a card has a red suit or not
     * @return if suit is diam or heart;
     *          otherwise, false
     */
    public boolean isRed()
    {
        return (suit.equals("d") || suit.equals("h"));
    }
    
    /**
     * says whether the card is facing up
     * @return isFaceUp
     */
    public boolean isFaceUp()
    {
        return isFaceUp;
    }
    
    /**
     * flips the card up by changing
     * the variable
     */
    public void turnUp()
    {
        isFaceUp = true;
    }
    
    
    /**
     * flips the card down by changing variable
     */
    public void turnDown()
    {
        isFaceUp = false;
    }
    
    
    /**
     * outputs a string with the specific
     * file name of a certain card based
     * on specified instructions
     * 
     * @return String filename of card
     */
    public String getFileName()
    {
        String sRank = "";
        if (rank == 10)
            sRank = "t";
        else if (rank == 11)
            sRank = "j";
        else if (rank == 12)
            sRank = "q";
        else if (rank == 13)
            sRank = "k";
        else if (rank == 1)
            sRank = "a";
        else
            sRank = ((Integer)rank).toString();
        if (!isFaceUp)
            return "cards\\back.gif";
        else
            return "cards\\" + sRank + suit + ".gif";
    }
}
