package com.epam.academy.homework.toy_bank.springconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({SpringConfigTestDB.class, SpringConfigDao.class})
@Configuration
public class SpringConfigTest {
}
