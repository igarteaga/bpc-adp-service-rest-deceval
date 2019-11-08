/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.pichincha.servicios.deceval.controller;

import co.com.pichincha.servicios.deceval.model.DecevalRequest;
import co.com.pichincha.servicios.deceval.model.DecevalResponse;
import co.com.pichincha.servicios.deceval.service.DecevalService;
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
                return new ResponseEntity(decevalResponse, HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage());
            decevalResponse.setResult(e.getMessage());
            return new ResponseEntity(decevalResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean ValidateDecevalFields(DecevalRequest request) {
        if (request == null) {
            return false;
        }

        if (request.getId() == null || request.getId().equals("")) {
            return false;
        }

        if (request.getIdType() == 0) {
            return false;
        }

        if (request.getNames() == null || request.getNames().equals("")) {
            return false;
        }

        if (request.getFirstLastName() == null || request.getFirstLastName().equals("")) {
            return false;
        }

        return true;
    }
}
