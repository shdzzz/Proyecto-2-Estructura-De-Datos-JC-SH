package proyecto.pkg2.estructura.de.datos.jc.sh;

/**
 * Cola de prioridad simple/ arbol binario completo
 *
 * @author shdz
 */
public class MonticuloBinario {

	private NodoDocumento[] monticulo;
	private int tamaño;
	private int capacidad;

	/**
	 * Constructor que crea un montículo con capacidad inicial de 100.
	 */
	public MonticuloBinario() {
		this.capacidad = 100;
		this.monticulo = new NodoDocumento[capacidad];
		this.tamaño = 0;
	}

	/**
	 * Inserta un documento en la cola de prioridad. Usa claveOrdenacion
	 * como criterio principal, etiquetaTiempo como desempate.
	 *
	 * @param doc Documento a insertar
	 * @return true si se insertó correctamente, false si está lleno
	 */
	public boolean insertar(NodoDocumento doc) {
		if (tamaño >= capacidad) {
			return false;
		}

		monticulo[tamaño] = doc;
		doc.setIndiceEnMonticulo(tamaño);
		doc.setEnCola(true);

		int actual = tamaño;
		while (actual > 0) {
			int padre = (actual - 1) / 2;

			// Comparar por claveOrdenacion, usar etiquetaTiempo como desempate
			if (monticulo[actual].getClaveOrdenacion() > monticulo[padre].getClaveOrdenacion()) {
				break;
			} else if (monticulo[actual].getClaveOrdenacion() == monticulo[padre].getClaveOrdenacion()) {
				if (monticulo[actual].getEtiquetaTiempo() >= monticulo[padre].getEtiquetaTiempo()) {
					break;
				}
			}

			NodoDocumento temp = monticulo[actual];
			monticulo[actual] = monticulo[padre];
			monticulo[padre] = temp;

			monticulo[actual].setIndiceEnMonticulo(actual);
			monticulo[padre].setIndiceEnMonticulo(padre);

			actual = padre;
		}

		tamaño++;
		return true;
	}

	/**
	 * Elimina el documento con mayor prioridad (menor valor). Usa
	 * claveOrdenacion como criterio principal, etiquetaTiempo como
	 * desempate.
	 *
	 * @return Documento eliminado, null si está vacío
	 */
	public NodoDocumento eliminar_min() {
		if (tamaño == 0) {
			return null;
		}

		NodoDocumento minimo = monticulo[0];
		minimo.setIndiceEnMonticulo(-1);
		minimo.setEnCola(false);

		tamaño--;
		if (tamaño > 0) {
			monticulo[0] = monticulo[tamaño];
			monticulo[0].setIndiceEnMonticulo(0);

			int actual = 0;
			while (true) {
				int izquierdo = 2 * actual + 1;
				int derecho = 2 * actual + 2;
				int menor = actual;

				// Comparar con hijo izquierdo
				if (izquierdo < tamaño) {
					if (monticulo[izquierdo].getClaveOrdenacion() < monticulo[menor].getClaveOrdenacion()) {
						menor = izquierdo;
					} else if (monticulo[izquierdo].getClaveOrdenacion() == monticulo[menor].getClaveOrdenacion()) {
						if (monticulo[izquierdo].getEtiquetaTiempo() < monticulo[menor].getEtiquetaTiempo()) {
							menor = izquierdo;
						}
					}
				}

				// Comparar con hijo derecho
				if (derecho < tamaño) {
					if (monticulo[derecho].getClaveOrdenacion() < monticulo[menor].getClaveOrdenacion()) {
						menor = derecho;
					} else if (monticulo[derecho].getClaveOrdenacion() == monticulo[menor].getClaveOrdenacion()) {
						if (monticulo[derecho].getEtiquetaTiempo() < monticulo[menor].getEtiquetaTiempo()) {
							menor = derecho;
						}
					}
				}

				if (menor == actual) {
					break;
				}

				NodoDocumento temp = monticulo[actual];
				monticulo[actual] = monticulo[menor];
				monticulo[menor] = temp;

				monticulo[actual].setIndiceEnMonticulo(actual);
				monticulo[menor].setIndiceEnMonticulo(menor);

				actual = menor;
			}
		}

		return minimo;
	}

	/**
	 * Verifica si el montículo está vacío.
	 *
	 * @return true si está vacío, false en caso contrario
	 */
	public boolean estaVacio() {
		return tamaño == 0;
	}

	/**
	 * Obtiene el número de elementos en el montículo.
	 *
	 * @return Tamaño actual del montículo
	 */
	public int getTamaño() {
		return tamaño;
	}

	/**
	 * Obtiene el documento con mayor prioridad sin eliminarlo.
	 *
	 * @return Documento en la raíz, null si está vacío
	 */
	public NodoDocumento verMinimo() {
		if (tamaño == 0) {
			return null;
		}
		return monticulo[0];
	}

	/**
	 * Elimina un documento específico del montículo. Cambia su clave a
	 * Long.MIN_VALUE, lo sube a la raíz y llama a eliminar_min().
	 *
	 * @param doc Documento a eliminar
	 * @return true si se eliminó correctamente, false si no está en el
	 * montículo
	 */
	public boolean eliminarDocumento(NodoDocumento doc) {
		if (doc.getIndiceEnMonticulo() == -1 || !doc.isEnCola()) {
			return false; // No está en el montículo
		}

		int indice = doc.getIndiceEnMonticulo();

		// Cambiar clave a valor mínimo para que vaya a la raíz
		long claveOriginal = doc.getClaveOrdenacion();
		doc.setClaveOrdenacion(Long.MIN_VALUE);

		// Flotar hasta la raíz
		while (indice > 0) {
			int padre = (indice - 1) / 2;

			if (monticulo[indice].getClaveOrdenacion() >= monticulo[padre].getClaveOrdenacion()) {
				break;
			}

			// Intercambiar con el padre
			NodoDocumento temp = monticulo[indice];
			monticulo[indice] = monticulo[padre];
			monticulo[padre] = temp;

			monticulo[indice].setIndiceEnMonticulo(indice);
			monticulo[padre].setIndiceEnMonticulo(padre);

			indice = padre;
		}

		// Eliminar el mínimo (que ahora es nuestro documento)
		NodoDocumento eliminado = eliminar_min();

		// Restaurar clave original (opcional, ya que se va a eliminar)
		if (eliminado != null) {
			eliminado.setClaveOrdenacion(claveOriginal);
		}

		return true;
	}
	/**
	 * Metodo necesario para acceder a los nodos por sus indices.
	 * @param i es el indice del nodo a buscar
	 */
	public NodoDocumento getNodo(int i) {
		if (i >= 0 && i < tamaño) {
			return monticulo[i];
		}
		return null;
	}
}
