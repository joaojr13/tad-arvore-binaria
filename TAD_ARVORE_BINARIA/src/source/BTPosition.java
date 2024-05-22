package source;

import position.Position;

public interface BTPosition<E> extends Position<E>{

	// Define o elemento armazenado nesta posição
	public void setElement(E o);
	
	// Retorna o filho da esquerda desta posição
	public BTPosition<E> getLeft();
	
	// Define o filho da esquerda desta posição
	public void setLeft(BTPosition<E> v);
	
	// Retorna o filho da direita desta posição
	public BTPosition<E> getRight();
	
	// Define o filho da direita desta posição
	public void setRight(BTPosition<E> v);
	
	// Retorna o pai desta posição
	public BTPosition<E> getParent();
	
	// Define o pai desta posição
	public void setParent(BTPosition<E> v);

}