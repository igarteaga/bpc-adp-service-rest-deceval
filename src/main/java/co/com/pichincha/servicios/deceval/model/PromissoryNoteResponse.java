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
public class PromissoryNoteResponse {

    private String idPromissoryNote;
    
    private String error;
    
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    public String getIdPromissoryNote() {
        return idPromissoryNote;
    }

    public void setIdPromissoryNote(String idPromissoryNote) {
        this.idPromissoryNote = idPromissoryNote;
    }
}
