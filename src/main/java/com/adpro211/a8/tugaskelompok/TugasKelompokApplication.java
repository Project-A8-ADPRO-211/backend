package com.adpro211.a8.tugaskelompok;

import com.adpro211.a8.tugaskelompok.auths.property.AuthProperty;
import com.adpro211.a8.tugaskelompok.email.property.EmailProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({AuthProperty.class, EmailProperty.class})
public class TugasKelompokApplication {

    public static void main(String[] args) {
        SpringApplication.run(TugasKelompokApplication.class, args);
    }


}
