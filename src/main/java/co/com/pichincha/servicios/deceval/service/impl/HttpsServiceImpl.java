/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.pichincha.servicios.deceval.service.impl;

import co.com.pichincha.servicios.deceval.service.HttpsService;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author julgue221
 */
@Service
public class HttpsServiceImpl implements HttpsService {

    @Value("${service.keystore.store}")
    String sslStore;

    @Value("${service.keystore.type}")
    String sslType;

    @Value("${service.keystore.storepass}")
    String sslStorepass;

    @Value("${service.keystore.keypass}")
    String sslKeypass;

    private HttpClient initCustomSSL() {
        try {
            KeyStore keyStore = KeyStore.getInstance(sslType);
            Path p = Paths.get("").toAbsolutePath().resolve(sslStore);
            keyStore.load(new FileInputStream(p.toFile()), sslStorepass.toCharArray());
            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
                    new SSLContextBuilder().loadTrustMaterial(keyStore, new TrustSelfSignedStrategy())
                            .loadKeyMaterial(keyStore, sslKeypass.toCharArray()).build()
            );
            System.setProperty("javax.net.ssl.trustStore", sslStore);
            System.setProperty("javax.net.ssl.trustStorePassword", sslStorepass);
            HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
            return httpClient;
        } catch (KeyStoreException ex) {
            System.out.println("----->    KeyStoreException ");
            Logger.getLogger(HttpsService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            System.out.println("----->    FileNotFoundException <------");
            Logger.getLogger(HttpsService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("----->    IOException <------");
            Logger.getLogger(HttpsService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("----->    NoSuchAlgorithmException <------");
            Logger.getLogger(HttpsService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            System.out.println("----->    CertificateException <------");
            Logger.getLogger(HttpsService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnrecoverableKeyException ex) {
            System.out.println("----->    UnrecoverableKeyException <------");
            Logger.getLogger(HttpsService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyManagementException ex) {
            System.out.println("----->    KeyManagementException <------");
            Logger.getLogger(HttpsService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Bean
    public RestTemplate restTemplate() {
        HttpClient httpClient = this.initCustomSSL();
        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }

    @Override
    public <U, V extends Object> U getHttps(Class<U> cls, V request, String urlService) throws Exception {
        HttpEntity<V> entity;
        if (request != null) {
            entity = new HttpEntity<V>(request, this.getHeaders());
        } else {
            entity = new HttpEntity<V>(this.getHeaders());
        }

        ResponseEntity<U> response = new RestTemplate().getForEntity(urlService, cls, entity);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            String msm = "Service response " + urlService + ", " + cls.getName() + "; Status: " + response.getStatusCode() + "; Error: " + response.getBody();
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, msm);
            throw new Exception(msm);
        }
    }

    @Override
    public <U, V extends Object> U postHttps(Class<U> cls, V request, String urlService) throws Exception {

        HttpEntity<V> entity;
        if (request != null) {
            entity = new HttpEntity<V>(request, this.getHeaders());
        } else {
            entity = new HttpEntity<V>(this.getHeaders());
        }

        ResponseEntity<U> response = null; 
        response = new RestTemplate().postForEntity(urlService, entity, cls);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            String msm = "Service response " + urlService + ", " + cls.getName() + "; Status: " + response.getStatusCode() + "; Error: " + response.getBody();
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, msm);
            throw new Exception(msm);
        }
    }
}
