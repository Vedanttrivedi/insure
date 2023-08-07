package com.example.insurance;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Line;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class OneRecord implements Initializable {
    private String sid;
    @FXML
    private Line remarkline;
    @FXML
    private Label fname, serialno, lname, fullname, surname, contact, age,remarklabel,vnolabel,vtypelabel;
    @FXML
    private Label insurance, company, policyno, premium, vehicletype, vehicleno;
    @FXML
    private TextArea remarks;
    @FXML
    private Label to, from;
    @FXML
    private Button logoutbutton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("class intialized");
    }

    public void setRecordInformation(String no) {
        System.out.println("setRecordInformation method is called");
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/insurance", "root", "Password@123");
            PreparedStatement ps = con.prepareStatement("select * from customer where serialno = ?");
            ps.setString(1, no);
            System.out.println("sid = " + sid);
            ResultSet rset = ps.executeQuery();
            if (rset != null) {
                while (rset.next()) {
                    String fullnames = rset.getString("firstname") + " " + rset.getString("surname");
                    String snames = rset.getString("surname");
                    String fnames = rset.getString("firstname");
                    fullnames = Utility.changeToTitleCase(snames,fnames);
                    System.out.println("Full name "+fullnames);
                    fullname.setText(fullnames);
                    serialno.setText(rset.getString("serialno"));
                    fname.setText(rset.getString("firstname"));
                    lname.setText(rset.getString("lastname"));
                    surname.setText(rset.getString("surname"));
                    contact.setText(rset.getString("phone"));
                    age.setText(String.valueOf(rset.getInt("age")));

                    insurance.setText(rset.getString("insurancetype"));
                    company.setText(rset.getString("company"));
                    policyno.setText(rset.getString("policyno"));
                    premium.setText(String.valueOf(rset.getInt("premium")));
                    if(rset.getString("vehicleno").equals("")){
                        vehicletype.setText("-");
                        vehicletype.setOpacity(0);
                        vehicleno.setOpacity(0);
                        vnolabel.setOpacity(0);
                        vtypelabel.setOpacity(0);
                    }else{
                        vehicletype.setText(rset.getString("vehicletype"));
                        vehicleno.setText(rset.getString("vehicleno"));
                    }
                    if(!rset.getString("remarks").equals("")){
                        remarkline.setOpacity(1);
                        remarklabel.setOpacity(1);
                        remarks.setText(rset.getString("remarks"));
                    }

                }
            }

        } catch (SQLException e) {
            System.out.println("got error in sqlexception");
            e.printStackTrace();
        }
    }

}
