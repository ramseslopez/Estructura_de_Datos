package mx.unam.ciencias.edd;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 *  <li>Todos los vértices son NEGROS o ROJOS.</li>
 *  <li>La raíz es NEGRA.</li>
 *  <li>Todas las hojas (<tt>null</tt>) son NEGRAS (al igual que la raíz).</li>
 *  <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 *  <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 *      mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T>> extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices de árboles rojinegros. La única
     * diferencia con los vértices de árbol binario, es que tienen un campo para
     * el color del vértice.
     */
    protected class VerticeRojinegro extends Vertice {

        /* El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
            super(elemento);
            color = Color.NINGUNO;
        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * @return una representación en cadena del vértice rojinegro.
         */
        public String toString() {
          return (color == Color.ROJO) ? "R{" + elemento.toString() + "}" : "N{" + elemento.toString() + "}";
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param o el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass())
                return false;
            @SuppressWarnings("unchecked")
                VerticeRojinegro vertice = (VerticeRojinegro)o;
            return (color == vertice.color && super.equals(o));
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolRojinegro() {
        super();
    }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol
     * rojinegro tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        rojinegro.
     */
    public ArbolRojinegro(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeRojinegro}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        return new VerticeRojinegro(elemento);
    }

    /**
     * Regresa el color del vértice rojinegro.
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
	     if (vertice == null) {
             return Color.NEGRO;
         }
         if (!(vertice instanceof ArbolRojinegro.VerticeRojinegro)) {
             throw new ClassCastException("No es instancia de la clase");
         }
         VerticeRojinegro v = verticeRojinegro(vertice);
         return v.color;
    }

    /**
     * Metodo para hacer un casting de VerticeArbolBinario a VerticeRojinegro
     * @param vertice vertice a castear
     * @return VerticeRojinegro - vertice ya casteado
     */
    private VerticeRojinegro verticeRojinegro(VerticeArbolBinario<T> vertice){
        VerticeRojinegro v = (VerticeRojinegro)vertice;
        return v;
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        super.agrega(elemento);
        VerticeRojinegro vertice = verticeRojinegro(ultimoAgregado);
        vertice.color = Color.ROJO;
        auxAgrega(vertice);
    }

    /**
     * Metodo auxiliar de agrega. Rebalancea el ArbolRojinegro
     * @param vertice vertice actual
     */
    private void auxAgrega(VerticeRojinegro vertice){
       VerticeRojinegro verticePapa;
       VerticeRojinegro verticeAbuelo;
       VerticeRojinegro verticeTio;
       VerticeRojinegro verticeAux;
        /*Caso 1*/
        if(!vertice.hayPadre()){
	         vertice.color = Color.NEGRO;
           return;
        }
        verticePapa = verticeRojinegro(vertice.padre);

        /*Caso 2*/
        if (esNegro(verticePapa))
            return;

        verticeAbuelo = verticeRojinegro(verticePapa.padre);

        /*Caso 3*/
       if (hijoIzquierdo(verticePapa))
	      verticeTio = verticeRojinegro(verticeAbuelo.derecho);
       else
	      verticeTio = verticeRojinegro(verticeAbuelo.izquierdo);

         if (esRojo(verticeTio)) {
             verticeTio.color = verticePapa.color = Color.NEGRO;
             verticeAbuelo.color = Color.ROJO;
             auxAgrega(verticeAbuelo);
             return;
        }

        /*Caso 4*/
        if (cruzados(vertice, verticePapa)) {
           if (hijoIzquierdo(verticePapa)) {
	             super.giraIzquierda(verticePapa);
            }else {
	              super.giraDerecha(verticePapa);
            }
           verticeAux = verticePapa;
           verticePapa = vertice;
           vertice = verticeAux;
        }

        /*Caso 5*/
        verticePapa.color = Color.NEGRO;
        verticeAbuelo.color = Color.ROJO;
        if (hijoIzquierdo(vertice)) {
           super.giraDerecha(verticeAbuelo);
        } else {
           super.giraIzquierda(verticeAbuelo);
        }
    }


    /**
     * Metodo para verificar si dos vertice tienen el mismo padre
     * @param vertice1 vertice
     * @param vertice2 vertice
     * @return boolean - true si el padre de vertice1 y vertice2 es el mismo, false en otro caso
     */
    private boolean cruzados(VerticeRojinegro vertice1, VerticeRojinegro vertice2){
        if(vertice1.padre.izquierdo == vertice1 && vertice2.padre.derecho == vertice2)
            return true;
        if(vertice1.padre.derecho == vertice1 && vertice2.padre.izquierdo == vertice2)
            return true;
        return false;
  }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento)  {
        VerticeRojinegro v = verticeRojinegro(busca(raiz, elemento));
        VerticeRojinegro fantasma = null;
        VerticeRojinegro hijo;
        if(v == null){
            return;
        }
        if (v.hayIzquierdo()) {
          VerticeRojinegro aux = v;
          v = verticeRojinegro(maximoEnSubarbol(v.izquierdo));
          aux.elemento = v.elemento;
        }

        if (!v.hayIzquierdo() && !v.hayDerecho()) {
            fantasma = verticeRojinegro(nuevoVertice(null));
            fantasma.color = Color.NEGRO;
            fantasma.padre = v;
            v.izquierdo = fantasma;
        }

        if(v.hayIzquierdo()){
        hijo = verticeRojinegro(v.izquierdo);
        } else{
            hijo = verticeRojinegro(v.derecho);
        }
        subirHijo(v);

        if (esNegro(v) && esNegro(hijo)) {
            auxElimina(hijo);
        } else {
            hijo.color = Color.NEGRO;
        }

        if (fantasma != null) {
            if (fantasma == raiz) {
                raiz = ultimoAgregado = fantasma = null;
            } else {
                if (hijoIzquierdo(fantasma)) {
                    fantasma.padre.izquierdo = null;
                } else {
                    fantasma.padre.derecho = null;
                }
            }
        }
        elementos--;
    }

    private void subirHijo(VerticeRojinegro v){
        if (v.hayIzquierdo()) {
            if (v == raiz) {
              raiz = v.izquierdo;
              raiz.padre = null;
            } else {
                v.izquierdo.padre = v.padre;
                if (hijoIzquierdo(v)) {
                    v.padre.izquierdo = v.izquierdo;
                } else {
                    v.padre.derecho = v.izquierdo;
                }
            }
        } else {
            if (v == raiz) {
                raiz = v.derecho;
                raiz.padre = null;
            } else {
                v.derecho.padre = v.padre;
                if (hijoIzquierdo(v)) {
                    v.padre.izquierdo = v.derecho;
                } else {
                    v.padre.derecho = v.derecho;
                }
            }
        }

    }

    /**
     * Metodo auxliar para eliminar. Rebalancea el ArbolRojinegro.
     * @param vertice vertice actual
     */
    private void auxElimina(VerticeRojinegro vertice){
      VerticeRojinegro verticeAbuelo;
      VerticeRojinegro verticePapa;
      VerticeRojinegro verticeHermano;
      VerticeRojinegro verticeSobrinoIzq;
      VerticeRojinegro verticeSobrinoDer;

      /*Caso 1*/
      if (!vertice.hayPadre()) {
          raiz = vertice;
          vertice.color = Color.NEGRO;
          return;
      }
      verticePapa = verticeRojinegro(vertice.padre);
      verticeHermano = (hijoIzquierdo(vertice)) ? (VerticeRojinegro) vertice.padre.derecho : (VerticeRojinegro) vertice.padre.izquierdo;
       /*Caso 2*/
       if (esNegro(verticePapa) && esRojo(verticeHermano)) {
           verticePapa.color = Color.ROJO;
           verticeHermano.color = Color.NEGRO;
           if (hijoIzquierdo(vertice)) {
               super.giraIzquierda(verticePapa);
           } else {
               super.giraDerecha(verticePapa);
           }
           verticePapa = verticeRojinegro(vertice.padre);
           verticeHermano = (hijoIzquierdo(vertice)) ? (VerticeRojinegro) vertice.padre.derecho : (VerticeRojinegro) vertice.padre.izquierdo;
       }
       verticeSobrinoIzq = verticeRojinegro(verticeHermano.izquierdo);
       verticeSobrinoDer = verticeRojinegro(verticeHermano.derecho);
       /*Caso 3*/
       if (esNegro(verticePapa) && esNegro(verticeHermano) && esNegro(verticeSobrinoIzq) && esNegro(verticeSobrinoDer)) {
           verticeHermano.color = Color.ROJO;
           auxElimina(verticePapa);
           return;
       }

       /*Caso 4*/
       //if (esRojo(verticePapa) && esNegro(verticeHermano) && esNegro(verticeSobrinoIzq) && esNegro(verticeSobrinoDer)) {
       if(verticePapa.color != Color.NEGRO && verticeHermano.color == Color.NEGRO && (verticeSobrinoIzq == null || verticeSobrinoIzq.color == Color.NEGRO) && (verticeSobrinoDer == null || verticeSobrinoDer.color == Color.NEGRO)){
           verticePapa.color = Color.NEGRO;
           verticeHermano.color = Color.ROJO;
           return;
        }

       /*Caso 5*/
       if (sobrinosBicolor(verticeSobrinoIzq, verticeSobrinoDer) && sonCruzados(vertice, verticeSobrinoIzq, verticeSobrinoDer)) {

           if (esRojo(verticeSobrinoIzq)) {
               verticeSobrinoIzq.color = Color.NEGRO;
           } else {
               verticeSobrinoDer.color = Color.NEGRO;
           }
           verticeHermano.color = Color.ROJO;

           if (hijoIzquierdo(vertice)) {
               super.giraDerecha(verticeHermano);
           } else {
               super.giraIzquierda(verticeHermano);
           }
           verticeHermano = (hijoIzquierdo(vertice)) ? (VerticeRojinegro) vertice.padre.derecho : (VerticeRojinegro) vertice.padre.izquierdo;
           verticeSobrinoIzq = verticeRojinegro(verticeHermano.izquierdo);
           verticeSobrinoDer = verticeRojinegro(verticeHermano.derecho);
        }

        /*Caso 6*/
        verticeHermano.color = verticePapa.color;
        verticePapa.color = Color.NEGRO;
        if (hijoIzquierdo(vertice)) {
            verticeSobrinoDer.color = Color.NEGRO;
        } else {
            verticeSobrinoIzq.color = Color.NEGRO;
        }

        if (hijoIzquierdo(vertice)) {
            super.giraIzquierda(verticePapa);
        } else {
            super.giraDerecha(verticePapa);
        }
    }

    /**
     * Metodo para verificar si los sobrinos son cruzados
     * @param vertice vertice actual
     * @param sobrinoIzq sobrino izquierdo
     * @param sobrinoDer sobrino derecho
     * @return boolean - true si vertice es hijo derecho y sobrinoIzq es NEGRO y sobrinoDer es ROJO ó
     *                   vertice es hijo izquierdo y sobrinoIzq es ROJO y sobrinoDer es NEGRO,
     *                   false en otro caso
     */
    private boolean sonCruzados(VerticeRojinegro vertice, VerticeRojinegro sobrinoIzq, VerticeRojinegro sobrinoDer){
        return hijoDerecho(vertice) && esNegro(sobrinoIzq) && esRojo(sobrinoDer) || hijoIzquierdo(vertice) && esRojo(sobrinoIzq) && esNegro(sobrinoDer);
    }

    /**
     * Metodo para verificar si los vertices sobrinos son sobrinosBicolor
     * @param si sobrino izquierdo
     * @param sd sobrino derecho
     * @return boolean - true si si es ROJO y sd es NEGRO ó si es NEGRO y sd es ROJO, false en otro caso
     */
    private boolean sobrinosBicolor(VerticeRojinegro si, VerticeRojinegro sd){
        return esNegro (si) && esRojo(sd) || esRojo(si) && esNegro(sd);
    }


    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la izquierda por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la izquierda " +
                                                "por el usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la derecha por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la derecha " +
                                                "por el usuario.");
    }

    /**
     * Metodo para verificar si un vertice es ROJO
     * @param vertice vertice actual
     * @return booolean - true si es ROJO, false en otro caso
     */
    private boolean esRojo(VerticeRojinegro vertice){
        return vertice != null && vertice.color == Color.ROJO;
    }

    /**
     * Metodo para verificar si un vertice es NEGRO
     * @param vertice vertice actual
     * @return boolean - true si el NEGRO, false en otro caso
     */
    private boolean esNegro(VerticeRojinegro vertice){
        return vertice == null || vertice.color == Color.NEGRO;
    }
}
