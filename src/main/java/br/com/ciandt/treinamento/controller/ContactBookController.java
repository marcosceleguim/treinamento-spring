package br.com.ciandt.treinamento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.ciandt.treinamento.model.Contact;
import br.com.ciandt.treinamento.service.ContactService;

@Controller
public class ContactBookController {

	@Autowired
	private ContactService service;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/contact-list")
	public String listContacts(Model model) {
		List<Contact> contacts = service.listContacts().getBody();
		model.addAttribute("contacts", contacts);
		return "contact-list";
	}

	@GetMapping("/create-contact")
	public String createContact(Model model) {
		return "create-contact";
	}

	@PostMapping(value = "save")
	public String saveContact(@RequestParam("name") String name, @RequestParam("phone") String phone,
			@RequestParam("email") String email, Model model) {
		Contact contact = new Contact(name, phone, email);
		service.createContact(contact);

		return "redirect:/contact-list";
	}
}
