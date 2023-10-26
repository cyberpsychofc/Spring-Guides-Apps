package com.omaryan.neodata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableNeo4jRepositories
public class NeoDataApplication {
    private final static Logger logger = LoggerFactory.getLogger(NeoDataApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(NeoDataApplication.class, args);
        System.exit(0);
    }
    @Bean
    CommandLineRunner demo(PersonRepository personRepository){
        return args -> {
            personRepository.deleteAll();

            //Nodes
            Person john = new Person("John");
            Person kanye = new Person("Kanye");
            Person wade = new Person("Wade");

            List<Person> team = Arrays.asList(john,kanye,wade);

            logger.info("Before linking up with Neo4j...");

            team.stream().forEach(person -> logger.info("\t" + person.toString()));

            personRepository.save(john);
            personRepository.save(kanye);
            personRepository.save(wade);

            john = personRepository.findByName(john.getName());
            john.worksWith(kanye);
            john.worksWith(wade);
            personRepository.save(john);

            kanye = personRepository.findByName(kanye.getName());
            kanye.worksWith(wade);

            personRepository.save(kanye);

            logger.info("Lookup Each Person by name : ");
            team.stream().forEach(person -> logger.info(
                    "\t" + personRepository.findByName(person.getName()).toString()
            ));
            List<Person> teammates = personRepository.findByTeammatesName(john.getName());
            logger.info("The following have John as a Teammate...");
            teammates.stream().forEach(person -> logger.info("\t" + person.getName()));
        };
    }
}