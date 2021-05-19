package uk.gov.companieshouse.personapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.companieshouse.personapi.exception.ErrorResponse;
import uk.gov.companieshouse.personapi.exception.PersonNotFoundException;
import uk.gov.companieshouse.personapi.model.Person;
import uk.gov.companieshouse.personapi.service.PersonService;

import java.time.LocalTime;
import java.util.List;

@RestController
public class PersonController {

    private final PersonService personService;
    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping("/people")
    public ResponseEntity<List<Person>> getPeople(){
        List<Person> people = personService.getPeople();
        return new ResponseEntity<>(people,HttpStatus.OK);

    }

    @PostMapping("/add-person")
    public ResponseEntity<Person> addPerson(@RequestBody Person person){

        personService.addPerson(person);
        return new ResponseEntity<>(person,HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-person/{id}")
    public ResponseEntity<HttpStatus> deletePerson(@PathVariable String id){
        personService.deletePerson(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/replace-person/{id}")
    public ResponseEntity<Person> replacePerson(@PathVariable String id,@RequestBody Person person){
        personService.replacePerson(id,person);

        return new ResponseEntity<>(person,HttpStatus.OK);
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable String id){
        Person byId =  personService.getPersonById(id);
            return new ResponseEntity<>(byId,HttpStatus.OK);

    }
    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePersonNotFoundException(PersonNotFoundException exception){
        ErrorResponse errorResponse = new ErrorResponse(LocalTime.now(),exception.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);

    }
}
