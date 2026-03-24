/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.pkg2.estructura.de.datos.jc.sh;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * Clase encargada de la carga, guardado y verificacion de formato de los
 * archivos csv o txt
 *
 * @author JuanColl
 */
public class ManejoDeArchivos {
	// Variable para guardar los mensajes de error y que tu JFrame pueda leerlos

	private String ultimoError = "";
	private File archivoCSVActual = null;

	/**
	 * Devuelve el último mensaje de error registrado. Ideal para usarlo en
	 * un JOptionPane en tu JFrame.
	 */
	public String getUltimoError() {
		return ultimoError;
	}

	/**
	 * Abre el JFileChooser, verifica el archivo y extrae los datos.
	 *
	 * @param padre El componente padre (tu JFrame).
	 * @return Una matriz String[][] con los usuarios y tipos, o null si
	 * hubo un error.
	 */
	public String[][] cargarUsuarios(Component padre) {
		JFileChooser selector = new JFileChooser();
		selector.setDialogTitle("Seleccionar archivo CSV de usuarios");
		selector.setFileFilter(new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv"));

		int seleccion = selector.showOpenDialog(padre);
		if (seleccion != JFileChooser.APPROVE_OPTION) {
			ultimoError = "Carga cancelada por el usuario.";
			return null; // Retornamos null porque no hay datos
		}
		File archivo = selector.getSelectedFile();
		if (!verificarFormato(archivo)) {
			return null; // Si falla, el mensaje de error ya se guardó dentro del método
		}
		this.archivoCSVActual = archivo;
		return extraerDatos(archivo);
	}

	/**
	 * metodo de verificacion: Revisa que sea .csv, que no esté vacío, que
	 * la cabecera sea "usuario, tipo" y que las líneas tengan 2 columnas.
	 */
	public boolean verificarFormato(File archivo) {
		if (!archivo.getName().toLowerCase().endsWith(".csv")) {
			ultimoError = "El archivo debe tener extensión .csv";
			return false;
		}

		try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
			String linea = br.readLine();

			if (linea == null) {
				ultimoError = "El archivo está vacío.";
				return false;
			}

			// Validar cabecera (quitamos espacios y pasamos a minúscula para evitar fallos tontos)
			String cabecera = linea.trim().toLowerCase().replace(" ", "");
			if (!cabecera.equals("usuario,tipo")) {
				ultimoError = "Formato inválido. La primera línea DEBE ser 'Usuario, tipo'.";
				return false;
			}

			// Validar que el resto de las líneas tengan el formato correcto (separadas por una coma)
			int numeroLinea = 2;
			while ((linea = br.readLine()) != null) {
				if (linea.trim().isEmpty()) {
					continue; // Ignoramos líneas en blanco
				}
				String[] partes = linea.split(",");
				if (partes.length != 2) {
					ultimoError = "Error en la línea " + numeroLinea + ": Se esperaban 2 valores separados por coma.";
					return false;
				}
				numeroLinea++;
			}

			return true; // Todo está perfecto

		} catch (IOException e) {
			ultimoError = "Error al intentar leer el archivo: " + e.getMessage();
			return false;
		}
	}

	/**
	 * Método auxiliar privado para sacar los datos sin usar ArrayList.
	 */
	private String[][] extraerDatos(File archivo) {
		try {
			// Primer paso: contar las líneas para saber de qué tamaño crear la matriz
			int cantidadLineas = 0;
			BufferedReader brContador = new BufferedReader(new FileReader(archivo));
			brContador.readLine(); // saltar cabecera
			String linea;
			while ((linea = brContador.readLine()) != null) {
				if (!linea.trim().isEmpty()) {
					cantidadLineas++;
				}
			}
			brContador.close();

			// Creamos la matriz: filas = cantidadLineas, columnas = 2 (Usuario y Tipo)
			String[][] datos = new String[cantidadLineas][2];

			// Segundo paso: Leer de nuevo para guardar los datos en la matriz
			BufferedReader brLector = new BufferedReader(new FileReader(archivo));
			brLector.readLine(); // saltar cabecera

			int indice = 0;
			while ((linea = brLector.readLine()) != null) {
				if (linea.trim().isEmpty()) {
					continue;
				}
				String[] partes = linea.split(",");
				datos[indice][0] = partes[0].trim(); // Guardamos el nombre del usuario
				datos[indice][1] = partes[1].trim(); // Guardamos el tipo
				indice++;
			}
			brLector.close();

			return datos; // Retornamos la matriz llena

		} catch (IOException e) {
			ultimoError = "Error al extraer los datos: " + e.getMessage();
			return null;
		}
	}

	/**
	 * Guarda una matriz de datos en un archivo CSV.
	 *
	 * @param padre El componente padre (JFrame).
	 * @param datos Matriz String[][] con los usuarios a guardar.
	 * @return true si se guardó correctamente, false si hubo error.
	 */
	public boolean guardarUsuarios(Component padre, String[][] datos) {
		File archivoDestino = archivoCSVActual;
		if (archivoDestino == null) {
			JFileChooser selector = new JFileChooser();
			selector.setDialogTitle("Guardar archivo CSV");
			selector.setFileFilter(new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv"));

			int seleccion = selector.showSaveDialog(padre);
			if (seleccion != JFileChooser.APPROVE_OPTION) {
				ultimoError = "Guardado cancelado.";
				return false; 
			}

			archivoDestino = selector.getSelectedFile();
			if (!archivoDestino.getName().toLowerCase().endsWith(".csv")) {
				archivoDestino = new File(archivoDestino.getParentFile(), archivoDestino.getName() + ".csv");
			}
			archivoCSVActual = archivoDestino;
		}
		try (PrintWriter pw = new PrintWriter(new FileWriter(archivoDestino))) {
			pw.println("usuario, tipo");
			if (datos != null) {
				for (int i = 0; i < datos.length; i++) {
					pw.println(datos[i][0] + ", " + datos[i][1]);
				}
			}
			return true;
		} catch (IOException e) {
			ultimoError = "Error al escribir el archivo: " + e.getMessage();
			return false;
		}
	}
}
