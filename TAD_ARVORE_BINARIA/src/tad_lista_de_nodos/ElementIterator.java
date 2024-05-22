package tad_lista_de_nodos;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;

/* Uma classe iterator Simples para listas. Os elementos da clise são retornados por
* este iterator. Nenhuma cópia da lista é realizada, de forma que qualquer mudança
* na lista é refletida por este iterator. */

public class ElementIterator<E> implements Iterator<E> { //
	
	protected PositionList<E> list; //recebe uma lista do tipo positionList
	
	protected Position<E> cursor; // Ponteiro de controle
	
	// Cria um elemento iterator
	
	public ElementIterator(PositionList<E> L) {
	
	list = L;
	
	cursor = (list.isEmpty()) ? null : list.first(); //verifica se tem algo no ponteiro
	
	}
	
	// Retorna se o iterator tem ou não um próximo objeto.
	
	public boolean hasNext() { return (cursor != null); } //vrifica quem é o proximo se null, retorna false.
	
	// Retorna o próximo objeto do iterator.
	
	public E next() throws NoSuchElementException {
	
	if (cursor == null) throw new NoSuchElementException("No next element");
	
	E toReturn = cursor.element(); //Pega o element da posição do cursor,
	
	cursor = (cursor == list.last()) ? null : list.next(cursor); // se cursor for ultimo, retorna null, senao
	//pega o proximo
	
	return toReturn;
	
	}
	
	// Dispara um {@link UnsupportedOperationException} para todos os casos, porque
	
	// a remoção não é uma operação suportada por este iterator.
	
	// não vai fazer nada, pois é um contrato, dever ter ele implementado, por causa do interator
	
	public void remove() throws UnsupportedOperationException {
	
	throw new UnsupportedOperationException("remove");
	
	}

}
