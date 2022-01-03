package com.udacity.rw.course1.cloudstorage.controllers;

import com.udacity.rw.course1.cloudstorage.models.Credential;
import com.udacity.rw.course1.cloudstorage.models.File;
import com.udacity.rw.course1.cloudstorage.models.Note;
import com.udacity.rw.course1.cloudstorage.models.User;
import com.udacity.rw.course1.cloudstorage.services.CredentialService;
import com.udacity.rw.course1.cloudstorage.services.FileService;
import com.udacity.rw.course1.cloudstorage.services.NoteService;
import com.udacity.rw.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

  private NoteService noteService;
  private UserService userService;
  private CredentialService credentialService;
  private FileService fileService;

  public HomeController(NoteService noteService, UserService userService,
                        CredentialService credentialService, FileService fileService) {
    this.noteService = noteService;
    this.userService = userService;
    this.credentialService = credentialService;
    this.fileService = fileService;
  }

  @GetMapping
  public String displayHomePage(Authentication auth, Model model) {

    int id = userService.getLoggedInUserId(auth);

    // a new note object must be added to the model
    Note note = new Note();
    model.addAttribute("note", note);

    // get all the notes for the current user
    List<Note> noteList = noteService.getAllNotes(id);
    model.addAttribute("noteList", noteList);

    // a new credential object must be added to the model
    Credential credential = new Credential();
    model.addAttribute("credential", credential);

    // get all the credentials for the current user
    List<Credential> credentialList = credentialService.getAllCredentials(id);
    model.addAttribute("credentialList", credentialList);

    // get all the files for the current user and then add it to the model.
    List<File> fileList = fileService.getAllFiles(id);
    model.addAttribute("fileList", fileList);

    return "home";
  }
}
