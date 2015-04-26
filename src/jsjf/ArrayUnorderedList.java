package jsjf;

import jsjf.exceptions.ElementNotFoundException;

/**
 * ArrayUnorderedList represents an array implementation of an unordered list.
 *
 * @author Java Foundations
 * @version 4.0
 */
public class ArrayUnorderedList<T> extends ArrayList<T>
        implements UnorderedListADT<T> {
    /**
     * Creates an empty list using the default capacity.
     */
    public ArrayUnorderedList() {
        super();
    }

    /**
     * Creates an empty list using the specified capacity.
     *
     * @param initialCapacity the intial size of the list
     */
    public ArrayUnorderedList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Adds the specified element to the front of this list.
     *
     * @param element the element to be added to the front of the list
     */
    public void addToFront(T element) {
        // To be completed as a Programming Project
        // If the list is full, expand it.
        if (size() == list.length)
            expandCapacity();

        // Initialize scan = 0   
        int scan = 0;

        // for loop to make room by shifting everything one unit to the rear
        System.arraycopy(list, scan, list, scan + 1, rear - scan);

        // make the first element = element
        list[scan] = element;

        // increment rear and modCount
        rear++;
        modCount++;
    }

    /**
     * Adds the specified element to the rear of this list.
     *
     * @param element the element to be added to the list
     */
    public void addToRear(T element) {
        // To be completed as a Programming Project
        // If the list is full, expand it.
        if (size() == list.length)
            expandCapacity();

        //  place the new element at the rear position
        list[rear] = element;

        // increment rear and modcount
        rear++;
        modCount++;
    }

    /**
     * Adds the specified element after the specified target element.
     * Throws an ElementNotFoundException if the target is not found.
     *
     * @param element the element to be added after the target element
     * @param target  the target that the element is to be added after
     */
    public void addAfter(T element, T target) {
        // If the list is full, expand it.
        if (size() == list.length)
            expandCapacity();

        // Initialize scan = 0
        int scan = 0;

        // find the insertion point using a while loop to find the target 
        while (scan < rear && !target.equals(list[scan]))
            scan++;

        // If the target has not been found throw a new ElementNotFound exception
        if (scan == rear)
            throw new ElementNotFoundException("UnorderedList");

        // Increment scan        
        scan++;

        // shift elements after the target up one position
        System.arraycopy(list, scan, list, scan + 1, rear - scan);

        // insert element
        list[scan] = element;

        // increment rear and modCount
        rear++;
        modCount++;
    }
}