/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.pichincha.servicios.deceval.service;

import co.com.pichincha.servicios.deceval.model.ClientRequest;
import co.com.pichincha.servicios.deceval.model.ClientResponse;

/**
 *
 * @author julgue221
 */
public interface ClientService {

    public ClientResponse saveClient(ClientRequest clientRequest) throws Exception;
}
