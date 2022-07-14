/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package med.demand.controller;

import com.sun.javafx.print.Units;
import static com.sun.javafx.print.Units.MM;

import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import med.demand.dao.EBillDAO;
import med.demand.dao.MappingDAO;
import med.demand.dao.SettingsDAO;
import med.demand.dao.SuppliersDAO;
import med.demand.enums.Message;
import med.demand.model.SuppliersDetails;
import med.demand.util.ToastUtil;
import med.demand.util.Util;
import med.demand.model.EBillDetails;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


/**
 * FXML Controller class
 *
 * @author ndhara
 */
public class SupplierController implements Initializable {

    SuppliersDetails suppliersDetails;
    @FXML
    TextField txtSupName;
    @FXML
    TextField txtSupAdd;
    @FXML
    TextField txtSupPhone;
    @FXML
    TextField txtSupEmail;
    @FXML
    TextField txtSupProfitPer;
    @FXML
    Label lblsuplername;
    @FXML
    Label lblemailId;

    @FXML
    TableView tblSuppliers;

    @FXML
    Label lblProfit;

    @FXML
    Label lblPhoneNo;

    @FXML
    Button btnSave;

    @FXML
    private TableColumn<SuppliersDetails, String> clmnSupName;
    @FXML
    private TableColumn<SuppliersDetails, String> clmnSupAddress;
    @FXML
    private TableColumn<SuppliersDetails, String> clmnSupPhoneNo;
    @FXML
    private TableColumn<SuppliersDetails, String> clmnSupEmail;
    @FXML
    private TableColumn<SuppliersDetails, Float> clmnSupProfitPercent;

    private ObservableList<ObservableList<SuppliersDetails>> data = null;

    long id=0;

    private void clearData(){
        suppliersDetails.setName("");
        suppliersDetails.setAddress("");
        suppliersDetails.setEmail("");
        suppliersDetails.setPhoneNo("");
        suppliersDetails.setProfitPercent(0.0f);
    }

    private boolean validateForm() {
        boolean flag = true;
        if (Util.isblank(txtSupName)) {
            lblsuplername.setText("Supplier Name Can not be blank");
            flag = false;
        } else {
            lblsuplername.setText("");
        }
        if (!Util.isblank(txtSupEmail)) {
            if(!Util.isValidEmail(txtSupEmail.getText())){
                lblemailId.setText("Not a valid email id");
                flag = false;
            }
            else {
                lblemailId.setText("");
            }
        }

        if (!Util.isblank(txtSupPhone)) {
            if(!Util.isValidMobile(txtSupPhone.getText())){
                lblPhoneNo.setText("Not a valid Mobile No");
                flag = false;
            }
            else {
                lblPhoneNo.setText("");
            }
        }

        if (!Util.isblank(txtSupProfitPer)) {
            if(!Util.isValidIntDec(txtSupProfitPer.getText())){
                lblProfit.setText("Invalid number");
                flag = false;
            }
            else {
                if(Integer.parseInt(txtSupProfitPer.getText())>99){
                    lblProfit.setText("Profit should not be more than 99%");
                    flag = false;
                }
                else
                lblProfit.setText("");
            }
        }

        return flag;
    }

    @FXML
    private void saveSupplier(ActionEvent event) throws IOException, SQLException {


        System.out.println("..........."+btnSave.getText());


        if(validateForm()) {

            suppliersDetails = new SuppliersDetails();
            suppliersDetails.setName(txtSupName.getText());
            suppliersDetails.setAddress(txtSupAdd.getText());
            suppliersDetails.setEmail(txtSupEmail.getText());
            suppliersDetails.setPhoneNo(txtSupPhone.getText());
            suppliersDetails.setProfitPercent(Float.parseFloat(txtSupProfitPer.getText().equals("")? "0":txtSupProfitPer.getText()));

            if(btnSave.getText().equalsIgnoreCase("Add")){
                Stage stage = (Stage)((Node)((EventObject) event).getSource()).getScene().getWindow();

                try{
                    SuppliersDAO.addSupplier(suppliersDetails, 1);
                    clearData();
                    ToastUtil.makeText(stage, Message.SUPPLIES_ADDED.get(), 2500, 500, 500,200);

                }
                catch (Exception e){

                    ToastUtil.makeText(stage, Message.SUPPLIES_ADD_ERR.get(), 2500, 500, 500,500);

                }

            }else if(btnSave.getText().equalsIgnoreCase("Edit")){
                suppliersDetails.setId(id);
                Stage stage = (Stage)((Node)((EventObject) event).getSource()).getScene().getWindow();
                try {
                    SuppliersDAO.editSupplier(suppliersDetails, 1);
                    clearData();
                    ToastUtil.makeText(stage, Message.SUPPLIES_EDIT.get(), 2500, 500, 500,200);

                    btnSave.setText("Add");
                }
                catch (Exception e){

                    ToastUtil.makeText(stage, Message.SUPPLIES_EDIT_ERROR.get(), 2500, 500, 500,500);

                }



            }

            showDataToTable(SuppliersDAO.getSuppliers());

        }


    }


    private void showDataToTable(List<SuppliersDetails> list) {
        List al = new ArrayList();
        al.addAll(list);
        data = FXCollections.observableArrayList(al);

        tblSuppliers.getItems().setAll(data);

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lblPhoneNo.setText("");
        lblsuplername.setText("");
        lblemailId.setText("");
        lblProfit.setText("");
        clmnSupName.setCellValueFactory(new PropertyValueFactory<SuppliersDetails, String>("name"));
        clmnSupAddress.setCellValueFactory(new PropertyValueFactory<SuppliersDetails, String>("address"));
        clmnSupPhoneNo.setCellValueFactory(new PropertyValueFactory<SuppliersDetails, String>("phoneNo"));
        clmnSupEmail.setCellValueFactory(new PropertyValueFactory<SuppliersDetails, String>("email"));
        clmnSupProfitPercent.setCellValueFactory(new PropertyValueFactory<SuppliersDetails, Float>("profitPercent"));

        try {
            showDataToTable(SuppliersDAO.getSuppliers());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        MenuItem mnEdit = new MenuItem("Edit");
        MenuItem mnDelet = new MenuItem("Delete");
        mnEdit.setOnAction((ActionEvent event) -> {
            btnSave.setText("Edit");
            System.out.println("Edit");
            SuppliersDetails item = (SuppliersDetails) tblSuppliers.getSelectionModel().getSelectedItem();
            txtSupName.setText(item.getName());
            txtSupAdd.setText(item.getAddress());
            txtSupPhone.setText(item.getPhoneNo());
            txtSupEmail.setText(item.getEmail());
            txtSupProfitPer.setText(""+item.getProfitPercent());
            id=item.getId();
            System.out.println("Selected item: " + item);
        });

        mnDelet.setOnAction((ActionEvent event) -> {

            SuppliersDetails item = (SuppliersDetails) tblSuppliers.getSelectionModel().getSelectedItem();
            try {
                SuppliersDAO.deleteSupplier(item.getId());
                showDataToTable(SuppliersDAO.getSuppliers());
                Stage stage = new Stage();
                Scene scne= new Scene(new VBox());
                stage.setScene(scne);
                ToastUtil.makeText(stage, item.getName() +"  "+Message.SUPPLIES_DELETE.get(), 2500, 500, 500,200);
                MappingDAO.deleteMappingBySupplier(item.getId());

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        ContextMenu menu = new ContextMenu();
        menu.getItems().add(mnEdit);
        menu.getItems().add(mnDelet);
        tblSuppliers.setContextMenu(menu);

        tblSuppliers.setRowFactory( tv -> {
            TableRow<SuppliersDetails> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                btnSave.setText("Add");
                System.out.println("clicked.................");
            });
            return row ;
        });


    }

}
