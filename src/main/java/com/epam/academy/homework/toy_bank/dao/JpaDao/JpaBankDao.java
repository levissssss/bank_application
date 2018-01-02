package com.epam.academy.homework.toy_bank.dao.JpaDao;

import com.epam.academy.homework.toy_bank.dao.BankDao;
import com.epam.academy.homework.toy_bank.domain.Bank;
import com.epam.academy.homework.toy_bank.domain.Client;

import java.math.BigDecimal;

public class JpaBankDao extends GenericJpaDao implements BankDao {

    @Override
    public void makeBank(Bank bank) {
        entityManager.persist(bank);
    }

    @Override
    public Bank getBank() {
        return entityManager.createQuery("select b from Bank b", Bank.class).getSingleResult();
    }

    @Override
    public void addClient(Client client) {
        getBank().addClient(client);
    }

    @Override
    public void putInVault(BigDecimal amount) {
        getBank().setVaultBalance(getBank().getVaultBalance().add(amount));
    }

    @Override
    public void takeFromVault(BigDecimal amount) {
        getBank().setVaultBalance(getBank().getVaultBalance().subtract(amount));
    }

    @Override
    public boolean bankCanAfford(BigDecimal amount) {
        return getBank().getVaultBalance().compareTo(amount) >= 0;
    }
}
