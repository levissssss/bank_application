package com.epam.academy.homework.toy_bank.dao;

import com.epam.academy.homework.toy_bank.domain.Bank;

public interface BankDao {
    void updateBank(Bank bank);

    void makeBank(Bank bank);
    Bank getBank();
}
