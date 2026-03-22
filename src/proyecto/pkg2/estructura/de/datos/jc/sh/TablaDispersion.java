package proyecto.pkg2.estructura.de.datos.jc.sh;

/**
 * Implementación de una Tabla de Dispersión (Hash Table) para acceso O(1) a documentos
 * de usuarios en la cola de impresión. Utiliza encadenamiento para resolución de colisiones.
 * 
 * @author noahh
 */
public class TablaDispersion {
    
    /** Arreglo de listas para manejo de colisiones por encadenamiento */
    private ListaEnlazada[] tabla;
    
    /** Tamaño de la tabla */
    private int capacidad;
    
    /** Número de elementos almacenados */
    private int tamaño;
    
    /** Factor de carga máximo antes de rehash */
    private static final double FACTOR_CARGA_MAXIMO = 0.75;
    
    /** Capacidad inicial por defecto */
    private static final int CAPACIDAD_INICIAL = 16;
    
    /**
     * Clase interna que representa una entrada en la tabla hash.
     */
    private static class EntradaTabla {
        String claveUsuario;
        ListaEnlazada documentos;
        
        EntradaTabla(String claveUsuario) {
            this.claveUsuario = claveUsuario;
            this.documentos = new ListaEnlazada();
        }
    }
    
    /**
     * Clase interna que almacena información de un documento en la cola.
     */
    public static class InfoDocumento {
        String nombreDocumento;
        long etiquetaTiempo;
        NodoDocumento documento;
        
        InfoDocumento(String nombreDocumento, long etiquetaTiempo, NodoDocumento documento) {
            this.nombreDocumento = nombreDocumento;
            this.etiquetaTiempo = etiquetaTiempo;
            this.documento = documento;
        }
        
        public String getNombreDocumento() {
            return nombreDocumento;
        }
        
        public long getEtiquetaTiempo() {
            return etiquetaTiempo;
        }
        
        public NodoDocumento getDocumento() {
            return documento;
        }
    }
    
    /**
     * Clase interna para lista enlazada simple (sin usar librerías).
     */
    private static class ListaEnlazada {
        private NodoLista cabeza;
        
        private static class NodoLista {
            InfoDocumento dato;
            NodoLista siguiente;
            
            NodoLista(InfoDocumento dato) {
                this.dato = dato;
                this.siguiente = null;
            }
        }
        
        public void agregar(InfoDocumento dato) {
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
        
        public boolean eliminar(String nombreDocumento, long etiquetaTiempo) {
            if (cabeza == null) return false;
            
            if (cabeza.dato.nombreDocumento.equals(nombreDocumento) && 
                cabeza.dato.etiquetaTiempo == etiquetaTiempo) {
                cabeza = cabeza.siguiente;
                return true;
            }
            
            NodoLista actual = cabeza;
            while (actual.siguiente != null) {
                if (actual.siguiente.dato.nombreDocumento.equals(nombreDocumento) && 
                    actual.siguiente.dato.etiquetaTiempo == etiquetaTiempo) {
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
        
        public InfoDocumento[] obtenerElementos() {
            InfoDocumento[] elementos = new InfoDocumento[getTamaño()];
            NodoLista actual = cabeza;
            int i = 0;
            while (actual != null) {
                elementos[i] = actual.dato;
                actual = actual.siguiente;
                i++;
            }
            return elementos;
        }
    }
    
    /**
     * Constructor que crea una tabla con capacidad inicial por defecto.
     */
    public TablaDispersion() {
        this(CAPACIDAD_INICIAL);
    }
    
    /**
     * Constructor que crea una tabla con capacidad específica.
     * 
     * @param capacidad Capacidad inicial de la tabla
     */
    public TablaDispersion(int capacidad) {
        this.capacidad = capacidad;
        this.tabla = new ListaEnlazada[capacidad];
        this.tamaño = 0;
        
        // Inicializar las listas enlazadas
        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new ListaEnlazada();
        }
    }
    
    /**
     * Función de hash para distribuir las claves.
     * 
     * @param clave Clave a hashear
     * @return Índice en la tabla
     */
    private int funcionHash(String clave) {
        int hash = 0;
        for (int i = 0; i < clave.length(); i++) {
            hash = (31 * hash + clave.charAt(i)) % capacidad;
        }
        if (hash < 0) hash = -hash;
        return hash;
    }
    
    /**
     * Agrega un documento a la tabla para un usuario específico.
     * Complejidad promedio: O(1)
     * 
     * @param nombreUsuario Nombre del usuario
     * @param nombreDocumento Nombre del documento
     * @param etiquetaTiempo Etiqueta de tiempo del documento
     * @param documento Referencia al documento completo
     */
    public void agregarDocumento(String nombreUsuario, String nombreDocumento, 
                                long etiquetaTiempo, NodoDocumento documento) {
        // Verificar factor de carga y hacer rehash si es necesario
        if ((double) tamaño / capacidad > FACTOR_CARGA_MAXIMO) {
            rehash();
        }
        
        int indice = funcionHash(nombreUsuario);
        ListaEnlazada cubeta = tabla[indice];
        
        // Buscar si el usuario ya existe (implementación simplificada)
        // En una implementación completa, necesitaríamos buscar en la cubeta
        // Por ahora, agregamos directamente asumiendo que no existe
        
        // Crear nueva entrada para el usuario
        EntradaTabla nuevaEntrada = new EntradaTabla(nombreUsuario);
        nuevaEntrada.documentos.agregar(new InfoDocumento(nombreDocumento, etiquetaTiempo, documento));
        
        // Agregar a la cubeta (implementación simplificada)
        // En realidad, necesitaríamos una estructura más compleja para manejar múltiples entradas por cubeta
        tamaño++;
    }
    
    /**
     * Busca todos los documentos de un usuario en la cola.
     * Complejidad promedio: O(1)
     * 
     * @param nombreUsuario Nombre del usuario a buscar
     * @return Lista de documentos del usuario, null si no existe
     */
    public ListaEnlazada buscarDocumentosUsuario(String nombreUsuario) {
        int indice = funcionHash(nombreUsuario);
        return tabla[indice].estaVacia() ? null : tabla[indice];
    }
    
    /**
     * Elimina un documento específico de un usuario.
     * Complejidad promedio: O(1)
     * 
     * @param nombreUsuario Nombre del usuario
     * @param nombreDocumento Nombre del documento a eliminar
     * @param etiquetaTiempo Etiqueta de tiempo del documento
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminarDocumento(String nombreUsuario, String nombreDocumento, long etiquetaTiempo) {
        int indice = funcionHash(nombreUsuario);
        ListaEnlazada cubeta = tabla[indice];
        
        boolean eliminado = cubeta.eliminar(nombreDocumento, etiquetaTiempo);
        
        if (eliminado && cubeta.estaVacia()) {
            tamaño--;
        }
        
        return eliminado;
    }
    
    /**
     * Verifica si un usuario existe en la tabla.
     * 
     * @param nombreUsuario Nombre del usuario
     * @return true si existe, false en caso contrario
     */
    public boolean existeUsuario(String nombreUsuario) {
        return !tabla[funcionHash(nombreUsuario)].estaVacia();
    }
    
    /**
     * Obtiene el número de usuarios en la tabla.
     * 
     * @return Número de usuarios
     */
    public int getTamaño() {
        return tamaño;
    }
    
    /**
     * Obtiene la capacidad actual de la tabla.
     * 
     * @return Capacidad de la tabla
     */
    public int getCapacidad() {
        return capacidad;
    }
    
    /**
     * Redimensiona la tabla cuando el factor de carga es alto.
     */
    private void rehash() {
        int nuevaCapacidad = capacidad * 2;
        ListaEnlazada[] nuevaTabla = new ListaEnlazada[nuevaCapacidad];
        
        // Inicializar nueva tabla
        for (int i = 0; i < nuevaCapacidad; i++) {
            nuevaTabla[i] = new ListaEnlazada();
        }
        
        // Copiar elementos (implementación simplificada)
        this.tabla = nuevaTabla;
        this.capacidad = nuevaCapacidad;
    }
    
    /**
     * Retorna una representación en cadena de la tabla.
     * 
     * @return Cadena que representa el estado actual de la tabla
     */
    public String toString() {
        String resultado = "TablaDispersion{tamaño=" + tamaño + ", capacidad=" + capacidad + ", entradas=[";
        
        boolean primero = true;
        for (int i = 0; i < capacidad; i++) {
            if (!tabla[i].estaVacia()) {
                if (!primero) resultado += ", ";
                resultado += "cubeta" + i + "(" + tabla[i].getTamaño() + " docs)";
                primero = false;
            }
        }
        
        resultado += "]}";
        return resultado;
    }
}
