package jpp.addressbook.gui;

public class Email {
    String provider;
    Double anzahl;

    public Email(String povider, Double anzahl) {
        this.provider = povider;
        this.anzahl = anzahl;
    }

    public String getProvider() {
        return provider;
    }

    public Double getAnzahl() {
        return anzahl;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setAnzahl(Double anzahl) {
        this.anzahl = anzahl;
    }
}
