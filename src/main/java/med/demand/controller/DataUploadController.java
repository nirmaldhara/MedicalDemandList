/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package med.demand.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import med.demand.dao.MappingDAO;
import med.demand.dao.SuppliersDAO;
import med.demand.enums.Message;
import med.demand.model.SuppliersDetails;
import med.demand.util.ToastUtil;
import med.demand.util.Util;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.ResourceBundle;


/**
 * FXML Controller class
 *
 * @author ndhara
 */
public class DataUploadController implements Initializable {

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











    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {



    }

}
