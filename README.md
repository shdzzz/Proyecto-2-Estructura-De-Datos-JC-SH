# Colas de Prioridad y Hash Tables

Este proyecto es una simulación de un gestor de colas de impresión de un Sistema Operativo, diseñado para optimizar la administración de recursos mediante prioridades dinámicas. El sistema permite que documentos urgentes o de usuarios con mayor jerarquía sean procesados antes, incluso si llegaron después a la cola.

## Integrantes
* **Juan Coll**
* **Santiago Hernandez**

---

## Arquitectura y Estructuras de Datos

Para cumplir con los requerimientos de eficiencia y anonimato de los registros en cola, el sistema utiliza un **Modelo de Datos Híbrido**:

### 1. Montículo Binario (Min-Heap)
Implementación propia de un árbol binario completo representado en un arreglo. 
* **Función:** Gestiona la cola de prioridad.
* **Eficiencia:** Las operaciones de `insertar` y `eliminar_min` operan en tiempo O(log n).
* **Ordenación:** Basada en una "Clave de Ordenación" que combina el tiempo del reloj con el nivel de prioridad del usuario[cite: 34, 37].

### 2. Tabla de Dispersión (Hash Table)
Implementación de una tabla hash con resolución de colisiones para la gestión de usuarios.
* **Función:** Permite el acceso rápido a la información de los usuarios y sus documentos.
* **Eficiencia:** Búsqueda y recuperación con complejidad cercana a O(1).
* **Anonimato:** Cumple con el requisito de que el registro encolado no posea información directa del propietario, delegando esa relación a la tabla hash.

---

## Funcionalidades Principales

### Gestión de Usuarios y Documentos
* **Carga de Datos:** Soporte para importación de usuarios desde archivos `.csv`.
* **Tipos de Usuario:** Clasificación por `prioridad_alta`, `prioridad_media` y `prioridad_baja`.
* **Operaciones:** Crear Usuarios, borrar Usuarios, crear documentos, borrar documentos que no esten en cola, mandar a imprimir, liberar impresora, y cancelar en cola.

### Control de la Cola de Impresión
* **Reloj de Simulación:** Un contador global que etiqueta cada documento al ser enviado a la cola.
* **Modo Prioritario:** Al activar la opción "Es prioritario", el sistema altera la etiqueta de tiempo restando un offset según el rango del usuario, permitiendo que el documento "escale" posiciones en el montículo.
* **Liberar Impresora:** Ejecuta la primitiva `eliminar_min`, procesando el documento con la clave más pequeña (mayor prioridad).
* **Cancelación en Cola:** Permite eliminar un documento específico moviéndolo a la raíz (prioridad máxima ficticia) y aplicando `eliminar_min` sin imprimirlo.

---

## Interfaz Gráfica (GUI)
El programa ofrece una interfaz intuitiva con:
* **Gestión de Usuarios:** Lista lateral con búsqueda por Hash.
* **Documentos:** Tabla detallada de archivos por usuario seleccionado.
* **Vistas de Cola:** * **Vista Lista:** Secuencia de registros con claves de ordenación y etiquetas de tiempo.
    * **Vista Árbol:** Representación gráfica del Montículo Binario para visualizar las propiedades de orden y forma.

---

## Requisitos del Sistema
* **Java SDK:** 8 o superior.
* **IDE:** NetBeans (Proyecto desarrollado bajo estandares de NetBeans).
* **Documentación:** El código cuenta con documentación interna en formato **Javadoc**.
