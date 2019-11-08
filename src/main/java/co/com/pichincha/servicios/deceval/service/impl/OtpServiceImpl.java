/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.pichincha.servicios.deceval.service.impl;

import co.com.pichincha.servicios.deceval.model.OtpRequest;
import co.com.pichincha.servicios.deceval.model.OtpResponse;
import co.com.pichincha.servicios.deceval.service.HttpService;
import co.com.pichincha.servicios.deceval.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author julgue221
 */
@Service
public class OtpServiceImpl implements OtpService{
    
    @Value("${service.url.aws.notifier}")
    String urlService;

    @Autowired
    HttpService httpService;
    
    public OtpResponse sendOtp(OtpRequest request) throws Exception
    {
        return httpService.postHttp(OtpResponse.class, request, this.urlService);
    }
}
