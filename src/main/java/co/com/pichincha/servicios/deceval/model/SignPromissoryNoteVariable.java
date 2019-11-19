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
public class SignPromissoryNoteVariable extends FlowVariable{

    private String clave;

    private int idDocumentoPagare;

    private short idRolFirmante;

    private String motivo;

    private String numeroDocumento;

    private short tipoDocumento;

    public String getClave() {
        return clave;
    }

    public int getIdDocumentoPagare() {
        return idDocumentoPagare;
    }

    public short getIdRolFirmante() {
        return idRolFirmante;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public short getTipoDocumento() {
        return tipoDocumento;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setIdDocumentoPagare(int idDocumentoPagare) {
        this.idDocumentoPagare = idDocumentoPagare;
    }

    public void setIdRolFirmante(short idRolFirmante) {
        this.idRolFirmante = idRolFirmante;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public void setTipoDocumento(short tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
}
