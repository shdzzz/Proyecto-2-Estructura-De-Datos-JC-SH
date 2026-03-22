package proyecto.pkg2.estructura.de.datos.jc.sh;

/**
 * Tabla hash simple para guardar documentos.
 * @author noahh
 */
public class TablaDispersion {
    
    private ListaEnlazada[] tabla;
    private int capacidad;
    private int tamaño;
    
    public TablaDispersion() {
        this.capacidad = 16;
        this.tabla = new ListaEnlazada[capacidad];
        this.tamaño = 0;
        
        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new ListaEnlazada();
        }
    }
    
    private int funcionHash(String clave) {
        int hash = 0;
        for (int i = 0; i < clave.length(); i++) {
            hash = (31 * hash + clave.charAt(i)) % capacidad;
        }
        if (hash < 0) hash = -hash;
        return hash;
    }
    
    public void agregarDocumento(String nombreUsuario, String nombreDocumento, 
                                long etiquetaTiempo, NodoDocumento documento) {
        if ((double) tamaño / capacidad > 0.75) {
            rehash();
        }
        
        int indice = funcionHash(nombreUsuario);
        tabla[indice].agregar(new InfoDocumento(nombreDocumento, etiquetaTiempo, documento));
        tamaño++;
    }
    
    public ListaEnlazada buscarDocumentosUsuario(String nombreUsuario) {
        int indice = funcionHash(nombreUsuario);
        if (tabla[indice].estaVacia()) {
            return null;
        }
        return tabla[indice];
    }
    
    public boolean eliminarDocumento(String nombreUsuario, String nombreDocumento, long etiquetaTiempo) {
        int indice = funcionHash(nombreUsuario);
        ListaEnlazada cubeta = tabla[indice];
        
        boolean eliminado = cubeta.eliminar(nombreDocumento, etiquetaTiempo);
        
        if (eliminado && cubeta.estaVacia()) {
            tamaño--;
        }
        
        return eliminado;
    }
    
    public boolean existeUsuario(String nombreUsuario) {
        return !tabla[funcionHash(nombreUsuario)].estaVacia();
    }
    
    public int getTamaño() {
        return tamaño;
    }
    
    public int getCapacidad() {
        return capacidad;
    }
    
    private void rehash() {
        int nuevaCapacidad = capacidad * 2;
        ListaEnlazada[] nuevaTabla = new ListaEnlazada[nuevaCapacidad];
        
        for (int i = 0; i < nuevaCapacidad; i++) {
            nuevaTabla[i] = new ListaEnlazada();
        }
        
        this.tabla = nuevaTabla;
        this.capacidad = nuevaCapacidad;
    }
    
    public static class InfoDocumento {
        String nombreDocumento;
        long etiquetaTiempo;
        NodoDocumento documento;
        
        InfoDocumento(String nombreDocumento, long etiquetaTiempo, NodoDocumento documento) {
            this.nombreDocumento = nombreDocumento;
            this.etiquetaTiempo = etiquetaTiempo;
            this.documento = documento;
        }
        
        public String getNombreDocumento() {
            return nombreDocumento;
        }
        
        public long getEtiquetaTiempo() {
            return etiquetaTiempo;
        }
        
        public NodoDocumento getDocumento() {
            return documento;
        }
    }
    
    private static class ListaEnlazada {
        private NodoLista cabeza;
        
        private static class NodoLista {
            InfoDocumento dato;
            NodoLista siguiente;
            
            NodoLista(InfoDocumento dato) {
                this.dato = dato;
                this.siguiente = null;
            }
        }
        
        public void agregar(InfoDocumento dato) {
            NodoLista nuevo = new NodoLista(dato);
            if (cabeza == null) {
                cabeza = nuevo;
            } else {
                NodoLista actual = cabeza;
                while (actual.siguiente != null) {
                    actual = actual.siguiente;
                }
                actual.siguiente = nuevo;
            }
        }
        
        public boolean eliminar(String nombreDocumento, long etiquetaTiempo) {
            if (cabeza == null) return false;
            
            if (cabeza.dato.nombreDocumento.equals(nombreDocumento) && 
                cabeza.dato.etiquetaTiempo == etiquetaTiempo) {
                cabeza = cabeza.siguiente;
                return true;
            }
            
            NodoLista actual = cabeza;
            while (actual.siguiente != null) {
                if (actual.siguiente.dato.nombreDocumento.equals(nombreDocumento) && 
                    actual.siguiente.dato.etiquetaTiempo == etiquetaTiempo) {
                    actual.siguiente = actual.siguiente.siguiente;
                    return true;
                }
                actual = actual.siguiente;
            }
            return false;
        }
        
        public boolean estaVacia() {
            return cabeza == null;
        }
        
        public int getTamaño() {
            int contador = 0;
            NodoLista actual = cabeza;
            while (actual != null) {
                contador++;
                actual = actual.siguiente;
            }
            return contador;
        }
    }
}
