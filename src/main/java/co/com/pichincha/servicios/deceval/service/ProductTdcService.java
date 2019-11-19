/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.pichincha.servicios.deceval.service;

import co.com.pichincha.servicios.deceval.model.ProductTdcRequest;
import co.com.pichincha.servicios.deceval.model.ProductTdcResponse;

/**
 *
 * @author julgue221
 */
public interface ProductTdcService {
    public ProductTdcResponse CreateProduct(ProductTdcRequest request) throws Exception;
}
