package com.adpro211.a8.tugaskelompok.email.config;

import org.jobrunr.jobs.mappers.JobMapper;
import org.jobrunr.storage.InMemoryStorageProvider;
import org.jobrunr.storage.StorageProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.sqlite.SQLiteDataSource;

import java.nio.file.Paths;

@Configuration
@ComponentScan(basePackageClasses = JobRunrStorageConfig.class)
public class JobRunrStorageConfig {

    @Bean
    public SQLiteDataSource dataSource() {
        final SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:" + Paths.get(System.getProperty("java.io.tmpdir"), "job-queue.db"));
        return dataSource;
    }

    public static SQLiteDataSource dataSourceStatic() {
        final SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:" + Paths.get(System.getProperty("java.io.tmpdir"), "job-queue.db"));
        return dataSource;
    }
}
