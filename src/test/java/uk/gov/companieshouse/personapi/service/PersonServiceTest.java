package uk.gov.companieshouse.personapi.service;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.personapi.TestParent;
import uk.gov.companieshouse.personapi.exception.PersonNotFoundException;
import uk.gov.companieshouse.personapi.repository.PersonRepository;

import java.util.Optional;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PersonServiceTest extends TestParent {
   @Mock
    private PersonRepository personRepository;
   @InjectMocks
    private PersonService personService;
   @Test
   void testGetPeople(){
       when(personRepository.findAll()).thenReturn(people);
       var expected = people;
       var actual = personService.getPeople();
       assertThat(actual,is(expected));
   }

   @Test
    void testGetPersonByIdSuccessful(){
       when(personRepository.findById(PERSON_ID)).thenReturn(Optional.of(person));
       var expected = person;
       var actual = personService.getPersonById(PERSON_ID);
       assertThat(actual,is(expected));

   }
   @Test
    void testGetPersonByIdThrowsPersonNotFoundException(){
       when(personRepository.findById(PERSON_ID)).thenReturn(Optional.empty());
       assertThrows(PersonNotFoundException.class,()->
               personService.getPersonById(PERSON_ID));


   }

    @Test
    void testReplacePersonThrowsPersonNotFoundException(){
        when(personRepository.findById(PERSON_ID)).thenReturn(Optional.empty());
        assertThrows(PersonNotFoundException.class,()->
                personService.replacePerson(PERSON_ID,personTwo));


    }

    @Test
    void testDeletePersonThrowsPersonNotFoundException(){
        when(personRepository.findById(PERSON_ID)).thenReturn(Optional.empty());
        assertThrows(PersonNotFoundException.class,()->
                personService.deletePerson(PERSON_ID));


    }
}
