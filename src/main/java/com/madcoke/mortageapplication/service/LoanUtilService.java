package com.madcoke.mortageapplication.service;

import com.madcoke.mortageapplication.model.LoanEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
public class LoanUtilService {
    public static final String DELIMITER = ",";
    public static final  byte MONTHS_IN_YEAR = 12;
    public static final  byte PERCENT = 100;

    @Autowired
    LoanService service;

    public static String calculateInstallment(Double totalamount, Double annualInterest, Integer years){
        String monthlyInstallment="";
        Double monthlyInterest = (annualInterest/PERCENT/MONTHS_IN_YEAR);
        Integer numberOfPayments = (years*MONTHS_IN_YEAR);
        Double power = 1.0;

        //Using formula: E = U[b(1+b)^p]/[(1+b)^p-1]
        if (numberOfPayments>0){
            //Calculate (1+b)^p as power
            for (int i=1; i<=numberOfPayments; i++) { power =power*(1+monthlyInterest);}
            //Finally calculate the monthly installment with E=U*(b*power)/(power-1)
            monthlyInstallment = String.format("%1.2f", (totalamount*(monthlyInterest*power/(power-1))));
        }

        return monthlyInstallment;

    }

    public static String cleanLine(String str){
        str.trim();
        if (str.contains("\"")){
            str=str.replace("\"","");
            str=str.replaceFirst(","," ");
        }
        return str;
    }

    public String readCsvFile(String csvFileName) {
        //Defaults to src/prospects.txt if no file is provided (/src/prospects.txt in docker container)
        if (csvFileName == null || csvFileName.isEmpty()) { csvFileName = "src/prospects.txt"; }

        BufferedReader reader = null;
        String line;
        Boolean header = true;
        LoanEntity entity = new LoanEntity();
        try {
            reader = new BufferedReader(new FileReader(csvFileName));

            while((line = reader.readLine()) != null) {
                line=cleanLine(line);
                if (!header && line.length()>2){
                    String[] row = line.split(DELIMITER);
                    entity.setId(null);
                    entity.setName(row[0]);
                    entity.setAmount(Double.valueOf(row[1]));
                    entity.setInterest(Double.valueOf(row[2]));
                    entity.setYears(Integer.valueOf(row[3]));
                    entity.setInstallment(Double.parseDouble(calculateInstallment( entity.getAmount(),
                            entity.getInterest(), entity.getYears()).replace(",",".")));

                    service.createOrUpdateLoan(entity);
                }
                header = false;
            }
            return "Success";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Reader closed";
        }
    }


}
