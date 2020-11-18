package jpp.addressbook;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.Year;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.function.Predicate;

public class AdressbuchUtil implements AddressBookUtil {
    AddressBook ad;

    public AdressbuchUtil(AddressBook ad) {
        this.ad = ad;
    }

    @Override
    public double getAverageAgeAt(LocalDate reference) {
        if (ad.getContacts().isEmpty()) {
            throw new IllegalStateException();
        }
        Set<Contact> kontakte = ad.getContacts();
        int alterJahrR = reference.getYear();
        int alterMonatR = reference.getMonthValue();
        int alterTagR = reference.getDayOfMonth();

        int alerGes = 0;
        int counter = 0;
        for (Contact kont : kontakte) {
            counter++;

            LocalDate myB = kont.getBirthday();
            Period period = Period.between(myB, reference);
            int alter = period.getYears();
            alerGes += alter;

            int alterJahr = myB.getYear();
            int alterMonat = myB.getMonthValue();
            int alterTag = myB.getDayOfMonth();

            if (alterJahr > alterJahrR) {
                throw new IllegalArgumentException();
            } else if (alterJahr == alterJahrR && alterMonat > alterMonatR) {
                throw new IllegalArgumentException();
            } else if (alterJahr == alterJahrR && alterMonat == alterMonatR && alterTag > alterTagR) {
                throw new IllegalArgumentException();
            }
        }

        return (double) alerGes / (double) counter;
    }

    @Override
    public double getSalutationShare(Salutation salutation) {
        if (ad.getContacts().isEmpty()) {
            throw new IllegalStateException();
        }
        Set<Contact> cont = ad.getContacts();
        double saltu = 0.0;
        double counter=0.0;
        for (Contact con : cont) {
            counter+=1;
            if (con.getSalutation().equals(salutation)) {
                saltu += 1.0;
            }
        }
        return saltu/counter;
    }

    @Override
    public Map<String, Double> getMailProviderShare() {
        if (ad.getContacts().isEmpty()) {
            throw new IllegalStateException();
        }
        Set<Contact> cont = ad.getContacts();
        List<String> list = new ArrayList<>();
        for (Contact con : cont) {
            Optional<String> mail = con.getEMail();
            if (mail.isPresent()) {
                String s = mail.get();
                String[] provider = s.split("@");
                list.add(provider[1]);
            } else list.add("");
        }
        Map<String, Double> result = new HashMap<>();
        double anzahl = list.size();
        for (String str : list) {
            double counter = 0;
            for (int i = 0; i < list.size(); i++) {
                if (str.equals(list.get(i))) {
                    counter++;
                }
            }
            if (result.get(str) == null) {
                result.put(str, counter / anzahl);
            }

        }
        return result;
    }

    @Override
    public Predicate<Contact> lastNamePrefixFilter(String prefix) {
        if (prefix == null) {
            throw new NullPointerException();
        }
        Predicate<Contact> prefixLastname = contact -> contact.getLastName().startsWith(prefix);
        return prefixLastname;
    }

    @Override
    public Predicate<Contact> birthYearFilter(Year year) {
        int yearInt = year.getValue();
        Predicate<Contact> birthyearFilter = contact -> contact.getBirthday().getYear() == yearInt;
        return birthyearFilter;
    }

    @Override
    public Predicate<Contact> zipCodeFilter(int zipCode) {
        if (zipCode <= 0) {
            throw new IllegalArgumentException();
        }
        Predicate<Contact> zipCFilter = contact -> contact.getZipCode() == zipCode;
        return zipCFilter;
    }

    @Override
    public Predicate<Contact> eMailInfixFilter(String infix) {
        if (infix == null) {
            throw new NullPointerException();
        }
        Predicate<Contact> mailFilter = contact -> contact.getEMail().toString().contains(infix);

        return mailFilter;

    }

    @Override
    public Set<Contact> filter(Predicate<Contact> filter) {
        Set<Contact> cont = ad.getContacts();
        Set<Contact> result = new HashSet<>();
        for (Contact con : cont) {
            if (filter.test(con)) {
                result.add(con);
            }
        }
        return result;
    }

    @Override
    public Set<Contact> filterAnd(Set<Predicate<Contact>> filters) {
        Set<Contact> cont = ad.getContacts();
        Set<Contact> result = new HashSet<>();
        if (filters.isEmpty()) {
            result.addAll(cont);

        } else {
            for (Contact con : cont) {
                boolean pre = false;
                for (Predicate<Contact> predi : filters) {
                    if (predi.test(con)) {
                        pre = true;
                    } else {
                        pre = false;
                        break;
                    }
                }
                if (pre) {
                    result.add(con);
                }
            }
        }
        return result;
    }

    @Override
    public Set<Contact> filterOr(Set<Predicate<Contact>> filters) {
        Set<Contact> cont = ad.getContacts();
        Set<Contact> result = new HashSet<>();
        if (filters.isEmpty()) {
            result.addAll(cont);

        } else {
            for (Contact con : cont) {
                boolean pre = false;
                for (Predicate<Contact> predi : filters) {
                    if (predi.test(con)) {
                        pre = true;
                    }
                }
                if (pre) {
                    result.add(con);
                }
            }
        }
        return result;
    }

    /*public static void main(String[] args) {
        AddressBook ad = new Adressbuch();
        ad.importContacts("5;Herr;Ian;Jacobs;17.02.1977;Teutonenstr 126;52072; Aachen;24123269738;ian.jacobs@hotmeel.com\n" +
                "2;Frau;Milena;Schweizer;06.02.1994;Hoove 127;84032;Landshut;87138315030;milena.schweizer@hotmeel.com\n" +
                "16;Herr;Ian;Jacobs;17.02.1977;Teutonenstr. 126;52072; Aachen;24123269738;ian.jacobs@hotmeel.com");
        AddressBookUtil adU = new AdressbuchUtil(ad);
        System.out.println(adU.getSalutationShare(Salutation.HERR));


    }*/
}
