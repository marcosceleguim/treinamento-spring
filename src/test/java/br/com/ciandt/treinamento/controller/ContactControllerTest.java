package br.com.ciandt.treinamento.controller;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.ciandt.treinamento.model.Contact;
import br.com.ciandt.treinamento.service.ContactService;

@ExtendWith(MockitoExtension.class)
public class ContactControllerTest {

	@Mock
	private ContactService service;

	@InjectMocks
	private ContactController controller;

	@Test
	public void deveCriarContatoComSucesso() {

		Contact contact = new Contact("name", "email", "phone");

		controller.createContact(contact);

		verify(service).createContact(contact);
	}

	@Test
	public void deveRecuperarContatoComSucesso() {

		controller.getContactById(1);

		verify(service).getContactById(1);
	}

	@Test
	public void deveRecuperarListaContatosComSucesso() {

		controller.listContacts();

		verify(service).listContacts();
	}

	@Test
	public void deveExcluirContatoComSucesso() {

		controller.deleteContactById(1);

		verify(service).deleteContactById(1);
	}

	@Test
	public void deveEditarContatoComSucesso() {

		Contact newContact = new Contact("newName", "newEmail", "newPhone");

		controller.updateContact(newContact);

		verify(service).updateContact(newContact);
	}
}
