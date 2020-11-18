package jpp.addressbook.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import jpp.addressbook.*;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Controler_provider {
    public Label durchschn;
    public ComboBox<Salutation> Frau_Herr;
    public Label frau_herr_Label;
    public TableView<Email> tabprovider;
    public TableColumn<Object, Object> provider;
    public TableColumn<Object, Object> anzahl;
    ObservableList<Email> observableList= FXCollections.observableArrayList();


    @FXML
    private void initialize() {

        provider.setCellValueFactory(new PropertyValueFactory<>("provider"));
        anzahl.setCellValueFactory(new PropertyValueFactory<>("anzahl"));

        ObservableList<Salutation> options = FXCollections.observableArrayList(Salutation.FRAU, Salutation.HERR);
        Frau_Herr.setItems(options);


        frau_herr_Label.setText("Anteil Frau/Herr");

        DecimalFormat f = new DecimalFormat("#0.00");
        Double durchschnitt =Controller.adU.getAverageAgeAt(LocalDate.now());

        durchschn.setText("Durchschnittliches Alter aller Kontakte: "+ f.format(durchschnitt));

        String[] str = new String[]{("Anteil an Kontakten mit Anrede Frau: "+f.format(Controller.adU.getSalutationShare(Salutation.FRAU))),("Anteil an Kontakten mit Anrede Herr: "+f.format(Controller.adU.getSalutationShare(Salutation.HERR)))};
        Frau_Herr.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                frau_herr_Label.setText(str[t1.intValue()]);
            }
        });

        provider.setCellValueFactory(new PropertyValueFactory<>("provider"));
        anzahl.setCellValueFactory(new PropertyValueFactory<>("anzahl"));
        Map<String,Double> gesamt = Controller.adU.getMailProviderShare();
        Set<String> prov = Controller.adU.getMailProviderShare().keySet();
        for (String s : prov){
            Email mail = new Email(s, gesamt.get(s));
            observableList.add(mail);
        }
        tabprovider.setItems(observableList);
    }
}
