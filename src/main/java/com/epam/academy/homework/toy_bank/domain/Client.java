package com.epam.academy.homework.toy_bank.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
public class Client {

    @OneToOne
    private Person person;
    private BigDecimal amountLentByBank = BigDecimal.valueOf(0);
    private BigDecimal amountLentToBank = BigDecimal.valueOf(0);
    private BigDecimal amountDeposited = BigDecimal.valueOf(0);
    @Id
    @GeneratedValue
    private int id;

    public BigDecimal getAmountDeposited() {
        return amountDeposited;
    }

    public void setAmountDeposited(BigDecimal amountDeposited) {
        this.amountDeposited = amountDeposited;
    }


    public BigDecimal getAmountLentByBank() {
        return amountLentByBank;
    }

    public void setAmountLentByBank(BigDecimal amountLentByBank) {
        this.amountLentByBank = amountLentByBank;
    }

    public BigDecimal getAmountLentToBank() {
        return amountLentToBank;
    }

    public void setAmountLentToBank(BigDecimal amountLentToBank) {
        this.amountLentToBank = amountLentToBank;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Client{" +
                "person=" + person +
                ", amountLentByBank=" + amountLentByBank +
                ", amountLentToBank=" + amountLentToBank +
                ", amountDeposited=" + amountDeposited +
                ", id=" + id +
                '}';
    }
}
