package com.epam.academy.homework.toy_bank.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
public class Ledger {
    @Id
    @GeneratedValue
    private int id;

    @OneToOne
    private Client client;

    private BigDecimal amountLent;
    private BigDecimal amountDeposited;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public BigDecimal getAmountLent() {
        return amountLent;
    }

    public void setAmountLent(BigDecimal amountLent) {
        this.amountLent = amountLent;
    }

    public BigDecimal getAmountDeposited() {
        return amountDeposited;
    }

    public void setAmountDeposited(BigDecimal amountDeposited) {
        this.amountDeposited = amountDeposited;
    }
}
