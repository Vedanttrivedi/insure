package com.example.insurance;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class UpdateRecord implements Initializable {
    @FXML
    private Label recorddate,updatetext;
    @FXML
    private TextField serialno,surname,firstName,lastName;

    @FXML
    private RadioButton lic,generalInsurance,mediclaim,vehicle,other;

    @FXML
    private TextField companyName,premium,policyNo,phone;

    @FXML
    private TextField vehicleNo;
    private RadioButton twoWheeler = new RadioButton();
    @FXML
    private RadioButton fourwheeler = new RadioButton();

    @FXML
    private DatePicker toDate,fromDate;

    @FXML
    private Button updateButton,showRecordButton;
    @FXML
    private TextArea remarks;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showRecordButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                doStuff(actionEvent);
            }
        });
        updateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
               doStuff(actionEvent);
            }
        });
    }
    public void setInformation(String id){
        try{
            System.out.println("Set Information");
            String username = DbDetails.username;
            String password = DbDetails.password;
            String dbquery = DbDetails.query;
            Connection con = DriverManager.getConnection(dbquery, username, password);
            PreparedStatement ps = con.prepareStatement("SELECT * FROM customer where serialno = ?");
            ps.setString(1,id);
            ResultSet rset = ps.executeQuery();
            while(rset.next()){
                serialno.setText(rset.getString("serialno"));
                surname.setText(rset.getString("surname"));
                firstName.setText(rset.getString("firstname"));
                lastName.setText(rset.getString("lastname"));
                String insurancetype = rset.getString("insurancetype");
                if(insurancetype.equals("lic")){
                   lic.setSelected(true);
                }else if(insurancetype.equals("GI")){
                    generalInsurance.setSelected(true);
                }
                else if(insurancetype.equals("mediclaim")){
                    mediclaim.setSelected(true);
                }
                else if(insurancetype.equals("vehicle")){

                   if(rset.getString("vehicletype").equals("2w")){
                       twoWheeler.setSelected(true);
                   }else{
                       fourwheeler.setSelected(true);
                   }
                    vehicleNo.setText(rset.getString("vehicleno"));
                }else{
                    other.setSelected(true);
                }
                companyName.setText(rset.getString("company"));
                premium.setText(String.valueOf(rset.getInt("premium")));
                policyNo.setText(rset.getString("policyno"));
                phone.setText(rset.getString("phone"));
                remarks.setText("coollllll");
                LocalDate tod = LocalDate.parse(rset.getString("todate"));
                LocalDate fromd = LocalDate.parse(rset.getString("fromdate"));
                toDate.setValue(tod);
                fromDate.setValue(fromd);
                String fullname = Utility.changeToTitleCase(rset.getString("surname"),rset.getString("firstname"));
                updatetext.setText("Update Record Of "+fullname);
                recorddate.setText(rset.getString("recorddate"));
            }
            ps = con.prepareStatement("delete from customer where serialno = ?");
            ps.setString(1,id);
            ps.executeUpdate();
        }catch (Exception e){
            System.out.println("Got Exception while setting information on update page");
            e.printStackTrace();
        }
    }
    public void doStuff(ActionEvent actionEvent){
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
            iType = "GI";
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
            boolean check = Utility.checkIfRecordExits(sNo);
            if(!check){
                Utility.addRecord(actionEvent,pNo,sNo,sName,fName,lName,comp,iType,prem,mytoD,myfromD,vType,vno,phones,remark,recorddate.getText(),"updated");
                Utility.showRecordListPage(actionEvent);
            }else{
                Utility.errorPopUp("Record With That Serial number already exits");
            }
        }
    }
}
