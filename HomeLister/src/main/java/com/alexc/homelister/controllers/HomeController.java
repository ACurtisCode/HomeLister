package com.alexc.homelister.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.alexc.homelister.models.Listing;
import com.alexc.homelister.models.LoginUser;
import com.alexc.homelister.models.Note;
import com.alexc.homelister.models.User;
import com.alexc.homelister.services.ListingService;
import com.alexc.homelister.services.NoteService;
import com.alexc.homelister.services.UserService;

@Controller
public class HomeController {
	@Autowired
	UserService userServ;
	@Autowired
	ListingService listServ;
	@Autowired
	NoteService noteServ;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("newUser", new User());
		model.addAttribute("newLogin", new LoginUser());
		return "Login.jsp";
	}

	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("newUser") User newUser, BindingResult result, Model model,
			HttpSession session) {

		User user = userServ.register(newUser, result);
		if (result.hasErrors()) {
			model.addAttribute("newLogin", new LoginUser());
			return "Login.jsp";
		}

		session.setAttribute("userId", newUser.getId());
		return "redirect:/home";
	}

	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, BindingResult result, Model model,
			HttpSession session) {

		User user = userServ.login(newLogin, result);

		if (result.hasErrors()) {
			model.addAttribute("newUser", new User());
			return "Login.jsp";
		}

		session.setAttribute("userId", user.getId());
		return "redirect:/home";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session, Model model) {
		session.setAttribute("userId", null);
		return "redirect:/";
	}
	@GetMapping("/home")
	public String dashboard(Model model, HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		if(userId==null) {
			return "redirect:/logout";
		}
		User user = userServ.findUser(userId);
		List<Listing> listings = listServ.allListings();
		model.addAttribute("listings", listings);
		model.addAttribute("user", user);
		return "Dashboard.jsp";
	}
	@GetMapping("/listings/new")
	public String newListing(@ModelAttribute("newListing") Listing listing, Model model, HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		if(userId==null) {
			return "redirect:/logout";
		}
		return "NewListing.jsp";
	}
	@PostMapping("/listings/add")
	public String addListing(@Valid @ModelAttribute("newListing") Listing listing, BindingResult result, Model model, HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		User user = userServ.findUser(userId);
		if(result.hasErrors()) {
			return "NewListing.jsp";
		}
		else {
		listing.setListCreator(user);
		listServ.createListing(listing);
		return "redirect:/home";
		}
	}
	
	@GetMapping("/listings/{id}")
	public String listingPage(@PathVariable("id") Long id, @ModelAttribute("note") Note newNote, Model model, HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		if(userId==null) {
			return "redirect:/logout";
		}
		User user = userServ.findUser(userId);
		Listing listing = listServ.findListing(id);
		List<Note> notes = listing.getNotes();
		model.addAttribute("notes", notes);
		model.addAttribute("user", user);
		model.addAttribute("listing", listing);
		return "Listing.jsp";
	}
	@GetMapping("/listings/{id}/delete")
	public String deleteListing(@PathVariable("id") Long id) {
		Listing listing = listServ.findListing(id);
		listServ.deleteListing(listing);
		return "redirect:/home";
	}
	@GetMapping("/listings/edit/{id}")
	public String editListing(@PathVariable("id") Long id, Model model, HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		if(userId==null) {
			return "redirect:/logout";
		}
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String paramDate = formatter.format(date);
		Listing listing = listServ.findListing(id);
		model.addAttribute("listing", listing);
		model.addAttribute("date", paramDate);
		return "EditListing.jsp";
	}
	@PutMapping("/listings/edit/{id}")
	public String updateListing(@PathVariable("id") Long id, @Valid @ModelAttribute("listing") Listing listing, BindingResult result, Model model, HttpSession session) {
		if(result.hasErrors()) {

			return "EditListing.jsp";
		}
		else {
			Listing listingUpd = listServ.findListing(id);
			listingUpd.setAddress(listing.getAddress());
			listingUpd.setPrice(listing.getPrice());
			listingUpd.setListingDate(listing.getListingDate());
			listServ.updateListing(listingUpd);
			return "redirect:/listings/" + id;
		}
	}
	@PostMapping("/notes/add/{id}")
	public String addNote(@PathVariable("id") Long id, @ModelAttribute("note") Note note, BindingResult result, Model model, HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		User user = userServ.findUser(userId);
		Listing listing = listServ.findListing(id);
		if(result.hasErrors() ) {
			List<Note> notes = listing.getNotes();
			model.addAttribute("notes", notes);
			model.addAttribute("user", user);
			model.addAttribute("listing", listing);
			return "Listing.jsp";
		}
		else {
			Note noteUpd = new Note(note.getMessage());
			noteUpd.setNoteCreator(user);
			noteUpd.setListingNote(listing);
			noteServ.newNote(noteUpd);

			return "redirect:/listings/" + id;
		}

		}
}
