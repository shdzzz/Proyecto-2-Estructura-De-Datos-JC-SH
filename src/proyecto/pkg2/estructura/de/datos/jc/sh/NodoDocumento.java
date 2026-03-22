package proyecto.pkg2.estructura.de.datos.jc.sh;

/**
 * Clase para guardar datos de un documento.
 * @author noahh
 */
public class NodoDocumento {
    
    private String nombre;
    private int tamaño;
    private String tipo;
    private long etiquetaTiempo;
    private boolean esPrioritario;
    private String nombreUsuario;
    private int indiceEnMonticulo;
    
    public NodoDocumento(String nombre, int tamaño, String tipo, long etiquetaTiempo, 
                        boolean esPrioritario, String nombreUsuario) {
        this.nombre = nombre;
        this.tamaño = tamaño;
        this.tipo = tipo;
        this.etiquetaTiempo = etiquetaTiempo;
        this.esPrioritario = esPrioritario;
        this.nombreUsuario = nombreUsuario;
        this.indiceEnMonticulo = -1;
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
}
