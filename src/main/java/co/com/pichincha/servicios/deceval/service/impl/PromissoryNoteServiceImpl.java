/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.pichincha.servicios.deceval.service.impl;

import co.com.pichincha.servicios.deceval.bean.DecevalUtil;
import co.com.pichincha.servicios.deceval.model.FlowRequest;
import co.com.pichincha.servicios.deceval.model.FlowResponse;
import co.com.pichincha.servicios.deceval.model.PromissoryNoteResponse;
import co.com.pichincha.servicios.deceval.service.HttpsService;
import co.com.pichincha.servicios.deceval.service.PromissoryNoteService;
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
public class PromissoryNoteServiceImpl implements PromissoryNoteService {

    @Value("${service.url.experian}")
    String urlService;

    @Autowired
    HttpsService httpsService;

    public PromissoryNoteResponse createPromissoryNote(FlowRequest request) throws Exception {
        FlowResponse[] responseService = httpsService.postHttps(FlowResponse[].class, request, this.urlService);

        PromissoryNoteResponse noteResponse = new PromissoryNoteResponse();
        if (responseService != null && responseService[0].getMensaje().equals("Servicio ejecutó correctamente")) {
            String responseReplace = responseService[0].getResultado().replace("\\n", "").replace("\\", "").replace("&gt;", ">").replace("&amp;", "<").replace("lt;", "").replace("<amp;quot;", "'").replace("<#209;", "Ñ");

            String[] split1 = responseReplace.split(">");
            String description = null;
            boolean successfulResponse = false;
            for (int i = 0; i < split1.length; i++) {
                if (split1[i].indexOf("</descripcion") != -1) {
                    String txt = split1[i];
                    description = DecevalUtil.ObtenerValorEtiquetaCierre(txt, "</descripcion");
                }

                if (split1[i].indexOf("</exitoso") != -1) {
                    String txt = split1[i];
                    String success = DecevalUtil.ObtenerValorEtiquetaCierre(txt, "</exitoso");
                    if (success != null
                            && Boolean.parseBoolean(success)
                            && description != null) {
                        successfulResponse = true;
                    }
                }
                
                if (split1[i].indexOf("</mensajeRespuesta") != -1) {
                    String txt = split1[i];
                    String msm = DecevalUtil.ObtenerValorEtiquetaCierre(txt, "</mensajeRespuesta");
                    description = msm != null ? msm : description; 
                }

                if (successfulResponse) {
                    if (split1[i].indexOf("</idDocumentoPagare") != -1) {
                        String txt = split1[i];
                        String idPromissoryNote = DecevalUtil.ObtenerValorEtiquetaCierre(txt, "</idDocumentoPagare");
                        noteResponse.setIdPromissoryNote(idPromissoryNote);
                    }
                }
            }

            if (!successfulResponse) {
                String msm;
                if (description == null) {
                    msm = "Service response " + this.urlService + ", createPromissoryNote; Error: invalid response body; Result: " + responseService[0].getResultado();
                } else {
                    msm = "Service response " + this.urlService + ", createPromissoryNote; Error: " + description + "; Result: " + responseService[0].getResultado();
                }

                noteResponse.setError(msm);
                Logger.getLogger(this.getClass().getName()).log(Level.WARNING, msm);
            }
        } else {
            if (responseService != null) {
                String msm;
                msm = "Service response " + this.urlService + ", createPromissoryNote; Error:" + responseService[0].getMensaje() + "; Result: " + responseService[0].getResultado();
                noteResponse.setError(msm);
                Logger.getLogger(this.getClass().getName()).log(Level.WARNING, msm);
            }
        }

        return noteResponse;
    }
}
