package source;

// Classe que implementa um nodo de árvore binária armazenando referências

// para um elemento, o nodo pai, o nodo da direita e o nodo da esquerda.

public class BTNode<E> implements BTPosition<E>{
	
	private E element; // elemento armazenado neste nodo
	private BTPosition<E> left, right, parent; // nodos adjacentes
	
	// Construtor principal
	public BTNode(E element, BTPosition<E> parent, BTPosition<E> left, BTPosition<E> right) {
	setElement(element);
	setParent(parent);
	setLeft(left);
	setRight(right);
	}
	
	// Retorna o elemento armazenado nesta posição
	public E element() { return element; }
	
	// Define o elemento armazenado nesta posição
	public void setElement(E o) { element = o; }
	
	// Retorna o filho da esquerda desta posição
	public BTPosition<E> getLeft() { return left; }
	
	// Define o filho da esquerda desta posição
	public void setLeft(BTPosition<E> v) { left = v; }
	
	// Retorna o filho da direita desta posição
	public BTPosition<E> getRight() { return right; }
	
	// Define o filho da direita desta posição
	public void setRight(BTPosition<E> v) { right = v; }
	
	// Retorna o pai desta posição
	public BTPosition<E> getParent() { return parent; }
	
	// Define o pai desta posição
	public void setParent(BTPosition<E> v) { parent = v; }

}