/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.pichincha.servicios.deceval.service.impl;

import co.com.pichincha.servicios.deceval.model.FlowRequest;
import co.com.pichincha.servicios.deceval.model.FlowVariables;
import co.com.pichincha.servicios.deceval.model.ProductTdcRequest;
import co.com.pichincha.servicios.deceval.model.ProductTdcResponse;
import co.com.pichincha.servicios.deceval.model.SignPromissoryNoteResponse;
import co.com.pichincha.servicios.deceval.model.SignPromissoryNoteVariable;
import co.com.pichincha.servicios.deceval.model.ValidateOtpRequest;
import co.com.pichincha.servicios.deceval.model.ValidateOtpResponse;
import co.com.pichincha.servicios.deceval.service.OtpService;
import org.springframework.stereotype.Service;
import co.com.pichincha.servicios.deceval.service.ProductTdcService;
import co.com.pichincha.servicios.deceval.service.SignPromissoryNoteService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author julgue221
 */
@Service
public class ProductTdcServiceImpl implements ProductTdcService {

    //Parametros de configuración servicio Firmar Pagare (SignPromissoryNote)
    @Value("${signPromissoryNote.flowId}")
    int signPromissoryNoteFlowId;
    @Value("${signPromissoryNote.user}")
    String signPromissoryNoteUser;
    @Value("${signPromissoryNote.password}")
    String signPromissoryNotePassword;
    @Value("${signPromissoryNote.aplication}")
    String signPromissoryNoteAplication;
    @Value("${signPromissoryNote.executionId}")
    short signPromissoryNoteExecutionId;
    @Value("${signPromissoryNote.activity}")
    short signPromissoryNoteActivity;
    @Value("${signPromissoryNote.section}")
    String signPromissoryNoteSection;

    //Datos fijos para el servicio de Firmar Pagare (SignPromissoryNote)
    @Value("${signPromissoryNote.passwordSection}")
    String signPromissoryNotePasswordSection;
    @Value("${signPromissoryNote.idRoleSignatory}")
    short signPromissoryNoteIdRoleSignatory;
    @Value("${signPromissoryNote.reason}")
    String signPromissoryNoteReason;

    @Autowired
    SignPromissoryNoteService spns;

    @Autowired
    OtpService otpService;

    @Override
    public ProductTdcResponse CreateProduct(ProductTdcRequest request) throws Exception {

        ProductTdcResponse productTdcResponse = new ProductTdcResponse();
        productTdcResponse.setOtpValid(false);
        ValidateOtpRequest otpRequest = new ValidateOtpRequest();
        SignPromissoryNoteResponse noteResponse = null;

        otpRequest.setId(request.getId());
        otpRequest.setOtpCode(request.getOtpCode());

        ValidateOtpResponse otpResponse = otpService.validateOtp(otpRequest);
        if (otpResponse != null) {
            if (!otpResponse.getIsValid() && otpResponse.getError() != null && otpResponse.getError() != "") {
                productTdcResponse.setError(otpResponse.getError());
            } else {
                productTdcResponse.setOtpValid(true);
                FlowRequest flowRequest = this.MapingFlowRequestSpinner(request);
                noteResponse = spns.signPromissoryNote(flowRequest);

                if (noteResponse != null) {
                    if (!noteResponse.getSuccessful()
                            && noteResponse.getError() != null
                            && noteResponse.getError() != "") {
                        productTdcResponse.setError(noteResponse.getError());
                    } else {
                        //Sigue el flujo de creación de archivos
                    }
                }
            }
        }

        FlowRequest flowRequest = this.MapingFlowRequestSpinner(request);
        noteResponse = spns.signPromissoryNote(flowRequest);

        return productTdcResponse;
    }

    private FlowRequest MapingFlowRequestSpinner(ProductTdcRequest productTdcRequest) {
        FlowRequest flow = new FlowRequest();
        if (productTdcRequest != null) {
            flow.setFlujoId(signPromissoryNoteFlowId);
            flow.setUsuario(signPromissoryNoteUser);
            flow.setClave(signPromissoryNotePassword);
            flow.setAplicativo(signPromissoryNoteAplication);
            flow.setIdEjecucion(signPromissoryNoteExecutionId);
            flow.setIdActividadInicio(signPromissoryNoteActivity);

            FlowVariables flowVariables = new FlowVariables();
            List<FlowVariables> listFlowVariables = new ArrayList<FlowVariables>();
            flowVariables.setSeccion(signPromissoryNoteSection);
            SignPromissoryNoteVariable variable = new SignPromissoryNoteVariable();

            variable.setClave(signPromissoryNotePasswordSection);
            variable.setIdDocumentoPagare(productTdcRequest.getIdPromissoryNote());
            variable.setIdRolFirmante(signPromissoryNoteIdRoleSignatory);
            variable.setMotivo(signPromissoryNoteReason);
            variable.setNumeroDocumento(productTdcRequest.getId());
            variable.setTipoDocumento(productTdcRequest.getIdType());

            flowVariables.setVariable(variable);

            listFlowVariables.add(flowVariables);

            flow.setVariables(listFlowVariables);
        }

        return flow;
    }
}
