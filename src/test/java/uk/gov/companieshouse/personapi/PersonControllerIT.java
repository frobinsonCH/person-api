package uk.gov.companieshouse.personapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.companieshouse.personapi.model.Person;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class PersonControllerIT extends TestParent{
    private static final String URL = "http://localhost:";

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    void testAddPersonSuccessful(){
        person.setId(null);
        var expectedPerson = person;
        var expectedStatusCode = HttpStatus.CREATED;

        HttpEntity<Person> entity = new HttpEntity<>(person,headers);
        ResponseEntity<Person> response =
                restTemplate.exchange(URL+port+"/add-person", HttpMethod.POST, entity, Person.class);
        var actualPerson = response.getBody();
        var actualStatusCode = response.getStatusCode();
        assertThat(actualPerson.getSurname(), is(expectedPerson.getSurname()));
        assertThat(actualStatusCode, is(expectedStatusCode));

    }
}
