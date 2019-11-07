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
public class PromissoryNoteVariable extends FlowVariable {

    private String nitEmisor;

    private int idClaseDefinicionDocumento;

    private String fechaGrabacionPagare;

    private short tipoPagare;

    private String numPagareEntidad;

    private String fechaDesembolso;

    private short otorganteTipoId;

    private String otorganteNumId;

    private int otorganteCuenta;

    private short creditoReembolsableEn;

    private double valorPesosDesembolso;

    private int ciudadDesembolso;

    private int departamento;

    private String pais;

    private double tasaInteres;

    private String fechaPagoPrimerCuota;

    private short numCuotas;

    private String mensajeRespuesta;

    private String seguroVida;

    private String nombreArchivo;

    private String contenido;

    public void setFechaPagoPrimerCuota(String fechaPagoPrimerCuota) {
        this.fechaPagoPrimerCuota = fechaPagoPrimerCuota;
    }

    public void setMensajeRespuesta(String mensajeRespuesta) {
        this.mensajeRespuesta = mensajeRespuesta;
    }

    public String getFechaPagoPrimerCuota() {
        return fechaPagoPrimerCuota;
    }

    public String getMensajeRespuesta() {
        return mensajeRespuesta;
    }

    public String getNitEmisor() {
        return nitEmisor;
    }

    public int getIdClaseDefinicionDocumento() {
        return idClaseDefinicionDocumento;
    }

    public String getFechaGrabacionPagare() {
        return fechaGrabacionPagare;
    }

    public short getTipoPagare() {
        return tipoPagare;
    }

    public String getNumPagareEntidad() {
        return numPagareEntidad;
    }

    public String getFechaDesembolso() {
        return fechaDesembolso;
    }

    public short getOtorganteTipoId() {
        return otorganteTipoId;
    }

    public String getOtorganteNumId() {
        return otorganteNumId;
    }

    public int getOtorganteCuenta() {
        return otorganteCuenta;
    }

    public short getCreditoReembolsableEn() {
        return creditoReembolsableEn;
    }

    public double getValorPesosDesembolso() {
        return valorPesosDesembolso;
    }

    public int getCiudadDesembolso() {
        return ciudadDesembolso;
    }

    public int getDepartamento() {
        return departamento;
    }

    public String getPais() {
        return pais;
    }

    public double getTasaInteres() {
        return tasaInteres;
    }

    public short getNumCuotas() {
        return numCuotas;
    }

    public String getSeguroVida() {
        return seguroVida;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setNitEmisor(String nitEmisor) {
        this.nitEmisor = nitEmisor;
    }

    public void setIdClaseDefinicionDocumento(int idClaseDefinicionDocumento) {
        this.idClaseDefinicionDocumento = idClaseDefinicionDocumento;
    }

    public void setFechaGrabacionPagare(String fechaGrabacionPagare) {
        this.fechaGrabacionPagare = fechaGrabacionPagare;
    }

    public void setTipoPagare(short tipoPagare) {
        this.tipoPagare = tipoPagare;
    }

    public void setNumPagareEntidad(String numPagareEntidad) {
        this.numPagareEntidad = numPagareEntidad;
    }

    public void setFechaDesembolso(String fechaDesembolso) {
        this.fechaDesembolso = fechaDesembolso;
    }

    public void setOtorganteTipoId(short otorganteTipoId) {
        this.otorganteTipoId = otorganteTipoId;
    }

    public void setOtorganteNumId(String otorganteNumId) {
        this.otorganteNumId = otorganteNumId;
    }

    public void setOtorganteCuenta(int otorganteCuenta) {
        this.otorganteCuenta = otorganteCuenta;
    }

    public void setCreditoReembolsableEn(short creditoReembolsableEn) {
        this.creditoReembolsableEn = creditoReembolsableEn;
    }

    public void setValorPesosDesembolso(double valorPesosDesembolso) {
        this.valorPesosDesembolso = valorPesosDesembolso;
    }

    public void setCiudadDesembolso(int ciudadDesembolso) {
        this.ciudadDesembolso = ciudadDesembolso;
    }

    public void setDepartamento(int departamento) {
        this.departamento = departamento;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setTasaInteres(double tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public void setNumCuotas(short numCuotas) {
        this.numCuotas = numCuotas;
    }

    public void setSeguroVida(String seguroVida) {
        this.seguroVida = seguroVida;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
