package com.epam.academy.homework.toy_bank;

import com.epam.academy.homework.toy_bank.service.BankingService;
import com.epam.academy.homework.toy_bank.springconfig.SpringConfigJpa;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;

public class App {
    private final BankingService bankingService;

    private App() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfigJpa.class);
        bankingService = context.getBean(BankingService.class);
    }

    public static void main(String args[]) {
        App app = new App();

        app.homeworkExample();
    }

    private void homeworkExample() {
        BankingService bankingService = new BankingService(BigDecimal.valueOf(0.1), BigDecimal.valueOf(0.2));
        bankingService.addClient("Alan", "", LocalDate.MIN, BigDecimal.valueOf(2000));
        bankingService.addClient("Ben", "", LocalDate.MIN, BigDecimal.valueOf(2000));
        bankingService.addClient("Cecil", "", LocalDate.MIN, BigDecimal.valueOf(2000));

    }
}
