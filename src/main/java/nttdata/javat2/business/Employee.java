package nttdata.javat2.business;

import java.util.Objects;

/**
 * Clase empleado
 * 
 * @author nandi
 *
 */
public class Employee implements Comparable<Employee> {

	/** ID del empleado en la empresa */
	private Integer id;

	/** Nombre del empleado */
	private String name;

	/** Categoría del empleado */
	private String category;

	/** Nombre de la empresa */
	private static final String COMPANY = "NTTDATA";

	/**
	 * Constructor del empleado
	 * 
	 * @param id
	 * @param name
	 * @param category
	 */
	public Employee(Integer id, String name, String category) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		
		
	}

	/**
	 * Método que devuelve el id del empleado
	 * 
	 * @return Integer
	 */
	public Integer getId() {

		return id;

	}

	/**
	 * Método que actualiza el id del empleado
	 * 
	 * @param id
	 */
	public void setId(Integer id) {

		this.id = id;

	}

	/**
	 * Método que devuelve un String con el nombre del empleado
	 * 
	 * @return String
	 */
	public String getName() {

		return name;

	}

	/**
	 * Método que actualiza el nombre del empleado
	 * 
	 * @param name
	 */
	public void setName(String name) {

		this.name = name;

	}

	/**
	 * Método que devuelve un String con la categoría del empleado
	 * 
	 * @return String
	 */
	public String getCategory() {

		return category;

	}

	/**
	 * Método que actualiza la categoría del empleado
	 * 
	 * @param category
	 */
	public void setCategory(String category) {

		this.category = category;

	}

	/**
	 * Método que devuelve un String con el nombre de la empresa
	 * 
	 * @return
	 */
	public static String getCompany() {

		return COMPANY;

	}

	/**
	 * Método que devuelve el codigo hash generado para el empleado a través de su
	 * nombre y categoría
	 * 
	 * @return Integer
	 */
	@Override
	public int hashCode() {
		return Objects.hash(category, name);
	}

	/**
	 * Método que devuelve un booleano a true si los empleados son iguales o false
	 * si son diferentes.
	 * 
	 * Dos empleados son iguales si sus nombres coinciden
	 * 
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(name, other.name);
	}

	/**
	 * Método que devuelve un entero mayor a 0 si el empleado invocante es mayor al
	 * pasado por parámetro, 0 si son iguales y menor a 0 si el empleado pasador por
	 * parámetro es mayor al invocante
	 * 
	 * Un empleado es mayor a otro depende su ID
	 * 
	 * @return Integer
	 */
	@Override
	public int compareTo(Employee o) {

		return this.id - o.id;
	}

	/**
	 * Método que devuelve un String con los atributos del usuario
	 * 
	 * @return String
	 */
	@Override
	public String toString() {

		return "ID: " + id + " Name: " + name + " Category=" + category;

	}

}
