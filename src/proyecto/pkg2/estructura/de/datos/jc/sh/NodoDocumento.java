package proyecto.pkg2.estructura.de.datos.jc.sh;

/**
 * Clase para guardar datos de un documento.
 * NOTA: Esta clase no almacena información del propietario.
 * La relación usuario-documento se gestiona exclusivamente en TablaDispersion.
 *
 * @author shdz
 */
public class NodoDocumento {

	private String nombre;
	private int tamaño;
	private String tipo;
	private long etiquetaTiempo;
	private boolean esPrioritario;
	private int indiceEnMonticulo;
	private int prioridadCalculada;
	private long claveOrdenacion;
	private boolean enCola;
	private NodoDocumento siguiente;

	/**
	 * Constructor para crear un nuevo documento.
	 *
	 * @param nombre Nombre del documento
	 * @param tamaño Tamaño en páginas
	 * @param tipo Tipo de documento
	 * @param etiquetaTiempo Tiempo de creación
	 * @param esPrioritario Indica si es prioritario
	 */
	public NodoDocumento(String nombre, int tamaño, String tipo, long etiquetaTiempo,
		boolean esPrioritario) {
		this.nombre = nombre;
		this.tamaño = tamaño;
		this.tipo = tipo;
		this.etiquetaTiempo = etiquetaTiempo;
		this.esPrioritario = esPrioritario;
		this.indiceEnMonticulo = -1;
		this.prioridadCalculada = 0; // Se calculará después
		this.claveOrdenacion = 0; // Se calculará después
		this.enCola = false;
		this.siguiente = null;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTamaño() {
		return tamaño;
	}

	public void setTamaño(int tamaño) {
		this.tamaño = tamaño;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public long getEtiquetaTiempo() {
		return etiquetaTiempo;
	}

	public void setEtiquetaTiempo(long etiquetaTiempo) {
		this.etiquetaTiempo = etiquetaTiempo;
	}

	public boolean isEsPrioritario() {
		return esPrioritario;
	}

	public void setEsPrioritario(boolean esPrioritario) {
		this.esPrioritario = esPrioritario;
	}

	public int getIndiceEnMonticulo() {
		return indiceEnMonticulo;
	}

	public void setIndiceEnMonticulo(int indiceEnMonticulo) {
		this.indiceEnMonticulo = indiceEnMonticulo;
	}

	public NodoDocumento getSiguiente() {
		return siguiente;
	}

	public void setSiguiente(NodoDocumento siguiente) {
		this.siguiente = siguiente;
	}

	/**
	 * Obtiene la prioridad calculada del documento.
	 *
	 * @return Valor de prioridad calculada (menor = mayor prioridad)
	 */
	public int getPrioridadCalculada() {
		return prioridadCalculada;
	}

	/**
	 * Establece la prioridad calculada del documento.
	 *
	 * @param prioridadCalculada Nuevo valor de prioridad
	 */
	public void setPrioridadCalculada(int prioridadCalculada) {
		this.prioridadCalculada = prioridadCalculada;
	}

	/**
	 * Calcula la prioridad basada en prioridad del usuario y tamaño del
	 * documento. Fórmula: (prioridadUsuario * 100) + tamaño
	 *
	 * @param nivelPrioridadUsuario Nivel de prioridad del usuario (1=alta,
	 * 2=media, 3=baja)
	 */
	public void calcularPrioridad(int nivelPrioridadUsuario) {
		this.prioridadCalculada = (nivelPrioridadUsuario * 100) + tamaño;
	}

	/**
	 * Calcula la clave de ordenación para el montículo utilizando un
	 * sistema de estratos. La clave se genera sumando una base de prioridad
	 * (10k, 20k, 30k o 40k) más el tiempo de llegada, garantizando que los
	 * niveles de prioridad no se solapen. * Jerarquía de claves (a menor
	 * valor, mayor prioridad en el Min-Heap): - Alta prioridad: 10,000 +
	 * tiempo - Media prioridad: 20,000 + tiempo - Baja prioridad: 30,000 +
	 * tiempo - No prioritarios: 40,000 + tiempo
	 *
	 * @param nivelPrioridadUsuario Nivel numérico del usuario (1=Alta,2=Media, 3=Baja)
	 */
	public void calcularClaveOrdenacion(int nivelPrioridadUsuario) {
		if (!esPrioritario) {
			this.claveOrdenacion = 40000 + etiquetaTiempo;
		} else {
			long basePrioridad = 0;
			switch (nivelPrioridadUsuario) {
				case 1:
					basePrioridad = 10000; // alta prioridad
					break;
				case 2:
					basePrioridad = 20000; // media prioridad
					break;
				case 3:
					basePrioridad = 30000; // baja prioridad
					break;
				default:
					basePrioridad = 30000;
			}
			// Sumamos el tiempo para que, dentro de su mismo rango, el que llegó primero salga primero
			this.claveOrdenacion = basePrioridad + etiquetaTiempo;
		}
	}

	/**
	 * Obtiene la clave de ordenación para el montículo.
	 *
	 * @return Clave de ordenación (menor = mayor prioridad)
	 */
	public long getClaveOrdenacion() {
		return claveOrdenacion;
	}

	/**
	 * Establece la clave de ordenación.
	 *
	 * @param claveOrdenacion Nueva clave de ordenación
	 */
	public void setClaveOrdenacion(long claveOrdenacion) {
		this.claveOrdenacion = claveOrdenacion;
	}

	/**
	 * Verifica si el documento está en cola.
	 *
	 * @return true si está en cola, false en caso contrario
	 */
	public boolean isEnCola() {
		return enCola;
	}

	/**
	 * Establece si el documento está en cola.
	 *
	 * @param enCola Nuevo estado en cola
	 */
	public void setEnCola(boolean enCola) {
		this.enCola = enCola;
	}
}
