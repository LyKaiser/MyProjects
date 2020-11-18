package jpp.addressbook;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public class Kontakt implements Contact {
    private final int id;
    private Salutation salutation;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String streetAddress;
    private int zipCode;
    private String city;
    private Long phone;
    private String EMail;

    public Kontakt(int id, Salutation salutation, String firstName, String lastName, LocalDate birthday, String streetAddress, int zipCode, String city) {
        this.id = id;
        this.salutation = salutation;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.streetAddress = streetAddress;
        this.zipCode = zipCode;
        this.city = city;
    }

    public Kontakt(int id, Salutation salutation, String firstName, String lastName, LocalDate birthday, String streetAddress, int zipCode, String city, Long phone) {
        this.id = id;
        this.salutation = salutation;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.streetAddress = streetAddress;
        this.zipCode = zipCode;
        this.city = city;
        this.phone = phone;
    }

    public Kontakt(int id, Salutation salutation, String firstName, String lastName, LocalDate birthday, String streetAddress, int zipCode, String city, Long phone, String EMail) {
        this.id = id;
        this.salutation = salutation;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.streetAddress = streetAddress;
        this.zipCode = zipCode;
        this.city = city;
        this.phone = phone;
        this.EMail = EMail;
    }

    public Kontakt(int id, Salutation salutation, String firstName, String lastName, LocalDate birthday, String streetAddress, int zipCode, String city, String EMail) {
        this.id = id;
        this.salutation = salutation;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.streetAddress = streetAddress;
        this.zipCode = zipCode;
        this.city = city;
        this.EMail = EMail;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Salutation getSalutation() {
        return salutation;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public LocalDate getBirthday() {
        return birthday;
    }

    @Override
    public String getStreetAddress() {
        return streetAddress;
    }

    @Override
    public int getZipCode() {
        return zipCode;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public Optional<Long> getPhone() {
        if (phone==null){
            return Optional.empty();
        }else return Optional.ofNullable(phone);
    }

    public Optional<String> getEMail() {
        if (EMail==null){
            return Optional.empty();
        }else return Optional.ofNullable(EMail);
    }

    @Override
    public void setSalutation(Salutation salutation) {
        if (salutation==null){
            throw new NullPointerException();
        }else this.salutation = salutation;
    }

    @Override
    public void setFirstName(String firstName) {
        if (firstName==null){
            throw new NullPointerException();
        }else this.firstName = firstName;
    }

    @Override
    public void setLastName(String lastName) {

        if (lastName==null){
            throw new NullPointerException();
        }else this.lastName = lastName;
    }

    @Override
    public void setBirthday(LocalDate birthday) {

        if (birthday==null){
            throw new NullPointerException();
        }
        else if (birthday.isAfter(LocalDate.now())){
            throw new IllegalArgumentException();
        }else this.birthday = birthday;
    }

    @Override
    public void setStreetAddress(String streetAddress) {
        if (streetAddress==null){
            throw new NullPointerException();
        }else this.streetAddress = streetAddress;
    }

    @Override
    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public void setCity(String city) {
        if (city==null){
            throw new NullPointerException();
        }else this.city = city;
    }

    @Override
    public void setPhone(Long phone) {
        this.phone = phone;
    }

    @Override
    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kontakt kontakt = (Kontakt) o;
        return id == kontakt.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Kontakt{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
