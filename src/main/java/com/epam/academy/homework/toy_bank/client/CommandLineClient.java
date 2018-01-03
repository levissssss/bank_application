package com.epam.academy.homework.toy_bank.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class CommandLineClient {
    private Scanner scanner = new Scanner(System.in);
    private Logger log = LoggerFactory.getLogger(CommandLineClient.class);
    public void startConsole(){
        log.info("Starting bank");
    }
}
