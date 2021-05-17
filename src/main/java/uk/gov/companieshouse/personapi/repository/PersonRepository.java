package uk.gov.companieshouse.personapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import uk.gov.companieshouse.personapi.model.Person;

public interface PersonRepository extends MongoRepository <Person,String> {

}
