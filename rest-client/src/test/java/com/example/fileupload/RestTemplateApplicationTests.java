package com.example.fileupload;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestTemplateApplicationTests {
    private RestTemplateClient restTemplateClient = new RestTemplateClient("http://localhost:8080");

    @Test
    public void uploadTest() throws Exception {
        AttachDescriptor testFileDescriptor =
                restTemplateClient.uploadFile("test_file", new ByteArrayInputStream("test_file_content".getBytes()));
        Assert.assertEquals(testFileDescriptor.getAttachName(), "test_file");
        Assert.assertEquals(testFileDescriptor.getAttachContent(), "test_file_content");
    }

    @Test
    public void downloadTest() throws Exception {
        InputStream test_file = restTemplateClient.downloadFile("test_file");

        byte[] fileContent = new byte[test_file.available()];
        test_file.read(fileContent);

        Assert.assertEquals(new String(fileContent), "byte array file content " + "test_file");
    }
}

