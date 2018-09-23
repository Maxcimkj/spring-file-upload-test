package com.example.fileupload;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FileuploadApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void uploadFileTest() throws Exception {
        mvc.perform(multipart("/store/upload")
                .file(createTestFile()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.attachName", is("test-file")))
                .andExpect(jsonPath("$.attachContent", is("Hello World !!, This is a test file.")));
    }

    private static MockMultipartFile createTestFile() {
        return new MockMultipartFile("file", "test-file",
                "text/plain", "Hello World !!, This is a test file.".getBytes());
    }

    @Test
    public void downloadFileTest() throws Exception {
        String downloadTestFileName = "test-file";
        mvc.perform(get("/store/download/" + downloadTestFileName))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + downloadTestFileName + "\""))
                .andExpect(content().bytes(("byte array file content " + downloadTestFileName).getBytes()));
    }

    @Test
    public void deleteFileTest() throws Exception {
        mvc.perform(delete("/store/delete/" + "deleted_file_name"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.attachName", is("deleted_file_name")))
                .andExpect(jsonPath("$.attachContent", is("deleted_file_content")));
    }
}
