package nttdata.javat2.execeptions;

/**
 * Excepción por si no se encuentra la categoría
 * 
 * @author nandi
 *
 */
public class CategoryNotFound extends Exception {

	private static final long serialVersionUID = 1L;

	public CategoryNotFound(String msg) {
		super(msg);
		
		
	}
}
