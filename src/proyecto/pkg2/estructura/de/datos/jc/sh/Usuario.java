package proyecto.pkg2.estructura.de.datos.jc.sh;

/**
 * Clase que representa un usuario en el sistema de cola de impresión.
 * Cada usuario tiene un tipo que determina su nivel de prioridad para los documentos.
 * 
 * @author noahh
 */
public class Usuario {
    
    /** Nombre identificador del usuario */
    private String nombre;
    
    /** Tipo de usuario que determina prioridad */
    private String tipo;
    
    /** Tipos de usuario válidos */
    public static final String PRIORIDAD_ALTA = "prioridad_alta";
    public static final String PRIORIDAD_MEDIA = "prioridad_media";
    public static final String PRIORIDAD_BAJA = "prioridad_baja";
    
    /**
     * Constructor para crear un nuevo usuario.
     * 
     * @param nombre Nombre identificador del usuario
     * @param tipo Tipo de usuario (prioridad_alta, prioridad_media, prioridad_baja)
     */
    public Usuario(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = validarTipo(tipo);
    }
    
    /**
     * Valida que el tipo de usuario sea válido.
     * 
     * @param tipo Tipo a validar
     * @return Tipo validado, si es inválido retorna prioridad_baja por defecto
     */
    private String validarTipo(String tipo) {
        if (tipo == null) {
            return PRIORIDAD_BAJA;
        }
        
        tipo = tipo.toLowerCase().trim();
        
        if (tipo.equals(PRIORIDAD_ALTA) || tipo.equals(PRIORIDAD_MEDIA) || tipo.equals(PRIORIDAD_BAJA)) {
            return tipo;
        }
        
        return PRIORIDAD_BAJA; // Valor por defecto
    }
    
    /**
     * Obtiene el nombre del usuario.
     * 
     * @return Nombre del usuario
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Establece el nombre del usuario.
     * 
     * @param nombre Nuevo nombre del usuario
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Obtiene el tipo de usuario.
     * 
     * @return Tipo de usuario
     */
    public String getTipo() {
        return tipo;
    }
    
    /**
     * Establece el tipo de usuario.
     * 
     * @param tipo Nuevo tipo de usuario
     */
    public void setTipo(String tipo) {
        this.tipo = validarTipo(tipo);
    }
    
    /**
     * Obtiene el nivel de prioridad numérico del usuario.
     * 
     * @return Nivel de prioridad (1 = alta, 2 = media, 3 = baja)
     */
    public int getNivelPrioridad() {
        switch (tipo) {
            case PRIORIDAD_ALTA:
                return 1;
            case PRIORIDAD_MEDIA:
                return 2;
            case PRIORIDAD_BAJA:
            default:
                return 3;
        }
    }
    
    /**
     * Calcula el factor de prioridad para ajustar etiquetas de tiempo.
     * Los usuarios de alta prioridad obtienen factores más bajos para ser atendidos primero.
     * 
     * @return Factor de prioridad (0.5 = alta, 1.0 = media, 2.0 = baja)
     */
    public double getFactorPrioridad() {
        switch (tipo) {
            case PRIORIDAD_ALTA:
                return 0.5;  // Reducción del 50% en tiempo de espera
            case PRIORIDAD_MEDIA:
                return 1.0;  // Tiempo normal
            case PRIORIDAD_BAJA:
            default:
                return 2.0;  // Duplicación del tiempo de espera
        }
    }
    
    /**
     * Aplica el factor de prioridad a una etiqueta de tiempo.
     * 
     * @param etiquetaTiempoOriginal Etiqueta de tiempo original
     * @return Etiqueta de tiempo ajustada según prioridad del usuario
     */
    public long aplicarPrioridad(long etiquetaTiempoOriginal) {
        double factor = getFactorPrioridad();
        return (long)(etiquetaTiempoOriginal * factor);
    }
    
    /**
     * Verifica si el usuario tiene prioridad alta.
     * 
     * @return true si tiene prioridad alta, false en caso contrario
     */
    public boolean esPrioridadAlta() {
        return PRIORIDAD_ALTA.equals(tipo);
    }
    
    /**
     * Verifica si el usuario tiene prioridad media.
     * 
     * @return true si tiene prioridad media, false en caso contrario
     */
    public boolean esPrioridadMedia() {
        return PRIORIDAD_MEDIA.equals(tipo);
    }
    
    /**
     * Verifica si el usuario tiene prioridad baja.
     * 
     * @return true si tiene prioridad baja, false en caso contrario
     */
    public boolean esPrioridadBaja() {
        return PRIORIDAD_BAJA.equals(tipo);
    }
    
    /**
     * Crea un usuario a partir de una línea del archivo CSV.
     * Formato esperado: "usuario,tipo"
     * 
     * @param lineaCSV Línea del archivo CSV
     * @return Usuario creado, null si la línea es inválida
     */
    public static Usuario desdeLineaCSV(String lineaCSV) {
        if (lineaCSV == null || lineaCSV.trim().isEmpty()) {
            return null;
        }
        
        String[] partes = lineaCSV.split(",");
        if (partes.length != 2) {
            return null;
        }
        
        String nombre = partes[0].trim();
        String tipo = partes[1].trim();
        
        if (nombre.isEmpty()) {
            return null;
        }
        
        return new Usuario(nombre, tipo);
    }
    
    /**
     * Convierte el usuario a formato CSV.
     * 
     * @return Línea CSV con formato "usuario,tipo"
     */
    public String aLineaCSV() {
        return nombre + "," + tipo;
    }
    
    /**
     * Representación en cadena del usuario.
     * 
     * @return Cadena con información del usuario
     */
    public String toString() {
        return "Usuario{" + 
               "nombre='" + nombre + '\'' + 
               ", tipo='" + tipo + '\'' + 
               ", nivelPrioridad=" + getNivelPrioridad() + 
               ", factorPrioridad=" + getFactorPrioridad() + 
               '}';
    }
    
    /**
     * Compara si dos usuarios son iguales por nombre.
     * 
     * @param otro Usuario a comparar
     * @return true si tienen el mismo nombre, false en caso contrario
     */
    public boolean equalsPorNombre(Usuario otro) {
        if (otro == null) {
            return false;
        }
        return this.nombre.equals(otro.nombre);
    }
    
    /**
     * Genera un código hash basado en el nombre del usuario.
     * 
     * @return Código hash
     */
    public int hashCodePorNombre() {
        return nombre.hashCode();
    }
}
