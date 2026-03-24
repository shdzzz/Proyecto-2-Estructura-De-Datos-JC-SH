package proyecto.pkg2.estructura.de.datos.jc.sh;

/**
 * Clase para guardar datos de un usuario.
 * @author shdz
 */
public class Usuario {
    
    private String nombre;
    private String tipo;
    private NodoDocumento primerDocumento;
    
    /**
     * Constructor para crear un nuevo usuario.
     * 
     * @param nombre Nombre del usuario
     * @param tipo Tipo de prioridad del usuario
     */
    public Usuario(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
	this.primerDocumento = null;
    }
    
    /**
     * Obtiene el nombre del usuario.
     * 
     * @return Nombre del usuario
     */
    public String getNombre() {
        return nombre;
    }
    
    public NodoDocumento getPrimerDocumento(){
	    return primerDocumento;
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
     * Obtiene el tipo de prioridad del usuario.
     * 
     * @return Tipo de prioridad
     */
    public String getTipo() {
        return tipo;
    }
    
    /**
     * Establece el tipo de prioridad del usuario.
     * 
     * @param tipo Nuevo tipo de prioridad
     */
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
    /**
     * Metodo responsable de agregar documento a un usuario: 
     * @param nuevo es el documento a introducir
     */
    public void agregarDocumento(NodoDocumento nuevo){
	    if(this.primerDocumento == null){ // si no hay primer documento el nuevo es el primero
		    this.primerDocumento = nuevo;
	    } else{ // cuando ya hay un primer documento
		    NodoDocumento aux = primerDocumento;
		    while (aux.getSiguiente() != null){
			    aux = aux.getSiguiente();
		    }
		    aux.setSiguiente(nuevo);
	    }
    }
    
    public NodoDocumento getPrimerDoc(){
	    return primerDocumento;
    }
    
}
