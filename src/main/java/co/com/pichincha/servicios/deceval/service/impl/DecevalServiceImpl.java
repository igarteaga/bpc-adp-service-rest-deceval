/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.pichincha.servicios.deceval.service.impl;

import co.com.pichincha.servicios.deceval.model.DecevalRequest;
import co.com.pichincha.servicios.deceval.model.DecevalResponse;
import co.com.pichincha.servicios.deceval.model.FlowRequest;
import co.com.pichincha.servicios.deceval.model.FlowVariables;
import co.com.pichincha.servicios.deceval.model.OtpRequest;
import co.com.pichincha.servicios.deceval.model.OtpResponse;
import co.com.pichincha.servicios.deceval.model.PromissoryNoteResponse;
import co.com.pichincha.servicios.deceval.model.PromissoryNoteVariable;
import co.com.pichincha.servicios.deceval.model.SpinnerResponse;
import co.com.pichincha.servicios.deceval.model.SpinnerVariable;
import co.com.pichincha.servicios.deceval.service.DecevalService;
import co.com.pichincha.servicios.deceval.service.OtpService;
import co.com.pichincha.servicios.deceval.service.PromissoryNoteService;
import co.com.pichincha.servicios.deceval.service.SpinnerService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 *
 * @author julgue221
 */
@Service
public class DecevalServiceImpl implements DecevalService {

    //Parametros de configuración servicio Crear Girador (Spinner)
    @Value("${spinner.flowId}")
    int spinnerFlowId;
    @Value("${spinner.user}")
    String spinnerUser;
    @Value("${spinner.password}")
    String spinnerPassword;
    @Value("${spinner.aplication}")
    String spinnerAplication;
    @Value("${spinner.executionId}")
    short spinnerExecutionId;
    @Value("${spinner.activity}")
    short spinnerActivity;
    @Value("${spinner.section}")
    String spinnerSection;

    //Parametros de configuración servicio Crear Pagare (PromissoryNote)
    @Value("${promissoryNote.flowId}")
    int promissoryNoteFlowId;
    @Value("${promissoryNote.user}")
    String promissoryNoteUser;
    @Value("${promissoryNote.password}")
    String promissoryNotePassword;
    @Value("${promissoryNote.aplication}")
    String promissoryNoteAplication;
    @Value("${promissoryNote.executionId}")
    short promissoryNoteExecutionId;
    @Value("${promissoryNote.activity}")
    short promissoryNoteActivity;
    @Value("${promissoryNote.section}")
    String promissoryNoteSection;

    //Parametros fijos servicio Crear Pagare (PromissoryNote)
    @Value("${promissoryNote.classDefinitionDocument}")
    int promissoryNoteClassDefinitionDocument;
    @Value("${promissoryNote.typeIWillPay}")
    short promissoryNoteTypeIWillPay;
    @Value("${promissoryNote.refundableCredit}")
    short promissoryNoteRefundableCredit;
    @Value("${promissoryNote.valuePesosDisbursement}")
    short promissoryNoteValuePesosDisbursement;
    @Value("${promissoryNote.interestRate}")
    short promissoryNoteInterestRate;
    @Value("${promissoryNote.numberInstallments}")
    short promissoryNoteNumberInstallments;
    @Value("${promissoryNote.responseMsm}")
    String promissoryNoteResponseMsm;
    @Value("${promissoryNote.safeLife}")
    String promissoryNoteSafeLife;
    @Value("${promissoryNote.content}")
    String promissoryNoteContent;
    @Value("${promissoryNote.fileName}")
    String promissoryNoteFileName;
    @Value("${promissoryNote.country}")
    String promissoryNoteCountry;
    
    //Url del servicio de experian
    @Value("${service.url.experian}")
    String urlService;

    //Nit del banco
    @Value("${pichincha.IssuerId}")
    String pichinchaIssuerId;

    @Autowired
    SpinnerService spinnerService;

    @Autowired
    PromissoryNoteService promissoryNoteService;
    
    @Autowired
    OtpService otpService;

    public DecevalResponse decevalCreate(DecevalRequest request) throws Exception {
        DecevalResponse decevalResponse = new DecevalResponse();
        PromissoryNoteResponse noteResponse = null;
        FlowRequest flowRequest = this.MapingFlowRequestSpinner(request);
        SpinnerResponse spinnerResponse = spinnerService.CreateSpinner(flowRequest);
        
        String msmError = null;
        if (spinnerResponse != null) {
            if (spinnerResponse.getError() != null && !spinnerResponse.getError().equals("")) {
                msmError = spinnerResponse.getError();
            } else {
                FlowRequest flowRequest1 = this.MapingFlowRequestPromissoryNote(spinnerResponse, request);
                noteResponse = promissoryNoteService.createPromissoryNote(flowRequest1);
            }
        } else {
            msmError = "Service response spinner invalid";
        }

        if (noteResponse != null) {
            if (noteResponse.getError() != null && !noteResponse.getError().equals("")) {
                msmError = noteResponse.getError();

            } else {
                decevalResponse.setIdPromissoryNote(noteResponse.getIdPromissoryNote());
                decevalResponse.setSpinnerNumber(spinnerResponse.getSpinnerNumber());
            }
        } else {
            msmError = msmError == null || msmError.equals("") ? "Service response promissory note invalid" : msmError;
        }

        if (msmError == null || msmError.equals("")) {
            // Consumir servicio OTP
            OtpRequest otpRequest = new OtpRequest();
            otpRequest.setCellPhone(request.getCellPhone());
            otpRequest.setEmail(request.getEmail());
            otpRequest.setFullName(request.getNames());
            otpRequest.setId(request.getId());
            OtpResponse otpResponse = otpService.sendOtp(otpRequest);
            if (otpResponse != null) {
                if ((otpResponse.getError() == null || otpResponse.getError().equals("")) && otpResponse.isSuccessful()) {
                    //Guardar respuesta correcta
                    decevalResponse.setResult("OK");
                } else {
                    decevalResponse.setResult(otpResponse.getError());
                }
            } else {
                decevalResponse.setResult("Service response otp invalid");
            }
        } else {
            decevalResponse.setResult(msmError);
        }

        return decevalResponse;
    }

    private FlowRequest MapingFlowRequestSpinner(DecevalRequest decevalRequest) {
        FlowRequest flow = new FlowRequest();
        if (decevalRequest != null) {
            flow.setFlujoId(spinnerFlowId);
            flow.setUsuario(spinnerUser);
            flow.setClave(spinnerPassword);
            flow.setAplicativo(spinnerAplication);
            flow.setIdEjecucion(spinnerExecutionId);
            flow.setIdActividadInicio(spinnerActivity);

            FlowVariables flowVariables = new FlowVariables();
            List<FlowVariables> listFlowVariables = new ArrayList<FlowVariables>();
            flowVariables.setSeccion(spinnerSection);
            SpinnerVariable variable = new SpinnerVariable();

            variable.setCorreoElectronico(decevalRequest.getEmail());
            variable.setEstadoCivil(decevalRequest.getCivilStatus());
            variable.setFechaExpedicion_Nat(decevalRequest.getExpeditionDate());
            variable.setFechaNacimiento_Nat(decevalRequest.getBirthDate());
            variable.setFkIdCiudadDomicilio_Nat(decevalRequest.getCityAddress());
            variable.setFkIdCiudadExpedicion_Nat(decevalRequest.getCityExpedition());
            variable.setFkIdClasePersona(decevalRequest.getPersonId());
            variable.setFkIdDepartamentoDomicilio_Nat(decevalRequest.getDepartmentAddress());
            variable.setFkIdDepartamentoExpedicion_Nat(decevalRequest.getDepartmentExpedition());
            variable.setFkIdPaisDomicilio_Nat(decevalRequest.getCountryAddress());
            variable.setFkIdPaisExpedicion_Nat(decevalRequest.getCountryExpedition());
            variable.setFkIdPaisNacionalidad_Nat(decevalRequest.getCountryNationality());
            variable.setFkIdTipoDocumento(decevalRequest.getIdType());
            variable.setIdentificacionEmisor(decevalRequest.getIssuerId() == null || decevalRequest.getIssuerId().equals("") ? pichinchaIssuerId : decevalRequest.getIssuerId());
            variable.setNumeroCelular(decevalRequest.getCellPhone());
            variable.setNombresNat_Nat(decevalRequest.getNames());
            variable.setNumeroDocumento(decevalRequest.getId());
            variable.setPensionado(decevalRequest.getPensioner());
            variable.setPrimerApellido_Nat(decevalRequest.getFirstLastName());
            variable.setSalario(decevalRequest.getSalary());
            variable.setSegundoApellido_Nat(decevalRequest.getSecondLastName());
            variable.setTiempoServicio(decevalRequest.getTimeService());

            flowVariables.setVariable(variable);

            listFlowVariables.add(flowVariables);

            flow.setVariables(listFlowVariables);
        }

        return flow;
    }

    private FlowRequest MapingFlowRequestPromissoryNote(SpinnerResponse spinnerResponse, DecevalRequest decevalRequest) {
        FlowRequest flow = new FlowRequest();
        if (spinnerResponse != null) {
            flow.setFlujoId(promissoryNoteFlowId);
            flow.setUsuario(promissoryNoteUser);
            flow.setClave(promissoryNotePassword);
            flow.setAplicativo(promissoryNoteAplication);
            flow.setIdEjecucion(promissoryNoteExecutionId);
            flow.setIdActividadInicio(promissoryNoteActivity);

            FlowVariables flowVariables = new FlowVariables();
            List<FlowVariables> listFlowVariables = new ArrayList<FlowVariables>();
            flowVariables.setSeccion(promissoryNoteSection);
            PromissoryNoteVariable variable = new PromissoryNoteVariable();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String now = formatter.format(new Date());
            variable.setNitEmisor(pichinchaIssuerId);
            variable.setIdClaseDefinicionDocumento(promissoryNoteClassDefinitionDocument);
            variable.setFechaGrabacionPagare(now);
            variable.setTipoPagare(promissoryNoteTypeIWillPay);
            variable.setNumPagareEntidad(decevalRequest.getRequestNumber());
            variable.setFechaDesembolso(now);
            variable.setOtorganteTipoId(decevalRequest.getIdType());
            variable.setOtorganteNumId(decevalRequest.getId());
            variable.setOtorganteCuenta(spinnerResponse.getSpinnerNumber());
            variable.setCreditoReembolsableEn(promissoryNoteRefundableCredit);
            variable.setValorPesosDesembolso(promissoryNoteValuePesosDisbursement);
            variable.setTasaInteres(promissoryNoteInterestRate);
            variable.setFechaPagoPrimerCuota(now);
            variable.setNumCuotas(promissoryNoteNumberInstallments);
            variable.setMensajeRespuesta(promissoryNoteResponseMsm);
            variable.setSeguroVida(promissoryNoteSafeLife);
            variable.setNombreArchivo(promissoryNoteFileName);
            variable.setContenido(promissoryNoteContent);
            variable.setPais(promissoryNoteCountry);
            variable.setDepartamento(decevalRequest.getDepartmentAddress());
            variable.setCiudadDesembolso(decevalRequest.getCityAddress());

            flowVariables.setVariable(variable);

            listFlowVariables.add(flowVariables);

            flow.setVariables(listFlowVariables);
        }

        return flow;
    }
}
