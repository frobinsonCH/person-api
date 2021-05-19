package uk.gov.companieshouse.personapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.personapi.exception.PersonNotFoundException;
import uk.gov.companieshouse.personapi.model.Person;
import uk.gov.companieshouse.personapi.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private static final String PERSON_NOT_FOUND_MESSAGE = "Person not found with id: %s";


    private final PersonRepository personRepository;
    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    public List<Person> getPeople() {
        return personRepository.findAll();

    }


    public void addPerson(Person person) {
        personRepository.save(person);

    }


    public void deletePerson(String id) {
        Optional<Person> byId = personRepository.findById(id);
        if (byId.isEmpty()) {
            throw new PersonNotFoundException(String.format(PERSON_NOT_FOUND_MESSAGE, id));
        } else {
        personRepository.deleteById(id);
        }

    }

    public void replacePerson(String id, Person person) {
        Optional<Person> byId = personRepository.findById(id);
        if (byId.isEmpty()) {
            throw new PersonNotFoundException(String.format(PERSON_NOT_FOUND_MESSAGE, id));
        } else {
            person.setId(id);
            personRepository.save(person);
        }
    }


    public Person getPersonById(String id) {
        Optional<Person> byId = personRepository.findById(id);
        if (byId.isEmpty()) {
            throw new PersonNotFoundException(String.format(PERSON_NOT_FOUND_MESSAGE, id));
        } else  {
            return byId.get();
        }
    }
}
