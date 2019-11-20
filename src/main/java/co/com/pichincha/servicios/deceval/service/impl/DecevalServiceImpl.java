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
import co.com.pichincha.servicios.deceval.service.ClientService;
import co.com.pichincha.servicios.deceval.service.DecevalService;
import co.com.pichincha.servicios.deceval.service.OtpService;
import co.com.pichincha.servicios.deceval.service.PromissoryNoteService;
import co.com.pichincha.servicios.deceval.service.SpinnerService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    @Autowired
    ClientService clientService;

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
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, msmError);
        }

        if (noteResponse != null) {
            if (noteResponse.getError() != null && !noteResponse.getError().equals("")) {
                msmError = noteResponse.getError();

            } else {
                decevalResponse.setIdPromissoryNote(noteResponse.getIdPromissoryNote());
                decevalResponse.setSpinnerNumber(spinnerResponse.getSpinnerNumber());
                request.getClient().setSpinnerNumber(Integer.toString(spinnerResponse.getSpinnerNumber()));
                request.getClient().setIdDocumentPayment(noteResponse.getIdPromissoryNote());
            }
        } else {
            msmError = msmError == null || msmError.equals("") ? "Service response promissory note invalid" : msmError;
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, msmError);
        }

        if (msmError == null || msmError.equals("")) {
            // Consumir servicio OTP
            OtpRequest otpRequest = new OtpRequest();
            otpRequest.setCellPhone(Long.toString(request.getClient().getPhoneNumber()));
            otpRequest.setEmail(request.getClient().getEmail());
            otpRequest.setFullName(request.getNames());
            otpRequest.setId(Long.toString(request.getClient().getIdClient()));
            otpRequest.setValidateAttempts(true);
            OtpResponse otpResponse = otpService.sendOtp(otpRequest);
            if (otpResponse != null) {
                if ((otpResponse.getError() == null || otpResponse.getError().equals("")) && otpResponse.isSuccessful()) {
                    decevalResponse.setResult("OK");
                    //Guardar respuesta correcta
                    try {
                        clientService.saveClient(request.getClient());
                    } catch (Exception e) {
                        Logger.getLogger(this.getClass().getName()).log(Level.WARNING, e.getMessage());
                    }

                } else {
                    decevalResponse.setResult(otpResponse.getError());
                }
            } else {
                decevalResponse.setResult("Service response otp invalid");
                Logger.getLogger(this.getClass().getName()).log(Level.WARNING, decevalResponse.getResult());
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

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            variable.setCorreoElectronico(decevalRequest.getClient().getEmail());
            variable.setEstadoCivil(decevalRequest.getCivilStatus());
            variable.setFechaExpedicion_Nat(formatter.format(decevalRequest.getClient().getDocumentDateIssuance()));
            variable.setFechaNacimiento_Nat(formatter.format(decevalRequest.getClient().getDateBirthday()));
            variable.setFkIdCiudadDomicilio_Nat(Integer.parseInt(decevalRequest.getClient().getResidenceCity()));
            variable.setFkIdCiudadExpedicion_Nat(Integer.parseInt(decevalRequest.getClient().getDocumentIssuePlaceCity()));
            variable.setFkIdClasePersona(decevalRequest.getPersonId());
            variable.setFkIdDepartamentoDomicilio_Nat(Short.parseShort(decevalRequest.getClient().getResidenceState()));
            variable.setFkIdDepartamentoExpedicion_Nat(Short.parseShort(decevalRequest.getClient().getDocumentIssuePlaceState()));
            variable.setFkIdPaisDomicilio_Nat(decevalRequest.getCountryAddress());
            variable.setFkIdPaisExpedicion_Nat(decevalRequest.getCountryExpedition());
            variable.setFkIdPaisNacionalidad_Nat(decevalRequest.getCountryNationality());
            variable.setFkIdTipoDocumento(Short.parseShort(decevalRequest.getClient().getIdDocumentType()));
            variable.setIdentificacionEmisor(decevalRequest.getIssuerId() == null || decevalRequest.getIssuerId().equals("") ? pichinchaIssuerId : decevalRequest.getIssuerId());
            variable.setNumeroCelular(Long.toString(decevalRequest.getClient().getPhoneNumber()));
            variable.setNombresNat_Nat(decevalRequest.getNames());
            variable.setNumeroDocumento(Long.toString(decevalRequest.getClient().getIdClient()));
            variable.setPensionado(decevalRequest.getPensioner());
            variable.setPrimerApellido_Nat(decevalRequest.getClient().getLastNameClient());
            variable.setSalario(decevalRequest.getClient().getMonthlyIncome());
            variable.setSegundoApellido_Nat(decevalRequest.getClient().getSecondLastNameClient());
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
            variable.setNumPagareEntidad(Long.toString(decevalRequest.getClient().getIdProcess()));
            variable.setFechaDesembolso(now);
            variable.setOtorganteTipoId(Short.parseShort(decevalRequest.getClient().getIdDocumentType()));
            variable.setOtorganteNumId(Long.toString(decevalRequest.getClient().getIdClient()));
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
            variable.setDepartamento(Integer.parseInt(decevalRequest.getClient().getResidenceState()));
            variable.setCiudadDesembolso(Integer.parseInt(decevalRequest.getClient().getResidenceCity()));

            flowVariables.setVariable(variable);

            listFlowVariables.add(flowVariables);

            flow.setVariables(listFlowVariables);
        }

        return flow;
    }
}
