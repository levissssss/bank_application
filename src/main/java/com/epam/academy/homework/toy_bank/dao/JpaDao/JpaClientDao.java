package com.epam.academy.homework.toy_bank.dao.JpaDao;

import com.epam.academy.homework.toy_bank.dao.ClientDao;
import com.epam.academy.homework.toy_bank.domain.Client;
import com.epam.academy.homework.toy_bank.domain.Person;

import java.util.List;

public class JpaClientDao extends GenericJpaDao implements ClientDao {

    @Override
    public void save(Client client) {
        entityManager.persist(client);
    }

    @Override
    public Client findByFirstName(String firstName) {
        return entityManager.createQuery("select c from Client c join c.person p where p.firstName = :firstName", Client.class)
                .setParameter("firstName", firstName)
                .getSingleResult();
    }

    @Override
    public Client findByDetails(Person toSearch) {
        return entityManager.createQuery("select c from Client c where c.person  = :person", Client.class)
                .setParameter("person", toSearch)
                .getSingleResult();
    }

    @Override
    public List<Client> getAllClients() {
        return entityManager.createQuery("select c from Client c", Client.class).getResultList();
    }

}
