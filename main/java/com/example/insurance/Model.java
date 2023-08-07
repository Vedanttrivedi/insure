package com.example.insurance;

import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.print.Printer;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Optional;

public class Model {
    private String sname,fname,lname,serialno,fullname;
    private String company,insuranceT,vehicleno,vehicle;
    private int premium;
    private String todate,fromdate,phone,policyno;
    private String remarks;
    private Button detailed,print,delete;
    public Model(String sno,String sName,String fName,String lName,String phone,String com,String iTy,String vno,String vTY,String pr,String toDa,String fromDa,String pNo){
        this.serialno = sno;
        this.sname = sName;
        this.fname = fName;
        this.lname = lName;
        this.fullname = Utility.changeToTitleCase(sName,fName);
        System.out.println("Fullname = "+fullname);
        this.phone = phone;
        this.company = com;
        this.insuranceT = iTy;
        this.vehicleno = vno;
        this.vehicle = vTY;
        this.premium = Integer.parseInt(pr);
        this.todate = toDa;
        this.fromdate = fromDa;
        this.policyno = pNo;
        this.detailed = new Button("Update");
        this.detailed.setId(this.serialno);
        this.print = new Button("Print");
        this.print.setId(this.serialno);
        this.delete = new Button("Delete");
        this.delete.setId(this.serialno);
        this.detailed.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
               final Node node= (Node) actionEvent.getSource();
               String id=node.getId();
                System.out.println("Passed Id from recordlist ="+id);
                Utility.updateRecord(id,actionEvent);

            }
        });
        this.delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Delete Function Called");
                final Node node = (Node) actionEvent.getSource();
                String id = node.getId();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Delete this record??");
                Optional<ButtonType> result =alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK){
                    Utility.deleteRecord(id);
                    Utility.showRecordListPage(actionEvent);
                }else{
                    alert.close();
                }



            }
        });
        this.print.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                final Node node = (Node) actionEvent.getSource();
                Button btn = new Button("noice");
                String id = node.getId();
                PrinterJob job = PrinterJob.createPrinterJob();
                ResultSet rset = Utility.fetchOneRecord(id);

                   try{
                       Parent root;
                       FXMLLoader loader = new FXMLLoader(Model.class.getResource("OneRecord.fxml"));
                       root = loader.load();
                       OneRecord ones = loader.getController();
                       ones.setRecordInformation(id);

                       boolean printed = job.printPage(root);
                       if(printed){
                           System.out.println("Document Printed");
                       }else{
                           System.out.println("Document not printed");
                       }
                   }catch (IOException e){
                       System.out.println("print root node is null");
                       e.printStackTrace();
                   }
            }
        });

    }

    public String getSerialno(){
        return this.serialno;
    }
    public String getName(){
        return Utility.changeToTitleCase(this.sname,this.fname);
    }
    public String getInsuranceT(){
        return this.insuranceT;
    }
    public String getCompany(){
        return this.company;
    }
    public String getVehicle(){
        return this.vehicle;
    }
    public String getPolicyno(){
        return this.policyno;
    }
    public String getVehicleno(){
        return this.vehicleno;
    }
    public Integer getPremium(){
        return this.premium;
    }
    public String getFromdate(){
        return this.fromdate;
    }
    public String getTodate(){
        return this.todate;
    }
    public String getPhone(){
        return this.phone;
    }
    public Button getDetailed(){
        return this.detailed;
    }
    public Button getPrint(){return this.print;}
    public Button getDelete(){
        return this.delete;
    }
}
