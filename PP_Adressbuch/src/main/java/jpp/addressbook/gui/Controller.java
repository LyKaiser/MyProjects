package jpp.addressbook.gui;


import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jpp.addressbook.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;

public class Controller {


    public static AddressBook ad = new Adressbuch();
    public static AddressBookUtil adU = new AdressbuchUtil(ad);
    private ObservableList<Person> data = FXCollections.observableArrayList();


    public TableView<Person> table;
    public TableColumn<Person, String> tabSalu;
    public TableColumn<Person, String> tabFirstname;
    public TableColumn<Person, String> tabLastname;

    public MenuBar startFile;
    public MenuItem im;
    public MenuItem ex;
    public MenuItem show;

    public RadioButton radioAnd;
    public RadioButton radioOR;

    public CheckBox checkboxZipcode;
    public CheckBox checkboxLastname;
    public CheckBox checkboxyearofBirth;

    public TextField textzipFilter;
    public TextField textlastnameFilter;
    public TextField textyearofbirthFilter;

    public Button refreshbutton;
    public Button buttshowall;

    public ComboBox<Salutation> saluAuswahl;
    public TextField textFirstn;
    public TextField textLastn;
    public TextField textBday;
    public TextField textStreet;
    public TextField textZipcode;
    public TextField textCity;
    public TextField textEmail;
    public TextField textPhone;
    public Button buttonUpdate;
    public Button buttonDelete;
    public Button buttonNew;

    public ByteArrayOutputStream outputStream= new ByteArrayOutputStream();


    @FXML
    private void initialize() {
        tabSalu.setCellValueFactory(new PropertyValueFactory<>("salutation"));
        tabFirstname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tabLastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));


        ObservableList<Salutation> options = FXCollections.observableArrayList(Salutation.FRAU, Salutation.HERR);
        saluAuswahl.setItems(options);

        final ToggleGroup andOr = new ToggleGroup();
        radioAnd.setToggleGroup(andOr);
        radioOR.setToggleGroup(andOr);


        textlastnameFilter.visibleProperty().bind(checkboxLastname.selectedProperty());
        textyearofbirthFilter.visibleProperty().bind(checkboxyearofBirth.selectedProperty());
        textzipFilter.visibleProperty().bind(checkboxZipcode.selectedProperty());


        ad.importContacts("7;Herr;Muhammed;Döring;07.07.1923;Gleueler Str. 56;61250;Usingen;;\n2;Frau;Milena;Schweizer;06.02.1994;Hoove 127;84032;Landshut;87138315030;milena.schweizer@hotmeel.com\n" +
                "5;Frau;Erik;Schäfer;11.02.1973;Oscar-Wilde-Str. 189;24860;Klappholz;46238105692;");
        refresh();


        tableClickAction();
        setButtonNew();
        setButtonDelete();
        setButtonUpdate();

        setIm();

        setEx();
        setShow();
        setRadioAndOr();
        setButtshowall();

    }


    @FXML
    private void tableClickAction() {
        table.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (table.getSelectionModel().getSelectedItem() != null) {
                    Person person = table.getSelectionModel().getSelectedItem();
                    int id = person.getId();


                    if ((ad.getContact(id).isPresent())) {

                        Contact contact = ad.getContact(id).get();

                        saluAuswahl.setValue(person.salutation);
                        textFirstn.setText(person.firstName);
                        textLastn.setText(person.lastName);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                        textBday.setText(Objects.requireNonNull(contact).getBirthday().format(formatter));
                        textStreet.setText(contact.getStreetAddress());
                        textZipcode.setText(String.valueOf(contact.getZipCode()));
                        textCity.setText(contact.getCity());
                        Optional<String> mail = contact.getEMail();
                        String mailstr = "";
                        if (mail.isPresent()) {
                            mailstr = mail.get();
                        }
                        textEmail.setText(mailstr);
                        Optional<Long> phone = contact.getPhone();
                        String phostr = "";
                        if (phone.isPresent()) {
                            phostr = String.valueOf(phone.get());
                        }
                        textPhone.setText(phostr);
                    }
                }
            }
        });
    }

    @FXML
    private void setIm() {
        Stage stage = new Stage();
        im.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showOpenDialog(stage);
            ad.setErrorStream(new PrintStream(outputStream));
            if (file != null) {
                try {
                    String content = new String(Files.readAllBytes(Paths.get(file.toString())), StandardCharsets.UTF_8);
                    ad.importContacts(content);
                    refresh();
                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Fehler");
                    alert.setHeaderText("Importfehler");
                    alert.setContentText("Einige Kontakte konnten nicht importiert werden!");
                    alert.showAndWait();
                } catch (IOException e) {

                }
            }
        });
    }

    @FXML
    private void setEx() {
        Stage stage = new Stage();
        ex.setOnAction(actionEvent -> {
            String s = ad.exportAllContacts();
            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
            fileChooser.getExtensionFilters().add(extFilter);

            //Show save file dialog
            File file = fileChooser.showSaveDialog(stage);
            ad.setErrorStream(new PrintStream(outputStream));
            if (file != null) {
                try {
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write(s);
                    fileWriter.close();

                } catch (IOException e) {

                }
            }

        });
    }

    @FXML
    private void setShow() {
        show.setOnAction(actionEvent -> {
            Stage primaryStage = new Stage();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("provider.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            Scene scene = new Scene(root);
            primaryStage.setTitle("Stats");
            primaryStage.setScene(scene);
            primaryStage.sizeToScene();
            primaryStage.show();

        });
    }


    @FXML
    private void setButtshowall() {
        buttshowall.setOnAction(actionEvent -> {
            refresh();
            table.setItems(data);
            textlastnameFilter.clear();
            textyearofbirthFilter.clear();
            textzipFilter.clear();
            checkboxLastname.setSelected(false);
            checkboxyearofBirth.setSelected(false);
            checkboxZipcode.setSelected(false);


        });
    }

    @FXML
    private void setRadioAndOr() {
        refreshbutton.setOnAction(actionEvent -> {
            Set<Predicate<Contact>> predicatesAndOr = new HashSet<>();
            if (!textlastnameFilter.getText().isEmpty()) {
                predicatesAndOr.add(adU.lastNamePrefixFilter(textlastnameFilter.getText()));
            }
            if (!textyearofbirthFilter.getText().isEmpty()) {
                try {
                    Year year = Year.of(Integer.parseInt(textyearofbirthFilter.getText()));
                    predicatesAndOr.add(adU.birthYearFilter(Year.of(Integer.parseInt(textyearofbirthFilter.getText()))));
                } catch (Exception e) {
                    textyearofbirthFilter.clear();
                }
            }
            if (!textzipFilter.getText().isEmpty()) {
                try {
                    int year = Integer.parseInt(textzipFilter.getText());
                    predicatesAndOr.add(adU.zipCodeFilter(Integer.parseInt(textzipFilter.getText())));
                } catch (Exception e) {
                    textzipFilter.clear();
                }
            }

            if (radioAnd.isSelected()) {
                Set<Contact> set = adU.filterAnd(predicatesAndOr);
                data = FXCollections.observableArrayList();
                for (Contact con : set) {
                    data.add(new Person(con.getId(), con.getSalutation(), con.getFirstName(), con.getLastName()));
                }
                table.setItems(data);
            } else if (radioOR.isSelected()) {
                Set<Contact> set = adU.filterOr(predicatesAndOr);
                data = FXCollections.observableArrayList();
                for (Contact con : set) {
                    data.add(new Person(con.getId(), con.getSalutation(), con.getFirstName(), con.getLastName()));
                }
                table.setItems(data);
            }
        });
    }

    @FXML
    private void setButtonNew() {
        buttonNew.setOnAction(actionEvent -> {
            ad.setErrorStream(new PrintStream(outputStream));
            try {
                ad.importContacts(ad.getSmallestUnusedId() + ";" + saluAuswahl.getValue() + ";" + textFirstn.getText() + ";" + textLastn.getText()
                        + ";" + textBday.getText() + ";" + textStreet.getText() + ";" + textZipcode.getText() + ";" + textCity.getText()
                        + ";" + textPhone.getText() + ";" + textEmail.getText());
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Fehler");
                alert.setHeaderText("NewKontaktfehler!");
                alert.setContentText("Es konnte kein neuer Kontakt erstellt werden!");
                alert.showAndWait();
            }catch (Exception e){

            }

            //data.add(new Person(ad.getSmallestUnusedId(), saluAuswahl.getValue(), textFirstn.getText(), textLastn.getText()));
            refresh();
            clear();
        });
    }

    @FXML
    private void setButtonDelete() {

        buttonDelete.setOnAction(actionEvent -> {
            Person person = table.getSelectionModel().getSelectedItem();
            ad.removeContact(person.getId());
            refresh();
            clear();
        });
    }

    @FXML
    private void setButtonUpdate() {

        buttonUpdate.setOnAction(actionEvent -> {
            ad.setErrorStream(new PrintStream(outputStream));
            try {
                if (table.getSelectionModel().getSelectedItem() != null) {


                    Person person = table.getSelectionModel().getSelectedItem();
                    ad.importContacts(person.getId() + ";" + saluAuswahl.getValue() + ";" + textFirstn.getText() + ";" + textLastn.getText()
                            + ";" + textBday.getText() + ";" + textStreet.getText() + ";" + textZipcode.getText() + ";" + textCity.getText()
                            + ";" + textPhone.getText() + ";" + textEmail.getText());
                    refresh();
                    clear();
                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Fehler");
                    alert.setHeaderText("Updatefehler");
                    alert.setContentText("Kontakte konnte nicht aktualisiert werden!");
                    alert.showAndWait();
                }
            }catch (Exception e){

            }
        });
    }


    @FXML
    private void refresh() {
        Set<Contact> kontakte = ad.getContacts();
        data = FXCollections.observableArrayList();
        for (Contact kontakt : kontakte) {
            data.add(new Person(kontakt.getId(), kontakt.getSalutation(), kontakt.getFirstName(), kontakt.getLastName()));
        }
        table.setItems(data);
    }

    @FXML
    private void clear() {
        textFirstn.clear();
        textLastn.clear();
        textBday.clear();
        textStreet.clear();
        textZipcode.clear();
        textCity.clear();
        textPhone.clear();
        textEmail.clear();
    }
}
