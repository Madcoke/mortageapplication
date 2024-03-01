package com.madcoke.mortageapplication.service;

import com.madcoke.mortageapplication.exception.RecordNotFoundException;
import com.madcoke.mortageapplication.model.LoanEntity;
import com.madcoke.mortageapplication.model.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {
    @Autowired
    LoanRepository repository;

    public List<LoanEntity> getAllLoans()
    {
        System.out.println("getAllLoans");
        List<LoanEntity> result = (List<LoanEntity>) repository.findAll();

        if(result.size() > 0) {
            return result;
        } else {
            return new ArrayList<LoanEntity>();
        }
    }


    public LoanEntity getLoanById(Long id) throws RecordNotFoundException
    {
        System.out.println("getLoanById");
        Optional<LoanEntity> Loan = repository.findById(id);

        if(Loan.isPresent()) {
            return Loan.get();
        } else {
            throw new RecordNotFoundException("No Loan record exist for given id");
        }
    }

    public LoanEntity createOrUpdateLoan(LoanEntity entity)
    {
        System.out.println("createOrUpdateLoan");
        // Create new entry
        if(entity.getId()  == null)
        {

            entity = repository.save(entity);

            return entity;
        }
        else
        {
            // update existing entry
            Optional<LoanEntity> Loan = repository.findById(entity.getId());

            if(Loan.isPresent())
            {
                LoanEntity newEntity = Loan.get();
                newEntity.setInterest(entity.getInterest());
                newEntity.setName(entity.getName());
                newEntity.setAmount(entity.getAmount());
                newEntity.setYears(entity.getYears());
                newEntity.setInstallment(entity.getInstallment());


                newEntity = repository.save(newEntity);

                return newEntity;
            } else {
                entity = repository.save(entity);

                return entity;
            }
        }
    }

    public void deleteLoanById(Long id) throws RecordNotFoundException
    {
        System.out.println("deleteLoanById");

        Optional<LoanEntity> Loan = repository.findById(id);

        if(Loan.isPresent())
        {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No Loan record exist for given id");
        }
    }


}
