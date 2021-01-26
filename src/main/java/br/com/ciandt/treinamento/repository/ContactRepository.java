package br.com.ciandt.treinamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ciandt.treinamento.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

}
