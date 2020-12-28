import java.util.*;
/**
 * TreeUtil contains the methods for manipulating and 
 *      utilizing binary trees
 * @author Author Dani
 * @version 12.18.20
 *
 */
public class TreeUtil
{
    //used to prompt for command line input
    private static Scanner in = new Scanner(System.in);

    private static final boolean debug = false;

    /**
     *finds the leftmost object
     * @param t the tree to find the leftmost object of
     * @return Object at the leftmost node
     */
    public static Object leftmost(TreeNode t)
    {
        if (t == null)
            return null;

        while (t.getLeft() != null)
        {
            t = t.getLeft();
        }

        return t.getValue();
    }

    /**
     * Recursively finds the rightmost object in a tree
     * @param t the tree to find the rightmost in
     * @return Object at the rightmost node
     */
    public static Object rightmost(TreeNode t)
    {
        if (t == null) 
            return null;
        else if (t.getRight() == null)
            return t.getValue();
        return rightmost(t.getRight());
    }

    /**
     * Recursively finds the height of the tree
     * @param t the tree to find the height of
     * @return int height of the tree
     */
    public static int maxDepth(TreeNode t)
    {
        if (t == null)
            return 0;
        return 1 + Math.max(maxDepth(t.getLeft()), maxDepth(t.getRight()));
    }

    /**
     * create a random tree of the specified depth.  
     * No attempt to balance the tree is provided.
     * @param depth of the tree
     * @return TreeNode object that points to the generated tree
     */
    public static TreeNode createRandom(int depth)
    {
        if (Math.random() * Math.pow(2, depth) < 1)
            return null;
        return new TreeNode(((int)(Math.random() * 10)),
            createRandom(depth - 1),
            createRandom(depth - 1));
    }

    /**
     * Counts the number nodes in a tree
     * @param t the tree to count the number of nodes of
     * @return number of nodes
     */
    public static int countNodes(TreeNode t)
    {
        if (t == null)
            return 0;                        
        return 1 + countNodes(t.getLeft()) + countNodes(t.getRight());

    }

    /**
     * Counts the number of leaves, or nodes without children,
     *      in the tree
     * @param t the tree to count the leaves in
     * @return the number of nodes without children
     */
    public static int countLeaves(TreeNode t)
    {
        if (t == null)
            return 0;

        if (t.getLeft() == null && t.getRight() == null)        
            return 1;

        return countLeaves(t.getLeft()) + countLeaves(t.getRight());
    }

    /**
     * Traverses through the tree in a preorder fashion
     *  where the parent is visited, then the left, then right child
     * 
     * @param t the tree being traversed
     * @param display the display; used to light up the nodes
     */
    public static void preOrder(TreeNode t, TreeDisplay display)
    {
        if (t != null)
        {
            display.visit(t);
            preOrder(t.getLeft(), display);
            preOrder(t.getRight(), display);
        }
    }

    /**
     * Traverses through the tree an an inOrder fashion
     *  where the left child, then parent, then right child are visited
     * 
     * @param t the tree being traversed
     * @param display the display; used to light up the nodes
     */
    public static void inOrder(TreeNode t, TreeDisplay display)
    {
        if (t != null)
        {
            inOrder(t.getLeft(), display);
            display.visit(t);
            inOrder(t.getRight(), display);
        }
    }

    /**
     * Traverses through the tree an a postOrder fashion
     *  where the left child, then right, then parent are visited
     * 
     * @param t the tree being traversed
     * @param display the display; used to light up the nodes
     */
    public static void postOrder(TreeNode t, TreeDisplay display)
    {
        if (t != null)
        {
            postOrder(t.getLeft(), display);
            postOrder(t.getRight(), display);
            display.visit(t);
        }
    }

    /**
     * Fills a list with everything in the tree, 
     *  traversing in a preOrder fashion
     *  
     * Nulls are represented with dollar signs to 
     *  avoid any structural ambiguity of the tree
     * 
     * @param t the tree being put into the list
     * @param list the list being filled
     */
    public static void fillList(TreeNode t, List<String> list)
    {
        if (t == null)
            list.add("$");
        else
        {
            list.add(t.getValue().toString());
            fillList(t.getLeft(), list);
            fillList(t.getRight(), list);
        }
    }

    /**
     * saveTree uses the FileUtil utility class to save the tree rooted at t
     * as a file with the given file name
     * @param fileName is the name of the file 
     *      to create which will hold the data
     *        values in the tree
     * @param t is the root of the tree to save
     */
    public static void saveTree(String fileName, TreeNode t)
    {
        List<String> preOrder = new ArrayList<String>();
        fillList(t, preOrder);
        FileUtil.saveFile(fileName, preOrder.listIterator());
    }

    /**
     * buildTree takes in an iterator which will 
     *          iterate through a valid description of
     * a binary tree with String values.  Null nodes are
     *          indicated by "$" markers
     * @param it the iterator which will iterate over the tree description
     * @return a pointer to the root of the tree built by the iteration
     */
    public static TreeNode buildTree(Iterator<String> it)
    {
        TreeNode t = null;
        if (it.hasNext())
        {            
            String next = it.next();
            if (next.equals("$"))
                return null;
            else
                t = new TreeNode(next, buildTree(it), buildTree(it));
        }
        return t;
    }

    /**
     * read a file description of a tree and then build the tree
     * @param fileName is a valid file name for a file
     *      that describes a binary tree
     * @return a pointer to the root of the tree
     */
    public static TreeNode loadTree(String fileName)
    {
        Iterator<String> it = FileUtil.loadFile(fileName);
        return buildTree(it);
    }

    /**
     * utility method that waits for a user to type text 
     *      into Std Input and then press enter
     * @return the string entered by the user
     */
    private static String getUserInput()
    {
        return in.nextLine();
    }

    /**
     * plays a single round of 20 questions
     * postcondition:  plays a round of twenty questions,
     *          asking the user questions as it
     *                 walks down the given knowledge tree, 
     *                          lighting up the display as it goes;
     *                 modifies the tree to include information learned.
     * @param t a pointer to the root of the game tree
     * @param display which will show the progress of the game
     */
    private static void twentyQuestionsRound(TreeNode t, TreeDisplay display)
    {   
        throw new RuntimeException("Write ME!");
    }

    /** 
     * plays a game of 20 questions
     * Begins by reading in a starting file and then plays multiple rounds
     * until the user enters "quit".  Then the final tree is saved
     */
    public static void twentyQuestions()
    {
        throw new RuntimeException("Write ME!");
    }

    /**
     * copy a binary tree
     * @param t the root of the tree to copy
     * @return a new tree, which is a complete copy
     *         of t with all new TreeNode objects
     *         pointing to the same values as t (in the same order, shape, etc)
     */
    public static TreeNode copy(TreeNode t)
    {
        if (t == null)
            return null;
        else
            return new TreeNode(t.getValue(), 
                copy(t.getLeft()), copy(t.getRight()));
    }

    /**
     * tests to see if two trees have the same shape, but not necessarily the
     * same values.  Two trees have the same shape if they have TreeNode objects
     * in the same locations relative to the root
     * @param t1 pointer to the root of the first tree
     * @param t2 pointer to the root of the second tree
     * @return true if t1 and t2 describe tr
     *      ees having the same shape, false otherwise
     */
    public static boolean sameShape(TreeNode t1, TreeNode t2)
    {
        if (t1 == null && t2 == null)
            return true;
        if (t1 == null && t2 != null)
            return false;
        if (t1 != null && t2 == null)
            return false;

        return sameShape(t1.getLeft(), t2.getLeft()) && 
        sameShape(t1.getRight(), t2.getRight());
    }

    /**
     * Generate a tree for decoding Morse code
     * @param display the display that will show the decoding tree
     * @return the decoding tree
     */
    public static TreeNode createDecodingTree(TreeDisplay display)
    {
        TreeNode tree = new TreeNode("Morse Tree");
        display.displayTree(tree);
        insertMorse(tree, "a", ".-", display);
        insertMorse(tree, "b", "-...", display);
        insertMorse(tree, "c", "-.-.", display);
        insertMorse(tree, "d", "-..", display);
        insertMorse(tree, "e", ".", display);
        insertMorse(tree, "f", "..-.", display);
        insertMorse(tree, "g", "--.", display);
        insertMorse(tree, "h", "....", display);
        insertMorse(tree, "i", "..", display);
        insertMorse(tree, "j", ".---", display);
        insertMorse(tree, "k", "-.-", display);
        insertMorse(tree, "l", ".-..", display);
        insertMorse(tree, "m", "--", display);
        insertMorse(tree, "n", "-.", display);
        insertMorse(tree, "o", "---", display);
        insertMorse(tree, "p", ".--.", display);
        insertMorse(tree, "q", "--.-", display);
        insertMorse(tree, "r", ".-.", display);
        insertMorse(tree, "s", "...", display);
        insertMorse(tree, "t", "-", display);
        insertMorse(tree, "u", "..-", display);
        insertMorse(tree, "v", "...-", display);
        insertMorse(tree, "w", ".--", display);
        insertMorse(tree, "x", "-..-", display);
        insertMorse(tree, "y", "-.--", display);
        insertMorse(tree, "z", "--..", display);
        return tree;
    }

    /**
     * helper method for building a Morse code decoding tree.
     * postcondition:  inserts the given letter into the decodingTree,
     *                 in the appropriate position, as determined by
     *                 the given Morse code sequence; lights up the display
     *                 as it walks down the tree
     * @param decodingTree is the partial decoding tree
     * @param letter is the letter to add
     * @param code is the Morse code for letter
     * @param display is the display that will 
     *      show progress as the method walks down the tree
     */
    private static void insertMorse(TreeNode decodingTree, String letter,
    String code, TreeDisplay display)
    {
        Character per = new Character('.');
        Character dash = new Character('-');

        if (code.length() == 0)
        {
            decodingTree.setValue(letter);
            display.visit(decodingTree);
        }
        else
        {
            if (code.charAt(0) == per)
            {
                if (decodingTree.getLeft() == null)
                    decodingTree.setLeft(new TreeNode(null));

                insertMorse(decodingTree.getLeft(), letter, 
                    code.substring(1), display);
            }

            else if (code.charAt(0) == dash)
            {
                if (decodingTree.getRight() == null)
                    decodingTree.setRight(new TreeNode(null));

                insertMorse(decodingTree.getRight(), letter, 
                    code.substring(1), display);
            }
        }

    }

    /**
     * decodes Morse code by walking the decoding tree 
     *      according to the input code
     * @param decodingTree is the Morse code decoding tree
     * @param cipherText is Morse code consisting of dots, dashes, and spaces
     * @param display is the display object that will show the decoding progress
     * @return the string represented by cipherText
     */
    public static String decodeMorse(TreeNode decodingTree, 
    String cipherText, TreeDisplay display)
    {
        int index = cipherText.indexOf(" ");
        if (index != -1)
            return decodeMorse(decodingTree, 
                cipherText.substring(0, index), display)
                + decodeMorse(decodingTree, 
                    cipherText.substring(index + 1), display);

        if (decodingTree == null)
            return null;

        if (cipherText.length() == 0)
        {    
            display.visit(decodingTree);
            return (String)decodingTree.getValue();
        }

        if (cipherText.length() > 0)
        {
            Character per = new Character('.');
            Character dash = new Character('-');

            if (cipherText.charAt(0) == per)
            {
                return decodeMorse(decodingTree.getLeft(), 
                        cipherText.substring(1), display);
            }

            else if (cipherText.charAt(0) == dash)
            {
                return decodeMorse(decodingTree.getRight(), 
                        cipherText.substring(1), display);
            }
        }

        return null;
    }

    /**
     * optional work
     * @param expTree tree being evaluated
     * @return evaluation of tree
     */
    public static int eval(TreeNode expTree)
    {
        throw new RuntimeException("Write ME!");
    }

    /**
     * optional work
     * @param exp expression to use to create tree
     * @return top node of expression tree
     */
    public static TreeNode createExpressionTree(String exp)
    {
        throw new RuntimeException("Write ME!");
    }

    /**
     * debug printout
     * postcondition: out is printed to System.out
     * @param out the string to send to System.out
     */

    private static void debugPrint(String out)
    {
        if (debug) System.out.println("debug: " + out);
    }
}
