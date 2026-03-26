package proyecto.pkg2.estructura.de.datos.jc.sh;

/**
 * Tabla hash simple para guardar usuarios y registrar documentos en cola.
 * @author shdz
 */
public class TablaDispersion {
    
    private ListaEnlazada[] tabla;
    private int capacidad;
    private int tamaño;
    
    /**
     * Constructor que crea una tabla hash con capacidad inicial de 16.
     */
    public TablaDispersion() {
        this.capacidad = 16;
        this.tabla = new ListaEnlazada[capacidad];
        this.tamaño = 0;
        
        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new ListaEnlazada();
        }
    }

	private int funcionHash(String clave) {
		int hash = 0;
		for (int i = 0; i < clave.length(); i++) {
			hash = (31 * hash + clave.charAt(i)) % capacidad;
		}
		if (hash < 0) {
			hash = -hash;
		}
		return hash;
	}

	public void agregarUsuario(String nombre, String tipo, Usuario usuario) {
		if ((double) tamaño / capacidad > 0.75) {
			rehash();
		}

		int indice = funcionHash(nombre);
		tabla[indice].agregar(new InfoUsuario(nombre, tipo, usuario));
		tamaño++;
	}

	/**
	 * Busca un usuario por su nombre.
	 *
	 * @param nombre Nombre del usuario a buscar
	 */
	public Usuario buscarUsuario(String nombre) {
		int indice = funcionHash(nombre);
		ListaEnlazada Cubeta = tabla[indice];
		if (Cubeta.estaVacia()) {
			return null;
		}
		ListaEnlazada.NodoLista actual = Cubeta.cabeza;
		while (actual != null){
			if(actual.dato.getNombre().equalsIgnoreCase(nombre)){
				return actual.dato.getUsuario();
			}
			actual = actual.siguiente;
		}
		return null;
	}

	/**
	 * Elimina un usuario de la tabla hash.
	 *
	 * @param nombre Nombre del usuario a eliminar
	 * @return true si se eliminó correctamente, false en caso contrario
	 */
	public boolean eliminarUsuario(String nombre) {
		int indice = funcionHash(nombre);
		ListaEnlazada cubeta = tabla[indice];

		boolean eliminado = cubeta.eliminar(nombre);

		if (eliminado && cubeta.estaVacia()) {
			tamaño--;
		}

		return eliminado;
	}

	/**
	 * Verifica si un usuario existe en la tabla.
	 *
	 * @param nombre Nombre del usuario a verificar
	 * @return true si existe, false en caso contrario
	 */
	public boolean existeUsuario(String nombre) {
		return !tabla[funcionHash(nombre)].estaVacia();
	}

	/**
	 * Obtiene el número de usuarios en la tabla.
	 *
	 * @return Número de usuarios almacenados
	 */
	public int getTamaño() {
		return tamaño;
	}

	/**
	 * Obtiene la capacidad actual de la tabla.
	 *
	 * @return Capacidad máxima de la tabla
	 */
	public int getCapacidad() {
		return capacidad;
	}

	/**
	 * Registra un documento en cola de impresión.
	 * Almacena la información para poder eliminarlo posteriormente.
	 * 
	 * @param nombreUsuario Nombre del usuario propietario
	 * @param documento Documento en cola
	 * @param indiceEnMonticulo Posición en el montículo
	 * @param etiquetaTiempo Etiqueta de tiempo para prioridad
	 */
	public void registrarDocumentoEnCola(String nombreUsuario, NodoDocumento documento, 
									   int indiceEnMonticulo, long etiquetaTiempo) {
		int indice = funcionHash(nombreUsuario);
		ListaEnlazada cubeta = tabla[indice];
		
		// Buscar al usuario y agregar el documento a su lista de encolados
		ListaEnlazada.NodoLista actual = cubeta.cabeza;
		while (actual != null) {
			if (actual.dato.nombre.equals(nombreUsuario)) {
				actual.dato.agregarDocumentoEnCola(documento, indiceEnMonticulo, etiquetaTiempo);
				break;
			}
			actual = actual.siguiente;
		}
	}

	/**
	 * Busca un documento en cola por nombre de usuario y nombre de documento.
	 * 
	 * @param nombreUsuario Nombre del usuario
	 * @param nombreDocumento Nombre del documento
	 * @return Información del documento en cola, null si no se encuentra
	 */
	public InfoDocumentoEnCola buscarDocumentoEnCola(String nombreUsuario, String nombreDocumento) {
		int indice = funcionHash(nombreUsuario);
		ListaEnlazada cubeta = tabla[indice];
		
		ListaEnlazada.NodoLista actual = cubeta.cabeza;
		while (actual != null) {
			if (actual.dato.nombre.equals(nombreUsuario)) {
				return actual.dato.buscarDocumentoEnCola(nombreDocumento);
			}
			actual = actual.siguiente;
		}
		return null;
	}

	/**
	 * Elimina un documento del registro de cola.
	 * 
	 * @param nombreUsuario Nombre del usuario
	 * @param nombreDocumento Nombre del documento
	 * @return true si se eliminó correctamente
	 */
	public boolean eliminarDocumentoDeCola(String nombreUsuario, String nombreDocumento) {
		int indice = funcionHash(nombreUsuario);
		ListaEnlazada cubeta = tabla[indice];
		
		ListaEnlazada.NodoLista actual = cubeta.cabeza;
		while (actual != null) {
			if (actual.dato.nombre.equals(nombreUsuario)) {
				return actual.dato.eliminarDocumentoDeCola(nombreDocumento);
			}
			actual = actual.siguiente;
		}
		return false;
	}

	/**
	 * Actualiza el índice de un documento en el montículo.
	 * 
	 * @param nombreUsuario Nombre del usuario
	 * @param nombreDocumento Nombre del documento
	 * @param nuevoIndice Nuevo índice en el montículo
	 */
	public void actualizarIndiceEnMonticulo(String nombreUsuario, String nombreDocumento, int nuevoIndice) {
		int indice = funcionHash(nombreUsuario);
		ListaEnlazada cubeta = tabla[indice];
		
		ListaEnlazada.NodoLista actual = cubeta.cabeza;
		while (actual != null) {
			if (actual.dato.nombre.equals(nombreUsuario)) {
				actual.dato.actualizarIndiceEnMonticulo(nombreDocumento, nuevoIndice);
				break;
			}
			actual = actual.siguiente;
		}
	}

	/**
	 * Busca el nombre del usuario propietario de un documento en cola.
	 * Recorre la tabla hash para encontrar la relación usuario-documento.
	 * 
	 * @param nombreDocumento Nombre del documento a buscar
	 * @return Nombre del usuario propietario, null si no se encuentra
	 */
	public String buscarUsuarioPorDocumento(String nombreDocumento) {
		for (int i = 0; i < capacidad; i++) {
			ListaEnlazada cubeta = tabla[i];
			ListaEnlazada.NodoLista actual = cubeta.cabeza;
			while (actual != null) {
				InfoUsuario info = actual.dato;
				InfoDocumentoEnCola infoDoc = info.buscarDocumentoEnCola(nombreDocumento);
				if (infoDoc != null) {
					return info.nombre;
				}
				actual = actual.siguiente;
			}
		}
		return null;
	}

	private void rehash() {
		int nuevaCapacidad = capacidad * 2;
		ListaEnlazada[] nuevaTabla = new ListaEnlazada[nuevaCapacidad];
		for (int i = 0; i < nuevaCapacidad; i++) {
			nuevaTabla[i] = new ListaEnlazada();
		}
		
		// Reinsertar todos los elementos existentes
		for (int i = 0; i < capacidad; i++) {
			ListaEnlazada cubeta = tabla[i];
			ListaEnlazada.NodoLista actual = cubeta.cabeza;
			while (actual != null) {
				InfoUsuario info = actual.dato;
				int nuevoIndice = funcionHash(info.nombre);
				nuevaTabla[nuevoIndice].agregar(info);
				actual = actual.siguiente;
			}
		}
		
		this.tabla = nuevaTabla;
		this.capacidad = nuevaCapacidad;
	}


	public static class InfoUsuario {

		String nombre;
		String tipo;
		Usuario usuario;
		private ListaDocumentosEnCola documentosEnCola;

		InfoUsuario(String nombre, String tipo, Usuario usuario) {
			this.nombre = nombre;
			this.tipo = tipo;
			this.usuario = usuario;
			this.documentosEnCola = new ListaDocumentosEnCola();
		}

		public String getNombre() {
			return nombre;
		}

		public String getTipo() {
			return tipo;
		}

		public Usuario getUsuario() {
			return usuario;
		}

		/**
		 * Agrega un documento a la lista de documentos en cola.
		 * 
		 * @param documento Documento en cola
		 * @param indiceEnMonticulo Posición en el montículo
		 * @param etiquetaTiempo Etiqueta de tiempo
		 */
		public void agregarDocumentoEnCola(NodoDocumento documento, int indiceEnMonticulo, long etiquetaTiempo) {
			documentosEnCola.agregar(new InfoDocumentoEnCola(documento, indiceEnMonticulo, nombre, etiquetaTiempo));
		}

		/**
		 * Busca un documento en cola por nombre.
		 * 
		 * @param nombreDocumento Nombre del documento
		 * @return Información del documento en cola
		 */
		public InfoDocumentoEnCola buscarDocumentoEnCola(String nombreDocumento) {
			return documentosEnCola.buscar(nombreDocumento);
		}

		/**
		 * Elimina un documento de la lista de encolados.
		 * 
		 * @param nombreDocumento Nombre del documento
		 * @return true si se eliminó correctamente
		 */
		public boolean eliminarDocumentoDeCola(String nombreDocumento) {
			return documentosEnCola.eliminar(nombreDocumento);
		}

		/**
		 * Actualiza el índice de un documento en el montículo.
		 * 
		 * @param nombreDocumento Nombre del documento
		 * @param nuevoIndice Nuevo índice
		 */
		public void actualizarIndiceEnMonticulo(String nombreDocumento, int nuevoIndice) {
			InfoDocumentoEnCola info = documentosEnCola.buscar(nombreDocumento);
			if (info != null) {
				info.setIndiceEnMonticulo(nuevoIndice);
			}
		}
	}

	private static class ListaEnlazada {

		public NodoLista cabeza;

		private static class NodoLista {

			InfoUsuario dato;
			NodoLista siguiente;

			NodoLista(InfoUsuario dato) {
				this.dato = dato;
				this.siguiente = null;
			}
		}

		public void agregar(InfoUsuario dato) {
			NodoLista nuevo = new NodoLista(dato);
			if (cabeza == null) {
				cabeza = nuevo;
			} else {
				NodoLista actual = cabeza;
				while (actual.siguiente != null) {
					actual = actual.siguiente;
				}
				actual.siguiente = nuevo;
			}
		}

		public boolean eliminar(String nombre) {
			if (cabeza == null) {
				return false;
			}

			if (cabeza.dato.nombre.equals(nombre)) {
				cabeza = cabeza.siguiente;
				return true;
			}

			NodoLista actual = cabeza;
			while (actual.siguiente != null) {
				if (actual.siguiente.dato.nombre.equals(nombre)) {
					actual.siguiente = actual.siguiente.siguiente;
					return true;
				}
				actual = actual.siguiente;
			}
			return false;
		}

		public boolean estaVacia() {
			return cabeza == null;
		}

		public int getTamaño() {
			int contador = 0;
			NodoLista actual = cabeza;
			while (actual != null) {
				contador++;
				actual = actual.siguiente;
			}
			return contador;
		}
	}

	/**
	 * Lista enlazada para gestionar documentos en cola de un usuario.
	 */
	private static class ListaDocumentosEnCola {
		
		private NodoDocumentoCola cabeza;
		
		private static class NodoDocumentoCola {
			InfoDocumentoEnCola dato;
			NodoDocumentoCola siguiente;
			
			NodoDocumentoCola(InfoDocumentoEnCola dato) {
				this.dato = dato;
				this.siguiente = null;
			}
		}
		
		public void agregar(InfoDocumentoEnCola dato) {
			NodoDocumentoCola nuevo = new NodoDocumentoCola(dato);
			if (cabeza == null) {
				cabeza = nuevo;
			} else {
				NodoDocumentoCola actual = cabeza;
				while (actual.siguiente != null) {
					actual = actual.siguiente;
				}
				actual.siguiente = nuevo;
			}
		}
		
		public InfoDocumentoEnCola buscar(String nombreDocumento) {
			NodoDocumentoCola actual = cabeza;
			while (actual != null) {
				if (actual.dato.getDocumento().getNombre().equals(nombreDocumento)) {
					return actual.dato;
				}
				actual = actual.siguiente;
			}
			return null;
		}
		
		public boolean eliminar(String nombreDocumento) {
			if (cabeza == null) {
				return false;
			}
			
			if (cabeza.dato.getDocumento().getNombre().equals(nombreDocumento)) {
				cabeza = cabeza.siguiente;
				return true;
			}
			
			NodoDocumentoCola actual = cabeza;
			while (actual.siguiente != null) {
				if (actual.siguiente.dato.getDocumento().getNombre().equals(nombreDocumento)) {
					actual.siguiente = actual.siguiente.siguiente;
					return true;
				}
				actual = actual.siguiente;
			}
			return false;
		}
		
		public boolean estaVacia() {
			return cabeza == null;
		}
	}
}
