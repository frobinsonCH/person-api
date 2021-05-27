package uk.gov.companieshouse.personapi.controller;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.companieshouse.personapi.exception.PersonNotFoundException;
import uk.gov.companieshouse.personapi.model.Address;
import uk.gov.companieshouse.personapi.model.Person;
import uk.gov.companieshouse.personapi.service.PersonService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {
    private static final String PERSON_ID = "abcd";
    private static final String ERROR_MESSAGE = "Test successful";

    @Autowired
    private MockMvc mvc;
    @MockBean
    private PersonService personService;

    private Address address;
    private Person person;
    private Person obj;public List<Object> people;


    @BeforeEach
    void setUpTests(){
        address = new Address("10", "Downing Street",
                "London", "Greater London", "LN10 6RS", "England");
        person = new Person("Mr", "Joris", "Bohnson", "Boris", "England",
                "British", LocalDate.of(1965, 12, 6), "MinePrinister", address);
        obj = new Person("Mr", "Gloris", "Glonson", "Jaris", "Wales",
                "British", LocalDate.of(1956, 1, 6), "Prime Mincer", address);
        person.setId(PERSON_ID);
        obj.setId("Blah Blah Blah");
        people = new ArrayList<>();
        people.add(person);
        people.add(obj);
    }

    @Test
    void testGetPersonByIdSuccessful() throws Exception {

        when(personService.getPersonById(PERSON_ID)).thenReturn(person);
        mvc.perform(get("/person/" + PERSON_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.forename",is(person.getForename())))
                .andExpect(jsonPath("$.address.postcode",is(person.getAddress().getPostcode())));

    }

    @Test
    void testGetPersonByIdThrowsPersonNotFoundException() throws Exception {
        when(personService.getPersonById(PERSON_ID)).thenThrow(new PersonNotFoundException(ERROR_MESSAGE));
        mvc.perform(get("/person/" + PERSON_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp",any(String.class)))
                .andExpect(jsonPath("$.message",is(ERROR_MESSAGE)));

    }

    @Test
    void testGetPeopleSuccessful() throws Exception {
        Person person1 = (Person) people.get(0);
        Person person2 = (Person) people.get(1);
        List<Person> people1 = new ArrayList<>();
        people1.add(person1);
        people1.add(person2);
        when(personService.getPeople()).thenReturn(people1);

        mvc.perform(get("/people"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].forename", is(person.getForename())))
                .andExpect(jsonPath("$[1].forename", is(obj.getForename())));
    }

    @Test
    void testDeletePersonReturnsOkWhenSuccessful() throws Exception {
        doNothing().when(personService).deletePerson(PERSON_ID); // assumes successful deletion as no exceptions thrown
        mvc.perform(delete("/delet-person/" + PERSON_ID))
                .andExpect(status().isOk());
    }

    @Test
    void test() throws Exception {
        doThrow(new Exception("error")).when(personService).deletePerson(PERSON_ID);

        mvc.perform(delete("/delete-person/" + PERSON_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp", any(String.class)))
                .andExpect(jsonPath("$.message", is(ERROR_MESSAGE)));
    }
}
