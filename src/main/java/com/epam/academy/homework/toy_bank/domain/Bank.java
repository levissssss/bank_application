package com.epam.academy.homework.toy_bank.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Bank {
    @Id
    @GeneratedValue
    private int id;

    private BigDecimal vaultBalance;

    @OneToMany
    private List<Client> clients;

    private BigDecimal lendingInterest;
    private BigDecimal depositInterest;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public BigDecimal getVaultBalance() {
        return vaultBalance;
    }

    public void setVaultBalance(BigDecimal vaultBalance) {
        this.vaultBalance = vaultBalance;
    }

    public BigDecimal getLendingInterest() {
        return lendingInterest;
    }

    public void setLendingInterest(BigDecimal lendingInterest) {
        this.lendingInterest = lendingInterest;
    }

    public BigDecimal getDepositInterest() {
        return depositInterest;
    }

    public void setDepositInterest(BigDecimal depositInterest) {
        this.depositInterest = depositInterest;
    }
}
