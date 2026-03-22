package proyecto.pkg2.estructura.de.datos.jc.sh;

/**
 * Clase que representa un documento en el sistema de cola de impresión.
 * Contiene la información necesaria para gestionar documentos en la cola de prioridad.
 * 
 * @author noahh
 */
public class NodoDocumento {
    
    /** Nombre del documento */
    private String nombre;
    
    /** Tamaño del documento en páginas */
    private int tamaño;
    
    /** Tipo de documento */
    private String tipo;
    
    /** Etiqueta de tiempo para prioridad en la cola */
    private long etiquetaTiempo;
    
    /** Indica si el documento es prioritario */
    private boolean esPrioritario;
    
    /** Nombre del usuario propietario */
    private String nombreUsuario;
    
    /** Índice del documento en el montículo para acceso O(1) */
    private int indiceEnMonticulo;
    
    /**
     * Constructor para crear un nuevo documento.
     * 
     * @param nombre Nombre del documento
     * @param tamaño Tamaño en páginas
     * @param tipo Tipo de documento
     * @param etiquetaTiempo Tiempo de creación para prioridad
     * @param esPrioritario Indica si es prioritario
     * @param nombreUsuario Usuario propietario
     */
    public NodoDocumento(String nombre, int tamaño, String tipo, long etiquetaTiempo, 
                        boolean esPrioritario, String nombreUsuario) {
        this.nombre = nombre;
        this.tamaño = tamaño;
        this.tipo = tipo;
        this.etiquetaTiempo = etiquetaTiempo;
        this.esPrioritario = esPrioritario;
        this.nombreUsuario = nombreUsuario;
        this.indiceEnMonticulo = -1; // Inicialmente no está en el montículo
    }
    
    /**
     * Obtiene el nombre del documento.
     * 
     * @return Nombre del documento
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Establece el nombre del documento.
     * 
     * @param nombre Nuevo nombre del documento
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Obtiene el tamaño del documento.
     * 
     * @return Tamaño en páginas
     */
    public int getTamaño() {
        return tamaño;
    }
    
    /**
     * Establece el tamaño del documento.
     * 
     * @param tamaño Nuevo tamaño en páginas
     */
    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }
    
    /**
     * Obtiene el tipo del documento.
     * 
     * @return Tipo de documento
     */
    public String getTipo() {
        return tipo;
    }
    
    /**
     * Establece el tipo del documento.
     * 
     * @param tipo Nuevo tipo de documento
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    /**
     * Obtiene la etiqueta de tiempo para prioridad.
     * 
     * @return Etiqueta de tiempo
     */
    public long getEtiquetaTiempo() {
        return etiquetaTiempo;
    }
    
    /**
     * Establece la etiqueta de tiempo para prioridad.
     * 
     * @param etiquetaTiempo Nueva etiqueta de tiempo
     */
    public void setEtiquetaTiempo(long etiquetaTiempo) {
        this.etiquetaTiempo = etiquetaTiempo;
    }
    
    /**
     * Verifica si el documento es prioritario.
     * 
     * @return true si es prioritario, false en caso contrario
     */
    public boolean isEsPrioritario() {
        return esPrioritario;
    }
    
    /**
     * Establece si el documento es prioritario.
     * 
     * @param esPrioritario Nuevo estado prioritario
     */
    public void setEsPrioritario(boolean esPrioritario) {
        this.esPrioritario = esPrioritario;
    }
    
    /**
     * Obtiene el nombre del usuario propietario.
     * 
     * @return Nombre del usuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    /**
     * Establece el nombre del usuario propietario.
     * 
     * @param nombreUsuario Nuevo nombre de usuario
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    /**
     * Obtiene el índice del documento en el montículo.
     * 
     * @return Índice en el montículo, -1 si no está en el montículo
     */
    public int getIndiceEnMonticulo() {
        return indiceEnMonticulo;
    }
    
    /**
     * Establece el índice del documento en el montículo.
     * 
     * @param indiceEnMonticulo Nuevo índice en el montículo
     */
    public void setIndiceEnMonticulo(int indiceEnMonticulo) {
        this.indiceEnMonticulo = indiceEnMonticulo;
    }
    
    /**
     * Representación en cadena del documento.
     * 
     * @return Cadena con información del documento
     */
    public String toString() {
        return "NodoDocumento{" + 
               "nombre='" + nombre + '\'' + 
               ", tamaño=" + tamaño + 
               ", tipo='" + tipo + '\'' + 
               ", etiquetaTiempo=" + etiquetaTiempo + 
               ", esPrioritario=" + esPrioritario + 
               ", nombreUsuario='" + nombreUsuario + '\'' + 
               '}';
    }
}
