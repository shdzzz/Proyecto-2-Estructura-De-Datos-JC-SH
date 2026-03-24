package proyecto.pkg2.estructura.de.datos.jc.sh;

/**
 * Tabla hash simple para guardar usuarios.
 * @author shdz
 */
public class TablaDispersion {
    
    private ListaEnlazada[] tabla;
    private int capacidad;
    private int tamaño;
    
    /**
     * Constructor que crea una tabla hash con capacidad inicial de 16.
     */
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
    
    public void agregarUsuario(String nombre, String tipo, Usuario usuario) {
        if ((double) tamaño / capacidad > 0.75) {
            rehash();
        }
        
        int indice = funcionHash(nombre);
        tabla[indice].agregar(new InfoUsuario(nombre, tipo, usuario));
        tamaño++;
    }
    
    /**
     * Busca un usuario por su nombre.
     * 
     * @param nombre Nombre del usuario a buscar
     * @return Lista enlazada con el usuario, null si no existe
     */
    public ListaEnlazada buscarUsuario(String nombre) {
        int indice = funcionHash(nombre);
        if (tabla[indice].estaVacia()) {
            return null;
        }
        return tabla[indice];
    }
    
    /**
     * Elimina un usuario de la tabla hash.
     * 
     * @param nombre Nombre del usuario a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminarUsuario(String nombre) {
        int indice = funcionHash(nombre);
        ListaEnlazada cubeta = tabla[indice];
        
        boolean eliminado = cubeta.eliminar(nombre);
        
        if (eliminado && cubeta.estaVacia()) {
            tamaño--;
        }
        
        return eliminado;
    }
    
    /**
     * Verifica si un usuario existe en la tabla.
     * 
     * @param nombre Nombre del usuario a verificar
     * @return true si existe, false en caso contrario
     */
    public boolean existeUsuario(String nombre) {
        return !tabla[funcionHash(nombre)].estaVacia();
    }
    
    /**
     * Obtiene el número de usuarios en la tabla.
     * 
     * @return Número de usuarios almacenados
     */
    public int getTamaño() {
        return tamaño;
    }
    
    /**
     * Obtiene la capacidad actual de la tabla.
     * 
     * @return Capacidad máxima de la tabla
     */
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
    
    public static class InfoUsuario {
        String nombre;
        String tipo;
        Usuario usuario;
        
        InfoUsuario(String nombre, String tipo, Usuario usuario) {
            this.nombre = nombre;
            this.tipo = tipo;
            this.usuario = usuario;
        }
        
        public String getNombre() {
            return nombre;
        }
        
        public String getTipo() {
            return tipo;
        }
        
        public Usuario getUsuario() {
            return usuario;
        }
    }
    
    private static class ListaEnlazada {
        private NodoLista cabeza;
        
        private static class NodoLista {
            InfoUsuario dato;
            NodoLista siguiente;
            
            NodoLista(InfoUsuario dato) {
                this.dato = dato;
                this.siguiente = null;
            }
        }
        
        public void agregar(InfoUsuario dato) {
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
        
        public boolean eliminar(String nombre) {
            if (cabeza == null) return false;
            
            if (cabeza.dato.nombre.equals(nombre)) {
                cabeza = cabeza.siguiente;
                return true;
            }
            
            NodoLista actual = cabeza;
            while (actual.siguiente != null) {
                if (actual.siguiente.dato.nombre.equals(nombre)) {
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
