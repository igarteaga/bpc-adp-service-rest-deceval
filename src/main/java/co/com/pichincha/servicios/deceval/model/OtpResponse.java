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
public class OtpResponse {

    private String error;

    private boolean successful;

    private boolean maxAttempts;

    public boolean isMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(boolean maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public String getError() {
        return error;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
}
