package nttdata.javat2.execeptions;

/**
 * Excepción si dos empleado son iguales
 * 
 * @author nandi
 *
 */
public class EqualEmployee extends Exception{

	private static final long serialVersionUID = 1L;


	public EqualEmployee(String msg) {
		super(msg);
		
		
	}
}
