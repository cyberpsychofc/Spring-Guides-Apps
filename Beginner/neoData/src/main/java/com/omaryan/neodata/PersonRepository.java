package com.omaryan.neodata;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import java.util.List;

public interface PersonRepository extends Neo4jRepository<Person,Long> {
    Person findByName(String name); //seeks nodes of type Person
    List<Person> findByTeammatesName(String name);
    //looks for a Person node, drills into each entry of the teammates field and matches based on the teammate's
}
