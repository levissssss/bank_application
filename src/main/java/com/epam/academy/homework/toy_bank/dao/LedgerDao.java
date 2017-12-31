package com.epam.academy.homework.toy_bank.dao;

import com.epam.academy.homework.toy_bank.domain.Client;
import com.epam.academy.homework.toy_bank.domain.Ledger;

import java.util.List;

public interface LedgerDao {
    void save(Ledger ledger);

    Ledger getLedgerOfClient(Client client);

    List<Ledger> getAllLedgers();
}
