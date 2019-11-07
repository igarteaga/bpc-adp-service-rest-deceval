/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.pichincha.servicios.deceval.bean;

/**
 *
 * @author julgue221
 */
public class DecevalUtil {

    //Busqueda de atributo en etiqueta XML para obtener valor especifico o verificar si existe el atributo.
    public static String ObtenerValorAtributo(String texto, String atributo) throws Exception {
        String response = null;
        int position = texto.indexOf(atributo + "=") + atributo.length() + 2;
        if (position > (atributo.length() + 1)) {
            String sub = texto.substring(position);
            response = sub.substring(0, sub.indexOf("\""));
        }
        return response;
    }
    
    //Busqueda de la etiqueta de cierre XML(ej = "</xxxxxx") para obtener el valor especifico o verificar si existe la etiqueta.
    public static String ObtenerValorEtiquetaCierre(String texto, String etiqueta) throws Exception {
        String response = null;
        int position = texto.indexOf(etiqueta);
        if (position > -1) {
            response = texto.substring(0, position);
        }
        return response;
    }
}
