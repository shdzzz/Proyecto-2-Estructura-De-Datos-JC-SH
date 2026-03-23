package proyecto.pkg2.estructura.de.datos.jc.sh;

/**
 * Cola de prioridad simple.
 * @author shdz
 */
public class MonticuloBinario {
    
    private NodoDocumento[] monticulo;
    private int tamaño;
    private int capacidad;
    
    public MonticuloBinario() {
        this.capacidad = 100;
        this.monticulo = new NodoDocumento[capacidad];
        this.tamaño = 0;
    }
    
    /**
     * Inserta un documento en la cola de prioridad.
     * Usa prioridadCalculada como criterio principal, etiquetaTiempo como desempate.
     * 
     * @param doc Documento a insertar
     * @return true si se insertó correctamente, false si está lleno
     */
    public boolean insertar(NodoDocumento doc) {
        if (tamaño >= capacidad) {
            return false;
        }
        
        monticulo[tamaño] = doc;
        doc.setIndiceEnMonticulo(tamaño);
        
        int actual = tamaño;
        while (actual > 0) {
            int padre = (actual - 1) / 2;
            
            // Comparar por prioridadCalculada, usar etiquetaTiempo como desempate
            if (monticulo[actual].getPrioridadCalculada() > monticulo[padre].getPrioridadCalculada()) {
                break;
            } else if (monticulo[actual].getPrioridadCalculada() == monticulo[padre].getPrioridadCalculada()) {
                if (monticulo[actual].getEtiquetaTiempo() >= monticulo[padre].getEtiquetaTiempo()) {
                    break;
                }
            }
            
            NodoDocumento temp = monticulo[actual];
            monticulo[actual] = monticulo[padre];
            monticulo[padre] = temp;
            
            monticulo[actual].setIndiceEnMonticulo(actual);
            monticulo[padre].setIndiceEnMonticulo(padre);
            
            actual = padre;
        }
        
        tamaño++;
        return true;
    }
    
    /**
     * Elimina el documento con mayor prioridad (menor valor).
     * Usa prioridadCalculada como criterio principal, etiquetaTiempo como desempate.
     * 
     * @return Documento eliminado, null si está vacío
     */
    public NodoDocumento eliminar_min() {
        if (tamaño == 0) {
            return null;
        }
        
        NodoDocumento minimo = monticulo[0];
        minimo.setIndiceEnMonticulo(-1);
        
        tamaño--;
        if (tamaño > 0) {
            monticulo[0] = monticulo[tamaño];
            monticulo[0].setIndiceEnMonticulo(0);
            
            int actual = 0;
            while (true) {
                int izquierdo = 2 * actual + 1;
                int derecho = 2 * actual + 2;
                int menor = actual;
                
                // Comparar con hijo izquierdo
                if (izquierdo < tamaño) {
                    if (monticulo[izquierdo].getPrioridadCalculada() < monticulo[menor].getPrioridadCalculada()) {
                        menor = izquierdo;
                    } else if (monticulo[izquierdo].getPrioridadCalculada() == monticulo[menor].getPrioridadCalculada()) {
                        if (monticulo[izquierdo].getEtiquetaTiempo() < monticulo[menor].getEtiquetaTiempo()) {
                            menor = izquierdo;
                        }
                    }
                }
                
                // Comparar con hijo derecho
                if (derecho < tamaño) {
                    if (monticulo[derecho].getPrioridadCalculada() < monticulo[menor].getPrioridadCalculada()) {
                        menor = derecho;
                    } else if (monticulo[derecho].getPrioridadCalculada() == monticulo[menor].getPrioridadCalculada()) {
                        if (monticulo[derecho].getEtiquetaTiempo() < monticulo[menor].getEtiquetaTiempo()) {
                            menor = derecho;
                        }
                    }
                }
                
                if (menor == actual) {
                    break;
                }
                
                NodoDocumento temp = monticulo[actual];
                monticulo[actual] = monticulo[menor];
                monticulo[menor] = temp;
                
                monticulo[actual].setIndiceEnMonticulo(actual);
                monticulo[menor].setIndiceEnMonticulo(menor);
                
                actual = menor;
            }
        }
        
        return minimo;
    }
    
    public boolean estaVacio() {
        return tamaño == 0;
    }
    
    public int getTamaño() {
        return tamaño;
    }
    
    public NodoDocumento verMinimo() {
        if (tamaño == 0) {
            return null;
        }
        return monticulo[0];
    }
}
