package com.omaryan.filemani;

import com.omaryan.filemani.storage.*;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.stream.Collectors;

@Controller
public class FileUploadController {
    private final StorageService storageService;
    @Autowired
    public FileUploadController(StorageService storageService){
        this.storageService = storageService;
    }
    /* loads it into a Thymeleaf template, it calculates a link to the actual resource by
    using MvcUriComponentsBuilder */

    @GetMapping("/") // current list of uploaded files from the StorageService
    public String listUploadedFiles(Model model) throws IOException{
        model.addAttribute("files",storageService.loadAll().map(
           path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,"serveFile",path.getFileName().toString()).build().toUri().toString()).collect(Collectors.toList()));
           return "uploadForm";
    }
    @GetMapping("/files/{filename:.+}") //Loads the resource (if it exists) and sends it to the browser to download
    public ResponseEntity<Resource> serveFile(@PathVariable String filename){
        Resource file = storageService.loadAsResource(filename);

        if (file == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=/'" + file.getFilename() +"\'").body(file);
    }
    //Handles a multi-part message file and gives it to the StorageService for saving
    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file")MultipartFile file, RedirectAttributes redirectAttributes){
        storageService.store(file);
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/";
    }
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc){
        return ResponseEntity.notFound().build();
    }
}
