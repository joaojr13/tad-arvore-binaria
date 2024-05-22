package tad_lista_de_nodos;

/* Exceção lançada se a lista estiver vazia e tentar, por exemplo,
* obter o primeiro elemento da lista usando o método first. */

@SuppressWarnings("serial")
public class EmptyListException extends RuntimeException {
	public EmptyListException(String err) { super(err); }
}