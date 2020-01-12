import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Charles Vidro
 * Assignment 1.
 * 
 * This class allows for the creation of a Doubly Linked List of type E.
 * 
 * @param <E> the type of the Doubly Linked List.
 *
 */
public class DoublyLinkedList<E> implements PositionalList<E> {

	public static class Node<E> implements Position<E> {
		Node<E> prev;
		Node<E> next;
		E element;
		
		public Node (E e) {
			element  = e;
			prev = null;
			next = null;
		}
		
		@Override
		public E getElement() throws IllegalStateException {
			return element;
		}
	}
	
	// class variables ---------------------------------------
	Node<E> head = null;
	Node<E> tail = null;
	private int size = 0;
	
	// constructors ------------------------------------------------
	public DoublyLinkedList() {
		head = new Node<E>(null);
		tail = new Node<E>(null);
		head.next = tail;
		tail.prev = head;
	}

	// methods ---------------------------------------------------------
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	private Node<E> convert(Position<E> p) {
		if (p instanceof Node) {
			Node<E> pos = (Node<E>) p;
			return pos;
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	@Override
	public Position<E> first() {
		if (head.next == tail) {
			return null;
		}
		return head.next;
	}

	@Override
	public Position<E> last() {
		if (tail.prev == head) {
			return null;
		}
		return tail.prev;
	}

	@Override
	public Position<E> before(Position<E> p) throws IllegalArgumentException {
		/* TCJ
		 * Calling the first method is a constant time method,
		 * Casting p to a node and returning its previous node's position is a constant time operation.
		 */
		if (this.first() == p) {
			return null;
		}
		
		return convert(p).prev;
	}

	@Override
	public Position<E> after(Position<E> p) throws IllegalArgumentException {
		/* TCJ
		 *  Calling the last method is a constant time method.
		 *  Casting p to a node and returning its next node's position is a constant time operation.
		 */
		if (this.last() == p) {
			return null;
		}
		
		return convert(p).next;
	}

	private Node<E> addBetween(Node<E> prevNode, Node<E> nextNode, E e) {
		Node<E> newNode = new Node<E>(e);
		newNode.prev = prevNode;
		newNode.next = nextNode;
		prevNode.next = newNode;
		nextNode.prev = newNode;
		size++;
		return newNode;
	}
	
	@Override
	public Position<E> addFirst(E e) {
		/* TCJ
		 * The add between method is of time complexity O(1), so this method is too.
		 */
		return addBetween(head, head.next, e);
	}

	@Override
	public Position<E> addLast(E e) {
		/* TCJ
		 * The add between method is of time complexity O(1), so this method is too.
		 */
		return addBetween(tail.prev, tail, e);
	}

	private boolean validPosition(Position<E> p) {
		return p instanceof Node;
	}
	
	@Override
	public Position<E> addBefore(Position<E> p, E e) throws IllegalArgumentException {
		/* TCJ
		 * The valid position method, addBetweenMethod, and convert method are all constant time calls, so this method is O(1).
		 */
		if (!validPosition(p)) {
			throw new IllegalArgumentException();
		}
		
		Node<E> reference = convert(p);
		
		return addBetween(reference.prev, reference, e);
	}

	@Override
	public Position<E> addAfter(Position<E> p, E e)throws IllegalArgumentException {
		/* TCJ
		 * The valid position method, addBetweenMethod, and convert method are all constant time calls, so this method is O(1).
		 */
		if (!validPosition(p)) {
			throw new IllegalArgumentException();
		}
		
		Node<E> reference = convert(p);
		
		return addBetween(reference, reference.next, e);
	}

	@Override
	public E set(Position<E> p, E e) throws IllegalArgumentException {
		/* TCJ
		 *  Determining position validity, retrieving and updating the element are constant time operations.
		 */
		if (!validPosition(p)) {
			throw new IllegalArgumentException();
		}
		
		Node<E> reference = convert(p);
		E temp = reference.getElement();
		
		reference.element = e;
		return temp;
	}

	@Override
	public E remove(Position<E> p) throws IllegalArgumentException {
		/* TCJ
		 *  Uses methods of constant time and performs actions of constant time
		 */
		if (!validPosition(p)) {
			throw new IllegalArgumentException();
		}
		
		Node<E> reference = convert(p);
		E temp = reference.getElement();
		
		reference.prev.next = reference.next;
		reference.next.prev = reference.prev;
		
		size--;
		return temp;
	}

	public class ListElementIterator implements Iterator<E> {
		Node<E> cursor = convert(first());
		
		@Override
		public boolean hasNext() {
			return cursor != tail;
		}

		@Override
		public E next() {
			E cur = cursor.getElement();
			cursor = cursor.next;
			return cur;
		}
	}
	
	@Override
	public Iterator<E> iterator() {
		return new ListElementIterator();
	}

	private class PositionIterator implements Iterator<Position<E>> {
		private Position<E> cursor = first();
		private Position<E> recent = null;
		@Override
		public boolean hasNext() {
			return cursor != null;
		}

		@Override
		public Position<E> next() throws NoSuchElementException {
			if (cursor == null) {
				throw new IllegalStateException("no remaining positions");
			}
			recent = cursor;
			cursor = after(cursor);
			return recent;
		}
	}

	private class PositionIterable implements Iterable<Position<E>> {
		public Iterator<Position<E>> iterator() {
			return new PositionIterator();
		}
	}
	
	@Override
	public Iterable<Position<E>> positions() {
		return new PositionIterable();
	}

	public E removeFirst() throws IllegalArgumentException {
		if (first() == null) {
			throw new IllegalArgumentException("element does not exist");
		}
		
		Node<E> node = convert(first());
		E temp = node.element;
		
		// connect head to node after first
		node.next.prev = head;
		head.next = node.next;
		
		size--;
		
		return temp;
	}

	public E removeLast() throws IllegalArgumentException {
		if (last() == null) {
			throw new IllegalArgumentException("element does not exist");
		}
		
		Node<E> node = convert(last());
		E temp = node.element;
		
		// connect node before last to tail
		node.prev.next = tail;
		tail.prev = node.prev;
		
		size--;
		
		return temp;
	}
}