/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.pichincha.servicios.deceval.model;

import java.util.List;

/**
 *
 * @author julgue221
 */
public class FlowRequest {
    
    private int flujoId;
    
    private String usuario;
    
    private String clave;
    
    private String aplicativo;
    
    private short idEjecucion;
    
    private short idActividadInicio;
    
    private List<FlowVariables> variables;
	
    public int getFlujoId() {
	return flujoId;
    }
    
    public void setFlujoId(int flujoId) {
	this.flujoId = flujoId;
    }
    
    public String getUsuario() {
	return usuario;
    }
    
    public void setUsuario(String usuario) {
	this.usuario = usuario;
    }
    
    public String getClave() {
	return clave;
    }
	
    public void setClave(String clave) {
	this.clave = clave;
    }
	
    public String getAplicativo() {
	return aplicativo;
    }
	
    public void setAplicativo(String aplicativo) {
	this.aplicativo = aplicativo;
    }
	
    public short getIdEjecucion() {
	return idEjecucion;
    }
	
    public void setIdEjecucion(short idEjecucion) {
	this.idEjecucion = idEjecucion;
    }
	
    public short getIdActividadInicio() {
        return idActividadInicio;
    }
	
    public void setIdActividadInicio(short idActividadInicio) {
	this.idActividadInicio = idActividadInicio;
    }
    
    public List<FlowVariables> getVariables() {
	return variables;
    }
	
    public void setVariables(List<FlowVariables> variables) {
	this.variables = variables;
    }	
}
