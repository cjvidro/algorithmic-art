import java.util.Iterator;
import java.util.NoSuchElementException;
public class ArrayList<E> implements Iterable<E> {
	private E[] list;
	private int size = 0;
	
	// constructors ----------------------------
	/* Default constructor for ArrayList with a capacity of 16
	 */
	@SuppressWarnings("unchecked")
	public ArrayList() {
		list = (E[]) new Object[16];
	}
	
	/* Default constructor for ArrayList with a specified capacity
	 */
	@SuppressWarnings("unchecked")
	public ArrayList(int initialCapacity) {
		if (initialCapacity > 0) {
			list = (E[]) new Object[initialCapacity];
		} else {
			list = (E[]) new Object[1];
		}
	}

	// methods ----------------------------
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public E get(int i) throws IndexOutOfBoundsException {
		if (i < 0 || i >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		return list[i];
	}

	public E get (E element) {
		return get(search(element));
	}

	public E set(int i, E e) throws IndexOutOfBoundsException {
		if (i < 0 || i >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		E temp = list[i];
		list[i] = e;
		return temp;
	}

	public void add(int i, E e) throws IndexOutOfBoundsException {
		/* TCJ
		 *  The worst case is that i = 0 and the list size is previously one less than capacity.
		 *  The list must grow, calling the growList method of O(n).
		 *  All elements must be shifted, an operation of O(n).
		 *  Thus, the time complexity of this method is O(n).
		 */
		if (i < 0 || i > size) {
			throw new IndexOutOfBoundsException();
		}
		
		// determine if list needs to grow
		if (size + 1 == list.length) {
			growList();
		}
		
		// shift elements 
		for (int j = size; j > i; j--) {
			list[j] = list[j - 1];
		}
		
		// add element
		list[i] = e;
		size++;
	}
	
	@SuppressWarnings("unchecked")
	private void growList() {
		/* TCJ
		 *  To grow the list, n elements are copied to a temporary list.
		 *  Thus the time complexity is O(n).
		 */
		// create a new list
		E[] tempList = (E[]) new Object[list.length * 2];
		for (int i = 0; i < list.length; i++) {
			tempList[i] = list[i];
		}
		
		// grow list
		list = tempList;
	}

	public E remove(int i) throws IndexOutOfBoundsException {
		/* TCJ
		 *  The worst case is that the element to be removed is the first element.
		 *  In this case, all elements, except the one removed, must be shifted, O(n - 1).
		 *  Therefore the time complexity of this method is O(n).
		 */
		if (i < 0 || i > size) {
			throw new IndexOutOfBoundsException();
		}
		
		// store temp value
		E temp = list[i];
		
		// shift elements
		for (int j = i; j < size; j++) {
			list[j] = list[j + 1];
		}
		
		size--;
		return temp;
	}

	public E remove(E element) {
		return remove(search(element));
	}

	private int search (E element) {
		int i = 0;
		for (E e : list) {
			if (e == element) {
				return i;
			}
			i++;
		}

		i = -1;
		return i;
	}

	public Iterator<E> iterator() {
		Iterator<E> iterator = new Iterator<E>() {
			int index = 0;
			
			@Override
			public boolean hasNext() {
				return index != size;
			}

			@Override
			public E next() {
				if (this.hasNext()) {
					return list[index++];
				} else {
					throw new NoSuchElementException();
				}
			}
		};
		
		return iterator;
	}

	public void addFirst(E e)  {
		/* TCJ
		 *  The worst case is that the list size is previously one less than capacity.
		 *  The list must grow, calling the growList method of O(n).
		 *  All elements must be shifted, an operation of O(n).
		 *  Thus, the time complexity of this method is O(n).
		 */
		// determine if list needs to grow
		if (size + 1 == list.length) {
			growList();
		}
		
		//shift elements right one
		for (int i = size; i > 0; i--) {
			list[i] = list[i - 1];
		}
		
		// add element
		list[0] = e;
		size++;
	}

	public void addLast(E e)  {
		/* TCJ
		 *  The worst case is that the list size is previously one less than capacity.
		 *  The list must grow, calling the growList method of O(n).
		 *  Thus, the time complexity of this method is O(n).
		 */
		if (size + 1 == list.length) {
			growList();
		}
		
		list[size] = e;
		size++;
	}

	public E removeFirst() throws IndexOutOfBoundsException {
		/* TCJ
		 * The worst case is that all remaining elements must be shifted by one, making the time complexity O(n).
		 */
		if (this.isEmpty()) {
			throw new IndexOutOfBoundsException();
		}
		
		E temp = list[0];
		
		// shift remaining elements
		for (int i = 0; i < size; i++) {
			list[i] = list[i+1];
		}
		
		size--;
		return temp;
	}

	public E removeLast() throws IndexOutOfBoundsException {
		if (this.isEmpty()) {
			throw new IndexOutOfBoundsException();
		}
		
		E temp = list[size - 1];
		list[size - 1] = null;
		size--;
		return temp;
	}
	
	// Return the capacity of array, not the number of elements.
	// Notes: The initial capacity is 16. When the array is full, the array should be doubled.
	public int capacity() {
		return list.length;
	}
	
}
