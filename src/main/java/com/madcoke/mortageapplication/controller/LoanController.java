package com.madcoke.mortageapplication.controller;

import com.madcoke.mortageapplication.exception.RecordNotFoundException;
import com.madcoke.mortageapplication.model.LoanEntity;
import com.madcoke.mortageapplication.service.LoanService;
import com.madcoke.mortageapplication.service.LoanUtilService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class LoanController {
    Boolean csvLoaded = false;

    Boolean calc = false;
    @Autowired
    LoanService service;

    @Autowired
    LoanUtilService utils;
    @Autowired
    FileController fileController;

    @RequestMapping
    public String getAllLoans(Model model)
    {
        List<LoanEntity> list = service.getAllLoans();
        model.addAttribute("Loans", list);
        return "list-loans";
    }

    @RequestMapping(path = {"/edit", "/edit/{id}"})
    public String editLoanById(Model model, @PathVariable("id") Optional<Long> id)
            throws RecordNotFoundException
    {

        if (id.isPresent()) {
            LoanEntity entity = service.getLoanById(id.get());
            if (entity.getInstallment() == 0.0) {
                entity.setInstallment(Double.parseDouble(LoanUtilService.calculateInstallment(entity.getAmount(),
                        entity.getInterest(),entity.getYears()).replace(",",".")));
                service.createOrUpdateLoan(entity);
            }
            model.addAttribute("Loan", entity);
        } else {
            model.addAttribute("Loan", new LoanEntity());
        }
        return "add-edit-loan";
    }



    @RequestMapping(path = "/delete/{id}")
    public String deleteLoanById(Model model, @PathVariable("id") Long id)
            throws RecordNotFoundException
    {
        service.deleteLoanById(id);
        return "redirect:/";
    }

    @RequestMapping(path = "/createLoan", method = RequestMethod.POST)
    public String createOrUpdateLoan(LoanEntity loan)
    {
        loan.setInstallment(Double.parseDouble(LoanUtilService.calculateInstallment(loan.getAmount(),
                loan.getInterest(),loan.getYears()).replace(",",".")));
        service.createOrUpdateLoan(loan);

        return "redirect:/";
    }

    @RequestMapping(path = "/calcLoan", method = RequestMethod.POST)
    public String calcLoan(LoanEntity loan)
    {
        calc = true;
        loan.setInstallment(Double.parseDouble(LoanUtilService.calculateInstallment(loan.getAmount(),
                loan.getInterest(),loan.getYears()).replace(",",".")));
        service.createOrUpdateLoan(loan);
        return ("redirect:/edit/"+ loan.getId());
    }

    @RequestMapping(path = "/deleteProspect")
    public String deleteProspect(LoanEntity loan) throws RecordNotFoundException
    {
        service.deleteLoanById(loan.getId());
        return "redirect:/";
    }

}
