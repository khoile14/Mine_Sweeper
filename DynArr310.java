// TO DO: add your implementation and JavaDoc

/**
 * A class implementing a dynamic array.
 *
 * @param <T> the type of elements stored in the array.
 */
public class DynArr310<T> {

    //underlying array for storage -- you MUST use this for credit!
    //Do NOT change the name or type
    /**
     * The underlying array for storage.
     */
    private T[] storage;    //underlying array

    /**
     * The default initial capacity of the array.
     */
    private static final int MINCAP = 2;    //default initial capacity / minimum capacity
    /**
     * The current number of elements in the storage.
     */
    private int size;    //current number of elements in the storage

    // ADD MORE PRIVATE MEMBERS HERE IF NEEDED!

    /**
     * Constructor. Initialize instance variables.
     */
    @SuppressWarnings("unchecked")
    public DynArr310() {
        //constructor
        //initial capacity of the array should be MINCAP

        // Hint: Can't remember how to make an array of generic Ts? It's in the textbook...
        storage = (T[]) new Object[MINCAP];
        size = 0;
    }

    /**
     * Constructor. Initialize instance variables.
     *
     * @param initCap the initial capacity of the array.
     */
    @SuppressWarnings("unchecked")
    public DynArr310(int initCap) {
        // Constructor
        // Initial capacity of the storage should be initCap.

        // - Throw IllegalArgumentException if initCap is smaller than MINCAP
        // - Use this _exact_ error message for the exception
        //   (quotes are not part of the message):
        //    "Capacity must be at least 2!"
        if (initCap < MINCAP) {
            throw new IllegalArgumentException("Capacity must be at least 2!");
        } else {
            storage = (T[]) new Object[initCap];
            size = 0;
        }
    }

    /**
     * Report the current capacity of the storage.
     *
     * @return the current capacity of the storage.
     */
    public int capacity() {
        //report max number of elements
        // O(1)
        return storage.length;
    }

    /**
     * Report the current number of elements in the storage.
     *
     * @return the current number of elements in the storage.
     */

    public int size() {
        //report current number of elements
        // O(1)
        return size;
    }

    /**
     * Set the value of the element at the given index to be the given value.
     *
     * @param index the index of the element to be set.
     * @param value the value to be set.
     * @return the old value of the element at the given index.
     */

    public T set(int index, T value) {
        // Replace the item at the given index to be the given value.
        // Return the old item at that index.
        // Note: You cannot add new items (i.e. cannot increase size) with this method.

        // O(1)

        // - Throw IndexOutOfBoundsException if index is not valid
        // - Use this code to produce the correct error message for
        // the exception (do not use a different message):
        //	  "Index: " + index + " out of bounds!"
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + " out of bounds!");
        }

        // - Throw IllegalArgumentException if value is null.
        // - Use this _exact_ error message for the exception
        //  (quotes are not part of the message):
        //    "Null values not accepted!"
        if (value == null) {
            throw new IllegalArgumentException("Null values not accepted!");
        }

        T oldValue = storage[index];
        storage[index] = value;

        return oldValue; //default return, remove/change as needed
    }

    /**
     * Report the value of the element at the given index.
     *
     * @param index the index of the element to be returned.
     * @return the value of the element at the given index.
     */
    public T get(int index) {
        // Return the item at the given index

        // Use the exception (and error message) described in set()
        // for invalid indicies.

        // O(1)
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + " out of bounds!");
        }
        return storage[index];
    }

    /**
     * Add the given value to the end of the storage.
     *
     * @param value the value to be added.
     */
    @SuppressWarnings("unchecked")
    public void add(T value) {
        // Append an element to the end of the storage.
        // Double the capacity if no space available.

        // For a null value, use the same exception and message
        // as set().

        // You can assume we will never need to grow the capacity to a value
        // beyond Integer.MAX_VALUE.  No need to check or test that boundary
        // value when you grow the capacity.

        // Amortized O(1)
        if (value == null) {
            throw new IllegalArgumentException("Null values not accepted!");
        }
        if (size == storage.length) {
            setCapacity(storage.length * 2);
        }
        storage[size] = value;

        size++;
    }

    /**
     * Insert the given value at the given index.
     *
     * @param index the index at which the value is to be inserted.
     * @param value the value to be inserted.
     */
    @SuppressWarnings("unchecked")
    public void insert(int index, T value) {
        // Insert the given value at the given index and shift elements if needed.
        // NOTE: You can also append items with this method.

        // If no space available, grow your storage in the same way as required by add().
        // Assume the same as add() for the upper bound of capacity.
        // Code reuse! Consider using setCapacity (see below).

        // For an invalid index or a null value,  use the same exception and message
        // as set(). However, remember that the condition of the exception is
        // different (a different invalid range for indexes).

        // O(N) where N is the number of elements in the storage
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + " out of bounds!");
        }

        if (value == null) {
            throw new IllegalArgumentException("Null values not accepted!");
        }

        if (size == storage.length) {
            setCapacity(storage.length * 2);
        }

        if (index == size) {
            add(value);
        } else {
            for (int i = size; i > index; i--) {
                storage[i] = storage[i - 1]; //
            }
            storage[index] = value;
            size++;
        }
    }

    /**
     * Set the capacity of the storage to be the given value.
     *
     * @param input the value to be set as the capacity of the storage.
     */
    public void setCapacity(int input) {
        T[] temp = (T[]) new Object[input];
        System.arraycopy(storage, 0, temp, 0, size);
        this.storage = temp;
    }

    /**
     * Remove and return the element at the given index.
     *
     * @param index the index of the element to be removed.
     * @return the element at the given index.
     */
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        // Remove and return the element at the given index. Shift elements
        // to ensure no gap. Throw an exception when there is an invalid
        // index (see set(), get(), etc. above).

        // If the number of elements after removal falls below or at 1/3 of the capacity,
        // halve capacity (rounding down) of the storage.
        // However, capacity should NOT go below MINCAP.

        // O(N) where N is the number of elements currently in the storage

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + " out of bounds!");
        }

        T removed = storage[index];

        for (int i = index; i < size - 1; i++) {
            storage[i] = storage[i + 1];
        }


        size--;

        if (size <= capacity() / 3) {
            setCapacity(storage.length / 2);
        }
        return removed;
    }

    //******************************************************
    //*******     BELOW THIS LINE IS PROVIDED code   *******
    //*******             Do NOT edit code!          *******
    //*******		   Remember to add JavaDoc		 *******
    //******************************************************

    /**
     * Return a String representation of the storage.
     *
     * @return a String representation of the storage.
     */
    @Override
    public String toString() {
        //This method is provided. Add JavaDoc and comments.

        StringBuilder s = new StringBuilder("[");
        for (int i = 0; i < size(); i++) {
            s.append(get(i));
            if (i < size() - 1)
                s.append(", ");
        }
        s.append("]");
        return s.toString().trim();

    }

    //******************************************************
    //*******     BELOW THIS LINE IS TESTING CODE    *******
    //*******      Edit it as much as you'd like!    *******
    //*******		Remember to add JavaDoc			 *******
    //******************************************************

    /**
     * Return a detailed String representation of the storage.
     *
     * @return a detailed String representation of the storage.
     */
    public String toStringDebug() {
        //This method is provided for debugging purposes
        //(use/modify as much as you'd like), it just prints
        //out the DynArr310 details for easy viewing.
        StringBuilder s = new StringBuilder("DynArr310 with " + size()
                + " items and a capacity of " + capacity() + ":");
        for (int i = 0; i < size(); i++) {
            s.append("\n  [" + i + "]: " + get(i));
        }
        return s.toString().trim();

    }

    /**
     * Main method. Contains tests.
     *
     * @param args command line arguments.
     */
    public static void main(String args[]) {
        //These are _sample_ tests. If you're seeing all the "yays" that's
        //an excellend first step! But it does NOT guarantee your code is 100%
        //working... You may edit this as much as you want, so you can add
        //own tests here, modify these tests, or whatever you need!

        //create a DynArr310 of integers
        DynArr310<Integer> ida = new DynArr310<>();
        if ((ida.size() == 0) && (ida.capacity() == 2)) {
            System.out.println("Yay 1");
        }

        //add some numbers at the end
        for (int i = 0; i < 3; i++)
            ida.add(i * 5);

        //uncomment to check details
        //System.out.println(ida);

        //checking dynamic array details
        if (ida.size() == 3 && ida.get(2) == 10 && ida.capacity() == 4) {
            System.out.println("Yay 2");
        }

        //insert, set, get
        ida.insert(1, -10);
        ida.insert(4, 100);
        if (ida.set(1, -20) == -10 && ida.get(2) == 5 && ida.size() == 5
                && ida.capacity() == 8) {
            System.out.println("Yay 3");
        }

        //create a DynArr310 of strings
        DynArr310<String> letters = new DynArr310<>(6);

        //insert some strings
        letters.insert(0, "c");
        letters.insert(0, "a");
        letters.insert(1, "b");
        letters.insert(3, "z");

        //get, toString()
        if (letters.get(0).equals("a") && letters.toString().equals("[a, b, c, z]")) {
            System.out.println("Yay 4");
        }

        //remove
        if (letters.remove(0).equals("a") && letters.remove(1).equals("c") &&
                letters.get(1).equals("z") && letters.size() == 2 && letters.capacity() == 3) {
            System.out.println("Yay 5");
        }

        //exception checking
        try {
            letters.set(-1, null);
        } catch (IndexOutOfBoundsException ex) {
            if (ex.getMessage().equals("Index: -1 out of bounds!")) {
                System.out.println("Yay 6");
            }
        }

    }


}