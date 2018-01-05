package com.epam.academy.homework.toy_bank.dao.JpaDao;

import com.epam.academy.homework.toy_bank.dao.BankDao;
import com.epam.academy.homework.toy_bank.domain.Bank;

public class JpaBankDao extends GenericJpaDao implements BankDao {

    @Override
    public void makeBank(Bank bank) {
        entityManager.persist(bank);
    }

    @Override
    public Bank getBank() {
        return entityManager.createQuery("select b from Bank b", Bank.class).getSingleResult();
    }

}
