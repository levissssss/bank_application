package com.epam.academy.homework.toy_bank.dao.JpaDao;

import com.epam.academy.homework.toy_bank.dao.PersonDao;
import com.epam.academy.homework.toy_bank.domain.Person;

public class JpaPersonDao extends GenericJpaDao implements PersonDao {

    @Override
    public Person findByFirstName(String firstName) {
        return entityManager.createQuery("select p from Person p where p.firstName = :firstName", Person.class)
                .setParameter("firstName", firstName)
                .getSingleResult();
    }

    @Override
    public void save(Person person) {
        entityManager.persist(person);
    }

}
