package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios ordenados. Los árboles son genéricos, pero
 * acotados a la interfaz {@link Comparable}.</p>
 *
 * <p>Un árbol instancia de esta clase siempre cumple que:</p>
 * <ul>
 *   <li>Cualquier elemento en el árbol es mayor o igual que todos sus
 *       descendientes por la izquierda.</li>
 *   <li>Cualquier elemento en el árbol es menor o igual que todos sus
 *       descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioOrdenado<T extends Comparable<T>> extends ArbolBinario<T> {

    /* Clase privada para iteradores de árboles binarios ordenados. */
    private class Iterador implements Iterator<T> {

        /* Pila para recorrer los vértices en DFS in-order. */
        private Pila<Vertice> pila;

        /* Construye un iterador con el vértice recibido. */
        public Iterador() {
            pila = new Pila<>();
            if (raiz == null) {
                return;
            }
            Vertice v = raiz;
            while(v != null){
                pila.mete(v);
                v = v.izquierdo;
            }
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return !pila.esVacia();
        }

        /* Regresa el siguiente elemento en orden DFS in-order. */
        @Override public T next() {
            Vertice v = pila.saca();
            T elem = v.elemento;
            v = v.derecho;
	        while(v != null){
                pila.mete(v);
                v = v.izquierdo;
	          }
	           return elem;
	          }
    }

    /**
     * El vértice del último elemento agegado. Este vértice sólo se puede
     * garantizar que existe <em>inmediatamente</em> después de haber agregado
     * un elemento al árbol. Si cualquier operación distinta a agregar sobre el
     * árbol se ejecuta después de haber agregado un elemento, el estado de esta
     * variable es indefinido.
     */
    protected Vertice ultimoAgregado;

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioOrdenado() { super(); }

    /**
     * Construye un árbol binario ordenado a partir de una colección. El árbol
     * binario ordenado tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario ordenado.
     */
    public ArbolBinarioOrdenado(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden in-order.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) throws IllegalArgumentException{
        if (elemento == null) {
            throw new IllegalArgumentException();
        }
		if(raiz == null){
	    raiz = ultimoAgregado = nuevoVertice(elemento);
        }else{
           agrega(raiz, elemento);
        }
       elementos++;
    }


    private void agrega(Vertice vertice, T elemento) {
          if (elemento.compareTo(vertice.elemento) <= 0)
            if (!vertice.hayIzquierdo()) {
                Vertice verticeNuevo = nuevoVertice(elemento);
                verticeNuevo.padre = vertice;
                vertice.izquierdo = ultimoAgregado = verticeNuevo;
            } else
                agrega(vertice.izquierdo, elemento);
        else
            if (!vertice.hayDerecho()) {
                Vertice verticeNuevo = nuevoVertice(elemento);
                verticeNuevo.padre = vertice;
                vertice.derecho = ultimoAgregado = verticeNuevo;
            } else
		            agrega(vertice.derecho, elemento);
    }


    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no hace nada; si
     * está varias veces, elimina el primero que encuentre (in-order). El árbol
     * conserva su orden in-order.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        Vertice eliminar = busca(raiz, elemento), vi;
        if (eliminar == null) {
            return;
        }
        if (eliminar.hayIzquierdo()) {
            vi = eliminar;
            eliminar = maximoEnSubarbol(eliminar.izquierdo);
            vi.elemento = eliminar.elemento;
        }

        if (!eliminar.hayIzquierdo() && !eliminar.hayDerecho()) {
            eliminaHoja(eliminar);
        } else if (!eliminar.hayIzquierdo()) {
            eliminaSinHijoIzquierdo(eliminar);
        } else {
            eliminaSinHijoDerecho(eliminar);
        }
    }

    /**
     * Metodo para buscar el máximo de un subarbol
     * @param v vertice actual
     * @return Vertice - el vertice maximo del subarbol
     */
    protected Vertice maximoEnSubarbol(Vertice v){
        if (v.derecho == null) {
            return v;
        }
        return maximoEnSubarbol(v.derecho);
    }

    /**
     * Auxiliar de elimina. Elimina una hoja.
     * @param v el elemento a eliminar que debe ser hoja.
     */
    protected void eliminaHoja(Vertice v) {
        if (raiz == v) {
            raiz = null;
            ultimoAgregado = null;
        } else if (hijoIzquierdo(v)) {
            v.padre.izquierdo = null;
        } else {
            v.padre.derecho = null;
        }
        elementos--;
    }

    /**
     * Auxiliar de elimina. Elimina vertice que no tiene hijo izquierdo.
     * @param v el elemento a eliminar que debe no tener hijo izquierdo.
     */
    protected void eliminaSinHijoIzquierdo(Vertice v) {
        if (raiz == v) {
            raiz = raiz.derecho;
            v.derecho.padre = null;
        } else {
            v.derecho.padre = v.padre;
            if (hijoIzquierdo(v)) {
                v.padre.izquierdo = v.derecho;
            } else {
                v.padre.derecho = v.derecho;
            }
        }
        elementos--;
    }

    /**
     * Auxiliar de elimina. Elimina vertice que no tiene hijo derecho.
     * @param v el elemento a eliminar que debe no tener hijo derecho.
     */
    protected void eliminaSinHijoDerecho(Vertice v) {
        if (raiz == v) {
            raiz = raiz.izquierdo;
            v.izquierdo.padre = null;
        } else {
            v.izquierdo.padre = v.padre;
            if (hijoIzquierdo(v)) {
                v.padre.izquierdo = v.izquierdo;
            } else {
                v.padre.derecho = v.izquierdo;
            }
        }
        elementos--;
    }

    /**
     * Intercambia el elemento de un vértice con dos hijos distintos de
     * <code>null</code> con el elemento de un descendiente que tenga a lo más
     * un hijo.
     * @param vertice un vértice con dos hijos distintos de <code>null</code>.
     * @return el vértice descendiente con el que vértice recibido se
     *         intercambió. El vértice regresado tiene a lo más un hijo distinto
     *         de <code>null</code>.
     */
    protected Vertice intercambiaEliminable(Vertice vertice) {
        // Aquí va su código.
        return null;
    }

    /**
     * Elimina un vértice que a lo más tiene un hijo distinto de
     * <code>null</code> subiendo ese hijo (si existe).
     * @param vertice el vértice a eliminar; debe tener a lo más un hijo
     *                distinto de <code>null</code>.
     */
    protected void eliminaVertice(Vertice vertice) {
        // Aquí va su código.
    }

    /**
     * Busca un elemento en el árbol recorriéndolo in-order. Si lo encuentra,
     * regresa el vértice que lo contiene; si no, regresa <tt>null</tt>.
     * @param elemento el elemento a buscar.
     * @return un vértice que contiene al elemento buscado si lo
     *         encuentra; <tt>null</tt> en otro caso.
     */
    @Override public VerticeArbolBinario<T> busca(T elemento) {
        return busca(raiz, elemento);
    }

    /**
     * Metodo para buscar un un elemento (auxiliar)
     * @param vertice vertice del elemento
     * @param elemento elemento a buscar
     * @return Vertice - el vertice que posee al elemento
     */
    protected Vertice busca(Vertice vertice, T elemento){
        if (vertice == null) {
            return null;
        } else {
            if (vertice.elemento.equals(elemento)) {
                return vertice;
            } else {
                Vertice a = busca(vertice.izquierdo, elemento);
                Vertice b = busca(vertice.derecho, elemento);
                if (a != null) {
                    return a;
                }
                return b;
            }
        }
    }

    /**
     * Regresa el vértice que contiene el último elemento agregado al
     * árbol. Este método sólo se puede garantizar que funcione
     * <em>inmediatamente</em> después de haber invocado al método {@link
     * agrega}. Si cualquier operación distinta a agregar sobre el árbol se
     * ejecuta después de haber agregado un elemento, el comportamiento de este
     * método es indefinido.
     * @return el vértice que contiene el último elemento agregado al árbol, si
     *         el método es invocado inmediatamente después de agregar un
     *         elemento al árbol.
     */
    public VerticeArbolBinario<T> getUltimoVerticeAgregado() {
        return ultimoAgregado;
    }

    /**
     * Metodo para verificar si el vertice es la raiz
     * @param vertice vertice actual
     * @return boolean - true si el la raiz, false en otro caso
     */
    private boolean esRaiz(Vertice vertice){
        return vertice == raiz;
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el vértice no
     * tiene hijo izquierdo, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraDerecha(VerticeArbolBinario<T> vertice){
	        if (vertice == null || !vertice.hayIzquierdo()){
            return;
	}
        Vertice v = (Vertice) vertice;
        Vertice vIzq = v.izquierdo;
        vIzq.padre = v.padre;
        if(!esRaiz(v)){
            if(hijoIzquierdo(v)){
                v.padre.izquierdo = vIzq;
            }else{
                v.padre.derecho = vIzq;
             }
        }else{
            raiz = vIzq;
         }
        v.izquierdo = vIzq.derecho;
        if(vIzq.hayDerecho()){
            vIzq.derecho.padre = v;
         }
        vIzq.derecho = v;
        v.padre = vIzq;
    }

    /**
     * Metodo para verificar si un vertice en hijo derecho
     * @param v vertice actual
     * @return boolean - true si v es hijo derecho, false en otro caso
     */
    protected boolean hijoDerecho(Vertice v){
        if (!v.hayPadre()) {
            return false;
        }
        return v.padre.derecho == v;
    }

    /**
     * Metodo para verificar si un vertice es hijo hayIzquierdo
     * @param v vertice actual
     * @return boolean - true si v es hijo izquierdo, false en otro caso
     */
    protected boolean hijoIzquierdo(Vertice v){
        if (!v.hayPadre()) {
            return false;
        }
        return v.padre.izquierdo == v;
    }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el vértice no
     * tiene hijo derecho, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        if (vertice == null || !vertice.hayDerecho()){
            return;
       }
	   Vertice v = (Vertice) vertice;
        Vertice vDer = v.derecho;
        vDer.padre = v.padre;
        if (!esRaiz(v)){
            if (hijoIzquierdo(v)){
                v.padre.izquierdo = vDer;
           }else{
                v.padre.derecho = vDer;
           }
        }else{
            raiz = vDer;
       }

        v.derecho = vDer.izquierdo;
        if (vDer.hayIzquierdo()){
            vDer.izquierdo.padre = v;
        }
        vDer.izquierdo = v;
        v.padre = vDer;
    }

    /**
     * Realiza un recorrido DFS <em>pre-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPreOrder(AccionVerticeArbolBinario<T> accion) {
        dfsPreOrder_0(raiz, accion);
    }

    /**
     * Metodo auxliar pada dfsPreOrder.
     * @param vertice vertice actual;
     * @param accion la aciiona a realizar en cada elemento del árbol
     */
    private void dfsPreOrder_0(Vertice vertice, AccionVerticeArbolBinario<T> accion){
        if (vertice == null) {
            return;
        }
        accion.actua(vertice);
        dfsPreOrder_0(vertice.izquierdo, accion);
        dfsPreOrder_0(vertice.derecho, accion);
    }

    /**
     * Realiza un recorrido DFS <em>in-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsInOrder(AccionVerticeArbolBinario<T> accion) {
        dfsInOrder_0(raiz,accion);
    }

    /**
     * Metodo auxliar pada dfsInOrder.
     * @param vertice vertice actual
     * @param accion la aciiona a realizar en cada elemento del árbol
     */
    private void dfsInOrder_0(Vertice vertice, AccionVerticeArbolBinario<T> accion){
        if (vertice == null) {
            return;
        }
        dfsInOrder_0(vertice.izquierdo, accion);
        accion.actua(vertice);
        dfsInOrder_0(vertice.derecho, accion);
    }

    /**
     * Realiza un recorrido DFS <em>post-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPostOrder(AccionVerticeArbolBinario<T> accion) {
        dfsPostOrder_0(raiz, accion);
    }

    /**
     * Metodo auxliar pada dfsPostOrder.
     * @param vertice vertice actual
     * @param accion la aciiona a realizar en cada elemento del árbol
     */
    private void dfsPostOrder_0(Vertice vertice, AccionVerticeArbolBinario<T> accion){
        if(vertice == null){
            return;
        }
        dfsPostOrder_0(vertice.izquierdo, accion);
        dfsPostOrder_0(vertice.derecho, accion);
        accion.actua(vertice);
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
