package com.sales.controllers;

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
import com.sales.services.BookService;

@Controller
public class BookController {
	@Autowired
	BookService bs;
	@RequestMapping(value = "/showBooks")
	public String getBooks(Model model) {
		ArrayList <Book> books = (ArrayList <Book>)bs.getBooks();
		model.addAttribute("books", books);
		return "showBooks";
	}

	@RequestMapping(value = "/addBook",method=RequestMethod.GET)
	public String addBookGet(Model book) {
		Book b = new Book();
		book.addAttribute("books", b);			
		return "addBook";
	}
	@RequestMapping(value = "/addBook",method=RequestMethod.POST)
	public String addBookPOST(@Valid @ModelAttribute("books")Book book, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println("error");
			return "addBook";
		}
		bs.addBook(book);
		return "redirect:/showBooks";
	}	
}
