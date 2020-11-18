package jpp.addressbook.gui;

import jpp.addressbook.Contact;
import jpp.addressbook.Kontakt;
import jpp.addressbook.Salutation;

public class Person {
    Integer id;
    Salutation salutation;
    String firstName;
    String lastName;

    public Person(Integer id, Salutation salutation, String firstName, String lastName) {
        this.id=id;
        this.salutation = salutation;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public Salutation getSalutation() {
        return salutation;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSalutation(Salutation salutation) {
        this.salutation = salutation;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
