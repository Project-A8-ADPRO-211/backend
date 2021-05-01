package com.adpro211.a8.tugaskelompok;

import com.adpro211.a8.tugaskelompok.auths.property.AuthProperty;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnableConfigurationProperties(AuthProperty.class)
class TugasKelompokApplicationTests {

    @Test
    void contextLoads() {
    }

}
