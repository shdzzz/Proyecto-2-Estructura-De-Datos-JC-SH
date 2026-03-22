package proyecto.pkg2.estructura.de.datos.jc.sh;

/**
 * Implementación de un Montículo Binario (Min-Heap) para la cola de impresión.
 * Utiliza representación en arreglo sin apuntadores, cumpliendo con las propiedades
 * de forma y orden del montículo binario.
 * 
 * @author noahh
 */
public class MonticuloBinario {
    
    /** Arreglo que almacena los documentos del montículo */
    private NodoDocumento[] monticulo;
    
    /** Capacidad máxima del montículo */
    private int capacidad;
    
    /** Tamaño actual del montículo */
    private int tamaño;
    
    /** Capacidad inicial por defecto */
    private static final int CAPACIDAD_INICIAL = 100;
    
    /**
     * Constructor que crea un montículo con capacidad inicial por defecto.
     */
    public MonticuloBinario() {
        this(CAPACIDAD_INICIAL);
    }
    
    /**
     * Constructor que crea un montículo con capacidad específica.
     * 
     * @param capacidad Capacidad máxima del montículo
     */
    public MonticuloBinario(int capacidad) {
        this.capacidad = capacidad;
        this.monticulo = new NodoDocumento[capacidad];
        this.tamaño = 0;
    }
    
    /**
     * Inserta un nuevo documento en el montículo manteniendo las propiedades.
     * Complejidad: O(log n)
     * 
     * @param documento Documento a insertar
     * @return true si se insertó correctamente, false si el montículo está lleno
     */
    public boolean insertar(NodoDocumento documento) {
        if (tamaño >= capacidad) {
            return false; // Montículo lleno
        }
        
        // Insertar al final
        monticulo[tamaño] = documento;
        documento.setIndiceEnMonticulo(tamaño);
        
        // Flotar el elemento para mantener propiedad de orden
        flotar(tamaño);
        tamaño++;
        
        return true;
    }
    
    /**
     * Elimina y retorna el documento con menor etiqueta de tiempo (raíz).
     * Complejidad: O(log n)
     * 
     * @return Documento con menor prioridad, null si el montículo está vacío
     */
    public NodoDocumento eliminar_min() {
        if (tamaño == 0) {
            return null; // Montículo vacío
        }
        
        NodoDocumento minimo = monticulo[0];
        minimo.setIndiceEnMonticulo(-1); // Ya no está en el montículo
        tamaño--;
        
        // Mover el último elemento a la raíz
        if (tamaño > 0) {
            monticulo[0] = monticulo[tamaño];
            monticulo[0].setIndiceEnMonticulo(0);
            hundir(0);
        }
        
        return minimo;
    }
    
    /**
     * Verifica si el montículo está vacío.
     * 
     * @return true si está vacío, false en caso contrario
     */
    public boolean estaVacio() {
        return tamaño == 0;
    }
    
    /**
     * Obtiene el tamaño actual del montículo.
     * 
     * @return Número de elementos en el montículo
     */
    public int getTamaño() {
        return tamaño;
    }
    
    /**
     * Obtiene el documento en la raíz sin eliminarlo.
     * 
     * @return Documento con menor prioridad, null si está vacío
     */
    public NodoDocumento verMinimo() {
        return tamaño == 0 ? null : monticulo[0];
    }
    
    /**
     * Flota un elemento desde una posición hacia arriba para mantener propiedad de orden.
     * 
     * @param indice Índice del elemento a flotar
     */
    private void flotar(int indice) {
        while (indice > 0) {
            int padre = (indice - 1) / 2;
            
            if (monticulo[indice].getEtiquetaTiempo() >= monticulo[padre].getEtiquetaTiempo()) {
                break; // Propiedad de orden mantenida
            }
            
            // Intercambiar con el padre
            intercambiar(indice, padre);
            indice = padre;
        }
    }
    
    /**
     * Hunde un elemento desde una posición hacia abajo para mantener propiedad de orden.
     * 
     * @param indice Índice del elemento a hundir
     */
    private void hundir(int indice) {
        while (true) {
            int hijoIzquierdo = 2 * indice + 1;
            int hijoDerecho = 2 * indice + 2;
            int menor = indice;
            
            // Encontrar el menor entre padre e hijos
            if (hijoIzquierdo < tamaño && 
                monticulo[hijoIzquierdo].getEtiquetaTiempo() < monticulo[menor].getEtiquetaTiempo()) {
                menor = hijoIzquierdo;
            }
            
            if (hijoDerecho < tamaño && 
                monticulo[hijoDerecho].getEtiquetaTiempo() < monticulo[menor].getEtiquetaTiempo()) {
                menor = hijoDerecho;
            }
            
            if (menor == indice) {
                break; // Propiedad de orden mantenida
            }
            
            // Intercambiar con el hijo menor
            intercambiar(indice, menor);
            indice = menor;
        }
    }
    
    /**
     * Intercambia dos elementos en el montículo.
     * 
     * @param i Primer índice
     * @param j Segundo índice
     */
    private void intercambiar(int i, int j) {
        NodoDocumento temp = monticulo[i];
        monticulo[i] = monticulo[j];
        monticulo[j] = temp;
        
        // Actualizar índices en los nodos intercambiados
        monticulo[i].setIndiceEnMonticulo(i);
        monticulo[j].setIndiceEnMonticulo(j);
    }
    
    /**
     * Retorna una representación en cadena del montículo.
     * 
     * @return Cadena que representa el estado actual del montículo
     */
    public String toString() {
        if (tamaño == 0) {
            return "MontículoBinario{vacío}";
        }
        
        String resultado = "MontículoBinario{tamaño=" + tamaño + ", elementos=[";
        
        for (int i = 0; i < tamaño; i++) {
            if (i > 0) resultado += ", ";
            resultado += monticulo[i].getNombre() + "(t=" + monticulo[i].getEtiquetaTiempo() + ")";
        }
        
        resultado += "]}";
        return resultado;
    }
    
    /**
     * Obtiene una copia de los elementos del montículo para visualización.
     * 
     * @return Arreglo con los elementos actuales
     */
    public NodoDocumento[] obtenerElementos() {
        NodoDocumento[] copia = new NodoDocumento[tamaño];
        for (int i = 0; i < tamaño; i++) {
            copia[i] = monticulo[i];
        }
        return copia;
    }
}
