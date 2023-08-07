package com.example.insurance;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.sql.*;
public class AddRecordController implements Initializable {
    @FXML
    private TextField serialno,surname,firstName,lastName;

    @FXML
    private RadioButton lic,generalInsurance,mediclaim,vehicle,other;

    @FXML
    private TextField companyName,premium,policyNo,phone;

    @FXML
    private TextField vehicleNo;
    private RadioButton twoWheeler,fourWheeler;

    @FXML
    private DatePicker toDate,fromDate;

    @FXML
    private Button addButton,showRecordButton;
    @FXML
    private TextArea remarks;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String sNo = serialno.getText();
                String sName = surname.getText();
                String fName = firstName.getText();
                String lName = lastName.getText();
                String comp = companyName.getText();
                String vno = vehicleNo.getText();
                String prem = premium.getText();
                String phones = phone.getText();
                String iType = "";
                String pNo = policyNo.getText();
                String remark = remarks.getText();
                if(sNo.equals("")){
                    Utility.errorPopUp("Serialnumber is required");
                    return;
                }
                if(sName.equals("")){
                    Utility.errorPopUp("Surname is required");
                    return;
                }
                if(fName.equals("")){
                    Utility.errorPopUp("Firstname is required");
                    return;
                }
                if(comp.equals("")){
                    Utility.errorPopUp("Company Name is required");
                    return;
                }
                if(prem.equals("")){
                   Utility.errorPopUp("Premium is required");
                   return;
                }
                if(pNo.equals("")){
                    Utility.errorPopUp("Policynumber is required");
                    return;
                }

                if(lic.isSelected()){
                    iType="lic";
                }else if(generalInsurance.isSelected()){
                    iType = "General Insurance";
                }else if(vehicle.isSelected()){
                    iType = "vehicle";
                }else if(mediclaim.isSelected()){
                    iType = "mediclaim";
                }else{
                    iType = "other";
                }
                String vType = "";
                if(vehicle.isSelected()){
                    vType = "2w";
                }else{
                    vType = "4w";
                }
                boolean add = Utility.checkIfRecordExits(sNo);
                if(toDate.getValue() == null || fromDate.getValue() == null){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("ToDate and FromDate should not be null");
                    alert.show();
                    return;
                }


               else{
                    LocalDate toD = toDate.getValue();
                    String mytoD = toD.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    LocalDate fromD = fromDate.getValue();
                    String myfromD = fromD.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                    if(!add)
                        Utility.addRecord(actionEvent,pNo,sNo,sName,fName,lName,comp,iType,prem,mytoD,myfromD,vType,vno,phones,remark);
                    else
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Record With That SerialNumber already exits");
                        alert.show();
                    }
                }

            }
        });
        showRecordButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Utility.showRecordListPage(actionEvent);
            }
        });
    }

}
