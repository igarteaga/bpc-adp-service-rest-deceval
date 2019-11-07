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
public class FlowVariables {
    
    private String seccion;
    
    private FlowVariable variable;
    
    public String getSeccion() {
        return seccion;
    }

    public FlowVariable getVariable() {
        return variable;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public void setVariable(FlowVariable variable) {
        this.variable = variable;
    }
}
