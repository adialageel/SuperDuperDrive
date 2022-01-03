package com.udacity.rw.course1.cloudstorage.controllers;

import com.udacity.rw.course1.cloudstorage.models.User;
import com.udacity.rw.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

  UserService userService;

  public SignupController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public String displaySignupPage() {
    return "signup";
  }

  @PostMapping
  public String signupUser(User user, Model model) {
    String signupError = null;

    if (!userService.isUsernameAvailable(user.getUsername())) {
      signupError = "The username already exists.";
    }

    if (signupError == null) {
      int id = userService.createUser(user);
      if (id < 0) {
        signupError = "There was an error signing you up. Please try again.";
      }
    }

    if (signupError == null) {
      model.addAttribute("signupSuccess", true);
    } else {
      model.addAttribute("signupError", signupError);
    }

    return "signup";
  }

}
