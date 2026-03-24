package proyecto.pkg2.estructura.de.datos.jc.sh;

/**
 * Clase para guardar datos de un documento.
 * @author shdz
 */
public class NodoDocumento {
    
    private String nombre;
    private int tamaño;
    private String tipo;
    private long etiquetaTiempo;
    private boolean esPrioritario;
    private String nombreUsuario;
    private int indiceEnMonticulo;
    private int prioridadCalculada;
    private long claveOrdenacion;
    private boolean enCola;
    
    /**
     * Constructor para crear un nuevo documento.
     * 
     * @param nombre Nombre del documento
     * @param tamaño Tamaño en páginas
     * @param tipo Tipo de documento
     * @param etiquetaTiempo Tiempo de creación
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
        this.indiceEnMonticulo = -1;
        this.prioridadCalculada = 0; // Se calculará después
        this.claveOrdenacion = 0; // Se calculará después
        this.enCola = false;
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
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    public int getIndiceEnMonticulo() {
        return indiceEnMonticulo;
    }
    
    public void setIndiceEnMonticulo(int indiceEnMonticulo) {
        this.indiceEnMonticulo = indiceEnMonticulo;
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
     * Calcula la prioridad basada en prioridad del usuario y tamaño del documento.
     * Fórmula: (prioridadUsuario * 100) + tamaño
     * 
     * @param nivelPrioridadUsuario Nivel de prioridad del usuario (1=alta, 2=media, 3=baja)
     */
    public void calcularPrioridad(int nivelPrioridadUsuario) {
        this.prioridadCalculada = (nivelPrioridadUsuario * 100) + tamaño;
    }
    
    /**
     * Calcula la clave de ordenación para el montículo.
     * Para documentos no prioritarios: clave = (tiempoActual * 100) + tamaño
     * Para prioritarios: clave = (tiempoActual * 100) - offsetPrioridad
     * 
     * @param nivelPrioridadUsuario Nivel de prioridad del usuario
     */
    public void calcularClaveOrdenacion(int nivelPrioridadUsuario) {
        if (!esPrioritario) {
            // No prioritario: tiempo + penalización por tamaño
            this.claveOrdenacion = (etiquetaTiempo * 100) + tamaño;
        } else {
            // Prioritario: tiempo - offset según prioridad
            long offsetPrioridad = 0;
            switch (nivelPrioridadUsuario) {
                case 1: offsetPrioridad = 10000; break; // alta prioridad
                case 2: offsetPrioridad = 5000; break;  // media prioridad
                case 3: offsetPrioridad = 1000; break;  // baja prioridad
                default: offsetPrioridad = 1000;
            }
            this.claveOrdenacion = (etiquetaTiempo * 100) - offsetPrioridad;
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
