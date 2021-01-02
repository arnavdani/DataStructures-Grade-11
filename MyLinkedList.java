import java.util.Iterator;
import java.util.ListIterator;

/**
 * MyLinkedList describes and defines the properties of a doubly
 *  linked list, which is a data structure that functions
 *  by chaining reference "nodes" that contain a reference to the
 *  previous node, next node, and the object at that node.
 * 
 * Since MyLinkedList functions by chaining nodes, this data structure
 *  does not have random access, but it is much faster to add/remove elements
 *  
 * Therefore this structure is most useful in situation where fast
 *  modification is needed, not random access
 *  
 *  
 * @author Arnav Dani
 * @version 11.12.20
 *
 * @param <E> generic type of all the objects 
 *  in the structure
 */
public class MyLinkedList<E>
{
    private DoubleNode first;
    private DoubleNode last;
    private int size;

    /**
     * Constructor for MyLinkedList
     */
    public MyLinkedList()
    {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * Converts the properties of the list into a readable string
     * @return String to test output
     */
    public String toString()
    {
        DoubleNode node = first;
        if (node == null)
            return "[]";
        String s = "[";
        while (node.getNext() != null)
        {
            s += node.getValue() + ", ";
            node = node.getNext();
        }
        return s + node.getValue() + "]";
    }

    /** 
     * @precondition  0 <= index <= size / 2
     * @postcondition starting from first, returns the node
     *               with given index (where index 0
     *               returns first)
     *               */
    private DoubleNode getNodeFromFirst(int index)
    {
        DoubleNode node = first;
        for (int i = 0; i < index; i++)
        {
            node = node.getNext();
        }
        return node;
    }

    /** 
     * @precondition  size / 2 <= index < size
     * @postcondition starting from last, returns the node
     *               with given index (where index size-1
     *               returns last)
     */
    private DoubleNode getNodeFromLast(int index)
    {
        DoubleNode node = last;
        for (int i = size - 1; i > index; i--)
        {
            node = node.getPrevious();
        }
        return node;
    }

    /** 
     * @precondition  0 <= index < size
     * @postcondition starting from first or last (whichever
     *               is closer), returns the node with given
     *              index
     *              */
    private DoubleNode getNode(int index)
    {
        if (index > size / 2)
            return getNodeFromLast(index);
        return getNodeFromFirst(index);
    }

    /**
     * Gets the size of the list
     * @return size of the list
     */
    public int size()
    {
        return size;
    }

    /**
     * gets the object at a specific index
     * @param index index to get object at
     * @return the object at index
     */
    public E get(int index)
    {
        return (E) getNode(index).getValue();
    }

    /** 
     * @postcondition replaces the element at position index with obj
     * returns the element formerly at the specified position
     * @param index index to replace object at
     * @param obj object to replace object at index with
     * @return element that was replaced
     */
    public E set(int index, E obj)
    {
        E oldObj = (E) get(index);        
        DoubleNode node = getNode(index);
        node.setValue(obj);
        return oldObj;        
    }

    /**
     * @postcondition appends obj to end of list; returns true
     * @param obj object to add at the back
     * @return true;
     */
    public boolean add(E obj)
    {
        DoubleNode newLast = new DoubleNode(obj);
        if (last != null)
        {   
            newLast.setPrevious(last);       
        }
        last.setNext(newLast);
        last = newLast;
        size++;
        return true;
    }

    /** 
     * @postcondition removes element from position index, moving elements
     *               at position index + 1 and higher to the left
     *               (subtracts 1 from their indices) and adjusts size
     *               returns the element formerly at the specified position
     * @param index index at which to remove object
     * @return element removed
     */
    public E remove(int index)
    {
        DoubleNode node = getNode(index);
        E obj = (E) get(index);
        if (index == 0)
            return removeFirst();
        else if (index == size - 1)
            return removeLast();
        else
        {
            node.getPrevious().setNext(node.getNext());
            node.getNext().setPrevious(node.getPrevious());
            size--;
            return obj;
        }
    }

    /** 
     * @precondition  0 <= index <= size
     * @postcondition inserts obj at position index,
     *                moving elements at position index and higher
     *                to the right (adds 1 to their indices) and adjusts size
     *                
     * index being 0 is a special condition because previous is null
     * so I use the addfirst to avoid rewriting those same modifications
     * 
     * @param index index to add the object at
     * @param obj object to add at specific index
     */
    public void add(int index, E obj)
    {
        if (index == 0)
            addFirst(obj);
        else
        {
            DoubleNode node = getNode(index - 1);
            DoubleNode newNode = new DoubleNode(obj);
            newNode.setPrevious(node);
            newNode.setNext(node.getNext());
            node.setNext(newNode);
            newNode.getNext().setPrevious(newNode);
            size++;
        }
    }

    /**
     * Adds an element to the front of the list
     * 
     * special if condition when adding first to an empty list
     * @param obj object to add at the front
     */
    public void addFirst(E obj)
    {
        DoubleNode newFirst = new DoubleNode(obj);
        newFirst.setNext(first);
        if (first != null)
            first.setPrevious(newFirst);
        else
            last = newFirst;
        first = newFirst;
        size++;
    }

    /**
     * Adds an element to the back of the list
     * @param obj object to add at the back
     */
    public void addLast(E obj)
    {
        DoubleNode newLast = new DoubleNode(obj);
        if (last != null)
            last.setNext(newLast);
        newLast.setPrevious(last);
        last = newLast;
        size++;
    }

    /**
     * gets the object at the front of the list
     * @return object at the front node
     */
    public E getFirst()
    {
        return (E) first.getValue();
    }

    /**
     * gets the object at the back of the list
     * @return object at last node
     */
    public E getLast()
    {
        return (E) last.getValue();
    }

    /**
     * removes the first element in the list
     * @return the element removed
     */
    public E removeFirst()
    {
        E obj = (E) first.getValue();
        if (last == first)
            last = null;
        first = first.getNext();

        if (first != null)
            first.setPrevious(null);
        size--;
        return obj;        
    }

    /**
     * removes the last element in the list
     * @return the element removed
     */
    public E removeLast()
    {
        E obj = (E) last.getValue();
        System.out.print(last.getValue());
        last = last.getPrevious();
        last.setNext(null);
        size--;
        return obj;
    }

    /**
     * gets the iterator to iterate over the list
     * @return the iterator
     */
    public Iterator<E> iterator()
    {
        return new MyLinkedListIterator();
    }

    /**
     * Defines the properties of the iterator which
     *  iterates through and operates on MyLinkedList
     *  
     * @author Arnav Dani
     * @version 11.12.20
     */
    private class MyLinkedListIterator implements Iterator<E>
    {
        private DoubleNode nextNode;

        /**
         * Constructor for the iterator
         *  initializes all instance variables
         */
        public MyLinkedListIterator()
        {
            nextNode = first;
        }

        /**
         * checks whether there is a next element
         * @return if there is a next element
         */
        public boolean hasNext()
        {
            return nextNode != null;
        }

        /**
         * iterates to the next object and 
         *  returns the value just passed
         * 
         * @return the value at the node just 
         *  passed by the iterator
         */
        public E next()
        {
            E obj = (E) nextNode.getValue();
            nextNode = nextNode.getNext();
            return (E) obj;
        }

        //@postcondition removes the last element that was returned by next
        /**
         * removes the element to the left of the
         *  current location of the iterator
         * 
         * no need to return the object removed
         */
        public void remove()
        {
            DoubleNode node = nextNode.getPrevious();
            if (node.getPrevious() != null)
                node.getPrevious().setNext(node.getNext());

            if (node.getNext() != null)
                node.getNext().setPrevious(node.getPrevious());
        }
    }
}
