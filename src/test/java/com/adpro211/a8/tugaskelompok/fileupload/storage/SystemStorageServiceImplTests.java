package com.adpro211.a8.tugaskelompok.fileupload.storage;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

/**
 *
 *
 */
public class SystemStorageServiceImplTests {

    private StorageProperties properties = new StorageProperties();
    private StorageServiceImpl service;

    @BeforeEach
    public void init() {
        properties.setLocation("target/files/" + Math.abs(new Random().nextLong()));
        service = new StorageServiceImpl(properties);
        service.init();
    }

    @Test
    public void saveNonExistent() {
        assertThrows(StorageException.class, () -> {
            service.store(new MockMultipartFile("foo.txt", (byte[]) null));
        });
    }

    @Test
    public void loadNonExistent() {
        assertThat(service.load("foo.txt")).doesNotExist();
    }

    @Test
    public void saveAndLoad() {
        service.store(new MockMultipartFile("foo", "foo.txt", MediaType.TEXT_PLAIN_VALUE,
                "Hello, World".getBytes()));
        assertThat(service.load("foo.txt")).exists();
        assertThat(service.loadAsResource("foo.txt")).isNotNull();
        assertThrows(StorageFileNotFound.class, () -> {
            service.loadAsResource("bar.txt");
        });
        assertThrows(StorageFileNotFound.class, () -> {
            service.loadAsResource("b////!waa:9817341");
        });
    }

    @Test
    public void saveRelativePathNotPermitted() {
        assertThrows(StorageException.class, () -> {
            service.store(new MockMultipartFile("foo", "../foo.txt",
                    MediaType.TEXT_PLAIN_VALUE, "Hello, World".getBytes()));
        });
    }

    @Test
    public void saveAbsolutePathNotPermitted() {
        assertThrows(StorageException.class, () -> {
            service.store(new MockMultipartFile("foo", "/etc/passwd",
                    MediaType.TEXT_PLAIN_VALUE, "Hello, World".getBytes()));
        });
    }

    @Test
    @EnabledOnOs({OS.LINUX})
    public void saveAbsolutePathInFilenamePermitted() {
        //Unix file systems allows backslash '\' in file names.
        String fileName="\\etc\\passwd";
        service.store(new MockMultipartFile(fileName, fileName,
                MediaType.TEXT_PLAIN_VALUE, "Hello, World".getBytes()));
        assertTrue(Files.exists(
                Paths.get(properties.getLocation()).resolve(Paths.get(fileName))));
    }

    @Test
    public void savePermitted() {
        service.store(new MockMultipartFile("foo", "bar/../foo.txt",
                MediaType.TEXT_PLAIN_VALUE, "Hello, World".getBytes()));
    }
}
