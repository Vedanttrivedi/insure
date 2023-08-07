package com.example.insurance;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TestController implements Initializable {
    @FXML
    private TableView<Test> table;

    @FXML
    private TableColumn<Test,String> c1;
    @FXML
    private TableColumn<Test,String> c2;

    ObservableList<Test> l1 = FXCollections.observableArrayList(
            new Test("cool","bro")
    );
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        c1.setCellValueFactory(new PropertyValueFactory<Test,String>("c1"));
        c2.setCellValueFactory(new PropertyValueFactory<Test,String>("c2"));
        table.setItems(l1);
    }
}
