package com.epam.academy.homework.toy_bank.springconfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.epam.academy.homework.toy_bank.service")
public class SpringConfigService {
}
