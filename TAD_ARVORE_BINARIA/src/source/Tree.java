package source;

import java.util.Iterator;
import exceptions.BoundaryViolationException;
import exceptions.EmptyTreeException;
import exceptions.InvalidPositionException;
import position.Position;

// Interface para uma árvore onde os nodos podem ter uma quantidade arbitrária de filhos.

public interface Tree<E> extends Iterable<E> {
	// Retorna a quantidade de nodos da árvore.
	public int size();
	
	// Retorna se a árvore está vazia.
	public boolean isEmpty();
	
	// Retorna um iterador sobre os elementos armazenados na árvore.
	public Iterator<E> iterator();
	
	// Retorna uma coleção iterável dos nodos.
	public Iterable<Position<E>> positions();
	
	// Substitui o elemento armazenado em um dado nodo.
	public E replace(Position<E> v, E e) throws InvalidPositionException;
	
	// Retorna a raiz da árvore.
	public Position<E> root() throws EmptyTreeException;
	
	// Retorna o pai de um dado nodo.
	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException;
	
	// Retorna uma coleção iterável dos filhos de um dado nodo.
	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException;
	
	// Retorna se um dado nodo é interno.
	public boolean isInternal(Position<E> v) throws InvalidPositionException;
	
	// Retorna se um dado nodo é externo.
	public boolean isExternal(Position<E> v) throws InvalidPositionException;
	
	// Retorna se um dado nodo é a raiz da árvore.
	public boolean isRoot(Position<E> v) throws InvalidPositionException;

}