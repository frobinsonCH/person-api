package uk.gov.companieshouse.personapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.companieshouse.personapi.model.Address;
import uk.gov.companieshouse.personapi.model.Person;
import uk.gov.companieshouse.personapi.repository.PersonRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class PersonController {
    @Autowired
    private PersonRepository personRepository;



    @GetMapping("/people")
    public ResponseEntity<List<Person>> getPeople(){
        List<Person> people = personRepository.findAll();
        return new ResponseEntity<>(people,HttpStatus.OK);





    }

    @PostMapping("/add-person")
    public ResponseEntity<HttpStatus> addPerson(@RequestBody Person person){

        personRepository.save(person);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete-person")
    public ResponseEntity<HttpStatus> deletePerson(@RequestParam String id){
        personRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/replace-person")
    public ResponseEntity<HttpStatus> replacePerson(@RequestParam String id,@RequestBody Person person){
       Optional<Person> byId =  personRepository.findById(id);
       if(byId.isPresent()){
           person.setId(id);
           personRepository.save(person);

       }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable String id){
        Optional<Person> byId =  personRepository.findById(id);
        if(byId.isPresent()){
            return new ResponseEntity<>(byId.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
