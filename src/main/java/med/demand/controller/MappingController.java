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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import med.demand.dao.MappingDAO;
import med.demand.dao.MedicineDAO;
import med.demand.dao.SuppliersDAO;
import med.demand.enums.Message;
import med.demand.model.MappingDetails;
import med.demand.model.MedicineDetails;
import med.demand.model.SuppliersDetails;
import med.demand.util.AutoCompleteComboBoxListener;
import med.demand.util.ToastUtil;
import med.demand.util.Util;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collector;
import java.util.stream.Collectors;


/**
 * FXML Controller class
 *
 * @author ndhara
 */
public class MappingController implements Initializable {

    SuppliersDetails suppliersDetails;
   @FXML
    ComboBox<MedicineDetails> cmbMedicines;
    @FXML
    ComboBox<SuppliersDetails> cmbSuppliers;

    @FXML
    TableView tblMapping;

    @FXML
    Label lblmedicine;
    @FXML
    Label lblsuppliers;

    @FXML
    private TableColumn<MappingDetails, String> clmnMedicine;
    @FXML
    private TableColumn<MappingDetails, String> clmnSupplier;


    private ObservableList<ObservableList<MappingDetails>> data = null;



    @FXML
    private void saveMapping(ActionEvent event) throws IOException, SQLException {
        boolean flag =true;
     //   if(validateForm()) {
            List<MedicineDetails> medLst = MedicineDAO.getMedicine().stream().filter(med -> med.getName().equalsIgnoreCase(cmbMedicines.getEditor().getText())).collect(Collectors.toList());
            List<SuppliersDetails> supLst = SuppliersDAO.getSuppliers().stream().filter(med -> med.getName().equalsIgnoreCase(cmbSuppliers.getEditor().getText())).collect(Collectors.toList());
           if(medLst.size()==0) {
               lblmedicine.setText("Please select Medicine");
               flag=false;
           }

           else{
               lblmedicine.setText("");
               flag=true;
           }
        if(supLst.size()==0) {
            lblsuppliers.setText("Please select Spplier");
            flag=false;
        }

        else{
            lblsuppliers.setText("");
            flag=true;
        }
        if(flag) {
            MappingDAO.addMapping(medLst.get(0).getId(), supLst.get(0).getId(), 1);
            showDataToTable(MappingDAO.getMapping());
        }
        //}
    }

    private boolean validateSuppliersData(TextField txt){

        return Util.isblank(txt);
    }

    private void showDataToTable(List<MappingDetails> list) {
        List al = new ArrayList();
        al.addAll(list);
        data = FXCollections.observableArrayList(al);
        tblMapping.getItems().setAll(data);

    }


    private boolean validateForm() {
        boolean flag = true;
        if (Util.isblank(cmbMedicines)) {
            lblmedicine.setText("Please Select the Medicine");
            flag = false;
        } else {
            lblmedicine.setText("");
        }

        if (Util.isblank(cmbSuppliers)) {
            lblsuppliers.setText("Please Select the Supplier");
            flag = false;
        } else {
            lblsuppliers.setText("");
        }

        return flag;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lblsuppliers.setText("");
        lblmedicine.setText("");
        MenuItem mnDelet = new MenuItem("Delete");
        ContextMenu menu = new ContextMenu();
        menu.getItems().add(mnDelet);
        tblMapping.setContextMenu(menu);

        mnDelet.setOnAction((ActionEvent event) -> {

            MappingDetails item = (MappingDetails) tblMapping.getSelectionModel().getSelectedItem();
            try {
                MappingDAO.deleteMapping(item.getId());
                showDataToTable(MappingDAO.getMapping());
                Stage stage = new Stage();
                Scene scne= new Scene(new VBox());
                stage.setScene(scne);
                ToastUtil.makeText(stage, item.getSupName() +"  -  "+item.getMedName() +"  "+ Message.MEDICINE_DELETE.get(), 2500, 500, 500,200);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        clmnMedicine.setCellValueFactory(new PropertyValueFactory<MappingDetails, String>("medName"));
        clmnSupplier.setCellValueFactory(new PropertyValueFactory<MappingDetails, String>("supName"));
        cmbMedicines.getItems().clear();
        new AutoCompleteComboBoxListener<>(cmbMedicines);
        cmbSuppliers.getItems().clear();
       new AutoCompleteComboBoxListener<>(cmbSuppliers);

        try {
            cmbMedicines.getItems().addAll(MedicineDAO.getMedicine());
            cmbSuppliers.getItems().addAll(SuppliersDAO.getSuppliers());
            showDataToTable(MappingDAO.getMapping());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}
