package com.ubb.postuniv.domain;


import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientCard extends Entity{
    private String firstName;
    private String lastName;
    private String pin;
    private Date birthDate;
    private Date registrationDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public ClientCard(int id, String firstName, String lastName, String pin, Date birthDate, Date registrationDate) {
       super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.pin = pin;
        this.birthDate = birthDate;
        this.registrationDate = registrationDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "ClientCard{" +
                "id=" + this.getId() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pin='" + pin + '\'' +
                ", birthDate=" + dateFormat.format(birthDate) +
                ", registrationDate=" + dateFormat.format(registrationDate) +
                '}';
    }
}
