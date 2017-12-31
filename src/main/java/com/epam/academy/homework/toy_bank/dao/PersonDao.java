package com.epam.academy.homework.toy_bank.dao;

import com.epam.academy.homework.toy_bank.domain.Person;

public interface PersonDao {
    Person findByName(String firstName, String lastName);

    void save(Person person);

    void changeDetails(Person person);
}
