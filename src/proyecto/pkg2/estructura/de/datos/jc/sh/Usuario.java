package proyecto.pkg2.estructura.de.datos.jc.sh;

/**
 * Clase para guardar datos de un usuario.
 * @author shdz
 */
public class Usuario {
    
    private String nombre;
    private String tipo;
    
    public Usuario(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    /**
     * Obtiene el nivel de prioridad numérico del usuario.
     * 
     * @return Nivel de prioridad (1=alta, 2=media, 3=baja)
     */
    public int getPrioridad() {
        if (tipo.equals("prioridad_alta")) {
            return 1;
        } else if (tipo.equals("prioridad_media")) {
            return 2;
        } else {
            return 3;
        }
    }
    
    public String toString() {
        return nombre + "," + tipo;
    }
}
