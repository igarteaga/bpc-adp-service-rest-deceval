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
import co.com.pichincha.servicios.deceval.model.PromissoryNoteResponse;
import co.com.pichincha.servicios.deceval.model.PromissoryNoteVariable;
import co.com.pichincha.servicios.deceval.model.SpinnerResponse;
import co.com.pichincha.servicios.deceval.model.SpinnerVariable;
import co.com.pichincha.servicios.deceval.service.DecevalService;
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
    @Value("${spinner.FlowId}")
    int spinnerFlowId;
    @Value("${spinner.User}")
    String spinnerUser;
    @Value("${spinner.Password}")
    String spinnerPassword;
    @Value("${spinner.Aplication}")
    String spinnerAplication;
    @Value("${spinner.ExecutionId}")
    short spinnerExecutionId;
    @Value("${spinner.Activity}")
    short spinnerActivity;
    @Value("${spinner.Section}")
    String spinnerSection;

    //Parametros de configuración servicio Crear Pagare (PromissoryNote)
    @Value("${promissoryNote.FlowId}")
    int promissoryNoteFlowId;
    @Value("${promissoryNote.User}")
    String promissoryNoteUser;
    @Value("${promissoryNote.Password}")
    String promissoryNotePassword;
    @Value("${promissoryNote.Aplication}")
    String promissoryNoteAplication;
    @Value("${promissoryNote.ExecutionId}")
    short promissoryNoteExecutionId;
    @Value("${promissoryNote.Activity}")
    short promissoryNoteActivity;
    @Value("${promissoryNote.Section}")
    String promissoryNoteSection;

    //Parametros fijos servicio Crear Pagare (PromissoryNote)
    @Value("${promissoryNote.ClassDefinitionDocument}")
    int promissoryNoteClassDefinitionDocument;
    @Value("${promissoryNote.TypeIWillPay}")
    short promissoryNoteTypeIWillPay;
    @Value("${promissoryNote.RefundableCredit}")
    short promissoryNoteRefundableCredit;
    @Value("${promissoryNote.ValuePesosDisbursement}")
    short promissoryNoteValuePesosDisbursement;
    @Value("${promissoryNote.InterestRate}")
    short promissoryNoteInterestRate;
    @Value("${promissoryNote.NumberInstallments}")
    short promissoryNoteNumberInstallments;
    @Value("${promissoryNote.ResponseMsm}")
    String promissoryNoteResponseMsm;
    @Value("${promissoryNote.SafeLife}")
    String promissoryNoteSafeLife;
    @Value("${promissoryNote.Content}")
    String promissoryNoteContent;
    @Value("${promissoryNote.FileName}")
    String promissoryNoteFileName;
    @Value("${promissoryNote.Country}")
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
                decevalResponse.setResult("OK");
            }
        } else {
            msmError = msmError == null || msmError.equals("") ? "Service response promissory note invalid" : msmError;
        }

        if (msmError == null || msmError.equals("")) {
            // Consumir servicio OTP
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
            variable.setNumPagareEntidad(decevalRequest.getToken());
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