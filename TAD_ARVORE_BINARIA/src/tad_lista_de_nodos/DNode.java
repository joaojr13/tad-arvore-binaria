package tad_lista_de_nodos;

public class DNode<E> implements Position<E> {
	
	private DNode<E> prev, next; // Referência para os nodos anterior e posterior
	
	private E element; // Elemento armazenado nesta posição
	
	// Construtor
	
	public DNode(DNode<E> newPrev, DNode<E> newNext, E elem) {
	
	prev = newPrev;
	
	next = newNext;
	
	element = elem;
	
	}
	
	// Método da interface Position
	
	public E element() throws InvalidPositionException {
	
	if ((prev == null) && (next == null))
	
	throw new InvalidPositionException("Position is not in a list!");
	
	return element;
	
	}
	
	// Métodos de acesso
	
	public DNode<E> getNext() { return next; }
	
	public DNode<E> getPrev() { return prev; }
	
	// Métodos de atualização
	
	public void setNext(DNode<E> newNext) { next = newNext; }
	
	public void setPrev(DNode<E> newPrev) { prev = newPrev; }
	
	public void setElement(E newElement) { element = newElement; }

}