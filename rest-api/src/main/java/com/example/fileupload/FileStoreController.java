package com.example.fileupload;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/store")
public class FileStoreController {

    @PostMapping("/upload")
    @ResponseBody
    public AttachDescriptor uploadFile(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        return new AttachDescriptor(multipartFile.getOriginalFilename(), new String(multipartFile.getBytes()));
    }

    @GetMapping("/download/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = new ByteArrayResource(("byte array file content " + filename).getBytes());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"").body(file);
    }

    @DeleteMapping("/delete/{filename:.+}")
    @ResponseBody
    public AttachDescriptor deleteFile(@PathVariable String filename) {
        return new AttachDescriptor("deleted_file_name", "deleted_file_content");
    }
}
