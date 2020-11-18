package jpp.addressbook;

import java.time.LocalDate;

public class AddressBookFactory {

    public static Contact createContact(int id, Salutation salutation, String firstName, String lastName, LocalDate birthday, String streetAddress, int
            zipCode, String city, Long phone, String eMail) {
        Contact con =new Kontakt(id,salutation,firstName,lastName,birthday,streetAddress,zipCode,city,phone,eMail);
        return con;
    }

    public static AddressBook createAddressBook() {
        AddressBook ad = new Adressbuch();
        return ad;
    }

    public static AddressBookUtil createAddressBookUtil(AddressBook addressBook) {
        AddressBookUtil bla = new AdressbuchUtil(addressBook);
        return bla;
    }
}
