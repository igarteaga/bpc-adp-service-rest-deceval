/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.pichincha.servicios.deceval.controller;

import co.com.pichincha.servicios.deceval.model.DecevalRequest;
import co.com.pichincha.servicios.deceval.model.DecevalResponse;
import co.com.pichincha.servicios.deceval.model.ProductTdcRequest;
import co.com.pichincha.servicios.deceval.model.ProductTdcResponse;
import co.com.pichincha.servicios.deceval.service.DecevalService;
import co.com.pichincha.servicios.deceval.service.ProductTdcService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author julgue221
 */
@RestController
@CrossOrigin("*")
public class DecevalController {

    @Autowired
    DecevalService decevalService;

    @Autowired
    ProductTdcService productTdcService;

    @GetMapping("/health")
    @ResponseBody
    public Boolean health() {
        return true;
    }

    @PostMapping(value = "/sign/generate")
    public ResponseEntity<DecevalResponse> DecevalCreate(@RequestBody DecevalRequest request) {
        DecevalResponse decevalResponse = new DecevalResponse();
        try {
            if (!this.ValidateDecevalFields(request)) {
                return new ResponseEntity<>(decevalResponse, HttpStatus.BAD_REQUEST);
            }

            decevalResponse = decevalService.decevalCreate(request);

            if (decevalResponse != null && decevalResponse.getResult().equals("OK")) {
                return new ResponseEntity(decevalResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity(decevalResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage());
            decevalResponse.setResult(e.getMessage());
            return new ResponseEntity(decevalResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/sign/createProductTdc")
    public ResponseEntity<ProductTdcResponse> CreateProduct(@RequestBody ProductTdcRequest request) {
        ProductTdcResponse productTdcResponse = new ProductTdcResponse();
        try {
            if (!this.ValidateProductTdcFields(request)) {
                return new ResponseEntity<>(productTdcResponse, HttpStatus.BAD_REQUEST);
            }

            productTdcResponse = productTdcService.CreateProduct(request);

            if (productTdcResponse != null) {
                return new ResponseEntity(productTdcResponse, HttpStatus.OK);
            } else {
                productTdcResponse = new ProductTdcResponse();
                productTdcResponse.setError("Service response CreateProduct invalid");
                return new ResponseEntity(productTdcResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage());
            productTdcResponse.setError(e.getMessage());
            return new ResponseEntity(productTdcResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean ValidateProductTdcFields(ProductTdcRequest request) {
        if (request == null) {
            return false;
        }

        if (request.getId() == null || request.getId().equals("")) {
            return false;
        }

        if (request.getIdType() <= 0) {
            return false;
        }

        if (request.getIdPromissoryNote() <= 0) {
            return false;
        }

        if (request.getOtpCode() == null || request.getOtpCode().equals("")) {
            return false;
        }

        return true;
    }

    private boolean ValidateDecevalFields(DecevalRequest request) {
        if (request == null) {
            return false;
        }

        if (request.getId() == null || request.getId().equals("")) {
            return false;
        }

        if (request.getIdType() <= 0) {
            return false;
        }

        if (request.getNames() == null || request.getNames().equals("")) {
            return false;
        }

        if (request.getFirstLastName() == null || request.getFirstLastName().equals("")) {
            return false;
        }

        if (request.getEmail() == null || request.getEmail().equals("")) {
            return false;
        }

        if (request.getCellPhone() == null || request.getCellPhone().equals("")) {
            return false;
        }

        if (request.getRequestNumber() == null || request.getRequestNumber().equals("")) {
            return false;
        }

        if (request.getCountryAddress() == null || request.getCountryAddress().equals("")) {
            return false;
        }

        if (request.getExpeditionDate() == null || request.getExpeditionDate().equals("")) {
            return false;
        }

        if (request.getBirthDate() == null || request.getBirthDate().equals("")) {
            return false;
        }

        if (request.getCityAddress() <= 0) {
            return false;
        }

        if (request.getPersonId() <= 0) {
            return false;
        }

        if (request.getDepartmentAddress() <= 0) {
            return false;
        }

        if (request.getDepartmentExpedition() <= 0) {
            return false;
        }

        if (request.getCountryExpedition() == null || request.getCountryExpedition().equals("")) {
            return false;
        }

        if (request.getCountryNationality() == null || request.getCountryNationality().equals("")) {
            return false;
        }

        if (request.getCityExpedition() <= 0) {
            return false;
        }

        return true;
    }
}
