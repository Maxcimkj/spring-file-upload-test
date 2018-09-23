package com.example.fileupload;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class RestTemplateBasicLiveTest {

    private RestTemplate restTemplate;
    private static final String getFileUrl = "http://localhost:8080/store/download";
    private static final String postServiceUrl = "http://localhost:8080/store/upload/file";

    @Before
    public void beforeTest() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void getFileFromService() throws IOException {
        final RestTemplate template = new RestTemplate();
        final ResponseEntity<byte[]> response = template.getForEntity(getFileUrl + "/foos", byte[].class);

        Assert.assertTrue(true);
    }

    @Test
    public void postFileToService() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("file", getTestFile());

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate
                .postForEntity(postServiceUrl, requestEntity, String.class);

        Assert.assertTrue(response.getBody().equals("test-file.txt"));
    }

    public static Resource getTestFile() throws IOException {
        Path testFile = Files.createTempFile("test-file", ".txt");
        Files.write(testFile, "Hello World !!, This is a test file.".getBytes());
        return new FileSystemResource(testFile.toFile());
    }
}
