package com.sales.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sales.models.Book;
import com.sales.models.Customer;
import com.sales.models.Loan;
import com.sales.services.BookService;
import com.sales.services.CustomerService;
import com.sales.services.LoanService;




//https://www.tutorialspoint.com/spring_boot/spring_boot_exception_handling.htm





@Controller
public class LoanController {
	@Autowired
	BookService bs;
	@Autowired
	CustomerService cs;
	@Autowired
	LoanService ls;
	@RequestMapping(value = "/showLoans")
	public String getLoans(Model model) {
		ArrayList <Loan> loans = (ArrayList <Loan>)ls.getLoans();
		model.addAttribute("loans", loans);
		return "showLoans";
	}	
	@RequestMapping(value = "/newLoan",method=RequestMethod.GET)
	public String addLoanGet(Model m) {
		Loan l = new Loan();
		m.addAttribute("loans", l);	
		return "newLoan";
	}
	@RequestMapping(value = "/newLoan",method=RequestMethod.POST)
	public String addLoanPost(@Valid @ModelAttribute ("loans") Loan loan, BindingResult result) {
		
		if ((bs.findBook(loan.getBook().getBid()) == null) || (cs.findCustomer(loan.getCust().getcId()) == null)){
			return "errorAddLoanPage";
		}
		
		Book b = bs.findBook(loan.getBook().getBid());
		Customer c = cs.findCustomer(loan.getCust().getcId());
		if (b.getBid() == loan.getBook().getBid() || (c.getcId() == loan.getCust().getcId())){
			return "errorAddLoanPage2";
		}
		
		if (result.hasErrors()) {
			System.out.println("error");
			return "addLoan";
		}
		Customer customer = cs.findCustomer(loan.getCust().getcId());

		LocalDate dueDate =  LocalDate.now().plusDays(customer.getLoanPeriod());
		System.out.println("loan.getCust().getLoanPeriod(): "+loan.getCust().getLoanPeriod());
		System.out.println("Adding days to the current date: "+dueDate);		
		System.out.println("customer.cid()"+loan.getCust().getcId());
		loan.setDueDate(dueDate.toString());
		ls.addLoan(loan);
	
		return "redirect:/showLoans";
	}
	@RequestMapping(value = "/deleteLoan",method=RequestMethod.GET)
	public String deleteLoanGet(Model m) {
		Loan l = new Loan();
		m.addAttribute("loans", l);			
		return "deleteLoan";
	}
	@RequestMapping(value = "/deleteLoan",method=RequestMethod.POST)
	public String deleteLoanPost(@Valid @ModelAttribute ("loans") Loan loan, BindingResult result) {
		if (result.hasErrors()) {
			System.out.println("error");
			return "deleteLoan";
		}
		ls.deleteLoan(loan);
		return "redirect:/showLoans";
	}		
}
