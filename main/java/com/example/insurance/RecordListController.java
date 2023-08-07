package com.example.insurance;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RecordListController implements Initializable {

    @FXML
    private Button addrecord,searchrecord;

    @FXML
    private TableView<Model> table;

    @FXML
    private TableColumn<Model,String> serialno;
    @FXML
    private TableColumn<Model,String> name;
    @FXML
    private TableColumn<Model,String> insuranceT;
    @FXML
    private TableColumn<Model,String> company;
    @FXML
    private TableColumn<Model,String> vehicle;
    @FXML
    private TableColumn<Model,String> vehicleno;
    @FXML
    private TableColumn<Model,String> policyno;
    @FXML
    private TableColumn<Model,String> fromdate;
    @FXML
    private TableColumn<Model,String> todate;
    @FXML
    private TableColumn<Model,String> phone;
    @FXML
    private TableColumn<Model,Integer> premium;

    @FXML
    private TableColumn<Model,Button> detailed;
    @FXML
    private TableColumn<Model,Button> print,delete;


    ObservableList<Model> list = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        serialno.setCellValueFactory(new PropertyValueFactory<Model,String>("serialno"));
        name.setCellValueFactory(new PropertyValueFactory<Model,String>("name"));
        insuranceT.setCellValueFactory(new PropertyValueFactory<Model,String>("insuranceT"));
        company.setCellValueFactory(new PropertyValueFactory<Model,String>("company"));

        vehicle.setCellValueFactory(new PropertyValueFactory<Model,String>("vehicle"));
        vehicleno.setCellValueFactory(new PropertyValueFactory<Model,String>("vehicleno"));
        policyno.setCellValueFactory(new PropertyValueFactory<Model,String>("policyno"));
        fromdate.setCellValueFactory(new PropertyValueFactory<Model,String>("fromdate"));
        todate.setCellValueFactory(new PropertyValueFactory<Model,String>("todate"));
        phone.setCellValueFactory(new PropertyValueFactory<Model,String>("phone"));
        premium.setCellValueFactory(new PropertyValueFactory<Model,Integer>("premium"));
        detailed.setCellValueFactory(new PropertyValueFactory<Model,Button>("detailed"));
        print.setCellValueFactory(new PropertyValueFactory<Model,Button>("print"));
        delete.setCellValueFactory(new PropertyValueFactory<Model,Button>("delete"));
        ResultSet set = Utility.fetchRecords();
        int counter=0;
        try{
            while (set.next()){

                String sno = set.getString("serialno");
                String firstname = set.getString("firstname");
                String lastname = set.getString("lastname");
                String surname = set.getString("surname");
                String iType = set.getString("insurancetype");
                String comp = set.getString("company");
                String phones = set.getString("phone");
                String vno = set.getString("vehicleno");
                String vty = set.getString("vehicletype");
                String pre = String.valueOf(set.getInt("premium"));
                String tod = set.getString("todate");
                String fromd = set.getString("fromdate");
                String pno = set.getString("policyno");
                if(vno.equals("")){
                    vno="  -  ";
                    vty = "  -  ";
                }
                if(iType.equals("General Insurance")){
                    iType = "    GI";
                }
                if(phones.equals("")){
                    phones = "\t-\t";
                }
                counter++;
                Model m1 = new Model(sno,surname,firstname,lastname,phones,comp,iType,vno,vty,pre,tod,fromd,pno);
                list.add(m1);
            }
        }catch (SQLException e){
            System.out.println("got exception");
            e.printStackTrace();
        }
       // System.out.println("this is list "+list.toString());
        table.setItems(list);
        addrecord.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Utility.showAddRecordsPage(actionEvent);
            }
        });

    }
}