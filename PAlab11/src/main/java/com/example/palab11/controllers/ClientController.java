package com.example.palab11.controllers;


import com.example.palab11.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RestController
public class ClientController {
    @GetMapping("/flux")
    public Flux<Person> getMostPopularPersons() {
        Flux<Person> productFlux = WebClient.create()
                .get()
                .uri("http://localhost:8080/person/popular?k=2")
                .retrieve()
                .bodyToFlux(Person.class);

        productFlux.subscribe(System.out::println);

        return productFlux;
    }
}