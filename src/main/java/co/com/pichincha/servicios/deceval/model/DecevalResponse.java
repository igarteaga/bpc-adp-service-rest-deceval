/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.pichincha.servicios.deceval.model;

/**
 *
 * @author julgue221
 */
public class DecevalResponse {
    
    private int spinnerNumber;
    
    private String idPromissoryNote;
    
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    
    public String getIdPromissoryNote() {
        return idPromissoryNote;
    }

    public void setIdPromissoryNote(String idPromissoryNote) {
        this.idPromissoryNote = idPromissoryNote;
    }
    
    public int getSpinnerNumber() {
        return spinnerNumber;
    }

    public void setSpinnerNumber(int spinnerNumber) {
        this.spinnerNumber = spinnerNumber;
    }
}
