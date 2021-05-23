package com.adpro211.a8.tugaskelompok;

import com.adpro211.a8.tugaskelompok.auths.property.AuthProperty;
import com.adpro211.a8.tugaskelompok.email.config.JobRunrStorageConfig;
import com.adpro211.a8.tugaskelompok.email.property.EmailProperty;
import com.adpro211.a8.tugaskelompok.fileupload.storage.StorageProperties;
import org.jobrunr.jobs.mappers.JobMapper;
import org.jobrunr.storage.InMemoryStorageProvider;
import org.jobrunr.storage.StorageProvider;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(JobRunrStorageConfig.class)
@EnableConfigurationProperties({AuthProperty.class, StorageProperties.class, EmailProperty.class})
class TugasKelompokApplicationTests {


    @Test
    void contextLoads() {
    }

}
