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
	public void addLoan(Loan loan) throws BookorCustomerNotFoundException, BookOnLoanException{
		Book book = bs.findBook(loan.getBook().getBid());//foundBook
		Customer customer = cs.findCustomer(loan.getCust().getcId());

		if (book == null || customer == null){
			throw new BookorCustomerNotFoundException("No such Customer: " + loan.getCust().getcId() + " No such Book: " + loan.getBook().getBid());
		}

		LocalDate dueDate =  LocalDate.now().plusDays(customer.getLoanPeriod());
		//System.out.println("loan.getCust().getLoanPeriod(): "+loan.getCust().getLoanPeriod());
		//System.out.println("Adding days to the current date: "+dueDate);		
		//System.out.println("customer.cid()"+loan.getCust().getcId());
		loan.setDueDate(dueDate.toString());

		try{
			lr.save(loan);
		}catch (Exception e){
			throw new BookOnLoanException("Book: " + loan.getBook().getBid() + " "+ book.getTitle() + " already on Loan to Customer: " + loan.getCust().getcId() + " " + customer.getcName());
		}			
	}
	public void deleteLoan(Loan loan)throws NoSuchLoanException{
		Loan l = ls.findLoan(loan.getLid());
		System.out.println(l);
		if(l ==null) {
			throw new NoSuchLoanException("No such Loan: " + loan.getLid());
		}

		lr.delete(loan);

	}
}
