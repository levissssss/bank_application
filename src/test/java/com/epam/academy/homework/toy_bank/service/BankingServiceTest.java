package com.epam.academy.homework.toy_bank.service;

import com.epam.academy.homework.toy_bank.dao.BankDao;
import com.epam.academy.homework.toy_bank.dao.ClientDao;
import com.epam.academy.homework.toy_bank.dao.PersonDao;
import com.epam.academy.homework.toy_bank.domain.Bank;
import com.epam.academy.homework.toy_bank.domain.Client;
import com.epam.academy.homework.toy_bank.domain.Person;
import com.epam.academy.homework.toy_bank.springconfig.SpringConfigTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfigTest.class)
public class BankingServiceTest {
    //Spy mocks, because we want to use the default interface implementations
    @Spy
    private BankDao bankDao;

    @Spy
    private ClientDao clientDao;

    @Mock
    private PersonDao personDao;

    @InjectMocks
    private BankingService bankingService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSetInterestRates() {
        //Given
        Bank bank = new Bank();
        BigDecimal depositInterest = BigDecimal.valueOf(0.001);
        bank.setDepositInterest(depositInterest);
        BigDecimal lendingInterest = BigDecimal.valueOf(10.32);
        bank.setLendingInterest(lendingInterest);
        //When
        when(bankDao.getBank()).thenReturn(bank);
        bankingService.setInterestRates(lendingInterest, depositInterest);
        //Then
        Assert.assertEquals(lendingInterest, bank.getLendingInterest());
        Assert.assertEquals(depositInterest, bank.getDepositInterest());
    }

    @Test
    public void addClientTest() {
        //Given
        String firstName = "Joe";
        String lastName = "Doe";
        int id = 1;
        BigDecimal amountDeposited = BigDecimal.valueOf(5000);
        LocalDate dateOfBirth = LocalDate.of(2017, 1, 1);

        Person person = new Person();
        person.setDateOfBirth(dateOfBirth);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setId(id);

        Client client = new Client();
        client.setPerson(person);
        client.setAmountDeposited(amountDeposited);
        client.setId(id);

        //When
        bankingService.addClient(firstName, lastName, dateOfBirth, amountDeposited);
        //Then
        verify(personDao).save(any(Person.class));
        verify(clientDao).save(any(Client.class));
        //verify(bankDao).addClient();
        //TODO
    }

    @Test
    public void lendMoneyToBank() {
    }

    @Test
    public void lendMoneyToClient() {
    }

    @Test
    public void payInterestsForDay() {
    }
}