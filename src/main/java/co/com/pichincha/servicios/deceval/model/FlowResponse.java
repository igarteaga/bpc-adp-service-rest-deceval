/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.pichincha.servicios.deceval.model;

/**
 *
 * @author julgue221
 */
public class FlowResponse {
    
    private String codigoActividad;
    
    private String nombreActividad;
    
    private String intento;
    
    private String horaInicial;
    
    private String codigoResultado;
    
    private String mensaje;
    
    private String resultado;
    
    private String horaFinal;

    public String getCodigoActividad() {
	return codigoActividad;
    }

    public void setCodigoActividad(String codigoActividad) {
	this.codigoActividad = codigoActividad;
    }

    public String getNombreActividad() {
	return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
	this.nombreActividad = nombreActividad;
    }

    public String getIntento() {
        return intento;
    }

    public void setIntento(String intento) {
	this.intento = intento;
    }

    public String getHoraInicial() {
	return horaInicial;
    }

    public void setHoraInicial(String horaInicial) {
	this.horaInicial = horaInicial;
    }

    public String getCodigoResultado() {
    	return codigoResultado;
    }

    public void setCodigoResultado(String codigoResultado) {
	this.codigoResultado = codigoResultado;
    }

    public String getMensaje() {
	return mensaje;
    }

    public void setMensaje(String mensaje) {
	this.mensaje = mensaje;
    }

    public String getResultado() {
    	return resultado;
    }

    public void setResultado(String resultado) {
	this.resultado = resultado;
    }

    public String getHoraFinal() {
	return horaFinal;
    }

    public void setHoraFinal(String horaFinal) {
	this.horaFinal = horaFinal;
    }
}
