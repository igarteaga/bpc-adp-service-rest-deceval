/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.pichincha.servicios.deceval.service.impl;

import co.com.pichincha.servicios.deceval.service.HttpService;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author julgue221
 */
@Service
public class HttpServiceImpl implements HttpService {

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Override
    public <U, V extends Object> U getHttp(Class<U> cls, V request, String urlService) throws Exception {
        HttpEntity<V> entity;
        if (request != null) {
            entity = new HttpEntity<V>(request, this.getHeaders());
        } else {
            entity = new HttpEntity<V>(this.getHeaders());
        }

        ResponseEntity<U> response = new RestTemplate().getForEntity(urlService, cls, entity);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            String msm = "Service response " + urlService + ", " + cls.getName() + "; Status: " + response.getStatusCode() + "; Error: " + response.getBody();
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, msm);
            throw new Exception(msm);
        }
    }

    @Override
    public <U, V extends Object> U postHttp(Class<U> cls, V request, String urlService) throws Exception {

        HttpEntity<V> entity;
        if (request != null) {
            entity = new HttpEntity<V>(request, this.getHeaders());
        } else {
            entity = new HttpEntity<V>(this.getHeaders());
        }

        ResponseEntity<U> response = null; 
        response = new RestTemplate().postForEntity(urlService, entity, cls);
        if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } else {
            String msm = "Service response " + urlService + ", " + cls.getName() + "; Status: " + response.getStatusCode() + "; Error: " + response.getBody();
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, msm);
            throw new Exception(msm);
        }
    }
}
