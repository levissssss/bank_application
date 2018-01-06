package com.epam.academy.homework.toy_bank.service;

import com.epam.academy.homework.toy_bank.dao.BankDao;
import com.epam.academy.homework.toy_bank.dao.ClientDao;
import com.epam.academy.homework.toy_bank.dao.PersonDao;
import com.epam.academy.homework.toy_bank.domain.Bank;
import com.epam.academy.homework.toy_bank.domain.Client;
import com.epam.academy.homework.toy_bank.domain.Person;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


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
        BigDecimal amountDeposited = BigDecimal.valueOf(5000);
        LocalDate dateOfBirth = LocalDate.of(2017, 1, 1);
        Bank bank = new Bank();
        bank.setClients(new ArrayList<>());

        //When
        when(bankDao.getBank()).thenReturn(bank);
        bankingService.addClient(firstName, lastName, dateOfBirth, amountDeposited);
        ArgumentCaptor<Person> createdPerson = ArgumentCaptor.forClass(Person.class);
        ArgumentCaptor<Client> createdClient = ArgumentCaptor.forClass(Client.class);

        //Then
        verify(personDao).save(createdPerson.capture());
        verify(clientDao).save(createdClient.capture());
        verify(bankDao).addClient(createdClient.capture());
        verify(bankDao).putInVault(amountDeposited);

        Assert.assertEquals(firstName, createdPerson.getValue().getFirstName());
        Assert.assertEquals(lastName, createdPerson.getValue().getLastName());
        Assert.assertEquals(dateOfBirth, createdPerson.getValue().getDateOfBirth());
        Assert.assertEquals(createdClient.getValue().getPerson(), createdPerson.getValue());
        Assert.assertEquals(amountDeposited, createdClient.getValue().getAmountDeposited());
        Assert.assertEquals(1, bank.getClients().size());
        Assert.assertEquals(createdClient.getValue(), bank.getClients().get(0));
        Assert.assertEquals(amountDeposited, bank.getVaultBalance());
    }

    @Test
    public void lendMoneyToBankTestClientCanAfford() {
        //Given
        Person person = new Person();
        Client client = new Client();
        client.setAmountDeposited(BigDecimal.valueOf(5000));

        //When
        when(clientDao.findByDetails(person)).thenReturn(client);
        bankingService.lendMoneyToBank(BigDecimal.valueOf(3000), person);

        //Then
        Assert.assertEquals(BigDecimal.valueOf(2000), client.getAmountDeposited());
        Assert.assertEquals(BigDecimal.valueOf(3000), client.getAmountLentToBank());
    }

    @Test
    public void lendMoneyToBankTestClientCantAfford() {
        //Given
        Person person = new Person();
        Client client = new Client();
        client.setAmountDeposited(BigDecimal.valueOf(2000));

        //When
        when(clientDao.findByDetails(person)).thenReturn(client);
        bankingService.lendMoneyToBank(BigDecimal.valueOf(3000), person);

        //Then
        Assert.assertEquals(BigDecimal.valueOf(2000), client.getAmountDeposited());
        Assert.assertEquals(BigDecimal.valueOf(0), client.getAmountLentToBank());
    }
    @Test
    public void lendMoneyToClientTestBankCanAfford() {
        //Given
        Person person = new Person();
        Client client = new Client();
        Bank bank = new Bank();
        bank.setVaultBalance(BigDecimal.valueOf(5000));

        //When
        when(bankDao.getBank()).thenReturn(bank);
        when(clientDao.findByDetails(person)).thenReturn(client);
        bankingService.lendMoneyToClient(BigDecimal.valueOf(3000), person);

        //Then
        Assert.assertEquals(BigDecimal.valueOf(3000), client.getAmountDeposited());
        Assert.assertEquals(BigDecimal.valueOf(3000), client.getAmountLentByBank());
    }

    @Test
    public void lendMoneyToClientTestBankCantAfford() {
        //Given
        Person person = new Person();
        Client client = new Client();
        Bank bank = new Bank();
        bank.setVaultBalance(BigDecimal.valueOf(2000));

        //When
        when(bankDao.getBank()).thenReturn(bank);
        when(clientDao.findByDetails(person)).thenReturn(client);
        bankingService.lendMoneyToClient(BigDecimal.valueOf(3000), person);

        //Then
        Assert.assertEquals(BigDecimal.valueOf(0), client.getAmountDeposited());
        Assert.assertEquals(BigDecimal.valueOf(0), client.getAmountLentByBank());
    }

    @Test
    public void payInterestsForDayTestBorrowingClientsDebtShouldIncrease() {
        //Given
        Client client1 = new Client();
        Client client2 = new Client();
        Client client3 = new Client();
        List<Client> clients = new ArrayList<>();
        clients.add(client1);
        clients.add(client2);
        clients.add(client3);
        Bank bank = new Bank();
        bank.setLendingInterest(BigDecimal.valueOf(0.01));
        client1.setAmountLentByBank(BigDecimal.valueOf(1000));
        client2.setAmountLentByBank(BigDecimal.valueOf(100));
        //client 3 has no debt

        //When
        when(bankDao.getBank()).thenReturn(bank);
        when(clientDao.getAllClients()).thenReturn(clients);
        bankingService.payInterestsForDay();

        //Then
        //Need hamcrest matcher here since BigDecimals aren't equal in different scales
        assertThat(BigDecimal.valueOf(1010), Matchers.comparesEqualTo(client1.getAmountLentByBank()));
        assertThat(BigDecimal.valueOf(101), Matchers.comparesEqualTo(client2.getAmountLentByBank()));
        assertThat(BigDecimal.valueOf(0), Matchers.comparesEqualTo(client3.getAmountLentByBank()));
    }

    @Test
    public void payInterestsForDayTestBankDebtShouldIncrease() {
        //Given
        Client client1 = new Client();
        Client client2 = new Client();
        Client client3 = new Client();
        List<Client> clients = new ArrayList<>();
        clients.add(client1);
        clients.add(client2);
        clients.add(client3);
        Bank bank = new Bank();
        bank.setDepositInterest(BigDecimal.valueOf(0.01));
        client1.setAmountLentToBank(BigDecimal.valueOf(1000));
        client2.setAmountLentToBank(BigDecimal.valueOf(100));
        //client 3 hasn't lent money

        //When
        when(bankDao.getBank()).thenReturn(bank);
        when(clientDao.getAllClients()).thenReturn(clients);
        bankingService.payInterestsForDay();

        //Then
        //Need hamcrest matcher here since BigDecimals aren't equal in different scales
        assertThat(BigDecimal.valueOf(1010), Matchers.comparesEqualTo(client1.getAmountLentToBank()));
        assertThat(BigDecimal.valueOf(101), Matchers.comparesEqualTo(client2.getAmountLentToBank()));
        assertThat(BigDecimal.valueOf(0), Matchers.comparesEqualTo(client3.getAmountLentToBank()));
    }
}