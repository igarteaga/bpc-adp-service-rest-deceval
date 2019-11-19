/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.pichincha.servicios.deceval.service.impl;

import co.com.pichincha.servicios.deceval.bean.DecevalUtil;
import co.com.pichincha.servicios.deceval.model.FlowRequest;
import co.com.pichincha.servicios.deceval.model.FlowResponse;
import co.com.pichincha.servicios.deceval.model.SignPromissoryNoteResponse;
import co.com.pichincha.servicios.deceval.service.HttpsService;
import co.com.pichincha.servicios.deceval.service.SignPromissoryNoteService;
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
public class SignPromissoryNoteServiceImpl implements SignPromissoryNoteService {

    @Value("${service.url.experian}")
    String urlService;

    @Autowired
    HttpsService httpsService;

    @Override
    public SignPromissoryNoteResponse signPromissoryNote(FlowRequest request) throws Exception {
        FlowResponse[] responseService = httpsService.postHttps(FlowResponse[].class, request, this.urlService);
        SignPromissoryNoteResponse signPromissoryNoteResponse = new SignPromissoryNoteResponse();
        signPromissoryNoteResponse.setSuccessful(false);
        if (responseService != null && responseService[0].getMensaje().equals("Servicio ejecutó correctamente")) {
            String responseReplace = responseService[0].getResultado().replace("\\n", "").replace("\\", "").replace("&gt;", ">").replace("&amp;", "<").replace("lt;", "").replace("<amp;quot;", "'").replace("<#209;", "Ñ");

            String[] split1 = responseReplace.split(">");
            String error = null;
            boolean successfulResponse = false;
            for (int i = 0; i < split1.length; i++) {
                if (split1[i].indexOf("</descripcion") != -1) {
                    String txt = split1[i];
                    String description = DecevalUtil.ObtenerValorEtiquetaCierre(txt, "</descripcion");
                    if (description != null) {
                        if (description.contains("Exitoso")) {
                            signPromissoryNoteResponse.setSuccessful(true);
                            successfulResponse = true;
                        } else {
                            error = description;
                        }
                    }else
                    {
                        error = "Description service response empty";
                    }
                }
            }

            if (!successfulResponse) {
                String msm;
                if (error == null) {
                    msm = "Service response " + this.urlService + ", signPromissoryNote; Error: invalid response body; Result: " + responseService[0].getResultado();
                } else {
                    msm = "Service response " + this.urlService + ", signPromissoryNote; Error: " + error + "; Result: " + responseService[0].getResultado();
                }

                signPromissoryNoteResponse.setError(msm);
                Logger.getLogger(this.getClass().getName()).log(Level.WARNING, msm);
            }
        }else {
            if (responseService != null) {
                String msm;
                msm = "Service response " + this.urlService + ", signPromissoryNote; Error:" + responseService[0].getMensaje() + "; Result: " + responseService[0].getResultado();
                signPromissoryNoteResponse.setError(msm);
                Logger.getLogger(this.getClass().getName()).log(Level.WARNING, msm);
            }
        }

        return signPromissoryNoteResponse;
    }
}
