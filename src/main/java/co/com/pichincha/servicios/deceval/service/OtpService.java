/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.pichincha.servicios.deceval.service;

import co.com.pichincha.servicios.deceval.model.OtpRequest;
import co.com.pichincha.servicios.deceval.model.OtpResponse;
import co.com.pichincha.servicios.deceval.model.ValidateOtpRequest;
import co.com.pichincha.servicios.deceval.model.ValidateOtpResponse;

/**
 *
 * @author julgue221
 */
public interface OtpService {
    
    public OtpResponse sendOtp(OtpRequest request) throws Exception;
    
    public ValidateOtpResponse validateOtp(ValidateOtpRequest request) throws Exception;
}
