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
public class ValidateOtpRequest {

    private String id;

    private String otpCode;

    public void setId(String id) {
        this.id = id;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public String getId() {
        return id;
    }

    public String getOtpCode() {
        return otpCode;
    }
}
