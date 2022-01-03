package com.udacity.rw.course1.cloudstorage.controllers;

import com.udacity.rw.course1.cloudstorage.models.Credential;
import com.udacity.rw.course1.cloudstorage.services.CredentialService;
import com.udacity.rw.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;
import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("/credential")
public class CredentialController {

  UserService userService;
  CredentialService credentialService;

  public CredentialController(UserService userService, CredentialService credentialService) {
    this.userService = userService;
    this.credentialService = credentialService;
  }

  @PostMapping("/save")
  public String createCredential(Authentication auth, Credential credential, Model model) {
    // Set the id of the currently logged in user.
    credential.setUserId(userService.getLoggedInUserId(auth));

    Boolean isSuccess = credentialService.insertCredential(credential) > 0;

    return "redirect:/result?isSuccess=" + isSuccess;
  }

  @GetMapping("/delete")
  public String deleteCredential(@RequestParam("id") int id) {
    boolean isSuccess = id > 0;
    if (isSuccess) {
      credentialService.deleteCredential(id);
    }
    return "redirect:/result?isSuccess=" + isSuccess;
  }
}
