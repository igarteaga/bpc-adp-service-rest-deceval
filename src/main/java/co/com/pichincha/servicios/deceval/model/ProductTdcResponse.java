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
public class ProductTdcResponse {

    private String error;
    
    private boolean otpValid;

    public boolean isOtpValid() {
        return otpValid;
    }

    public void setOtpValid(boolean otpValid) {
        this.otpValid = otpValid;
    }
    
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
