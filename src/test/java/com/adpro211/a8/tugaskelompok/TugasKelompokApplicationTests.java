package com.adpro211.a8.tugaskelompok;

import com.adpro211.a8.tugaskelompok.auths.property.AuthProperty;
import com.adpro211.a8.tugaskelompok.email.property.EmailProperty;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@EnableConfigurationProperties({AuthProperty.class, EmailProperty.class})
class TugasKelompokApplicationTests {


    @Test
    void contextLoads() {
    }

}
