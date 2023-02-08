 package mx.unam.ciencias.edd;

import java.util.NoSuchElementException;

/**
 * <p>Clase abstracta para árboles binarios genéricos.</p>
 *
 * <p>La clase proporciona las operaciones básicas para árboles binarios, pero
 * deja la implementación de varias en manos de las subclases concretas.</p>
 */
public abstract class ArbolBinario<T> implements Coleccion<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class Vertice implements VerticeArbolBinario<T> {

        /** El elemento del vértice. */
        public T elemento;
        /** El padre del vértice. */
        public Vertice padre;
        /** El izquierdo del vértice. */
        public Vertice izquierdo;
        /** El derecho del vértice. */
        public Vertice derecho;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public Vertice(T elemento) {
            this.elemento = elemento;
        }

        /**
         * Nos dice si el vértice tiene un padre.
         * @return <tt>true</tt> si el vértice tiene padre,
         *         <tt>false</tt> en otro caso.
         */
        @Override public boolean hayPadre() {
            return padre != null;
        }

        /**
         * Nos dice si el vértice tiene un izquierdo.
         * @return <tt>true</tt> si el vértice tiene izquierdo,
         *         <tt>false</tt> en otro caso.
         */
        @Override public boolean hayIzquierdo() {
            return izquierdo != null;
        }

        /**
         * Nos dice si el vértice tiene un derecho.
         * @return <tt>true</tt> si el vértice tiene derecho,
         *         <tt>false</tt> en otro caso.
         */
        @Override public boolean hayDerecho() {
            return derecho != null;
        }

        /**
         * Regresa el padre del vértice.
         * @return el padre del vértice.
         * @throws NoSuchElementException si el vértice no tiene padre.
         */
        @Override public VerticeArbolBinario<T> padre() throws NoSuchElementException{
            if (padre == null) {
              throw new NoSuchElementException("No hay padre");
            }
            return padre;
        }

        /**
         * Regresa el izquierdo del vértice.
         * @return el izquierdo del vértice.
         * @throws NoSuchElementException si el vértice no tiene izquierdo.
         */
        @Override public VerticeArbolBinario<T> izquierdo() throws NoSuchElementException{
          if (izquierdo == null) {
            throw new NoSuchElementException("No hay izquierdo");
          }
          return izquierdo;
        }

        /**
         * Regresa el derecho del vértice.
         * @return el derecho del vértice.
         * @throws NoSuchElementException si el vértice no tiene derecho.
         */
        @Override public VerticeArbolBinario<T> derecho() throws NoSuchElementException{
          if (derecho == null) {
            throw new NoSuchElementException("No hay derecho");
          }
          return derecho;
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
            return altura(this);
        }

        private int altura(Vertice v){
            if (v == null) {
                return 0;
            }
            if (v.izquierdo == null && v.derecho == null) {
                return 0;
            }
            return 1 + Math.max(altura(v.izquierdo), altura(v.derecho));
        }

        /**
         * Regresa la profundidad del vértice.
         * @return la profundidad del vértice.
         */
        @Override public int profundidad() {
            if (padre == null) {
                return 0;
            }
            return 1 + padre.profundidad();
        }


        /**
         * Regresa el elemento al que apunta el vértice.
         * @return el elemento al que apunta el vértice.
         */
        @Override public T get() {
            return elemento;
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>. Las clases que extiendan {@link Vertice} deben
         * sobrecargar el método {@link Vertice#equals}.
         * @param o el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link Vertice}, su elemento es igual al elemento de éste
         *         vértice, y los descendientes de ambos son recursivamente
         *         iguales; <code>false</code> en otro caso.
         */
        @Override public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass())
                return false;
            @SuppressWarnings("unchecked") Vertice vertice = (Vertice)o;
            return verticesIguales(this, vertice);
        }

        private boolean verticesIguales(Vertice vertice1, Vertice vertice2){
          if (vertice1 == null && vertice2 == null) {
                return true;
            }
            if ((vertice1 == null && vertice2 != null) || (vertice1 != null && vertice2 == null) || !vertice1.elemento.equals(vertice2.elemento)) {
                return false;
            }
            return verticesIguales(vertice1.izquierdo, vertice2.izquierdo) && verticesIguales(vertice1.derecho, vertice2.derecho);
        }

        /**
         * Regresa una representación en cadena del vértice.
         * @return una representación en cadena del vértice.
         */
        public String toString() {
            return elemento.toString();
        }
    }

    /** La raíz del árbol. */
    protected Vertice raiz;
    /** El número de elementos */
    protected int elementos;

    /**
     * Constructor sin parámetros. Tenemos que definirlo para no perderlo.
     */
    public ArbolBinario() {}

    /**
     * Construye un árbol binario a partir de una colección. El árbol binario
     * tendrá los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario.
     */
    public ArbolBinario(Coleccion<T> coleccion) {
        for (T n: coleccion) {
            agrega(n);
        }
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link Vertice}. Para
     * crear vértices se deb utilizar este método en lugar del operador
     * <code>new</code>, para que las clases herederas de ésta puedan
     * sobrecargarlo y permitir que cada estructura de árbol binario utilice
     * distintos tipos de vértices.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    protected Vertice nuevoVertice(T elemento) {
        return new Vertice(elemento);
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol es la altura de su
     * raíz.
     * @return la altura del árbol.
     */
    public int altura() {
        return altura_00(raiz);
    }

     protected int altura_00(Vertice v) {
        if (v == null) {
            return -1;
        }
        return 1 + Math.max(altura_00(v.izquierdo), altura_00(v.derecho));
    }

    /**
     * Regresa el número de elementos que se han agregado al árbol.
     * @return el número de elementos en el árbol.
     */
    @Override public int getElementos() {
        return elementos;
    }

    /**
     * Nos dice si un elemento está en el árbol binario.
     * @param elemento el elemento que queremos comprobar si está en el árbol.
     * @return <code>true</code> si el elemento está en el árbol;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        return contiene_0(raiz, elemento);
    }

    private boolean contiene_0(Vertice v, T elemento){
        if (v == null) {
            return false;
        }else{
            if (v.elemento.equals(elemento)) {
                return true;
            } else {
                return contiene_0(v.izquierdo, elemento) || contiene_0(v.derecho, elemento);
            }
        }
    }
    /**
     * Busca el vértice de un elemento en el árbol. Si no lo encuentra regresa
     * <tt>null</tt>.
     * @param elemento el elemento para buscar el vértice.
     * @return un vértice que contiene el elemento buscado si lo encuentra;
     *         <tt>null</tt> en otro caso.
     */
    public VerticeArbolBinario<T> busca(T elemento) {
        return busca_0(raiz, elemento);
    }

    private Vertice busca_0(Vertice vertice, T elemento){
        if (vertice == null) {
            return null;
        } else {
            if (vertice.elemento.equals(elemento)) {
                return vertice;
            } else {
                Vertice a = busca_0(vertice.izquierdo, elemento);
                Vertice b = busca_0(vertice.derecho, elemento);
                if (a != null) {
                    return a;
                }
                return b;
            }
        }
    }

    /**
     * Regresa el vértice que contiene la raíz del árbol.
     * @return el vértice que contiene la raíz del árbol.
     * @throws NoSuchElementException si el árbol es vacío.
     */
    public VerticeArbolBinario<T> raiz()  throws NoSuchElementException{
      if (raiz == null) {
          throw new NoSuchElementException("Arbol vacio");
      }
      return raiz;
    }

    /**
     * Nos dice si el árbol es vacío.
     * @return <code>true</code> si el árbol es vacío, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        return raiz == null;
    }

    /**
     * Limpia el árbol de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
        raiz = null;
        elementos = 0;
    }

    /**
     * Compara el árbol con un objeto.
     * @param o el objeto con el que queremos comparar el árbol.
     * @return <code>true</code> si el objeto recibido es un árbol binario y los
     *         árboles son iguales; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked")
            ArbolBinario<T> arbol = (ArbolBinario<T>)o;
        if (esVacia()) {
            return arbol.esVacia();
        }
        return raiz.equals(arbol.raiz);
    }

    /**
     * Regresa una representación en cadena del árbol.
     * @return una representación en cadena del árbol.
     */
    @Override public String toString() {
        if (raiz == null) {
            return "";
        }
        boolean [] a = new boolean[altura() + 1];
        for (int i = 0; i < altura() + 1; i++) {
            a[i] = false;
        }
        return cadena(raiz, 0, a);
    }

    private String dibujaEspacios(int nivel, boolean [] a){
        String s = "";
        for (int i = 0; i < nivel; i++) {
            if (a[i] == true) {
                s += "│  ";
            } else {
                s += "   ";
            }
        }
        return s;
    }

    private String cadena(Vertice v, int nivel, boolean [] a){
        String s = v + "\n";
        a[nivel] = true;
        if (v.izquierdo != null && v.derecho != null) {
            s += dibujaEspacios(nivel,a);
            s += "├─›";
            s += cadena(v.izquierdo, nivel + 1, a);
            s += dibujaEspacios(nivel, a);
            s += "└─»";
            a[nivel] = false;
            s += cadena(v.derecho, nivel + 1, a);
        }else if (v.izquierdo != null) {
            s += dibujaEspacios(nivel, a);
            s += "└─›";
            a[nivel] = false;
            s += cadena(v.izquierdo, nivel + 1, a);
        } else if (v.derecho != null) {
            s += dibujaEspacios(nivel, a);
            s += "└─»";
            a[nivel] = false;
            s += cadena(v.derecho, nivel + 1, a);
        }
        return s;
    }

    /**
     * Convierte el vértice (visto como instancia de {@link
     * VerticeArbolBinario}) en vértice (visto como instancia de {@link
     * Vertice}). Método auxiliar para hacer esta audición en un único lugar.
     * @param vertice el vértice de árbol binario que queremos como vértice.
     * @return el vértice recibido visto como vértice.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         Vertice}.
     */
    protected Vertice vertice(VerticeArbolBinario<T> vertice) {
        return (Vertice)vertice;
    }
}
