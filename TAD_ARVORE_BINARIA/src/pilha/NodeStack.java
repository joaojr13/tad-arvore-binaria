package pilha;

public class NodeStack<E> implements Stack<E> {
	protected Node<E> top;
	protected int size;
	
	public NodeStack() { 
		top = null;
		size = 0;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return (top == null);
	}

	public void push(E elem) {
		Node<E> v = new Node<E>(elem, top);
		top = v;
		size++;

	}

	public E top() throws EmptyStackException {
		if (isEmpty())
			throw new EmptyStackException("Stack is empty.");

		return top.getElement();
	}

	public E pop() throws EmptyStackException {
		Node<E> aux = top;
		if (isEmpty()) throw new EmptyStackException("Stack is empty.");
		E temp = top.getElement();
		top = top.getNext();
		aux.setNext(null);
		size--;
		return temp;
	}
}
