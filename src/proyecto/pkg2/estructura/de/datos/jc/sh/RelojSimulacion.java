package proyecto.pkg2.estructura.de.datos.jc.sh;

/**
 * Clase singleton para manejar el tiempo de simulación.
 * Mantiene un contador que se incrementa con cada operación.
 * @author shdz
 */
public class RelojSimulacion {
    
    private static RelojSimulacion instancia;
    private long tiempoActual;
    
    private RelojSimulacion() {
        this.tiempoActual = 0;
    }
    
    /**
     * Obtiene la instancia única del reloj (singleton).
     * 
     * @return Instancia del reloj
     */
    public static RelojSimulacion getInstancia() {
        if (instancia == null) {
            instancia = new RelojSimulacion();
        }
        return instancia;
    }
    
    /**
     * Obtiene el tiempo actual de la simulación.
     * 
     * @return Tiempo actual
     */
    public long getTiempoActual() {
        return tiempoActual;
    }
    
    /**
     * Establece el tiempo actual.
     * 
     * @param tiempoActual Nuevo tiempo
     */
    public void setTiempoActual(long tiempoActual) {
        this.tiempoActual = tiempoActual;
    }
    
    /**
     * Incrementa el tiempo en una unidad.
     */
    public void incrementarTiempo() {
        this.tiempoActual++;
    }
    
    /**
     * Incrementa el tiempo en la cantidad especificada.
     * 
     * @param cantidad Cantidad a incrementar
     */
    public void incrementarTiempo(long cantidad) {
        this.tiempoActual += cantidad;
    }
    
    /**
     * Reinicia el reloj a cero.
     */
    public void reiniciar() {
        this.tiempoActual = 0;
    }
    
    /**
     * Obtiene el tiempo actual y luego lo incrementa.
     * Útil para asignar etiquetas de tiempo a documentos.
     * 
     * @return Tiempo actual antes del incremento
     */
    public long obtenerTiempoYIncrementar() {
        long tiempo = this.tiempoActual;
        this.tiempoActual++;
        return tiempo;
    }
    
    /**
     * Devuelve una representación en cadena del tiempo actual.
     * 
     * @return Tiempo formateado
     */
    public String toString() {
        return "RelojSimulacion{tiempoActual=" + tiempoActual + "}";
    }
}
