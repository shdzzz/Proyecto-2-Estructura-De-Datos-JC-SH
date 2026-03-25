package proyecto.pkg2.estructura.de.datos.jc.sh;

/**
 * Clase para registrar información de documentos en cola de impresión.
 * Almacena el documento, su posición en el montículo y datos del usuario.
 * @author shdz
 */
public class InfoDocumentoEnCola {
    
    private NodoDocumento documento;
    private int indiceEnMonticulo;
    private String nombreUsuario;
    private long etiquetaTiempo;
    
    /**
     * Constructor para registrar un documento en cola.
     * 
     * @param documento Documento en cola
     * @param indiceEnMontículo Posición en el montículo
     * @param nombreUsuario Usuario propietario
     * @param etiquetaTiempo Etiqueta de tiempo para prioridad
     */
    public InfoDocumentoEnCola(NodoDocumento documento, int indiceEnMonticulo, 
                              String nombreUsuario, long etiquetaTiempo) {
        this.documento = documento;
        this.indiceEnMonticulo = indiceEnMonticulo;
        this.nombreUsuario = nombreUsuario;
        this.etiquetaTiempo = etiquetaTiempo;
    }
    
    /**
     * Obtiene el documento.
     * 
     * @return Documento en cola
     */
    public NodoDocumento getDocumento() {
        return documento;
    }
    
    /**
     * Establece el documento.
     * 
     * @param documento Nuevo documento
     */
    public void setDocumento(NodoDocumento documento) {
        this.documento = documento;
    }
    
    /**
     * Obtiene el índice en el montículo.
     * 
     * @return Posición en el montículo
     */
    public int getIndiceEnMonticulo() {
        return indiceEnMonticulo;
    }
    
    /**
     * Establece el índice en el montículo.
     * 
     * @param indiceEnMonticulo Nueva posición
     */
    public void setIndiceEnMonticulo(int indiceEnMonticulo) {
        this.indiceEnMonticulo = indiceEnMonticulo;
    }
    
    /**
     * Obtiene el nombre del usuario.
     * 
     * @return Nombre del usuario propietario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    /**
     * Establece el nombre del usuario.
     * 
     * @param nombreUsuario Nuevo nombre de usuario
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    /**
     * Obtiene la etiqueta de tiempo.
     * 
     * @return Etiqueta de tiempo para prioridad
     */
    public long getEtiquetaTiempo() {
        return etiquetaTiempo;
    }
    
    /**
     * Establece la etiqueta de tiempo.
     * 
     * @param etiquetaTiempo Nueva etiqueta de tiempo
     */
    public void setEtiquetaTiempo(long etiquetaTiempo) {
        this.etiquetaTiempo = etiquetaTiempo;
    }
}
