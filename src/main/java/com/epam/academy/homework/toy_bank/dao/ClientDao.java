package com.epam.academy.homework.toy_bank.dao;

import com.epam.academy.homework.toy_bank.domain.Client;

import java.util.List;

public interface ClientDao {

    void updateClient(Client client);

    Client findByName(String firstName, String lastName);

    List<Client> getAllClients();
}
