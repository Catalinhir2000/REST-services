package com.example.palab11.controllers;

import com.example.palab11.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/person")

public class PersonController {
    private final List<Person> persons = new ArrayList<>();
    public PersonController() {
        persons.add(new Person("Ion", 0));
        persons.add(new Person("Gheorghe", 1));
        persons.add(new Person("Andrei", 2));
        persons.add(new Person("Gigel", 3));
        persons.add(new Person("Alin", 4));

        persons.get(0).addFriend(persons.get(1));
        persons.get(0).addFriend(persons.get(2));
        persons.get(0).addFriend(persons.get(3));
        persons.get(3).addFriend(persons.get(4));
    }
    @GetMapping
    public List<Person> getPersons() {
        return persons;
    }
    @GetMapping("/count")
    public int countPersons() {
        return persons.size();
    }
    @GetMapping("/{id}")
    public Person getPerson(@PathVariable("id") int id) {
        return persons.stream()
                .filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    @PostMapping
    public int createPerson(@RequestParam String name) {
        int id = persons.size();
        persons.add(new Person(name, id));
        return id;
    }

    @PostMapping(value = "/obj", consumes="application/json")
    public ResponseEntity<String>
    createProduct(@RequestBody Person person) {
        persons.add(person);
        return new ResponseEntity<>(
                "Product created successfully", HttpStatus.CREATED);
    }

    @PutMapping("person/{id}")
    public ResponseEntity<String> updatePerson(
            @PathVariable int id, @RequestParam String name) {
        Person person = findById(id);
        if (person == null) {
            return new ResponseEntity<>(
                    "Person not found", HttpStatus.NOT_FOUND); //or GONE
        }
        person.setName(name);
        return new ResponseEntity<>(
                "Person updated successsfully", HttpStatus.OK);
    }

    private Person findById(int id) {
        return persons.stream()
                .filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable int id) {
        Person person = findById(id);
        if (person == null) {
            return new ResponseEntity<>(
                    "Person not found", HttpStatus.GONE);
        }
        persons.remove(person);
        return new ResponseEntity<>("Person removed", HttpStatus.OK);
    }

    @GetMapping("/friends")
    public List<Person> getFriends(@RequestParam int id) {
        Person person = findById(id);
        if (person == null) {
            return null;
        }
        return person.getFriends();
    }
    @GetMapping("/popular")
    public List<Person> mostPopularPerson(@RequestParam int k) {
        return persons.stream()
                .sorted((p1, p2) -> p2.getFriends().size() - p1.getFriends().size())
                .limit(k).collect(Collectors.toList());
    }

    @PutMapping("/friend")
    public ResponseEntity<String> addFriend(
            @RequestParam int id, @RequestParam int id2) {
        Person person = findById(id);
        Person friend = findById(id2);
        if (person == null || friend == null) {
            return new ResponseEntity<>(
                    "Person not found", HttpStatus.NOT_FOUND);
        }
        person.addFriend(friend);
        return new ResponseEntity<>(
                "Friend added", HttpStatus.OK);
    }


}
