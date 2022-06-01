package nttdata.javat2;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nttdata.javat2.business.Employee;
import nttdata.javat2.business.ManagmentServiceI;
import nttdata.javat2.business.ManagmentServiceImpl;
import nttdata.javat2.execeptions.CategoryNotFound;
import nttdata.javat2.execeptions.EqualEmployee;
import nttdata.javat2.execeptions.MaximumOfCategories;



/**
 * Clase Main
 * 
 * @author nandi
 *
 */
public class T2MainFMR {
	/**
	 * Método Main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		/** Inyecta el servicion de gestion de usuarios */
		ManagmentServiceI ms = new ManagmentServiceImpl();

		/** Nombre del empleado */
		String employeeName;

		/** categoría del empleado */
		String categoryName;
		
		/** Logger para las trazas */
		final Logger MAINLOG = LoggerFactory.getLogger(ManagmentServiceImpl.class);

		/** Menu */
		final String MENU = "¿Que opcion desea realizar?" + "\n1.Dar de alta una categoría"
				+ "\n2.Dar de alta un empleado" + "\n3.Comprobar si existe un usuario"
				+ "\n4.Comprobar si existe una categoria" + "\n5.Numero de empleados"
				+ "\n6.Numero de empleados en una categoria concreta" + "\n7.Numero de categorias"
				+ "\n8.Mostrar los empleados" + "\n9.Mostrar las categorias" + "\n10.Salir";
		// Genera un scanner para la recogida de datos por consola
		try (Scanner sc = new Scanner(System.in)) {

			// Muestra el menú
			MAINLOG.info("Se genera el menu");
			System.out.println(MENU);

			// Recoge la opcion
			int option = sc.nextInt();

			// Mientras que la opcion es distinta de 10 entra en el bucle
			while (option != 10) {
				// Lo siguiente va dentro de estre try ya que puede generar excepciones
				try {
					// Entra en el caso que indique la variable option
					switch (option) {
					// En este caso pide el nombre de una categoría y llama al método
					// dischargeCategory de la clase del servicio de
					// gestión, que es para dar de alta una categoría
					case 1:
						
						MAINLOG.info("Caso 1");

						System.out.println("Introduzca el nombre de la categoria: ");
						categoryName = sc.next();

						ms.dischargeCategory(categoryName);

						break;
					// En este caso pide el nombre del empleado y a la categoría que pertenece y
					// luego llama al método dischargeEmployee de la clase del servicio de gestión,
					// que es para dar de alta a un empleado
					case 2:
						
						MAINLOG.info("Caso 2");
						
						System.out.println("Introduzca el nombre del empleado: ");
						employeeName = sc.next();

						System.out.println("Introduzca la categoria: ");
						categoryName = sc.next();

						ms.dischargeEmployee(employeeName, categoryName);
						break;
					// En este caso pide el nombre de un empleado y llama al método checkEmployee de
					// la clase del servicio de gestión que es para comprobar si un usuario existe o
					// no.
					case 3:
						
						MAINLOG.info("Caso 3");

						System.out.println("Introduzca el nombre del empleado: ");
						employeeName = sc.next();

						System.out.println(ms.checkEmployee(new Employee(0, employeeName, null)));
						System.out.println(" ");

						break;
					// En este caso pide el nombre de una categoría y llama al método checkCategory
					// de la clase del servicio de gestión que es para comprobar si un usuario
					// existe o no
					case 4:
						
						MAINLOG.info("Caso 4");

						System.out.println("Introduzca la categoria: ");
						categoryName = sc.next();

						System.out.println(ms.checkCategory(categoryName));
						System.out.println(" ");

						break;
					// En este caso muestra por consola el numero de empleados el número de
					// empleados de la empresa llamando al método numberOfEmployees
					case 5:
						
						MAINLOG.info("Caso 5");

						System.out.println(ms.numberOfEmployees());
						System.out.println(" ");

						break;
					// En este caso pide el nombre de una categoría y llama al método
					// numberEmployeesInCategory de la clase del servicio de gestión para mostrar
					// por consola el número de empleados de una categoría concreta
					case 6:
						
						MAINLOG.info("Caso 6");

						System.out.println("Introduzca la categoria: ");
						categoryName = sc.next();

						System.out.println(ms.numberEmployeesInCategory(categoryName));
						System.out.println(" ");

						break;
					// En este caso muestra por consola el número de categorías de la empresa
					// llamando al método numberOfCategories de la clase del servicio de gestión
					case 7:
						
						MAINLOG.info("Caso ");

						System.out.println(ms.numberOfCategories());
						System.out.println(" ");

						break;
					// En este caso muestra a los empleados de la empresa llamando al método
					// showEmployees de la clase del servicio de gestión
					case 8:
						
						MAINLOG.info("Caso 8");

						System.out.println(ms.showEmployees());
						System.out.println(" ");

						break;
					// En este caso se muestra las categorias de la empresa llamando al método
					// showCategories de la clase del servicio de gestión
					case 9:
						
						MAINLOG.info("Caso 9");

						System.out.println(ms.showCategories());
						System.out.println(" ");

						break;
					// Este es el caso default por si no encuetra la opción introducida
					default:
						
						MAINLOG.info("Caso default");

						System.out.println("Esta opcion no existe escoja alguna del menu");

						break;
					}

					// Se recogen las excepciones y se muestra por consola el mensaje de la
					// excepción ocurrida
				} catch (MaximumOfCategories | EqualEmployee | CategoryNotFound e) {

					MAINLOG.warn(e.getMessage());
					System.out.println(e.getMessage());
					System.out.println(" ");
				}

				// Se vuelve a mostrar el menu y recoger la opción
				MAINLOG.info("Se genera el menu");
				System.out.println(MENU);
				option = sc.nextInt();
			}
		}
	}
}
