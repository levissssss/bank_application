package com.epam.academy.homework.toy_bank.springconfig;

import com.epam.academy.homework.toy_bank.dao.BankDao;
import com.epam.academy.homework.toy_bank.dao.ClientDao;
import com.epam.academy.homework.toy_bank.dao.JpaDao.JpaBankDao;
import com.epam.academy.homework.toy_bank.dao.JpaDao.JpaClientDao;
import com.epam.academy.homework.toy_bank.dao.JpaDao.JpaPersonDao;
import com.epam.academy.homework.toy_bank.dao.PersonDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfigDao {
    @Bean
    ClientDao clientDao() {
        return new JpaClientDao();
    }

    @Bean
    BankDao bankDao() {
        return new JpaBankDao();
    }

    @Bean
    PersonDao personDao() {
        return new JpaPersonDao();
    }
}
