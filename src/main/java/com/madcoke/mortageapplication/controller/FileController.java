package com.madcoke.mortageapplication.controller;

import com.madcoke.mortageapplication.exception.FileNotFoundException;
import com.madcoke.mortageapplication.model.FileEntity;
import com.madcoke.mortageapplication.service.LoanUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
public class FileController {
    @Autowired
    LoanUtilService utils;
    @GetMapping("upload")
    public String inputCsvFileName(Model model){

        FileEntity csvFile = new FileEntity();
        model.addAttribute("csvFile", csvFile);
        return "upload-csv";
    }
    @PostMapping("save")
    public String submitCsvFile(Model model,
                             @ModelAttribute("csvFile") FileEntity csvFile) throws FileNotFoundException {
        model.addAttribute("csvFile", csvFile);
        utils.readCsvFile(csvFile.getFileName());

        return "redirect:/";
    }
}
