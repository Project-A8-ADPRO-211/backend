package com.adpro211.a8.tugaskelompok.fileupload.storage;

public class StorageFileNotFound extends StorageException {

    public StorageFileNotFound(String message) {
        super(message);
    }

    public StorageFileNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
