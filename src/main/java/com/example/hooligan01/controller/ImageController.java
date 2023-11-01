package com.example.hooligan01.controller;

import com.example.hooligan01.dto.ImageDTO;
import com.example.hooligan01.dto.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping("/image")
public class ImageController {

    @PostMapping
    public ResponseEntity<Object> imageUpload(@RequestPart("file") MultipartFile file) {

        Message message;

        try {

            String projectPath = System.getProperty("user.dir")
                    + "/src/main/resources/static/files";

            UUID uuid = UUID.randomUUID();

            String fileName = uuid + "_" + file.getOriginalFilename();

            File saveFile = new File(projectPath, fileName);
            file.transferTo(saveFile);

            ImageDTO imageDTO = ImageDTO.builder()
                    .filename(fileName)
                    .filepath("/files/" + fileName)
                    .build();

            return new ResponseEntity<>(imageDTO, HttpStatus.OK);

        } catch (Exception e) {

            message = new Message("이미지 업로드 에러 " + e);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
}
