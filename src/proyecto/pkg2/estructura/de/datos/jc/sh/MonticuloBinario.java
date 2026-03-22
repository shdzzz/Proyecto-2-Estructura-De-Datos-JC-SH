package proyecto.pkg2.estructura.de.datos.jc.sh;

/**
 * Cola de prioridad simple.
 * @author noahh
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
    
    public boolean insertar(NodoDocumento doc) {
        if (tamaño >= capacidad) {
            return false;
        }
        
        monticulo[tamaño] = doc;
        doc.setIndiceEnMonticulo(tamaño);
        
        int actual = tamaño;
        while (actual > 0) {
            int padre = (actual - 1) / 2;
            if (monticulo[actual].getEtiquetaTiempo() >= monticulo[padre].getEtiquetaTiempo()) {
                break;
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
                
                if (izquierdo < tamaño && monticulo[izquierdo].getEtiquetaTiempo() < monticulo[menor].getEtiquetaTiempo()) {
                    menor = izquierdo;
                }
                
                if (derecho < tamaño && monticulo[derecho].getEtiquetaTiempo() < monticulo[menor].getEtiquetaTiempo()) {
                    menor = derecho;
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
