package nttdata.javat2.business;

import nttdata.javat2.execeptions.CategoryNotFound;
import nttdata.javat2.execeptions.EqualEmployee;
import nttdata.javat2.execeptions.MaximumOfCategories;

/**
 * Interfaz del servicio de gestión de usuarios implementa los metodos de
 * gestión
 * 
 * @author nandi
 *
 */
public interface ManagmentServiceI {

	/**
	 * Método para dar de alta a los empleados
	 * 
	 * @param name
	 * @param category
	 * @throws CategoryNotFound
	 */
	public void dischargeEmployee(String name, String category) throws CategoryNotFound;

	/**
	 * Método para dar de alta a las categorías
	 * 
	 * @param categoryName
	 * @throws MaximumOfCategories
	 */
	public void dischargeCategory(String categoryName) throws MaximumOfCategories;

	/**
	 * Método para que devuelve un String con los empleados de la empresa
	 * 
	 * @return String
	 */
	public String showEmployees();

	/**
	 * Método que devuelve el numero de empleados de la empresa
	 * 
	 * @return Integer
	 */
	public Integer numberOfEmployees();

	/**
	 * Método que devuelve un booleano a true si existe el empleado o a false si no
	 * existe
	 * 
	 * @param employee
	 * @return boolean
	 * @throws EqualEmployee
	 */
	public boolean checkEmployee(Employee employee) throws EqualEmployee;

	/**
	 * Método que devuelve un booleano a true si existe la categoría o a false si no
	 * existe
	 * 
	 * @param categoryName
	 * @return boolean
	 * @throws CategoryNotFound
	 */
	public boolean checkCategory(String categoryName) throws CategoryNotFound;

	/**
	 * Método que devuelve el número de empleados que hay en una categoría concreta
	 * 
	 * @param categoryName
	 * @return Integer
	 * @throws CategoryNotFound 
	 */
	public Integer numberEmployeesInCategory(String categoryName) throws CategoryNotFound;

	/**
	 * Método que devuelve el número de categorías que hay en la empresa
	 * 
	 * @return Integer
	 */
	public Integer numberOfCategories();

	/**
	 *  Método que devuelve un String con las categorías de la empresa
	 *  
	 * @return String
	 */
	public String showCategories();
}
