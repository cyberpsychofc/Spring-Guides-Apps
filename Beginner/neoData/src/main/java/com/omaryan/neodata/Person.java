package com.omaryan.neodata;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

//Added to N4j DB when objects of this class are instantiated
@Node
public class Person {
    @Id
    @GeneratedValue private Long id;

    private String name;
    private Person(){
        // Empty constructor required as of Neo4j API
    }
    public Person(String name){
        this.name = name;
    }
/**
 * Neo4j doesn't REALLY have bi-directional relationships. It just means when querying
 * to ignore the direction of the relationship.
 */
    //Relationship of each node w/ its correlated nodes
    @Relationship(type = "TEAMMATE")
    public Set<Person> teammates;
    //to link nodes together
    public void worksWith(Person person){
        if(teammates == null){
            teammates = new HashSet<>();
        }
        teammates.add(person);
    }
    public String toString(){
        return this.name + "'s teammates => " +
                Optional.ofNullable(this.teammates).orElse(Collections.emptySet()
                ).stream().map(Person::getName).collect(Collectors.toList());
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
}
