package com.epam.academy.homework.toy_bank.springconfig;

import com.epam.academy.homework.toy_bank.service.BankingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfigService {
    @Bean
    BankingService bankingService() {
        return new BankingService();
    }
}
