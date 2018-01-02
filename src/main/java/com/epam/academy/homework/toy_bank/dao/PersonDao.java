package com.epam.academy.homework.toy_bank.dao;

import com.epam.academy.homework.toy_bank.domain.Person;

public interface PersonDao {
    Person findByFirstName(String firstName);
    void save(Person person);
}
