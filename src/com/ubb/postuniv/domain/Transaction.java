package com.ubb.postuniv.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction extends Entity{
    private int medicineId;
    private int clientCardId;
    private int quantity;
    private Date transactionDateTime;

    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public Transaction(int id, int medicineId, int clientCardId, int quantity, Date transactionDateTime) {
        super(id);
        this.medicineId = medicineId;
        this.clientCardId = clientCardId;
        this.quantity = quantity;
        this.transactionDateTime = transactionDateTime;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public int getClientCardId() {
        return clientCardId;
    }

    public void setClientCardId(int clientCardId) {
        this.clientCardId = clientCardId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDate(Date transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "medicineId=" + medicineId +
                ", clientCardId=" + clientCardId +
                ", quantity=" + quantity +
                ", transactionDateTime=" + dateTimeFormat.format(transactionDateTime) +
                '}';
    }
}
