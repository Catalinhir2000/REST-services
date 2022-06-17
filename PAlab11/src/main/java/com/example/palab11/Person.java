package com.example.palab11;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private String name;
    private int id;
    @JsonBackReference
    private List<Person> friends = new ArrayList<>();

    public Person()
    {

    }

    public Person(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public void addFriend(Person p) {
        friends.add(p);
        p.getFriends().add(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Person> getFriends() {
        return friends;
    }

    public void setFriends(List<Person> friends) {
        this.friends = friends;
    }
}
