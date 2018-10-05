package mx.unam.ciencias.edd;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para diccionarios (<em>hash tables</em>). Un diccionario generaliza el
 * concepto de arreglo, mapeando un conjunto de <em>llaves</em> a una colección
 * de <em>valores</em>.
 */
public class Diccionario<K, V> implements Iterable<V> {

    /* Clase para las entradas del diccionario. */
    private class Entrada {

        /* La llave. */
        public K llave;
        /* El valor. */
        public V valor;

        /* Construye una nueva entrada. */
        public Entrada(K llave, V valor) {
    	    this.llave = llave;
    	    this.valor = valor;
        }
    }

    /* Clase privada para iteradores de diccionarios. */
    private class Iterador {

        /* En qué lista estamos. */
        private int indice;
        /* Iterador auxiliar. */
        private Iterator<Entrada> iterador;

        /* Construye un nuevo iterador, auxiliándose de las listas del
         * diccionario. */
        public Iterador() {
          Lista<Entrada> lista = new Lista<>();
          for (int i = 0; i < entradas.length; i++) {
           if (entradas[i] !=  null) {
             for (Entrada e : entradas[i]) {
	              lista.agrega(e);
              }
            }
         }
         iterador = lista.iterator();
        }

        /* Nos dice si hay una siguiente entrada. */
        public boolean hasNext() {
          return iterador.hasNext();
        }

        /* Regresa la siguiente entrada. */
        public Entrada siguiente() {
          return iterador.next();
        }
    }

    /* Clase privada para iteradores de llaves de diccionarios. */
    private class IteradorLlaves extends Iterador
        implements Iterator<K> {

        /* Construye un nuevo iterador de llaves del diccionario. */
        public IteradorLlaves() {
          super();
        }

        /* Regresa el siguiente elemento. */
        @Override public K next() {
          return super.siguiente().llave;
        }
    }

    /* Clase privada para iteradores de valores de diccionarios. */
    private class IteradorValores extends Iterador
        implements Iterator<V> {

        /* Construye un nuevo iterador de llaves del diccionario. */
        public IteradorValores() {
          super();
        }

        /* Regresa el siguiente elemento. */
        @Override public V next() {
          return super.siguiente().valor;
        }
    }

    /** Máxima carga permitida por el diccionario. */
    public static final double MAXIMA_CARGA = 0.72;

    /* Capacidad mínima; decidida arbitrariamente a 2^6. */
    private static final int MINIMA_CAPACIDAD = 64;

    /* Dispersor. */
    private Dispersor<K> dispersor;
    /* Nuestro diccionario. */
    private Lista<Entrada>[] entradas;
    /* Número de valores. */
    private int elementos;

    /* Truco para crear un arreglo genérico. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked")
    private Lista<Entrada>[] nuevoArreglo(int n) {
        return (Lista<Entrada>[])Array.newInstance(Lista.class, n);
    }

    /**
     * Construye un diccionario con una capacidad inicial y dispersor
     * predeterminados.
     */
    public Diccionario() {
      this(MINIMA_CAPACIDAD, (K llave) -> llave.hashCode());
    }

    /**
     * Construye un diccionario con una capacidad inicial definida por el
     * usuario, y un dispersor predeterminado.
     * @param capacidad la capacidad a utilizar.
     */
    public Diccionario(int capacidad) {
      this(capacidad, (K llave) -> llave.hashCode());
    }

    /**
     * Construye un diccionario con una capacidad inicial predeterminada, y un
     * dispersor definido por el usuario.
     * @param dispersor el dispersor a utilizar.
     */
    public Diccionario(Dispersor<K> dispersor) {
      this(MINIMA_CAPACIDAD, dispersor);
    }

    /**
     * Construye un diccionario con una capacidad inicial y un método de
     * dispersor definidos por el usuario.
     * @param capacidad la capacidad inicial del diccionario.
     * @param dispersor el dispersor a utilizar.
     */
    public Diccionario(int capacidad, Dispersor<K> dispersor) {
    	this.dispersor = dispersor;
    	if (capacidad < MINIMA_CAPACIDAD) {
    	    entradas = nuevoArreglo(MINIMA_CAPACIDAD);
    	} else {
    	    capacidad = tamanio(capacidad);
    	    entradas = nuevoArreglo(capacidad);
    	}
    	elementos = 0;
    }

    /**
     * Agrega un nuevo valor al diccionario, usando la llave proporcionada. Si
     * la llave ya había sido utilizada antes para agregar un valor, el
     * diccionario reemplaza ese valor con el recibido aquí.
     * @param llave la llave para agregar el valor.
     * @param valor el valor a agregar.
     * @throws IllegalArgumentException si la llave o el valor son nulos.
     */
    public void agrega(K llave, V valor) {
    	if (llave == null || valor == null) {
    	    throw new IllegalArgumentException();
    	}
    	int i = (entradas.length - 1) & (dispersor.dispersa(llave));
    	if (entradas[i] == null) {
    	    entradas[i] = new Lista<Entrada>();
    	}
    	for (Entrada e : entradas[i]) {
    	    if (e.llave.equals(llave)) {
    		e.valor = valor;
    		return;
    	    }
    	}
    	entradas[i].agrega(new Entrada(llave, valor));
    	elementos++;
    	if (carga() > MAXIMA_CARGA) {
    	    hazCrecerArreglista();
    	}

    }

    /**
     * Metodo para aumentar el tamaño del arreglo
     */
     private void hazCrecerArreglista(){
    	int m = tamanio(entradas.length);
    	elementos = 0;
    	Lista<Entrada>[] arreglistaViejo = entradas;
    	Lista<Entrada>[] arreglistaNuevo = nuevoArreglo(m);
    	entradas = arreglistaNuevo;
    	   for (int i = 0; i < arreglistaViejo.length; i++) {
    	      if (arreglistaViejo[i] != null) {
    		        for (Entrada ent : arreglistaViejo[i]) {
    		            agrega(ent.llave, ent.valor);
		            }
             }
        }
    }

    /**
     * Metodo para obtener el tamanio del nuevoArreglo
     * @param n la capacidad del arreglo actual
     * @return int la capacidad del arreglo
     */private int tamanio(int n){
      n = (n < 64) ? 64 : n;
      int c = 1;
      while (c < n * 2)
          c *= 2;
      return c;
    }

    /**
     * Regresa el valor del diccionario asociado a la llave proporcionada.
     * @param llave la llave para buscar el valor.
     * @return el valor correspondiente a la llave.
     * @throws IllegalArgumentException si la llave es nula.
     * @throws NoSuchElementException si la llave no está en el diccionario.
     */
    public V get(K llave) {
      V valor = null;
      if (llave == null) {
        throw new IllegalArgumentException("Llave nula");
      }
      if (!contiene(llave)) {
        throw new NoSuchElementException("La llave no esta en el diccionario");
      }
      int i = (entradas.length - 1) & (dispersor.dispersa(llave));
      for (Entrada ent : entradas[i]) {
        if (ent.llave.equals(llave)) {
          valor = ent.valor;
        }
      }
      return valor;
    }

    /**
     * Nos dice si una llave se encuentra en el diccionario.
     * @param llave la llave que queremos ver si está en el diccionario.
     * @return <tt>true</tt> si la llave está en el diccionario,
     *         <tt>false</tt> en otro caso.
     */
    public boolean contiene(K llave) {
      if (llave == null) {
        return false;
      }
      int i = (entradas.length - 1) & (dispersor.dispersa(llave));
      if (entradas[i] == null) {
        return false;
      }
      for (Entrada e : entradas[i]) {
        if (e.llave.equals(llave)) {
          return true;
        }
      }
      return false;
    }

    /**
     * Elimina el valor del diccionario asociado a la llave proporcionada.
     * @param llave la llave para buscar el valor a eliminar.
     * @throws IllegalArgumentException si la llave es nula.
     * @throws NoSuchElementException si la llave no se encuentra en
     *         el diccionario.
     */
    public void elimina(K llave) {
      if (llave == null) {
        throw new IllegalArgumentException();
      }
      if (!contiene(llave)) {
        throw new NoSuchElementException();
      }
      int i = (entradas.length - 1) & (dispersor.dispersa(llave));
      for (Entrada e : entradas[i]) {
        if (e.llave.equals(llave)) {
          entradas[i].elimina(e);
        }
      }
      elementos--;
    }

    /**
     * Nos dice cuántas colisiones hay en el diccionario.
     * @return cuántas colisiones hay en el diccionario.
     */
    public int colisiones() {
      int col = 0;
      for (int i = 0; i < entradas.length; i++) {
        if (entradas[i] != null && entradas[i].getElementos() > 1) {
          col += entradas[i].getElementos() - 1;
        }
      }
      return col;
    }

    /**
     * Nos dice el máximo número de colisiones para una misma llave que tenemos
     * en el diccionario.
     * @return el máximo número de colisiones para una misma llave.
     */
    public int colisionMaxima() {
      int col = 0;
      for (int i = 0; i < entradas.length; i++) {
        if (entradas[i] != null) {
          if ((entradas[i].getElementos() - 1) > col) {
            col += entradas[i].getElementos() - 1;
          }
        }
      }
      return col;
    }

    /**
     * Nos dice la carga del diccionario.
     * @return la carga del diccionario.
     */
    public double carga() {
      return (double)((elementos + 0.0)/entradas.length);
    }

    /**
     * Regresa el número de entradas en el diccionario.
     * @return el número de entradas en el diccionario.
     */
    public int getElementos() {
      return elementos;
    }

    /**
     * Nos dice si el diccionario es vacío.
     * @return <code>true</code> si el diccionario es vacío, <code>false</code>
     *         en otro caso.
     */
    public boolean esVacia() {
      return elementos == 0;
    }

    /**
     * Limpia el diccionario de elementos, dejándolo vacío.
     */
    public void limpia() {
        entradas = nuevoArreglo(elementos);
        elementos = 0;
    }

    /**
     * Regresa una representación en cadena del diccionario.
     * @return una representación en cadena del diccionario.
     */
    @Override public String toString() {
      String dic = "{ ";
      for (Lista<Entrada> lista : entradas) {
        if (lista != null) {
          for (Entrada e : lista) {
            dic += "'" + e.llave + "'" + ": '" + e.valor + "', ";
          }
        }
      }
      if (dic.equals("{ ")) {
        return "{}";
      }
      dic += "}";
      return dic;
    }

    /**
     * Nos dice si el diccionario es igual al objeto recibido.
     * @param o el objeto que queremos saber si es igual al diccionario.
     * @return <code>true</code> si el objeto recibido es instancia de
     *         Diccionario, y tiene las mismas llaves asociadas a los mismos
     *         valores.
     */
    @Override public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked") Diccionario<K, V> d = (Diccionario<K, V>)o;
        Lista<K> ll = keys();
        Lista<K> dic = d.keys();
        if (ll.getLongitud() != dic.getLongitud()) {
          return false;
        }
        for (K llaves : ll) {
          if (!(d.contiene(llaves) && d.get(llaves).equals(get(llaves)))) {
            return false;
          }
        }
        return true;
    }
    
    private Lista<K> keys(){
      Lista<K> k = new Lista<>();
      for (Lista<Entrada> lista : entradas) {
        if (lista != null) {
          for (Entrada e : lista) {
            k.agrega(e.llave);
          }
        }
      }
      return k;
    }

    /**
     * Regresa un iterador para iterar las llaves del diccionario. El
     * diccionario se itera sin ningún orden específico.
     * @return un iterador para iterar las llaves del diccionario.
     */
    public Iterator<K> iteradorLlaves() {
        return new IteradorLlaves();
    }

    /**
     * Regresa un iterador para iterar los valores del diccionario. El
     * diccionario se itera sin ningún orden específico.
     * @return un iterador para iterar los valores del diccionario.
     */
    @Override public Iterator<V> iterator() {
        return new IteradorValores();
    }
}
