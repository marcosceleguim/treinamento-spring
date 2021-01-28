package br.com.ciandt.treinamento.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.ciandt.treinamento.model.Contact;
import br.com.ciandt.treinamento.repository.ContactRepository;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

	@InjectMocks
	private ContactService service;

	@Mock
	private ContactRepository repository;

	@Test
	public void deveCriarContatoComSucesso() {
		Contact contact = new Contact();

		ResponseEntity<Contact> result = service.createContact(contact);

		assertEquals(contact, result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	public void deveRecuperarContatoCorretoPorId() {
		Contact contact = new Contact();
		contact.setId(1);

		Optional<Contact> optionalContact = Optional.of(contact);

		when(repository.findById(1)).thenReturn(optionalContact);

		ResponseEntity<Optional<Contact>> result = service.getContactById(1);

		assertEquals(optionalContact, result.getBody());
		assertEquals(contact, result.getBody().get());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	public void deveTentarRecuperarContatoInexistente() {

		when(repository.findById(2)).thenThrow(new NoSuchElementException());

		ResponseEntity<Optional<Contact>> result = service.getContactById(2);

		assertFalse(result.hasBody());
		assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
	}

	@Test
	public void deveListarContatosComSucesso() {
		List<Contact> contacts = new ArrayList<>();
		contacts.add(new Contact("name1", "phone1", "email1"));
		contacts.add(new Contact("name2", "phone2", "email2"));

		when(repository.findAll()).thenReturn(contacts);

		ResponseEntity<List<Contact>> result = service.listContacts();

		assertEquals(contacts, result.getBody());
		assertEquals(2, result.getBody().size());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	public void deveExcluirContatoComSucesso() {

		ResponseEntity<Optional<Contact>> result = service.deleteContactById(1);

		assertFalse(result.hasBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	public void deveTentarExcluirContatoInexistente() {

		doThrow(new NoSuchElementException()).when(repository).deleteById(2);

		ResponseEntity<Optional<Contact>> result = service.deleteContactById(2);

		assertFalse(result.hasBody());
		assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
	}

	@Test
	public void deveEditarContatoComSucesso() {
		Contact contact = new Contact("name", "phone", "email");
		contact.setId(1);

		Contact newContact = new Contact("newName", "newPhone", "newEmail");
		newContact.setId(1);

		when(repository.findById(1)).thenReturn(Optional.of(contact));

		ResponseEntity<Contact> result = service.updateContact(newContact);

		ArgumentCaptor<Contact> captor = ArgumentCaptor.forClass(Contact.class);
		verify(repository).save(captor.capture());

		Contact contactResult = captor.getValue();

		assertEquals(newContact.getName(), contactResult.getName());
		assertEquals(newContact.getEmail(), contactResult.getEmail());
		assertEquals(newContact.getPhone(), contactResult.getPhone());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	public void deveTentarEditarContatoInexistente() {

		Contact newContact = new Contact("newName", "newPhone", "newEmail");
		newContact.setId(1);

		ResponseEntity<Contact> result = service.updateContact(newContact);

		assertFalse(result.hasBody());
		assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
	}
}
