/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.pichincha.servicios.deceval.service;

/**
 *
 * @author julgue221
 */
public interface HttpService {
    public <U,V extends Object> U postHttp(Class<U> cls, V request, String urlService) throws Exception;
    
    public <U,V extends Object> U getHttp(Class<U> cls, V request, String urlService) throws Exception;
}
