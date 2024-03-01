package com.madcoke.mortageapplication.controller;

import com.madcoke.mortageapplication.exception.RecordNotFoundException;
import com.madcoke.mortageapplication.model.LoanEntity;
import com.madcoke.mortageapplication.service.LoanService;
import com.madcoke.mortageapplication.service.LoanUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping
    public String getAllLoans(Model model)
    {
        System.out.println("getAllLoans");

        List<LoanEntity> list = service.getAllLoans();

        model.addAttribute("Loans", list);

        return "list-loans";
    }




    @RequestMapping(path = {"/edit", "/edit/{id}"})
    public String editLoanById(Model model, @PathVariable("id") Optional<Long> id)
            throws RecordNotFoundException
    {

        System.out.println("editLoanById" + id);
        if (id.isPresent()) {
            LoanEntity entity = service.getLoanById(id.get());
            model.addAttribute("Loan", entity);
        } else {
            model.addAttribute("Loan", new LoanEntity());
        }


        return "add-edit-loan";
    }
    @RequestMapping(path = {"/load"})
    public String loadCsvFile()	{
        if (csvLoaded) {
            System.out.println("CSV Loaded");
            return "redirect:/";
        }
        else {
            System.out.println("Load CSV File");
            //System.out.println(filename);

            utils.readCsvFile("src/prospects.txt");
            csvLoaded = true;
            return "redirect:/";
        }

    }

    @RequestMapping(path = "/delete/{id}")
    public String deleteLoanById(Model model, @PathVariable("id") Long id)
            throws RecordNotFoundException
    {

        System.out.println("deleteLoanById" + id);

        service.deleteLoanById(id);
        return "redirect:/";
    }

    @RequestMapping(path = "/createLoan", method = RequestMethod.POST)
    public String createOrUpdateLoan(LoanEntity loan)
    {
        System.out.println("createOrUpdateLoan ");

        loan.setInstallment(Double.parseDouble(LoanUtilService.calculateInstallment(loan.getAmount(),
                loan.getInterest(),loan.getYears()).replace(",",".")));

        service.createOrUpdateLoan(loan);

        return "redirect:/";
    }

    @RequestMapping(path = "/calcLoan", method = RequestMethod.POST)
    public String calcLoan(LoanEntity loan)
    {
        System.out.println("calcLoan ");
        calc = true;
        loan.setInstallment(Double.parseDouble(LoanUtilService.calculateInstallment(loan.getAmount(),
                loan.getInterest(),loan.getYears()).replace(",",".")));
        service.createOrUpdateLoan(loan);
        return ("redirect:/edit/"+ loan.getId());
    }

    @RequestMapping(path = "/deleteProspect")
    public String deleteProspect(LoanEntity loan) throws RecordNotFoundException
    {

        System.out.println("deleteProspect " +loan.getId());

        service.deleteLoanById(loan.getId());
        return "redirect:/";
    }

}
