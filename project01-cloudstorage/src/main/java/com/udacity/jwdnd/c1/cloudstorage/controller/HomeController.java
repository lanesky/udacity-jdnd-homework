package com.udacity.jwdnd.c1.cloudstorage.controller;

import com.udacity.jwdnd.c1.cloudstorage.model.*;
import com.udacity.jwdnd.c1.cloudstorage.services.FileService;
import com.udacity.jwdnd.c1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.c1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.c1.cloudstorage.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class HomeController {

    private UserService userService;
    private FileService fileService;
    private NoteService noteService;
    private CredentialService credentialService;

    public HomeController(UserService userService, NoteService noteService,
                          FileService fileService, CredentialService credentialService) {
        this.userService = userService;
        this.noteService = noteService;
        this.fileService = fileService;
        this.credentialService = credentialService;

    }

    @GetMapping("/home")
    public String notes(Authentication authentication, NoteForm noteForm, CredentialForm credentialForm, Model model) {
        Integer userId = this.userService.getUser(authentication.getName()).getUserId();
        model.addAttribute("myNotes", this.noteService.getNotes(userId));
        model.addAttribute("myFiles", this.fileService.getFiles(userId));
        model.addAttribute("myCredentials", this.credentialService.getCredentials(userId));
        return "home";
    }

    private String resultView(Model model, String errorMessage, int rowsAffected, String nextUrl) {
        if (errorMessage == null) {
            if (rowsAffected > 0) {
                model.addAttribute("postSuccess", true);
            } else {
                model.addAttribute("changeNoteSaved", true);
            }
        } else {
            model.addAttribute("postError", errorMessage);
        }
        model.addAttribute("nextUrl", nextUrl);
        return "result";
    }

    /*
     * ////////////////////////////////
     * Add note, show note, delete note
     * ////////////////////////////////
     * */

    @PostMapping("/note")
    public String addUpdateNote(Authentication authentication, NoteForm noteForm, Model model) {
        String errorMessage = null;
        int rowsAffected = 0;

        Integer userId = this.userService.getUser(authentication.getName()).getUserId();

        if (noteForm.getNoteId() != null ) {
            Note note = this.noteService.getNoteById(noteForm.getNoteId());
            if (note == null) {
                errorMessage = "Note does not exists.";
            } else if (note.getUserId() != userId) {
                errorMessage = "The note is not yours.";
            }
        }

        if (errorMessage == null) {
            noteForm.setUserId(userId);

            if (noteForm.getNoteId() == null ) {
                rowsAffected = this.noteService.addNote(noteForm);
            } else {
                rowsAffected = this.noteService.updateNote(noteForm);
            }
        }
        return resultView(model, errorMessage, rowsAffected, "/home#nav-notes-tab");
    }

    @PostMapping("/deleteNote")
    public String deleteNote(Authentication authentication, @ModelAttribute("noteId") Integer noteId, Model model) {
        String errorMessage = null;
        int rowsAffected = 0;

        Integer userId = this.userService.getUser(authentication.getName()).getUserId();

        if (noteId >=0 ) {
            Note note = this.noteService.getNoteById(noteId);
            if (note == null) {
                errorMessage = "Note does not exists.";
            } else if (note.getUserId() != userId) {
                errorMessage = "The note is not yours.";
            }
        }

        if (errorMessage == null) {
            rowsAffected = this.noteService.deleteNote(noteId);
        }
        return resultView(model, errorMessage, rowsAffected, "/home#nav-notes-tab");
    }

    /*
    * ////////////////////////////////
    * Add file, show file, delete file
    * ////////////////////////////////
    * */

    @GetMapping("/files/{fileId}")
    @ResponseBody
    public ResponseEntity<byte[]> serveFile(Authentication authentication, @PathVariable Integer fileId, Model model) {
        String errorMessage = null;

        File file = this.fileService.getFileById(fileId);
        Integer userId = this.userService.getUser(authentication.getName()).getUserId();

        // download as attachment
//      return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + file.getFileName() + "\"").body(file.getFileData());

        if (file == null) {
            errorMessage = "File does not exists.";
        } else if (file.getUserId() != userId) {
            errorMessage = "The file is not yours.";
        }

        if (errorMessage == null) {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.valueOf(file.getContentType()))
                    .body(file.getFileData());
        } else {
            return ResponseEntity
                    .ok()
                    .body(null);
        }

    }

    @PostMapping("/file")
    public String handleFileUpload(Authentication authentication,
                                   @RequestParam("fileUpload") MultipartFile fileUpload,
                                   RedirectAttributes redirectAttributes, Model model) throws IOException {

        String errorMessage = null;
        int rowsAffected = 0;

        Integer userId = this.userService.getUser(authentication.getName()).getUserId();

        String fileName = fileUpload.getOriginalFilename();
        if (!fileService.existSameFileName(fileName, userId)) {
            errorMessage = "The file name already exists.";
        }
        if (errorMessage == null) {
            File file = new File(null,
                    fileName,
                    fileUpload.getContentType(),
                    String.valueOf(fileUpload.getSize()),
                    userId,
                    fileUpload.getBytes());

            rowsAffected  = this.fileService.addFile(file);
        }
        return resultView(model, errorMessage, rowsAffected, "/home#nav-files-tab");
    }

    @PostMapping("/deleteFile")
    public String deleteFile(Authentication authentication, @ModelAttribute("fileId") Integer fileId, Model model) {
        String errorMessage = null;
        int rowsAffected = 0;

        Integer userId = this.userService.getUser(authentication.getName()).getUserId();

        if (fileId >=0 ) {
            File file = this.fileService.getFileById(fileId);
            if (file == null) {
                errorMessage = "File does not exists.";
            } else if (file.getUserId() != userId) {
                errorMessage = "The file is not yours.";
            }
        }

        if (errorMessage == null) {
            rowsAffected = this.fileService.deleteFile(fileId);
        }
        return resultView(model, errorMessage, rowsAffected, "/home#nav-files-tab");
    }

    /*
     * ////////////////////////////////
     * Add note, show note, delete note
     * ////////////////////////////////
     * */

    @PostMapping("/credential")
    public String addUpdateCredential(Authentication authentication, CredentialForm credentialForm, Model model) {
        String errorMessage = null;
        int rowsAffected = 0;

        Integer userId = this.userService.getUser(authentication.getName()).getUserId();

        if (credentialForm.getCredentialId() == null  && !credentialService.existSameUserName(credentialForm.getUserName(), userId)) {
            errorMessage = "The username already exists.";
        }

        if (credentialForm.getCredentialId() != null ) {
            Credential credential = this.credentialService.getCredentialById(credentialForm.getCredentialId());
            if (credential == null) {
                errorMessage = "Credential does not exists.";
            } else if (credential.getUserId() != userId) {
                errorMessage = "The credential is not yours.";
            }
        }

        if (errorMessage == null) {
            credentialForm.setUserId(userId);
            if (credentialForm.getCredentialId() == null ) {
                rowsAffected = this.credentialService.addCredential(credentialForm);
            } else {
                rowsAffected = this.credentialService.updateCredential(credentialForm);
            }
        }

        return resultView(model, errorMessage, rowsAffected, "/home#nav-credentials-tab");
    }



    @PostMapping("/deleteCredential")
    public String deleteCredential(Authentication authentication, @ModelAttribute("credentialId") Integer credentialId, Model model) {
        String errorMessage = null;
        int rowsAffected = 0;

        Integer userId = this.userService.getUser(authentication.getName()).getUserId();

        if (credentialId >=0 ) {
            Credential credential = this.credentialService.getCredentialById(credentialId);
            if (credential == null) {
                errorMessage = "Credential does not exists.";
            } else if (credential.getUserId() != userId) {
                errorMessage = "The credential is not yours.";
            }
        }

        if (errorMessage == null) {
            rowsAffected = this.credentialService.deleteCredential(credentialId);
        }
        return resultView(model, errorMessage, rowsAffected, "/home#nav-credentials-tab");
    }

}
