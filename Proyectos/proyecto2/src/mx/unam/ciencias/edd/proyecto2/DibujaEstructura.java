package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Pila;
import mx.unam.ciencias.edd.Cola;
import mx.unam.ciencias.edd.MeteSaca;
import mx.unam.ciencias.edd.ArbolBinarioOrdenado;
import mx.unam.ciencias.edd.ArbolBinarioCompleto;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.VerticeGrafica;
import mx.unam.ciencias.edd.MonticuloMinimo;

/**
 * Clase para dibujar las estructuras de datos
 * @author Ramses Lopez
 * @version abril 2018
 */
 public class DibujaEstructura{


   private String estructura;

   private static final String inicio_svg = "<?xml version=\'1.0\' encoding=\'"+
	                                         "UTF-8\'"+"\n\twidth='%d' height='%d'>\n";

  private static final String fin_svg = "</svg>\n";

  private static final String inicio_etiqueta_g = "\t<g>\n";

  private static final String fin_etiqueta_g = "\t</g>\n";

  private static final String etiqueta_rectangulo = "\t<rect x='%d' y='%d' width='%d' height='%d' "+
                                                    "stroke='black' fill='white'/>\n";

  private static final String etiqueta_texto = "\t<text font-family='sans-serif' "+
    					    		                         "font-size='12' x='%d' y='%d' "+
					                                     "text-anchor='middle' fill='%s'>%s</text>\n";

  private static final String etiqueta_circulo = "\t<circle cx='%f' cy='%f' r='10' "+
                                                 "stroke='%s' fill='%s' />\n";

  private static final String conecta_lista = "\t<text fill='black' font-family='sans-serif' "+
                                                 "font-size='%d' x='%d' y='%d' "+
                                                 "text-anchor='middle'>%s</text>\n";

  private static final String line_a = "\t<line x1='%d' y1='%d' x2='%d' y2='%d' stroke='black'/>\n";


 }
