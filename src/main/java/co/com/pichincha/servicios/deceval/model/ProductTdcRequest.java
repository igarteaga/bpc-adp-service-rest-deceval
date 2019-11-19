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
public class ProductTdcRequest {

    private int idPromissoryNote;

    private short idType;

    private String id;

    private String otpCode;

    public int getIdPromissoryNote() {
        return idPromissoryNote;
    }

    public short getIdType() {
        return idType;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public String getId() {
        return id;
    }

    public void setIdPromissoryNote(int idPromissoryNote) {
        this.idPromissoryNote = idPromissoryNote;
    }

    public void setIdType(short idType) {
        this.idType = idType;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setId(String id) {
        this.id = id;
    }
}
