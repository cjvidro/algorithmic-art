/**
 * @author Charles Vidro
 * Assignment 2.
 * 
 * This class creates a stack from a generic type
 */

public class DLLStack<E> implements Stack<E> {
	private DoublyLinkedList<E> stack = new DoublyLinkedList<>();
	
	@Override
	public int size() {
		return stack.size();
	}

	@Override
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	@Override
	public void push(E e) {
		/* TCJ
		 *  DoublyLinkedList add method is O(1)
		 */
		stack.addLast(e);
	}

	@Override
	public E top() {
		/* TCJ
		 * DoublyLinked List last method is O(1) and and getElement method is O(1)
		 */
		if (this.isEmpty()) {
			return null;
		}
		
		return stack.last().getElement();
	}

	@Override
	public E pop() {
		/* TCJ
		 * DoublyLinkedList removeLast method is O(1)
		 */
		if (this.isEmpty()) {
			return null;
		}
		
		return stack.removeLast();
	}
}
