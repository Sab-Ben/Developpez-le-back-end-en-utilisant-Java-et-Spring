package com.chatop.chatopbackend.service.filestorage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final AmazonS3 amazonS3;

    public FileStorageServiceImpl(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String saveToS3(MultipartFile file){
        try {
            String recordedFilename = this.formatFileName(file);
            amazonS3.putObject("chatop-bucket", recordedFilename, file.getInputStream(), buildObjectMetadata(file));
            return recordedFilename;
        } catch (FileNameException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ObjectMetadata buildObjectMetadata(MultipartFile file) throws IOException {
        var objectMetadata = new ObjectMetadata();
        InputStream inputStream = file.getInputStream();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(inputStream.available());
        objectMetadata.setContentDisposition("inline; filename=" + file.getName());
        return objectMetadata;
    }

    private String formatFileName(MultipartFile file) throws FileNameException {
        long currentDate = System.currentTimeMillis();
        String filename = file.getOriginalFilename();
        if (filename == null || filename.isEmpty()){
            throw new FileNameException("It must have a filename");
        }

        int indexOfExtension = filename.lastIndexOf(".");
        return filename.substring(0, indexOfExtension) + currentDate + filename.substring(indexOfExtension);
    }
}
