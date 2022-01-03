package com.udacity.rw.course1.cloudstorage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/result")
public class ResultController {

  @GetMapping
  public String displayResultPage(@RequestParam("isSuccess") boolean isSuccess, Model model) {
    model.addAttribute("success", isSuccess);
    return "result";
  }
}
