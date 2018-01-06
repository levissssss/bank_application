package com.epam.academy.homework.toy_bank;

import com.epam.academy.homework.toy_bank.dao.PersonDao;
import com.epam.academy.homework.toy_bank.service.BankingService;
import com.epam.academy.homework.toy_bank.service.TransferService;
import com.epam.academy.homework.toy_bank.springconfig.SpringConfigDao;
import com.epam.academy.homework.toy_bank.springconfig.SpringConfigJpa;
import com.epam.academy.homework.toy_bank.springconfig.SpringConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;

public class App {
    private final BankingService bankingService;
    private final TransferService transferService;
    private final PersonDao personDao;
    private final Logger log = LoggerFactory.getLogger(App.class);
    private App() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfigJpa.class, SpringConfigDao.class, SpringConfigService.class);
        bankingService = context.getBean(BankingService.class);
        transferService = context.getBean(TransferService.class);
        personDao = context.getBean(PersonDao.class);
    }

    public static void main(String args[]) {
        App app = new App();
        app.homeworkExample();
    }
    private void homeworkExample() {
        //The lending rate is 0.1% per day, the borrowing is 0.2% per day.
        bankingService.setInterestRates(BigDecimal.valueOf(0.002), BigDecimal.valueOf(0.001));

        //Alan, Ben and Cecil all have 2000 HUF on there account
        bankingService.addClient("Alan", "", LocalDate.now(), BigDecimal.valueOf(2000));
        bankingService.addClient("Ben", "", LocalDate.now(), BigDecimal.valueOf(2000));
        bankingService.addClient("Cecil", "", LocalDate.now(), BigDecimal.valueOf(2000));
        log.warn("Opening accounts:");
        bankingService.printStatus();

        //Alan and Cecil decides to lend 1000 HUF to the bank.
        bankingService.lendMoneyToBank(BigDecimal.valueOf(1000), personDao.findByFirstName("Alan"));
        bankingService.lendMoneyToBank(BigDecimal.valueOf(1000), personDao.findByFirstName("Cecil"));
        log.warn("Lending money to bank");
        bankingService.printStatus();

        //Ben needs 4000 HUF, so decides to take all his money out of the bank and also borrow 1000 HUF, and asks Cecil to send 1000 HUF as gift to Ben.
        bankingService.lendMoneyToClient(BigDecimal.valueOf(1000), personDao.findByFirstName("Ben"));
        transferService.transferMoney(personDao.findByFirstName("Cecil"), personDao.findByFirstName("Ben"), BigDecimal.valueOf(1000));
        transferService.retrieveCash(personDao.findByFirstName("Ben"), BigDecimal.valueOf(4000));
        log.warn("Borrowing transferring and taking out money");
        bankingService.printStatus();

        //Next midnight the interest calculation is run, after that, the following balances will be set:
        bankingService.payInterestsForDay();
        log.warn("Paying interests");
        bankingService.printStatus();
    }

}
