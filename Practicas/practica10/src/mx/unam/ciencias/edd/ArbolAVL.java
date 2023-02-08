package mx.unam.ciencias.edd;

/**
 * <p>Clase para árboles AVL.</p>
 *
 * <p>Un árbol AVL cumple que para cada uno de sus vértices, la diferencia entre
 * la áltura de sus subárboles izquierdo y derecho está entre -1 y 1.</p>
 */
public class ArbolAVL<T extends Comparable<T>> extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices de árboles AVL. La única diferencia
     * con los vértices de árbol binario, es que tienen una variable de clase
     * para la altura del vértice.
     */
    protected class VerticeAVL extends Vertice {

        /** La altura del vértice. */
        public int altura;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeAVL(T elemento) {
            super(elemento);
            altura = 0;
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
            return altura;
        }

        /**
         * Regresa una representación en cadena del vértice AVL.
         * @return una representación en cadena del vértice AVL.
         */
        @Override public String toString() {
            return elemento + " " + this.altura + "/" + obtenerBalance(this);
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param o el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeAVL}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente
         *         iguales, y las alturas son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass())
                return false;
            @SuppressWarnings("unchecked") VerticeAVL vertice = ( VerticeAVL)o;
            return altura == vertice.altura && super.equals(o);
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolAVL() {
	super();
    }

    /**
     * Construye un árbol AVL a partir de una colección. El árbol AVL tiene los
     * mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol AVL.
     */
    public ArbolAVL(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeAVL}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        return new VerticeAVL(elemento);
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol girándolo como
     * sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        super.agrega(elemento);
        rebalanceaAVL(verticeAVL(ultimoAgregado));
    }

    /**
     * Metodo para rebalancear el ArbolAVL
     * @param vertice vertice a agregar o eliminar
     */
    private void rebalanceaAVL(VerticeAVL vertice){
        if (vertice == null) {
            return;
        }
        vertice.altura = getAltura(vertice);
        if (obtenerBalance(vertice) == -2) {
            if (obtenerBalance(verticeAVL(vertice.izquierdo)) == 1) {
                aLaDerecha(verticeAVL(vertice.izquierdo));
            }
            aLaIzquierda(vertice);
        } else if (obtenerBalance(vertice) == 2) {
            if (obtenerBalance(verticeAVL(vertice.izquierdo)) == -1) {
                aLaIzquierda(verticeAVL(vertice.izquierdo));
            }
            aLaDerecha(vertice);
        }
        rebalanceaAVL(verticeAVL(vertice.padre));
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y gira el árbol como sea necesario para rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
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
        rebalanceaAVL(verticeAVL(eliminar.padre));
    }

    /**
     * Metodo para obtener el balance de un vertice
     * @param vertice vertice actual
     * @return int - balance del vertice
     */
    private int obtenerBalance(VerticeAVL vertice){
        if (vertice == null) {
            return 0;
        } else {
            return getAltura(verticeAVL(vertice.izquierdo)) - getAltura(verticeAVL(vertice.derecho));
        }
    }

    /**
     * Metodo para obtenr la altura de un vertice
     * @param vertice vertice actual
     * @return int - altura del vertice
     */
    private int getAltura(VerticeAVL vertice){
      if(vertice == null){
        return -1;
      }
      return 1 + Math.max(getAltura(verticeAVL(vertice.izquierdo)), getAltura(verticeAVL(vertice.derecho)));
    }

    /**
     * Metodo para girar un vertice a la derecha
     * @param vertice vertice sobre el cual se va a girar
     */
     private void aLaDerecha(VerticeAVL vertice){
         super.giraDerecha(vertice);
         vertice.altura = getAltura(vertice);
         verticeAVL(vertice.padre).altura = getAltura(verticeAVL(vertice.padre));
     }

     /**
      * Metodo para girar un vertice a la izquierda
      * @param vertice vertice sobre el cual se va a girar
      */
      private void aLaIzquierda(VerticeAVL vertice){
          super.giraIzquierda(vertice);
          vertice.altura = getAltura(vertice);
          verticeAVL(vertice.padre).altura = getAltura(verticeAVL(vertice.padre));
      }

      /**
       * Metodo para subir al hijo de un vertice
       * @param vertice vertice actual
       */
       private void subirHijo(VerticeAVL vertice){
           if (!vertice.hayIzquierdo()) {
               eliminaSinHijoIzquierdo(vertice);
           } else {
             eliminaSinHijoDerecho(vertice);
           }
       }

       /**
        * Auxiliar de elimina. Elimina una hoja.
        * @param vertice el elemento a eliminar que debe ser hoja.
        */
       private void eliminaHoja(VerticeAVL vertice) {
           if (raiz == vertice) {
               raiz = ultimoAgregado = null;
           } else if (hijoIzquierdo(vertice)) {
               vertice.padre.izquierdo = null;
           } else {
               vertice.padre.derecho = null;
           }
           elementos--;
       }

       /**
        * Auxiliar de elimina. Elimina vertice que no tiene hijo izquierdo.
        * @param v el elemento a eliminar que debe no tener hijo izquierdo.
        */
       protected void eliminaSinHijoIzquierdo(VerticeAVL v) {
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
       protected void eliminaSinHijoDerecho(VerticeAVL v) {
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
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la derecha por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la izquierda por el " +
                                                "usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la izquierda por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la derecha por el " +
                                                "usuario.");
    }

    /**
     * Metodo para convertir un VerticeArbolBinario en VerticeAVL
     * @param vertice vertice a convertir
     * @return VerticeAVL - vertice convertido
     */
     private VerticeAVL verticeAVL(VerticeArbolBinario<T> vertice){
         VerticeAVL v = (VerticeAVL)vertice;
         return v;
     }
}
