package com.epam.academy.homework.toy_bank;

import com.epam.academy.homework.toy_bank.service.BankingService;
import com.epam.academy.homework.toy_bank.springconfig.SpringConfigJpa;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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

    }
}
