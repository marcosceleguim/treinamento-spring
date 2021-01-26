package br.com.ciandt.treinamento.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.ciandt.treinamento.model.Contact;
import br.com.ciandt.treinamento.repository.ContactRepository;

@Service
public class ContactService {

	@Autowired
	private ContactRepository repository;

	public ResponseEntity<Contact> createContact(Contact contact) {
		repository.save(contact);
		return new ResponseEntity<>(contact, HttpStatus.OK);
	}

	public ResponseEntity<Optional<Contact>> getContactById(int id) {
		Optional<Contact> contact;

		try {
			contact = repository.findById(id);
			return new ResponseEntity<Optional<Contact>>(contact, HttpStatus.OK);
		} catch (NoSuchElementException ex) {
			return new ResponseEntity<Optional<Contact>>(HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<List<Contact>> listContacts() {
		List<Contact> contacts = new ArrayList<>();
		contacts = repository.findAll();
		return new ResponseEntity<>(contacts, HttpStatus.OK);
	}

	public ResponseEntity<Optional<Contact>> deleteContactById(int id) {
		try {
			repository.deleteById(id);
			return new ResponseEntity<Optional<Contact>>(HttpStatus.OK);
		} catch (NoSuchElementException ex) {
			return new ResponseEntity<Optional<Contact>>(HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<Contact> updateContact(Contact newContact) {
		return repository.findById(newContact.getId()).map(contact -> {
			contact.setName(newContact.getName());
			contact.setEmail(newContact.getEmail());
			contact.setPhone(newContact.getPhone());
			return ResponseEntity.ok().body(repository.save(contact));
		}).orElse(ResponseEntity.notFound().build());
	}
}
