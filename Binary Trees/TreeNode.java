/**
 * Describes methods and properties of 
 *  a TreeNode object, which is used to construct
 *  binary trees
 *  
 *  @author HKR CS, Arnav Dani
 *  @version 12.18.20
 */
public class TreeNode
{
    private Object value;
    private TreeNode left;
    private TreeNode right;

    /**
     * Constructor for TreeNode object
     *  with only 1 param passed
     * @param initValue initial value to set node to
     */
    public TreeNode(Object initValue)
    { 
        this(initValue, null, null);
    }

    /**
     * Constructor for TreeNode with 3 parameters
     * @param initValue initial value/object to set the node to
     * @param initLeft what the left reference is pointing to
     * @param initRight what the right reference is pointing to
     */
    public TreeNode(Object initValue, TreeNode initLeft, TreeNode initRight)
    { 
        value = initValue; 
        left = initLeft; 
        right = initRight; 
    }

    /**
     * gets hte value at the node
     * @return value
     */
    public Object getValue() { return value; }
    
    /**
     * gets the node to the left
     * @return LeftNode
     */
    public TreeNode getLeft() { return left; }
    
    /**
     * gets the node to the right
     * @return RightNode
     */
    public TreeNode getRight() { return right; }

    /**
     * changes the value to the new specific one
     * @param theNewValue the new value
     */
    public void setValue(Object theNewValue) { value = theNewValue; }
    
    /**
     * changes the left reference to a specific reference
     * @param theNewLeft the specific reference passed in
     */
    public void setLeft(TreeNode theNewLeft) { left = theNewLeft; }
    
    /**
     * changes the right reference to a specific reference
     * @param theNewRight the specific reference passed in
     */
    public void setRight(TreeNode theNewRight) { right = theNewRight; }
}