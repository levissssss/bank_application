package com.epam.academy.homework.toy_bank.dao.JpaDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class GenericJpaDao {

    @PersistenceContext
    protected EntityManager entityManager;
}
