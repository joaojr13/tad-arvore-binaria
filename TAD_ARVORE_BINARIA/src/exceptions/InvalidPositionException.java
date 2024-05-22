package exceptions;

@SuppressWarnings("serial")
public class InvalidPositionException
extends RuntimeException {

	/* Exceção lançada se a posição fornecida como argumento não é
	* válida (por exemplo, se é uma referência nula ou não tem
	* lista associada). */
	public InvalidPositionException(String err) { super(err); }

}