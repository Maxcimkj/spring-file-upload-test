package com.example.fileupload;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;

public class RestTemplateClient {
    private String downloadEndpoint;
    private String uploadEndpoint;

    public RestTemplateClient(String baseUrl) {
        this.downloadEndpoint = baseUrl + "/store/download";
        this.uploadEndpoint = baseUrl + "/store/upload";
    }

    public InputStream downloadFile(String fileName) throws Exception {
        final RestTemplate template = new RestTemplate();
        final ResponseEntity<byte[]> response = template.getForEntity(downloadEndpoint + "/" + fileName, byte[].class);

        return new ByteArrayInputStream(response.getBody());
    }

    public AttachDescriptor uploadFile(String fileName, InputStream inputStream) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();

        byte[] fileContent = new byte[inputStream.available()];
        inputStream.read(fileContent);

        body.add("file", new ByteArrayResource(fileContent) {
            @Override
            public String getFilename() {
                return fileName;
            }
        });

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AttachDescriptor> response = restTemplate
                .postForEntity(uploadEndpoint, requestEntity, AttachDescriptor.class);

        return response.getBody();
    }
}
