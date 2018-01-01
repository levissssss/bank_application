package com.epam.academy.homework.toy_bank.service;

import com.epam.academy.homework.toy_bank.dao.ClientDao;
import com.epam.academy.homework.toy_bank.domain.Client;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

public class TransferService {
    private ClientDao clientDao;

    @Resource
    private PlatformTransactionManager platformTransactionManager;

    @Transactional
    void transferMoney(Client sender, Client receiver, BigDecimal amount) {
        if (clientCanAfford(sender, amount)) {
            //TODO where to subtract money?
        }
    }

    private boolean clientCanAfford(Client client, BigDecimal amount) {
        return client.getLiquidBalance().compareTo(amount) > 0;
    }
}
