package jpp.addressbook.gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import jpp.addressbook.Contact;
import jpp.addressbook.Salutation;

import java.io.IOException;

public class AddressBookApplicaton extends Application {

    //private Stage primaryStage;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene=new Scene(root);
        primaryStage.setTitle("Addressbook");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();


    }
}
