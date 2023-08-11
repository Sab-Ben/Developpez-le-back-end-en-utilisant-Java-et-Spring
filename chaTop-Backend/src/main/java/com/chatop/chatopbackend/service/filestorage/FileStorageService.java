package com.chatop.chatopbackend.service.filestorage;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String saveToS3(MultipartFile file);

}
