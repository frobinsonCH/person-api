package uk.gov.companieshouse.personapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.personapi.model.Person;
import uk.gov.companieshouse.personapi.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {


    private PersonRepository personRepository;
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
        personRepository.deleteById(id);

    }

    public void replacePerson(String id, Person person) {
        Optional<Person> byId = personRepository.findById(id);
        if (byId.isPresent()) {
            person.setId(id);
            personRepository.save(person);

        }
        //add some sort of error to report person not found
    }


    public Person getPersonById(String id) {
        Optional<Person> byId = personRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        } else return null;
    }
}
