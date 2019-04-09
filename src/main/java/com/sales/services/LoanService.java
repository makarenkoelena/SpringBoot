package com.sales.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sales.models.Loan;
import com.sales.repositories.LoanRepository;

@Service
public class LoanService {
	@Autowired
	LoanRepository lr;
	
	public Iterable<Loan> getLoans(){
		return lr.findAll();
	}
	public void addLoan(Loan l){
		lr.save(l);
	}	
	public void deleteLoan(Loan l){
		lr.delete(l);
	}
	public void findLoan(Long l){
		lr.findOne(l);
	}
	
}
