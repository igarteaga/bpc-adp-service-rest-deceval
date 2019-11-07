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
public class SpinnerResponse {

    private int spinnerNumber;

    private String error;
    
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    public int getSpinnerNumber() {
        return spinnerNumber;
    }

    public void setSpinnerNumber(int spinnerNumber) {
        this.spinnerNumber = spinnerNumber;
    }
}
