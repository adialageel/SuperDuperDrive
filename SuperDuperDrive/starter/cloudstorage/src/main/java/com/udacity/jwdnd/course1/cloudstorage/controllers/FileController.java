package com.udacity.rw.course1.cloudstorage.controllers;

import com.udacity.rw.course1.cloudstorage.models.File;
import com.udacity.rw.course1.cloudstorage.services.FileService;
import com.udacity.rw.course1.cloudstorage.services.UserService;
import org.apache.coyote.Response;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/file")
public class FileController {

  private UserService userService;
  private FileService fileService;

  public FileController(UserService userService, FileService fileService) {
    this.userService = userService;
    this.fileService = fileService;
  }

  @PostMapping("/upload")
  public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload,
                           Authentication auth, Model model) throws IOException {
    boolean isSuccess;

    if (fileUpload.isEmpty()) {
      isSuccess = false;
    } else {
      if (!fileService.doesFileExist(fileUpload)) {
        isSuccess = fileService.insertFile(fileUpload, userService.getLoggedInUserId(auth)) > 0;
      } else {
        isSuccess = false;
      }
    }
    return "redirect:/result?isSuccess=" + isSuccess;
  }

  @GetMapping("/view")
  public ResponseEntity<InputStreamResource> viewFile(@RequestParam("id") int id) {
    File file = fileService.getFile(id);

    // grab the file data
    InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(file.getFileData()));

    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=" + file.getFilename())
            .contentType(MediaType.parseMediaType(file.getContentType()))
            .body(resource);
  }

  @GetMapping("/delete")
  public String deleteFile(@RequestParam("id") int id) {
    boolean isSuccess = id > 0;
    if (isSuccess) {
      fileService.deleteFile(id);
    }
    return "redirect:/result?isSuccess=" + isSuccess;
  }
}
