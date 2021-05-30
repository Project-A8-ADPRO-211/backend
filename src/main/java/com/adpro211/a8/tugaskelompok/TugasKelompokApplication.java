package com.adpro211.a8.tugaskelompok;

import com.adpro211.a8.tugaskelompok.auths.property.AuthProperty;
import com.adpro211.a8.tugaskelompok.email.config.JobRunrStorageConfig;
import com.adpro211.a8.tugaskelompok.email.property.EmailProperty;
import com.adpro211.a8.tugaskelompok.fileupload.storage.StorageProperties;
import org.jobrunr.jobs.mappers.JobMapper;
import org.jobrunr.server.BackgroundJobServer;
import org.jobrunr.server.JobActivator;
import org.jobrunr.storage.InMemoryStorageProvider;
import org.jobrunr.storage.StorageProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(JobRunrStorageConfig.class)
@EnableConfigurationProperties({AuthProperty.class, StorageProperties.class, EmailProperty.class})
public class TugasKelompokApplication {

    public static void main(String[] args) {
        SpringApplication.run(TugasKelompokApplication.class, args);
    }


}
