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
public class ValidateOtpResponse {

    private String error;

    private boolean isValid;

    public String getError() {
        return error;
    }

    public boolean getIsValid() {
        return isValid;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }
}
