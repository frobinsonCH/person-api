package uk.gov.companieshouse.personapi.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.companieshouse.personapi.TestParent;
import uk.gov.companieshouse.personapi.exception.PersonNotFoundException;
import uk.gov.companieshouse.personapi.service.PersonService;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PersonController.class)
class  PersonControllerTest extends TestParent {


    @Autowired
    private MockMvc mvc;
    @MockBean
    private PersonService personService;


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
        doThrow(new PersonNotFoundException(ERROR_MESSAGE)).when(personService).getPersonById(PERSON_ID);
        mvc.perform(get("/person/" + PERSON_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp",any(String.class)))
                .andExpect(jsonPath("$.message",is(ERROR_MESSAGE)));

    }

    @Test
    void testGetPeopleSuccessful() throws Exception {
        when(personService.getPeople()).thenReturn(people);

        mvc.perform(get("/people"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].forename", is(person.getForename())))
                .andExpect(jsonPath("$[1].forename", is(personTwo.getForename())));
    }

    @Test
    void testDeletePersonReturnsOkWhenSuccessful() throws Exception {
        doNothing().when(personService).deletePerson(PERSON_ID); // assumes successful deletion as no exceptions thrown
        mvc.perform(delete("/delete-person/" + PERSON_ID))
                .andExpect(status().isOk());
    }

    @Test
    void testDeletePersonThrowsPersonNotFoundException() throws Exception {
        doThrow(new PersonNotFoundException(ERROR_MESSAGE)).when(personService).deletePerson(PERSON_ID);

        mvc.perform(delete("/delete-person/" + PERSON_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp", any(String.class)))
                .andExpect(jsonPath("$.message", is(ERROR_MESSAGE)));
    }
}
