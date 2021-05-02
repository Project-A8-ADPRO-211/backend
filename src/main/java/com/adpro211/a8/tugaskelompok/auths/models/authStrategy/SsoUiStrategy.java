//package com.adpro211.a8.tugaskelompok.auths.models.authStrategy;
//
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
//import org.jasig.cas.client.validation.TicketValidationException;
//import org.jasig.cas.client.validation.TicketValidator;
//import org.springframework.beans.factory.annotation.Value;
//
//import javax.annotation.PostConstruct;
//import javax.persistence.Entity;
//import javax.persistence.Transient;
//import java.util.Map;
//
//@Entity
//@Data
//@NoArgsConstructor
//public class SsoUiStrategy extends AuthStrategy {
//
//    @Transient
//    @Value("${auth.sso-provider-url}")
//    private String CAS_PREFIX = "http://sso.ui.ac.id/cas2";
//
//    @Transient
//    @Value("${auth.auth.cas-calback-url}")
//    private String SERVICE_URL = "http://localhost:8080/login?strategy=sso-ui";
//
//    @Transient
//    private TicketValidator validator;
//
//    @PostConstruct
//    public void SetUp() {
//        validator =  new Cas20ServiceTicketValidator(CAS_PREFIX);
//    }
//
//    @Override
//    public String getStrategyName() {
//        return "sso-ui";
//    }
//
//    @Override
//    public boolean authenticate(Map<String, Object> requestBody) {
//        try {
//            Map<String, Object> attribute = validator.validate(requestBody.getOrDefault("ticket", null).toString(), SERVICE_URL).getAttributes();
//            for (String key : attribute.keySet()) {
//                System.out.println(key + ": " + attribute.get(key).toString());
//            }
//        } catch (TicketValidationException e) {
//            e.printStackTrace();
//        }
//        return true;
//    }
//}
