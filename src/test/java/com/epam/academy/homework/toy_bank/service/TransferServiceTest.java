package com.epam.academy.homework.toy_bank.service;

import com.epam.academy.homework.toy_bank.dao.BankDao;
import com.epam.academy.homework.toy_bank.dao.ClientDao;
import com.epam.academy.homework.toy_bank.domain.Bank;
import com.epam.academy.homework.toy_bank.domain.Client;
import com.epam.academy.homework.toy_bank.domain.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TransferServiceTest {

    @InjectMocks
    private TransferService transferService;

    //Spy mocks, because we want to use the default interface implementations

    @Spy
    private ClientDao clientDaoMock;

    @Spy
    private BankDao bankDaoMock;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void transferMoneyTestClientCanAfford() {
        //Given
        Person sender = new Person();
        Person receiver = new Person();
        Client senderClient = new Client();
        senderClient.setAmountDeposited(BigDecimal.valueOf(5000));
        Client receiverClient = new Client();
        BigDecimal amount = BigDecimal.valueOf(3000);

        //When
        when(clientDaoMock.findByDetails(sender)).thenReturn(senderClient);
        when(clientDaoMock.findByDetails(receiver)).thenReturn(receiverClient);
        transferService.transferMoney(sender, receiver, amount);

        //Then
        verify(clientDaoMock).clientCanAfford(senderClient, amount);
        Assert.assertEquals(BigDecimal.valueOf(2000), senderClient.getAmountDeposited());
        Assert.assertEquals(BigDecimal.valueOf(3000), receiverClient.getAmountDeposited());
    }

    @Test
    public void transferMoneyTestClientCantAfford() {
        //Given
        Person sender = new Person();
        Person receiver = new Person();
        Client senderClient = new Client();
        senderClient.setAmountDeposited(BigDecimal.valueOf(2000));
        Client receiverClient = new Client();
        BigDecimal amount = BigDecimal.valueOf(3000);

        //When
        when(clientDaoMock.findByDetails(sender)).thenReturn(senderClient);
        when(clientDaoMock.findByDetails(receiver)).thenReturn(receiverClient);
        transferService.transferMoney(sender, receiver, amount);

        //Then
        verify(clientDaoMock).clientCanAfford(senderClient, amount);
        Assert.assertEquals(BigDecimal.valueOf(2000), senderClient.getAmountDeposited());
        Assert.assertEquals(BigDecimal.valueOf(0), receiverClient.getAmountDeposited());
    }

    @Test
    public void retrieveCashTestClientAndBankCanAfford() {
        //Given
        Person person = new Person();
        Client client = new Client();
        Bank bank = new Bank();
        bank.setVaultBalance(BigDecimal.valueOf(10000));
        client.setAmountDeposited(BigDecimal.valueOf(5000));
        BigDecimal amount = BigDecimal.valueOf(3000);

        //When
        when(clientDaoMock.findByDetails(person)).thenReturn(client);
        when(bankDaoMock.getBank()).thenReturn(bank);
        transferService.retrieveCash(person, amount);

        //Then
        verify(clientDaoMock).clientCanAfford(client, amount);
        verify(bankDaoMock).bankCanAfford(amount);
        Assert.assertEquals(BigDecimal.valueOf(2000), client.getAmountDeposited());
        Assert.assertEquals(BigDecimal.valueOf(7000), bank.getVaultBalance());
    }

    @Test
    public void retrieveCashTestClientCantAfford() {
        //Given
        Person person = new Person();
        Client client = new Client();
        Bank bank = new Bank();
        bank.setVaultBalance(BigDecimal.valueOf(10000));
        client.setAmountDeposited(BigDecimal.valueOf(2000));
        BigDecimal amount = BigDecimal.valueOf(3000);

        //When
        when(clientDaoMock.findByDetails(person)).thenReturn(client);
        when(bankDaoMock.getBank()).thenReturn(bank);
        transferService.retrieveCash(person, amount);

        //Then
        verify(clientDaoMock).clientCanAfford(client, amount);
        Assert.assertEquals(BigDecimal.valueOf(2000), client.getAmountDeposited());
        Assert.assertEquals(BigDecimal.valueOf(10000), bank.getVaultBalance());
    }

    @Test
    public void retrieveCashTestBankCantAfford() {
        //Given
        Person person = new Person();
        Client client = new Client();
        Bank bank = new Bank();
        bank.setVaultBalance(BigDecimal.valueOf(2000));
        client.setAmountDeposited(BigDecimal.valueOf(5000));
        BigDecimal amount = BigDecimal.valueOf(3000);

        //When
        when(clientDaoMock.findByDetails(person)).thenReturn(client);
        when(bankDaoMock.getBank()).thenReturn(bank);
        transferService.retrieveCash(person, amount);

        //Then
        verify(bankDaoMock).bankCanAfford(amount);
        Assert.assertEquals(BigDecimal.valueOf(5000), client.getAmountDeposited());
        Assert.assertEquals(BigDecimal.valueOf(2000), bank.getVaultBalance());
    }
}