import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 * runs and draws the solitaire game
 * @author HarkerCS
 * @author Arnav Dani added score
 * @version 12.7.20
 */
public class SolitaireDisplay extends JComponent implements MouseListener
{
    private static final int MAG = 2;
    private static final int CARD_WIDTH = 73 * MAG;
    private static final int CARD_HEIGHT = 97 * MAG;
    private static final int SPACING = 5 * MAG;  //distance between cards
    private static final int FACE_UP_OFFSET = 15 * MAG;  
                                    //distance for cascading face-up cards
    private static final int FACE_DOWN_OFFSET = 5 * MAG;  
                                    //distance for cascading face-down cards

    private JFrame frame;
    private int selectedRow = -1;
    private int selectedCol = -1;
    private Solitaire game;
    int score;

    /**
     * constructor for solitaire class
     * @param game solitaire game to be played
     */
    public SolitaireDisplay(Solitaire game)
    {
        this.game = game;

        frame = new JFrame("Solitaire");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);

        this.setPreferredSize(new Dimension(CARD_WIDTH * 7 + SPACING * 8, 
                    CARD_HEIGHT * 2 + SPACING * 3 + 
                        FACE_DOWN_OFFSET * 7 + 13 * FACE_UP_OFFSET));
        this.addMouseListener(this);

        frame.pack();
        frame.setVisible(true);

        score = 0;
    }

    /**
     * increments the score a certain amount
     * @param num amount to increase score by
     */
    public void addPoints(int num)
    {
        score += num;
    }

    /**
     * repaints the game when called by painting each card by where it is
     * 
     * Also controls win screen and win condition
     * 
     * @param g graphics used
     */
    public void paintComponent(Graphics g)
    {
        //background
        g.setColor(new Color(0, 128, 0));
        g.fillRect(0, 0, getWidth(), getHeight());

        //face down
        drawCard(g, game.getStockCard(), SPACING, SPACING);

        //stock
        drawCard(g, game.getWasteCard(), SPACING * 2 + CARD_WIDTH, SPACING);
        if (selectedRow == 0 && selectedCol == 1)
            drawBorder(g, SPACING * 2 + CARD_WIDTH, SPACING);

        //aces
        for (int i = 0; i < 4; i++)
            drawCard(g, game.getFoundationCard(i), 
                        SPACING * (4 + i) + CARD_WIDTH * (3 + i), SPACING);

        //piles
        for (int i = 0; i < 7; i++)
        {
            Stack<Card> pile = game.getPile(i);
            int offset = 0;
            for (int j = 0; j < pile.size(); j++)
            {
                drawCard(g, pile.get(j), SPACING + (CARD_WIDTH + SPACING) * i, 
                                CARD_HEIGHT + 2 * SPACING + offset);
                if (selectedRow == 1 && selectedCol == i 
                                        && j == pile.size() - 1)
                    drawBorder(g, SPACING + (CARD_WIDTH + SPACING) * i, 
                            CARD_HEIGHT + 2 * SPACING + offset);

                if (pile.get(j).isFaceUp())
                    offset += FACE_UP_OFFSET;
                else
                    offset += FACE_DOWN_OFFSET;
            }
        }

        g.setFont(new Font("Calibri", Font.PLAIN, 30));
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score + "", 15, CARD_HEIGHT * 2 + 
                        SPACING * 3 + FACE_DOWN_OFFSET * 7 
                            + 13 * FACE_UP_OFFSET - 50);
        
        g.setColor(new Color(200, 0, 0));
        if (game.hasWon())
        {
            g.setColor(new Color(200, 0, 0));
            g.fillRect((CARD_WIDTH * 7 + SPACING * 8) / 4, 
                (CARD_HEIGHT * 2 + SPACING * 3 + 
                    FACE_DOWN_OFFSET * 7 + 13 * FACE_UP_OFFSET) / 4, 
                    getWidth() / 2, getHeight() / 2);
            g.setColor(Color.WHITE);
            g.drawString("You Won. Final Score: " + score, 
                (CARD_WIDTH * 7 + SPACING * 8) / 4 + 50, 
                (CARD_HEIGHT * 2 + SPACING * 3 + 
                FACE_DOWN_OFFSET * 7 + 13 * FACE_UP_OFFSET) / 2);
        }
    }

    /**
     * draws each card based on information of the card
     * @param g graphics used
     * @param card card to draw
     * @param x x position of the card
     * @param y y position of the card
     */
    private void drawCard(Graphics g, Card card, int x, int y)
    {
        if (card == null)
        {
            g.setColor(Color.BLACK);
            g.drawRect(x, y, CARD_WIDTH, CARD_HEIGHT);
        }
        else
        {
            String fileName = card.getFileName();
            if (!new File(fileName).exists())
                throw new 
                    IllegalArgumentException("bad file name:  " + fileName);
            Image image = new ImageIcon(fileName).getImage();
            g.drawImage(image, x, y, CARD_WIDTH, CARD_HEIGHT, null);
        }
    }

    /**
     * what to do if mouse is exited
     * @param e the mouse
     */
    public void mouseExited(MouseEvent e)
    {
    }

    /**
     * what to do if mouse is entered
     * @param e the mouse
     */
    public void mouseEntered(MouseEvent e)
    {
    }

    /**
     * what to do when mouse is released
     * @param e the mouse
     */
    public void mouseReleased(MouseEvent e)
    {
    }

    /**
     * what to do if mouse is held and pressed
     * @param e the mouse
     */
    public void mousePressed(MouseEvent e)
    {
    }

    /**
     * Determines what to do when mouse clicked
     * @param e the mouse event
     */
    public void mouseClicked(MouseEvent e)
    {
        //none selected previously
        int col = e.getX() / (SPACING + CARD_WIDTH);
        int row = e.getY() / (SPACING + CARD_HEIGHT);
        if (row > 1)
            row = 1;
        if (col > 6)
            col = 6;

        if (row == 0 && col == 0)
            game.stockClicked();
        else if (row == 0 && col == 1)
            game.wasteClicked();
        else if (row == 0 && col >= 3)
            game.foundationClicked(col - 3);
        else if (row == 1)
            game.pileClicked(col);
        repaint();
    }

    /**
     * draws the border around the selected card
     * @param g the graphics used
     * @param x x coordinate
     * @param y y coordinate
     */
    private void drawBorder(Graphics g, int x, int y)
    {
        g.setColor(Color.RED);
        g.drawRect(x, y, CARD_WIDTH, CARD_HEIGHT);
        g.drawRect(x + 1, y + 1, CARD_WIDTH - 2, CARD_HEIGHT - 2);
        g.drawRect(x + 2, y + 2, CARD_WIDTH - 4, CARD_HEIGHT - 4);
        g.drawRect(x + 5, y + 5, CARD_WIDTH - 10, CARD_HEIGHT - 10);
        g.drawRect(x - 2, y - 2, CARD_WIDTH + 4, CARD_HEIGHT + 4);
    }

    /**
     * unselects every object
     */
    public void unselect()
    {
        selectedRow = -1;
        selectedCol = -1;
    }

    /**
     * determines whether the waste is selected
     * @return true if selected row and col match waste;
     *          otherwise, false
     */
    public boolean isWasteSelected()
    {
        return selectedRow == 0 && selectedCol == 1;
    }

    /**
     * selects the waste by setting the
     * selection to the right place
     */
    public void selectWaste()
    {
        selectedRow = 0;
        selectedCol = 1;
    }

    /**
     * determines whhether a pile is selected
     * @return true if row 1 is selected;
     *          otherwise, false
     */
    public boolean isPileSelected()
    {
        return selectedRow == 1;
    }

    /**
     * determins which pile is selected
     * @return int of which pile was selected
     */
    public int selectedPile()
    {
        if (selectedRow == 1)
            return selectedCol;
        else
            return -1;
    }

    /**
     * selects the pile by alligning 
     * selections with the right rows
     * and columns
     * 
     * @param index the specific index of
     *  the pile to select
     */
    public void selectPile(int index)
    {
        selectedRow = 1;
        selectedCol = index;
    }
}