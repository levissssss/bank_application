package com.epam.academy.homework.toy_bank.springconfig;

import com.epam.academy.homework.toy_bank.client.CommandLineClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfigClient {

    @Bean
    CommandLineClient commandLineClient(){
        return new CommandLineClient();
    }
}
