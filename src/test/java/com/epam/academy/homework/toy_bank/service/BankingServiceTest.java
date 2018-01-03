package com.epam.academy.homework.toy_bank.service;

import com.epam.academy.homework.toy_bank.springconfig.SpringConfigTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = SpringConfigTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class BankingServiceTest {

    @Before
    public void setUp() {
    }

    @Test
    public void testSetInterestRates() {
    }

    @Test
    public void addClient() {
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