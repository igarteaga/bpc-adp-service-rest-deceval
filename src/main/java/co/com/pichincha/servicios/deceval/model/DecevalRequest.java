/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.pichincha.servicios.deceval.model;

import java.util.Date;

/**
 *
 * @author julgue221
 */
public class DecevalRequest {

    private short civilStatus;
    
    private short personId;
        
    private String countryAddress;
    
    private String countryExpedition;
    
    private String countryNationality;
    
    private String issuerId;
    
    private String names;
    
    private String pensioner;
       
    private String timeService;
     
    private ClientRequest client;
   
    public ClientRequest getClient() {
        return client;
    }

    public void setClient(ClientRequest client) {
        this.client = client;
    }
    
    public short getCivilStatus() {
        return civilStatus;
    }

    public short getPersonId() {
        return personId;
    }

    public String getCountryAddress() {
        return countryAddress;
    }

    public String getCountryExpedition() {
        return countryExpedition;
    }

    public String getCountryNationality() {
        return countryNationality;
    }

    public String getIssuerId() {
        return issuerId;
    }

    public String getNames() {
        return names;
    }

    public String getPensioner() {
        return pensioner;
    }

    public String getTimeService() {
        return timeService;
    }

    public void setCivilStatus(short civilStatus) {
        this.civilStatus = civilStatus;
    }

    public void setPersonId(short personId) {
        this.personId = personId;
    }

    public void setCountryAddress(String countryAddress) {
        this.countryAddress = countryAddress;
    }

    public void setCountryExpedition(String countryExpedition) {
        this.countryExpedition = countryExpedition;
    }

    public void setCountryNationality(String countryNationality) {
        this.countryNationality = countryNationality;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }

    public void setNames(String names) {
        this.names = names;
    }
    
    public void setPensioner(String pensioner) {
        this.pensioner = pensioner;
    }

    public void setTimeService(String timeService) {
        this.timeService = timeService;
    } 
}