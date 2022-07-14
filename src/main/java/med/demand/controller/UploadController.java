/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package med.demand.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import med.demand.dao.MappingDAO;
import med.demand.dao.MedicineDAO;
import med.demand.dao.SignUpDAO;
import med.demand.dao.SuppliersDAO;
import med.demand.model.MedicineDetails;
import med.demand.model.SignUp;
import med.demand.model.SuppliersDetails;
import med.demand.util.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author ndhara
 */
public class UploadController implements Initializable {
@FXML
ImageView imgProcess;
@FXML
TextField txtFilePath;
@FXML
Button btnFileUpload;
@FXML
Label lblMsgFileChooser;
@FXML
Label lblMsgProcess;
    @FXML
    public void openFileChooser(){
        Stage primaryStage =new Stage();
        FileChooser file = new FileChooser();
        file.setTitle("Open File");
        file.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV FILES", "*.csv")

        );
        File path =file.showOpenDialog(primaryStage);
        txtFilePath.setText(path.getPath());
        System.out.println(path.getPath());
//        HBox root = new HBox();
//
//        root.setSpacing(20);
//
//        Scene scene = new Scene(root,350,100);
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("FileChooser Example");
//        primaryStage.show();
    }
   private void readDataFromCSV(){
       String line = "";
       String splitBy = ",";
       try
       {

           BufferedReader br = new BufferedReader(new FileReader(txtFilePath.getText()));
           br.readLine();
           while ((line = br.readLine()) != null)
           {

               String[] data = line.split(splitBy);    // use comma as separator

               try {
                   SuppliersDetails suppliersDetails = new SuppliersDetails();
                   suppliersDetails.setName(data[0]);
                   suppliersDetails.setAddress(data[1]);
                   suppliersDetails.setEmail(data[2]);
                   suppliersDetails.setPhoneNo(data[3]);
                   suppliersDetails.setProfitPercent(Float.parseFloat(data[4]));
                   SuppliersDAO.addSupplier(suppliersDetails, 1);

               }
               catch ( SQLException e){
                e.printStackTrace();
               }

               try{
                   MedicineDetails medicineDetails = new MedicineDetails();
                   medicineDetails.setName(data[5]);
                   MedicineDAO.addMedicine(medicineDetails, 1);
               }
               catch ( SQLException e){
                   e.printStackTrace();
               }

               try{
                   List<SuppliersDetails> suppliersDetails= SuppliersDAO.getSupplierByName(data[0]);
                   List<MedicineDetails> medicineDetails =MedicineDAO.getMedicineByName(data[5]);
                   MappingDAO.addMapping(medicineDetails.get(0).getId(),suppliersDetails.get(0).getId(),1);

               }
               catch ( SQLException e){
                   e.printStackTrace();
               }
               System.out.println("Employee [First Name=" + data[0] + ", Last Name=" + data[1] );
           }
       }
       catch (IOException e)
       {
           e.printStackTrace();

       }
   }


    @FXML
    public void onUploadButtonClick(){
        if(txtFilePath.getText().isEmpty())
            lblMsgFileChooser.setVisible(true);
        else {
            lblMsgFileChooser.setVisible(false);
            imgProcess.setVisible(true);
            
            lblMsgProcess.setVisible(true);
            readDataFromCSV();

            lblMsgProcess.setText("Data  upload completed");
            imgProcess.setVisible(false);
        }

}
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        imgProcess.setVisible(false);
        lblMsgFileChooser.setVisible(false);
        lblMsgProcess.setVisible(false);

    }

}
