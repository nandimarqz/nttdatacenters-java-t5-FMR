package nttdata.javat2.business;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nttdata.javat2.execeptions.CategoryNotFound;
import nttdata.javat2.execeptions.EqualEmployee;
import nttdata.javat2.execeptions.MaximumNumOfEmplInCateg;
import nttdata.javat2.execeptions.MaximumOfCategories;

/**
 * Clase para el servicio de gestión de usuarios
 * 
 * @author nandi
 *
 */
public class ManagmentServiceImpl implements ManagmentServiceI {

	/** Mapa para guardar los empleados en su cateogoría */
	private Map<String, List<Employee>> categoryEmployees = new HashMap<>();

	/** Contador de empleados */
	private Integer employeeAccountant = 0;

	/** Número máximo de empleados por categoría */
	private static final Integer MAXNUMOFEMPLBYCATEG = 5;

	/** Número máximo de categorías */
	private static final Integer MAXNUMOFCATEG = 3;

	/** Logger para las trazas */
	private static final Logger SERVICELOG = LoggerFactory.getLogger(ManagmentServiceImpl.class);

	/** Final del método */
	private static final String ENDOFMETHOD = "FIN DEL MÉTODO";

	/** Inicio del método */
	private static final String BEGINNINGOFMETOD = "INICIO DEL MÉTODO";

	/** Nombre del archivo */
	private static final String excelNAME = "CategoriaEmpleado.xlsx";

	@Override
	public void dischargeEmployee(String name, String category) {

		SERVICELOG.info(BEGINNINGOFMETOD);

		// Se genera un try para capturar las excepciones
		try {

			// Si la categoría existe y el empleado no ha sido insertado antes en alguna
			// categoría entra en la condición
			if (this.checkCategory(category.toUpperCase())
					&& !this.checkEmployee(new Employee(employeeAccountant, name, category))) {

				// Si el número de empleados en la cateogoría es menor que el máximo entra en la
				// condición e inserta al empleado en su categoría en el mapa. si el numero de
				// empleado es igual al maximo salta una excepción.
				if (this.numberEmployeesInCategory(category.toUpperCase()) < MAXNUMOFEMPLBYCATEG) {

					SERVICELOG.info("INSERTANDO EL EMPLEADO {} EN LA CATEGORÍA {}", name, category);
					categoryEmployees.get(category.toUpperCase()).add(new Employee(employeeAccountant, name, category));
					employeeAccountant++;

				} else {

					SERVICELOG.warn("MÁXIMO NUMERO DE EMPLEADOS EN LA CATEGORÍA {}", category);
					throw new MaximumNumOfEmplInCateg(
							"El numero de empleados en la categoria " + category + " ha llegado a su maximo");

				}

			}
			// Captura las excepciones y muestra el mensaje por consola
		} catch (CategoryNotFound | MaximumNumOfEmplInCateg | EqualEmployee e) {

			SERVICELOG.warn(e.getMessage());
			System.out.println(e.getMessage());
		}

		SERVICELOG.info(ENDOFMETHOD);
	}

	@Override
	public void dischargeCategory(String category) throws MaximumOfCategories {

		SERVICELOG.info(BEGINNINGOFMETOD);

		// Si el mapa no contiene la categoría como clave entra en la condición
		if (!categoryEmployees.containsKey(category.toUpperCase())) {

			// Si el número de categorías de la empresa es menor al máximo entra en la
			// condición e inserta la cateogria como clave y como valor una lista vacia de
			// empleados, si no lanza una excepción.
			if (this.numberOfCategories() < MAXNUMOFCATEG) {

				SERVICELOG.info("SE HA INSERTADO LA CATEGORÍA {}", category);
				categoryEmployees.put(category.toUpperCase(), new LinkedList<Employee>());

			} else {

				SERVICELOG.warn("MÁXIMO NUMERO DE CATEGORÍAS {}", category);
				throw new MaximumOfCategories("El numero de categorias ha llegado al maximo");

			}

		}

		SERVICELOG.info(ENDOFMETHOD);

	}

	@Override
	public boolean checkEmployee(Employee e) throws EqualEmployee {

		SERVICELOG.info(BEGINNINGOFMETOD);

		// booleano que indica si existe
		boolean exist = Boolean.FALSE;

		// Genera el iterador para iterar la lista de claves
		Iterator<String> it = categoryEmployees.keySet().iterator();

		// Mientras el iterador tenga valor siguiente y exist sea falso entra en el
		// bucle
		while (it.hasNext() && !exist) {

			// Guarda la categoria
			String category = it.next();

			// Comprueba si la lista de la categoría contiene al usuario si da true entra en
			// la condicion y si da false no
			if (categoryEmployees.get(category).contains(e)) {

				// Cambia el boolean a true y lanza una excepción
				exist = Boolean.TRUE;
				throw new EqualEmployee("Ya existe este usuario en alguna categoria");

			}

		}

		SERVICELOG.info(ENDOFMETHOD);
		// Devuelve exist
		return exist;
	}

	@Override
	public String showEmployees() throws CategoryNotFound {

		SERVICELOG.info(BEGINNINGOFMETOD);

		/** Creacion excel */
		 XSSFWorkbook excel = new XSSFWorkbook();
		
		/** Crea la hoja al excel */
		XSSFSheet sheet = excel.createSheet("Categorias-empleados");
		
		//Genera la cabecera
		this.generateHeader(sheet);
		
		// Contador se pone a 1 ya que la cabecera es la 0
		int i = 1;
				
		// Genera un stringBuilder para la lista de empleados
		StringBuilder employeeList = new StringBuilder();

		
		employeeList.append("EMPLEADOS: ");
		employeeList.append("\n");

		// Recorre las claves del mapa
		for (String category : categoryEmployees.keySet()) {

			// Añade la categoria y un salto de línea
			employeeList.append("CATEGORIA: " + category);
			employeeList.append("\n");
			
			// Recorre los empleados de la categoría
			for (Employee e : categoryEmployees.get(category)) {

				// Añade el empleado y un salto de linea
				employeeList.append(e);
				employeeList.append("\n");
				
				//Generamos una fila con los datos del empleado
				this.generateRow(sheet, i, e);
				
				i++;
			}

		}
		
		//Creamos el archivo
		try(FileOutputStream out = new FileOutputStream(new File(excelNAME))) {
			
			employeeList.append("Se ha generado el fichero: "+ excelNAME);
			
			excel.write(out); // Escribimos el contenido en el archivo
			excel.close(); // Cerramos el excel
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SERVICELOG.info(ENDOFMETHOD);

		// Devuelve el contenido del StringBuilder
		return employeeList.toString();
	}
	
	/**
	 * Genera una fila con los datos del empleado pasado por parámetro
	 * 
	 * @param sheet
	 * @param rowNum
	 * @param e
	 */
	public void generateRow (XSSFSheet sheet, int rowNum, Employee e) {
		
		//Creamos la fila con el numero que se pasa por parametro
		XSSFRow row = sheet.createRow(rowNum);
		
		//Se van creando las celdas de la fila con los datos del empleado
		Cell cell = row.createCell(0);
		cell.setCellValue(e.getId());
		cell  = row.createCell(1);
		cell.setCellValue(e.getName());
		cell  = row.createCell(2);
		cell.setCellValue(e.getCategory());
	}
	
	
	/**
	 * Genera la cabecera del excel
	 * 
	 * @param sheet
	 */
	public void generateHeader(XSSFSheet sheet) {
		
		//Crea la fila 0 que es la cabecera
		XSSFRow row = sheet.createRow(0);
		
		// Crea la cabecera
		String[] header = new String[] { "ID", "Nombre", "Categoria" };
		
		for(int i = 0; i < 3; i++) {
			
			//Se van generand las celdas con el contenido de la cabecera
			Cell cell = row.createCell(i);
			cell.setCellValue(header[i]);
			
		}
		
	}

	@Override
	public Integer numberOfEmployees() {

		SERVICELOG.info("DEVOLVIENDO NUMERO DE EMPLEADOS");

		// Devuelve el contador de los empleados
		return employeeAccountant;
	}

	@Override
	public Integer numberEmployeesInCategory(String category) throws CategoryNotFound {

		SERVICELOG.info("DEVOLVIENDO EL NUMERO DE EMPLEADOS EN LA CATEGORÍA {}", category);
		Integer numOfEmpl = 0;

		// Si el mapa contiene la categoría entra en la condición, si no, lanza una
		// excepción
		if (categoryEmployees.containsKey(category.toUpperCase())) {

			// se cambia el valor de la variable al tamaño de la lista de la categoría
			numOfEmpl = categoryEmployees.get(category.toUpperCase()).size();

		} else {

			throw new CategoryNotFound("No se ha encontrado la categoría");

		}

		// Devuelve numOfEmpl
		return numOfEmpl;
	}

	@Override
	public Integer numberOfCategories() {

		SERVICELOG.info("DEVOLVIENDO NUMERO DE CATEGORÍAS");

		// Devuelve el tamaño del mapa
		return categoryEmployees.size();
	}

	@Override
	public boolean checkCategory(String category) throws CategoryNotFound {

		SERVICELOG.info(BEGINNINGOFMETOD);

		// booleano que indica si existe o no
		boolean exist = Boolean.FALSE;

		// Si el mapa contiene la categoria pasada por parámetro entra en la condición,
		// si no lanza una excepción
		if (categoryEmployees.containsKey(category.toUpperCase())) {

			// Cambia el valor de exist a true
			exist = Boolean.TRUE;

		} else {

			SERVICELOG.warn("LA CATEGORÍA NO EXISTE  {}", category);
			throw new CategoryNotFound("La categoria no existe");

		}

		SERVICELOG.info(ENDOFMETHOD);

		// Devuelve exist
		return exist;
	}

	@Override
	public String showCategories() {

		SERVICELOG.info(BEGINNINGOFMETOD);

		// Genera un StringBuilder para la lista de categorías
		StringBuilder categoryList = new StringBuilder();

		categoryList.append("Categorias : ");
		categoryList.append("\n");

		// Recorre las claves del mapa
		for (String category : categoryEmployees.keySet()) {

			// Añade la categoría y un salto de línea
			categoryList.append(category);
			categoryList.append("\n");

		}

		SERVICELOG.info(ENDOFMETHOD);

		// Devuelve el contenido del StringBuilder
		return categoryList.toString();

	}

}
