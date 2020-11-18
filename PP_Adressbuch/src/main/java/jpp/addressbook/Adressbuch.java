package jpp.addressbook;

import com.sun.jdi.Value;

import java.io.PrintStream;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Adressbuch implements AddressBook {
    private PrintStream err=System.err;
    private Map<Integer, Contact> kontakte = new TreeMap<>();

    @Override
    public void setErrorStream(PrintStream err) {
        this.err = err;
    }

    @Override
    public void importContacts(String input) {
        List<String> li = input.lines().collect(Collectors.toList());

        int counter = 0;
        int counterError=0;
        for (String s : li) {
            int id = 0;
            Salutation salutation = null;
            String vorname = null;
            String nachname = null;
            LocalDate date = null;
            String strasse = null;
            int adresse = 0;
            String ort = null;
            long telef = 0;
            String email = null;
            counter++;
            String[] str = s.split(";", -1);
            boolean stop = false;
            if (str.length != 10) {

                err.println("import error: line " + counter + " must contain exactly 10 columns");
                stop = true;
            }

            for (int i = 0; i < str.length; i++) {
                if (!stop) {
                    switch (i) {
                        case 0:
                            if (str[i].length() < 1) {
                                err.println("import error: invalid value for id: empty in line " + counter);
                                stop = true;
                                break;
                            }
                            try {
                                id = Integer.parseInt(str[i]);
                                if (id < 1) {
                                    err.println("import error: invalid value for id: " + id + " in line " + counter);
                                    stop = true;
                                    break;
                                }
                            } catch (NumberFormatException e) {
                                err.println("import error: invalid value for id: " + str[i] + " in line " + counter);
                                stop = true;
                                break;
                            }
                            break;
                        case 1:
                            if (str[i].length() < 1) {
                                err.println("import error: invalid value for salutation: empty in line " + counter);
                                stop = true;
                                break;
                            }
                            if (str[i].equals("Frau")) {
                                salutation = Salutation.FRAU;
                            } else if (str[i].equals("Herr")) {
                                salutation = Salutation.HERR;
                            } else {
                                err.println("import error: invalid value for salutation: " + str[i] + " in line " + counter);
                                stop = true;
                                break;
                            }
                            break;
                        case 2:
                            if (str[i].length() < 1) {
                                err.println("import error: invalid value for first name: empty in line " + counter);
                                stop = true;
                                break;
                            } else vorname = str[i];
                            break;
                        case 3:
                            if (str[i].length() < 1) {
                                err.println("import error: invalid value for last name: empty in line " + counter);
                                stop = true;
                                break;
                            } else nachname = str[i];
                            break;
                        case 4:
                            if (str[i].length() < 1) {
                                err.println("import error: invalid value for birthday: empty in line " + counter);
                                stop = true;
                                break;
                            }
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                            try {
                                date = LocalDate.parse(str[i], formatter);
                            } catch (Exception e) {
                                err.println("import error: invalid value for birthday: " + str[i] + " in line " + counter);
                                stop = true;
                                break;
                            }
                            break;
                        case 5:
                            if (str[i].length() < 1) {
                                err.println("import error: invalid value for street address: empty in line " + counter);
                                stop = true;
                                break;
                            } else strasse = str[i];
                            break;
                        case 6:
                            if (str[i].length() < 1) {
                                err.println("import error: invalid value for zip code: empty in line " + counter);
                                stop = true;
                                break;
                            }else {
                                try {
                                    adresse = Integer.parseInt(str[i]);
                                    if (adresse<1){
                                        err.println("import error: invalid value for zip code: " + str[i] + " in line " + counter);
                                        stop = true;
                                        break;
                                    }
                                } catch (NumberFormatException e) {
                                    err.println("import error: invalid value for zip code: " + str[i] + " in line " + counter);
                                    stop = true;
                                    break;
                                }
                            }
                            break;
                        case 7:
                            if (str[i].length() < 1) {
                                err.println("import error: invalid value for city: empty in line " + counter);
                                stop = true;
                                break;
                            } else ort = str[i];
                            break;
                        case 8:
                            if (str[i].equals("")) {
                                break;
                            } else if (str[i].startsWith("0")) {
                                err.println("import error: invalid value for phone number: " + str[i] + " in line " + counter);
                                stop = true;
                                telef = -1;
                                break;
                            } else if (str[i].length() < 1) {
                                err.println("import error: invalid value for phone number: empty in line " + counter);
                                stop = true;
                                telef = -1;
                                break;
                            } else {
                                try {
                                    telef = Long.parseLong(str[i]);
                                    if (telef < 0) {
                                        err.println("import error: invalid value for phone number: " + telef + " in line " + counter);
                                        stop = true;
                                        telef = -1;
                                        break;
                                    }

                                } catch (NumberFormatException e) {
                                    err.println("import error: invalid value for phone number: " + str[i] + " in line " + counter);
                                    stop = true;
                                    telef = -1;
                                    break;
                                }
                            }
                            break;
                        case 9:
                            if (str[i].equals("")) {
                                break;
                            } else if (str[i].length() < 1) {
                                err.println("import error: invalid value for email: empty in line " + counter);
                                email = "1";
                                break;

                            } else if (str[i].contains("@")) {
                                String[] ohneAt = str[i].split("@");
                                String[] ohnePunkt = ohneAt[1].split("\\.");
                                if (ohneAt == null | ohneAt[0].length() < 1 | ohneAt[1].length() < 1) {
                                    err.println("import error: invalid value for email: " + str[i] + " in line " + counter);
                                    email = "1";
                                    break;

                                } else if (ohneAt == null | ohnePunkt[0].length() < 1 | ohnePunkt[1].length() < 1) {
                                    err.println("import error: invalid value for email: " + str[i] + " in line " + counter);
                                    email = "1";
                                    break;

                                } else email = str[i];
                            } else {
                                err.println("import error: invalid value for email: " + str[i] + " in line " + counter);
                                email = "1";
                                break;
                            }
                            break;
                    }
                }
            }
            Contact con = null;
            if (telef != 0 && email != null && !email.equals("1") && telef != -1) {
                con = new Kontakt(id, salutation, vorname, nachname, date, strasse, adresse, ort, telef, email);
            } else if (telef != 0 && email == null && telef != -1) {
                con = new Kontakt(id, salutation, vorname, nachname, date, strasse, adresse, ort, telef);
            } else if (telef == 0 && email != null && !email.equals("1")) {
                con = new Kontakt(id, salutation, vorname, nachname, date, strasse, adresse, ort, email);
            } else if (id != 0 & salutation != null & vorname != null & nachname != null & date != null & strasse != null & adresse != 0 & ort != null && telef == 0 && email == null) {
                con = new Kontakt(id, salutation, vorname, nachname, date, strasse, adresse, ort);
            }
            if (con == null) {
                continue;
            } else kontakte.put(id, con);

        }

    }


    @Override
    public void addContact(Contact contact) {
        if (contact == null) {
            throw new NullPointerException();
        }
        int id = contact.getId();
        kontakte.put(id, contact);
    }

    @Override
    public Optional<Contact> getContact(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException();
        }
        if (kontakte.get(id) == null) {
            return Optional.empty();
        } else return Optional.of(kontakte.get(id));
    }

    @Override
    public Set<Contact> getContacts() {
        Collection<Contact> con = kontakte.values();
        Set<Contact> kontakteSet = new HashSet<>(con);
        return kontakteSet;
    }

    @Override
    public void removeContact(int id) {
        if (kontakte.get(id) == null) {
            throw new IllegalArgumentException();
        }
        kontakte.remove(id);
    }

    @Override
    public void removeContact(Contact contact) {
        if ((kontakte.get(contact.getId()) == null)) {
            throw new IllegalArgumentException();
        }
        kontakte.remove(contact.getId());
    }

    @Override
    public int getSmallestUnusedId() {
        /*int smallest;
        if (kontakte.firstKey()==null){
            smallest = 0;
        }else smallest=kontakte.firstKey();*/

        Set<Integer> ids = kontakte.keySet();
        int smallest = Integer.MAX_VALUE;
        for (Integer id : ids) {
            if (id < smallest) {
                smallest = id;
            }
        }
        if (smallest == Integer.MAX_VALUE) {
            smallest = 1;
        } else if (smallest > 1) {
            smallest--;
        } else {
            while (kontakte.get(smallest) != null) {
                smallest++;
            }
        }
        return smallest;
    }

    @Override
    public String exportAllContacts() {
        if (kontakte.isEmpty()){
            return "";
        }
        Collection<Contact> kont = kontakte.values();
        StringBuilder sb = new StringBuilder();
        for (Contact kon : kont) {
            sb.append('\n');
            sb.append(kon.getId()).append(';').append(kon.getSalutation()).append(';').append(kon.getFirstName()).append(';').append(kon.getLastName()).append(';');
            LocalDate lo = kon.getBirthday();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            sb.append(lo.format(formatter));
            sb.append(';').append(kon.getStreetAddress()).append(';').append(kon.getZipCode()).append(';').append(kon.getCity()).append(';');
            Optional<Long> ph = kon.getPhone();
            if (ph.isPresent()){
                Long pho = ph.get();
                sb.append(pho).append(";");
            }else sb.append(";");
            Optional<String> em = kon.getEMail();
            if (em.isPresent()){
                String ema = em.get();
                sb.append(ema);
            }
        }
        sb.deleteCharAt(0);
        return sb.toString();
    }

    @Override
    public String exportMultipleContacts(Set<Integer> ids) {
        Map<Integer,Contact> sortiert= new TreeMap<>();
        for (Integer in : ids) {
            if (kontakte.get(in)==null){
                throw new IllegalArgumentException();
            }else sortiert.put(in,kontakte.get(in));
        }
        if (sortiert.isEmpty()){
            return "";
        }
        Collection<Contact> kont = sortiert.values();
        StringBuilder sb = new StringBuilder();
        for (Contact kon : kont) {
            sb.append('\n');
            sb.append(kon.getId()).append(';').append(kon.getSalutation()).append(';').append(kon.getFirstName()).append(';').append(kon.getLastName()).append(';');
            LocalDate lo = kon.getBirthday();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            sb.append(lo.format(formatter));
            sb.append(';').append(kon.getStreetAddress()).append(';').append(kon.getZipCode()).append(';').append(kon.getCity()).append(';');
            Optional<Long> ph = kon.getPhone();
            if (ph.isPresent()){
                Long pho = ph.get();
                sb.append(pho).append(";");
            }else sb.append(";");
            Optional<String> em = kon.getEMail();
            if (em.isPresent()){
                String ema = em.get();
                sb.append(ema);
            }
        }
        sb.deleteCharAt(0);
        return sb.toString();
    }

    @Override
    public String exportSingleContact(int id) {
        if (kontakte.get(id)==null){
            throw new IllegalArgumentException();
        }
        StringBuilder sb = new StringBuilder();
        Contact kon = kontakte.get(id);
        sb.append(kon.getId()).append(';').append(kon.getSalutation()).append(';').append(kon.getFirstName()).append(';').append(kon.getLastName()).append(';');
        LocalDate lo = kon.getBirthday();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        sb.append(lo.format(formatter));
        sb.append(';').append(kon.getStreetAddress()).append(';').append(kon.getZipCode()).append(';').append(kon.getCity()).append(';');
        Optional<Long> ph = kon.getPhone();
        if (ph.isPresent()){
            Long pho = ph.get();
            sb.append(pho).append(";");
        }else sb.append(";");
        Optional<String> em = kon.getEMail();
        if (em.isPresent()){
            String ema = em.get();
            sb.append(ema);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        AddressBook ad = new Adressbuch();
    }
}
