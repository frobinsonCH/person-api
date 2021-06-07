package uk.gov.companieshouse.personapi;

import org.junit.jupiter.api.BeforeEach;
import uk.gov.companieshouse.personapi.model.Address;
import uk.gov.companieshouse.personapi.model.Person;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestParent {
    protected static final String PERSON_ID = "abcd";
    protected static final String ERROR_MESSAGE = "Test successful";

    protected Person person;
    protected Person personTwo;
    protected List<Person> people;

    @BeforeEach
    void setUpTests(){
        Address address = new Address("10", "Downing Street",
                "London", "Greater London", "LN10 6RS", "England");
        person = new Person("Mr", "Joris", "Bohnson", "Boris", "England",
                "British", LocalDate.of(1965, 12, 6), "MinePrinister", address);
        personTwo = new Person("Mr", "Gloris", "Glonson", "Jaris", "Wales",
                "British", LocalDate.of(1956, 1, 6), "Prime Mincer", address);
        person.setId(PERSON_ID);
        personTwo.setId("Blah Blah Blah");
        people = new ArrayList<>();
        people.add(person);
        people.add(personTwo);
    }
}
