/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.pichincha.servicios.deceval.service.impl;

import co.com.pichincha.servicios.deceval.model.ClientRequest;
import co.com.pichincha.servicios.deceval.model.ClientResponse;
import co.com.pichincha.servicios.deceval.service.ClientService;
import co.com.pichincha.servicios.deceval.service.HttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author julgue221
 */
@Service
public class ClientServiceImpl implements ClientService {

    @Value("${service.url.aws.creditCard}")
    String urlService;

    @Autowired
    HttpService httpService;

    @Override
    public ClientResponse saveClient(ClientRequest clientRequest) throws Exception {
        return httpService.postHttp(ClientResponse.class, clientRequest, this.urlService + "/client/create");
    }
}
