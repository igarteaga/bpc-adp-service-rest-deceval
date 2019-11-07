/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.pichincha.servicios.deceval.service.impl;

import co.com.pichincha.servicios.deceval.bean.DecevalUtil;
import co.com.pichincha.servicios.deceval.model.FlowRequest;
import co.com.pichincha.servicios.deceval.model.FlowResponse;
import co.com.pichincha.servicios.deceval.model.SpinnerResponse;
import co.com.pichincha.servicios.deceval.service.HttpsService;
import co.com.pichincha.servicios.deceval.service.SpinnerService;
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
public class SpinnerServiceImpl implements SpinnerService {

    @Value("${service.url.experian}")
    String urlService;

    @Autowired
    HttpsService httpsService;

    public SpinnerResponse CreateSpinner(FlowRequest request) throws Exception {
        FlowResponse[] responseService = httpsService.postHttps(FlowResponse[].class, request, this.urlService);
        SpinnerResponse response = new SpinnerResponse();
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
                    if (split1[i].indexOf("</cuentaGirador") != -1) {
                        String txt = split1[i];
                        String spinnerNumber = DecevalUtil.ObtenerValorEtiquetaCierre(txt, "</cuentaGirador");
                        response.setSpinnerNumber(Integer.parseInt(spinnerNumber));
                    }
                }
            }

            if (!successfulResponse) {
                String msm;
                if (description == null) {
                    msm = "Service response " + this.urlService + ", CreateSpinner; Error: invalid response body; Result: " + responseService[0].getResultado();
                } else {
                    msm = "Service response " + this.urlService + ", CreateSpinner; Error: " + description + "; Result: " + responseService[0].getResultado();
                }

                response.setError(msm);
                Logger.getLogger(this.getClass().getName()).log(Level.WARNING, msm);
            }
        } else {
            if (responseService != null) {
                String msm;
                msm = "Service response " + this.urlService + ", CreateSpinner; Error:" + responseService[0].getMensaje() + "; Result: " + responseService[0].getResultado();
                response.setError(msm);
                Logger.getLogger(this.getClass().getName()).log(Level.WARNING, msm);
            }
        }

        return response;
    }
}
