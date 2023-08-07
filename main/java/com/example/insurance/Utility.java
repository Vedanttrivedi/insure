package com.example.insurance;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utility {
    public static void showRecordListPage(ActionEvent event) {
        Parent root=null;
        try{
            FXMLLoader loader = new FXMLLoader(Utility.class.getResource("RecordList.fxml"));
            root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("All records");
            stage.setScene(new Scene(root,900,550));
            stage.show();

        }catch (IOException e){

        }
    }
    public static void showOneRecordPage(ActionEvent event,String recordId){
        System.out.println("method called");
        try{
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(Utility.class.getResource("OneRecord.fxml"));
            root = loader.load();
            OneRecord one = loader.getController();
            one.setRecordInformation(recordId);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("One Detailed Record");
            stage.setScene(new Scene(root,600,550));
            stage.show();
        }catch (IOException e){
            System.out.println("ERROR");
            e.printStackTrace();
        }
    }
    public static void addRecord(ActionEvent event,String pNo,String serialNo,String surname,String firstName,String lastName,String company,String insuranceType,String premium,String toDate,String fromDate,String vType,String vNo,String phones,String remark,String... recorddate){
        try{
            String username = DbDetails.username;
            String password = DbDetails.password;
            String dbquery = DbDetails.query;
            Connection con = DriverManager.getConnection(dbquery, username, password);
            String query="";
            if(recorddate.length !=0){
                 query = "INSERT INTO customer (serialno,surname,firstname,lastname,company,insurancetype,vehicleno,vehicletype,premium,fromdate,todate,policyno,phone,remarks,recorddate) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            }else{
                 query = "INSERT INTO customer (serialno,surname,firstname,lastname,company,insurancetype,vehicleno,vehicletype,premium,fromdate,todate,policyno,phone,remarks) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            }
            System.out.println("From Date "+fromDate);
            System.out.println("To Date "+toDate);
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,serialNo);
            ps.setString(2,surname);
            ps.setString(3,firstName);
            ps.setString(4,lastName);
            ps.setString(5,company);
            ps.setString(6,insuranceType);
            ps.setString(7,vNo);
            ps.setString(8,vType);
            ps.setString(9,premium);
            ps.setString(10,fromDate);
            ps.setString(11,toDate);
            ps.setString(12,pNo);
            ps.setString(13,phones);
            ps.setString(14,remark);
            if(recorddate.length != 0)
                ps.setString(15,recorddate[0]);
            ps.executeUpdate();

           if(recorddate.length !=0){
               if(recorddate[1].equals("updated")){
                   Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                   alert.setContentText("Record Updated");
                   alert.show();
               }else{
                   Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                   alert.setContentText("New Record Added");
                   alert.show();
               }
           }
            showRecordListPage(event);

        }catch (SQLException e){
           e.printStackTrace();
        }

    }
    public static void deleteRecord(String id){
        try{
            String username = DbDetails.username;
            String password = DbDetails.password;
            String dbquery = DbDetails.query;
            Connection con = DriverManager.getConnection(dbquery, username, password);
            PreparedStatement ps = con.prepareStatement("DELETE FROM customer where serialno = ?");
            ps.setString(1,id);
            ps.executeUpdate();
        }catch (Exception e){
            System.out.println("Got Error WHile deleting record");
            e.printStackTrace();
        }

    }
    public static void updateRecord(String id,ActionEvent event){
        try{
            System.out.println("Update Record Method called");
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(Utility.class.getResource("UpdateRecord.fxml"));
            root = loader.load();
            UpdateRecord update = loader.getController();
            update.setInformation(id);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Update Record");
            stage.setScene(new Scene(root,700,550));
            stage.show();
        }catch (Exception e){
            System.out.println("Got Error WHile deleting record");
            e.printStackTrace();
        }

    }
    public static void showAddRecordsPage(ActionEvent event){
       try{
           Parent root = null;
           FXMLLoader loader =new FXMLLoader(Utility.class.getResource("AddRecord.fxml"));
           root = loader.load();
           Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
           stage.setTitle("All records");
           stage.setScene(new Scene(root,700,550));
           stage.show();
       }catch (IOException e){
           System.out.println("got exception in showAddRecords Function");
           e.printStackTrace();
       }
    }
    public static ResultSet fetchRecords(){
        ResultSet rset = null;
        try{
            String username = DbDetails.username;
            String password = DbDetails.password;
            String dbquery = DbDetails.query;
            Connection con = DriverManager.getConnection(dbquery, username, password);
            PreparedStatement ps = con.prepareStatement("SELECT * FROM customer ORDER BY recorddate DESC");
            rset = ps.executeQuery();
        }catch (SQLException e){

        }
        return rset;
    }
   public static boolean checkIfRecordExits(String serialNumber){
        try{
            String username = DbDetails.username;
            String password = DbDetails.password;
            String dbquery = DbDetails.query;
            Connection con = DriverManager.getConnection(dbquery, username, password);
            PreparedStatement ps = con.prepareStatement("SELECT * FROM customer where serialno = ?");
            ps.setString(1,serialNumber);
            ResultSet rset = ps.executeQuery();
            if(rset.isBeforeFirst()){
                return true;
            }
        }catch (SQLException e){
            System.out.println("Got Error while checking if record exits or not");
            e.printStackTrace();
        }
        return false;
   }
    public static void errorPopUp(String error){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(error);
        alert.show();
    }
    public static ResultSet fetchOneRecord(String serialNo){
        try{
            String username = DbDetails.username;
            String password = DbDetails.password;
            String dbquery = DbDetails.query;
            Connection con = DriverManager.getConnection(dbquery, username, password);
            PreparedStatement ps = con.prepareStatement("SELECT * FROM customer where serialno = ?");
            ps.setString(1,serialNo);
            ResultSet rset = ps.executeQuery();
            if(rset.isBeforeFirst()){
                return rset;
            }
        }catch (SQLException e){
            System.out.println("Got Error while checking if record exits or not");
            e.printStackTrace();
        }
        String s ="";
        return null;
    }
    public static String changeToTitleCase(String surname,String fname){
        String newname="";
        newname+=fname.charAt(0);
        newname = newname.toUpperCase();
        for(int i=1;i<fname.length();i++){
            newname+=fname.charAt(i);
        }
        newname+=" ";
        char c = surname.charAt(0);
        String g = String.valueOf(c).toUpperCase();
        newname+=g;
        for(int i=1;i<surname.length();i++){
            newname+=surname.charAt(i);
        }
        return newname;
    }
    public static String datetimeFormat(String dateTime){
        String newdateTime="";
        for(int i=0;i<=9;i++)
            newdateTime+=dateTime.charAt(i);
        newdateTime+="-";
        for(int i=0;i<=3;i++)
            newdateTime+=dateTime.charAt(i);
        newdateTime+="-";
        return newdateTime;
    }
}
