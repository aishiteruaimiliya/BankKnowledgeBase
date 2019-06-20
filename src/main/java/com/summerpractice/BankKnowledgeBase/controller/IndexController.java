package com.summerpractice.BankKnowledgeBase.controller;

import com.summerpractice.BankKnowledgeBase.util.ExcelReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index(){
      return "Index";
    }
    @PostMapping("/uploadfile")
    public String uploadFile(@RequestParam(name = "file",required = true) MultipartFile file){

        try {
            ExcelReader.readExcel((FileInputStream) file.getInputStream(),file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Index";
    }
}
