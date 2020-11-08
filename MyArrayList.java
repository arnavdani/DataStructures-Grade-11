import java.util.Iterator;
import java.util.ListIterator;
import java.util.ConcurrentModificationException;

/**
 * Describes the methods and properties of the ArrayList data structure
 * The arraylist object stores other objects in a data structure. 
 * Each index in the list is available via random access and indexing. 
 * In contrast to arrays, Arraylists are mutable, meaning that objects 
 *      can be added to the front, back, or anywhere in the list while 
 *      maintaining the efficiency of random access. 
 * 
 * @version 11/6/20
 * @author Arnav Dani
 * 
 * @param <E> type of object making up the ArrayList
 */
public class MyArrayList<E>
{
    private int size;
    private Object[] values;  //(Java doesn't let us make an array of type E)

    /**
     * Constructor for MyArrayList Object
     */
    public MyArrayList()
    {
        size = 0;
        values = new Object[1];
    }

    /**
     * @return a string of the array in a readable form for testing
     */
    public String toString()
    {
        if (size == 0)
            return "[]";

        String s = "[";
        for (int i = 0; i < size - 1; i++)
            s += values[i] + ", ";
        return s + values[size - 1] + "]";
    }

    /**
     * Doubles the capacity of the array and duplicates all the values
     * @postcondition replaces the array with one that is
     *               twice as long, and copies all of the
     *               old elements into it
     */
    private void doubleCapacity()
    {
        Object[] doubleVals = new Object[size * 2];
        for (int i = 0; i < size; i++)
        {
            doubleVals[i] = values[i];
        }
        values = doubleVals;
    }

    /**
     * @return the capacity of the array, how much can be held
     * @postcondition returns the length of the array
     */
    public int getCapacity()
    {
        return values.length;
    }

    /**
     * Returns the number of elements stored in the list
     * @return size of the ArrayList
     */
    public int size()
    {
        return size;
    }

    /**
     * @return the object at a certain index and casts it to generic type E
     * @param index index to get object from
     */
    public E get(int index)
    {
        if (index < 0 || index >= size)
            throw new RuntimeException("Index is out of bounds");

        return (E)values[index];
    }

    /** 
     * @postcondition replaces the element at position index with obj
     *               returns the element formerly at the specified position
     * @param index index to replace object at
     * @param obj object replace current object with
     * @return E object that was replaced
     */
    public E set(int index, E obj)
    {
        if (index < 0 || index >= size)
            throw new RuntimeException("Index is out of bounds");

        E oldObj = (E)values[index];

        values[index] = obj;

        return oldObj;

        //(You will need to promise the return value is of type E.)
    }

    /**
     * Adds an object to the back of the array
     * @return true 
     * @param obj object to add at the back
     * @postcondition appends obj to end of list; returns true
     */
    public boolean add(E obj)
    {
        /* if values is already full, call doubleCapacity before adding */

        if (size < values.length)
        {
            values[size] = obj;
        }
        else
        {
            doubleCapacity();
            values[size] = obj;
        }
        size++;
        return true;
    }

    /**
     * Removes element from a specified index in the array
     * @param index index to remove from
     * @return generic object that was removed
     * @postcondition removes element from position index, moving elements
     *               at position index + 1 and higher to the left
     *               (subtracts 1 from their indices) and adjusts size
     *               returns the element formerly at the specified position
     */
    public E remove(int index)
    {
        if (index < 0 || index >= size)
            throw new RuntimeException("Index is out of bounds");

        E removed = (E) values[index];
        for (int i = index; i < size - 1; i++)
        {
            values[i] = values[i + 1];
        }
        values[size - 1] = null;
        size -= 1;

        return removed;

        //(You will need to promise the return value is of type E.)
    }

    /**
     * @return the iterator defined inside the class
     */
    public Iterator<E> iterator()
    {
        return new MyArrayListIterator();
    }
    
    /**
     * @return the listiterator defined
     */
    public ListIterator<E> listIterator()
    {
        return new MyArrayListListIterator();
    }

    /**
     * Adds an object at the index specified, shifts the rest of the list back
     * @param index index to add object at
     * @param obj object to add at index
     * @precondition  0 <= index <= size
     * @postcondition inserts obj at position index,
     *               moving elements at position index and higher
     *               to the right (adds 1 to their indices) and adjusts size
     */
    public void add(int index, E obj)
    {
        if (index < 0 || index >= size)
            throw new RuntimeException("Index is out of bounds");

        if (size >= values.length)
        {
            doubleCapacity();
        }
        for (int i = size - 1; i > index; i++)
        {
            values[i + 1] = values[i];
        }
        values[index] = obj;
        size++;
    }

    /**
     * Defines methods of MyArrayListIterator which iterates 
     *      through the indexes of the arraylist
     * 
     * @author Arnav Dani
     * @version 11/6/20
     */
    private class MyArrayListIterator implements Iterator<E>
    {
        //the index of the value that will be returned by next()
        private int nextIndex;

        /**
         * Constructor for iterator
         */
        public MyArrayListIterator()
        {
            nextIndex = 0;
        }

        /**
         * checks if there is a next value beyond the 
         *  current position of the iterator
         * @return true if there is another value 
         *  in the arrayList; otherwise, false
         */
        public boolean hasNext()
        {
            return ( nextIndex < size);
        }

        /**
         * Moves the iterator to the next index and returns that object
         * @return object that iterator moved past
         */
        public E next()
        {
            E obj = (E) MyArrayList.this.get(nextIndex);
            nextIndex++;
            return obj;
        }

        //@postcondition removes the last element that was returned by next
        /**
         * Uses the iterator to remove the element 
         *  by using the ArrayLists remove method
         */
        public void remove()
        {
            if (!hasNext())
                throw new RuntimeException("NoSuchElementException");
            MyArrayList.this.remove(nextIndex);
        }
    }

    /**
     * Describes the methods of the ListIterator for the ArrayList,
     *  which can perform more functions than the normal Iterator could
     *  
     * It inherits all the methods from the previously defined Iterator
     * 
     * @author Arnav Dani
     * @version 11/6/20
     */
    private class MyArrayListListIterator extends MyArrayListIterator 
                    implements ListIterator<E>
    {
        // note the extends MyArrayListIterator 
        // Remember this class thus inherits the methods from the parent class.

        private int nextIndex;
        private int previousIndex; // Index of element that 
                        //will be returned by the next call of next()
        private boolean forward; //direction of traversal
        private boolean modified;

        /**
         * Constructs a new MyArrayListListIterator
         */
        public MyArrayListListIterator()
        {
            nextIndex = 0;
            previousIndex = -1;
            forward = true;
            modified = false;
        }

        /**
         * Returns next element and moves pointer forward
         * @return next Object in MyArryaList
         */
        public E next()
        {
            if (modified)
            {
                throw new 
                    ConcurrentModificationException("Concurrent" +
                                    "Modification Exception");
            }
            previousIndex++;
            forward = true;
            return super.next();
        }

        /**
         * Adds element before element that would be returned by next
         * @param obj element to add
         */
        public void add(E obj)
        {
            modified = true;
            MyArrayList.this.add(nextIndex, obj);
            modified = false;
        }

        /**
         * Determines whether there is another element in MyArrayList
         * while traversing in the backward direction
         * @return true if there is another element, false otherwise
         */
        public boolean hasPrevious()
        {
            return (previousIndex > 0);
        }

        /**
         * gets the object at the position previous 
         *  to the iterators current position
         */
        public E previous()
        {
            if (modified)
            {
                throw new ConcurrentModificationException();
            }
            forward = false;
            return (E)MyArrayList.this.get(nextIndex);
        }

        /**
         * Returns index of the next element 
         * @return index of element that would be 
         *         returned by a call to next()
         */
        public int nextIndex()
        {
            return nextIndex;
        }

        /**
         * Returns index of the previous element
         * @return index of element that would be
         *          returned by a call to previous()
         */
        public int previousIndex()
        {
            return previousIndex;
        }

        /**
         * Removes element that was returned by next() or previous()
         * USE direction FOR THIS
         */
        public void remove()
        {
            modified = true;
            if (forward)
                super.remove();
            else
            {
                if (!hasPrevious())
                    throw new RuntimeException("NoSuchElementException");
                MyArrayList.this.remove(previousIndex);
            }
            modified = false;
        }

        /**
         * Uses the iterator to replace an object at an index iwth a new one
         */
        public void set(E obj)
        {
            modified = true;
            MyArrayList.this.set(nextIndex, obj);
            modified = false;
        }
    }
}