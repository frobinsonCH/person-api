package uk.gov.companieshouse.personapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uk.gov.companieshouse.personapi.controller.PersonController;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PersonApiApplicationTests {
	@Autowired
	private PersonController personController;

	@Test
	void contextLoads() {
		assertThat(personController).isNotNull();
	}

}
