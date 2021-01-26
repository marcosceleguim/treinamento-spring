package br.com.ciandt.treinamento.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ciandt.treinamento.model.Contact;
import br.com.ciandt.treinamento.service.ContactService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/contacts")
@Api(tags = { "Contatos" })
public class ContactController {

	@Autowired
	private ContactService service;

	@ApiOperation("Criar um novo contato")
	@PostMapping
	public ResponseEntity<Contact> createContact(@RequestBody Contact contact) {
		return service.createContact(contact);
	}

	@ApiOperation("Recuperar um contato pelo id")
	@GetMapping(path = "/{id}")
	public ResponseEntity<Optional<Contact>> getContactById(@PathVariable int id) {
		return service.getContactById(id);
	}

	@ApiOperation("Listar todos os contatos cadastrados")
	@GetMapping
	public ResponseEntity<List<Contact>> listContacts() {
		return service.listContacts();
	}

	@ApiOperation("Excluir um contato pelo id")
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Optional<Contact>> deleteContactById(@PathVariable int id) {
		return service.deleteContactById(id);
	}

	@ApiOperation("Atualizar as informações de um contato")
	@PutMapping
	public ResponseEntity<Contact> updateContact(@RequestBody Contact contact) {
		return service.updateContact(contact);
	}
}
