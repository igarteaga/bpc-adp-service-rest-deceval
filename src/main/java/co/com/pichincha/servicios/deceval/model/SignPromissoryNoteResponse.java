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
public class SignPromissoryNoteResponse {
     
    private String error;

    private boolean successful;
    
    public void setError(String error) {
        this.error = error;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getError() {
        return error;
    }

    public boolean getSuccessful() {
        return successful;
    }
}
