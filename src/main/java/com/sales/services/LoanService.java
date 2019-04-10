package com.sales.services;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sales.exceptions.BookOnLoanException;
import com.sales.exceptions.BookorCustomerNotFoundException;
import com.sales.exceptions.NoSuchLoanException;
import com.sales.models.Book;
import com.sales.models.Customer;
import com.sales.models.Loan;
import com.sales.repositories.LoanRepository;

@Service
public class LoanService {
	@Autowired
	BookService bs;
	@Autowired
	CustomerService cs;
	@Autowired
	LoanService ls;
	@Autowired
	LoanRepository lr;

	public Iterable<Loan> getLoans(){
		return lr.findAll();
	}
	public Loan findLoan(Long lid){
		return lr.findOne(lid);
	}
	public void addLoan(Loan loan) throws BookorCustomerNotFoundException{
		Book bookINdb = bs.findBook(loan.getBook().getBid());//foundBook
		Customer customerINdb = cs.findCustomer(loan.getCust().getcId());

		if (bookINdb == null || customerINdb == null){
			throw new BookorCustomerNotFoundException("Error");
		}
		if (bookINdb.getBid() == loan.getBook().getBid() || (customerINdb.getcId() == loan.getCust().getcId())){
			throw new BookOnLoanException("Error");
		}

		LocalDate dueDate =  LocalDate.now().plusDays(customerINdb.getLoanPeriod());
		System.out.println("loan.getCust().getLoanPeriod(): "+loan.getCust().getLoanPeriod());
		System.out.println("Adding days to the current date: "+dueDate);		
		System.out.println("customer.cid()"+loan.getCust().getcId());
		loan.setDueDate(dueDate.toString());

		lr.save(loan);
	}
	public void deleteLoan(Loan loan){
		Loan loanINdb = ls.findLoan(loan.getLid());
		if (loanINdb.getLid() != loan.getLid() && loanINdb.getLid() ==null ){
			throw new NoSuchLoanException("error");
		}
		lr.delete(loan);
	}
}
