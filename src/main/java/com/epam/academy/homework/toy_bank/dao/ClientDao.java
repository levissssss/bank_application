package com.epam.academy.homework.toy_bank.dao;

import com.epam.academy.homework.toy_bank.domain.Client;
import com.epam.academy.homework.toy_bank.domain.Person;

import java.util.List;

public interface ClientDao {

    void updateClient(Client client);

    void newClient(Client client);
    Client findByName(String firstName, String lastName);

    Client findByDetails(Person toSearch);
    List<Client> getAllClients();
}
