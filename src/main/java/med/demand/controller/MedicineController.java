/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package med.demand.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
import med.demand.dao.MedicineDAO;
import med.demand.dao.SuppliersDAO;
import med.demand.enums.Message;
import med.demand.model.MedicineDetails;
import med.demand.model.SuppliersDetails;
import med.demand.util.AutoCompleteComboBoxListener;
import med.demand.util.ToastUtil;
import med.demand.util.Util;

/**
 * FXML Controller class
 *
 * @author ndhara
 */
public class MedicineController implements Initializable {

    MedicineDetails medicineDetails;

    @FXML
    TextField txtMedicineName;
    @FXML
    TableView tblMedicine;
    @FXML
    private TableColumn<MedicineDetails, String> medicineName;
    @FXML
    private Button btnSave;

    @FXML
    Label lblmedicine;
    private ObservableList<ObservableList<MedicineDetails>> data = null;
    long id=0;
    @FXML
    private void saveMedicine(ActionEvent event) throws IOException, SQLException {
        medicineDetails = new MedicineDetails();
        medicineDetails.setName(txtMedicineName.getText());
        if(validateForm()) {
            Stage stage = (Stage) ((Node) ((EventObject) event).getSource()).getScene().getWindow();

            if (btnSave.getText().equalsIgnoreCase("Add")) {

                try {
                    MedicineDAO.addMedicine(medicineDetails, 1);
                    ToastUtil.makeText(stage, Message.MEDICINE_ADDED.get(), 2500, 500, 500,200);

                }
                catch(SQLException e){
                    ToastUtil.makeText(stage, Message.MEDICINE_ADD_ERR.get(), 2500, 500, 500,500);
                }

            } else if (btnSave.getText().equalsIgnoreCase("Edit")) {
                medicineDetails.setId(id);
                medicineDetails.setName(txtMedicineName.getText());
                MedicineDAO.editMedicine(medicineDetails,1);
                ToastUtil.makeText(stage, Message.MEDICINE_EDIT.get(), 2500, 500, 500,200);
                btnSave.setText("Add");
            }
            showDataToTable(MedicineDAO.getMedicine());
        }


    }

    private void showDataToTable(List<MedicineDetails> list) {
        List al = new ArrayList();
        al.addAll(list);
        data = FXCollections.observableArrayList(al);

        tblMedicine.getItems().setAll(data);

    }
    private boolean validateSuppliersData(TextField txt){

        return Util.isblank(txt);
    }


    private boolean validateForm() {
        boolean flag = true;
        if (Util.isblank(txtMedicineName)) {
            lblmedicine.setText("Please Supply the Medicine Name");
            flag = false;
        } else {
            lblmedicine.setText("");
        }


        return flag;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblmedicine.setText("");
        medicineName.setCellValueFactory(new PropertyValueFactory<MedicineDetails, String>("name"));
        MenuItem mnEdit = new MenuItem("Edit");
        MenuItem mnDelet = new MenuItem("Delete");
        ContextMenu menu = new ContextMenu();
        menu.getItems().add(mnEdit);
        menu.getItems().add(mnDelet);
        tblMedicine.setContextMenu(menu);

        mnEdit.setOnAction((ActionEvent event) -> {
            btnSave.setText("Edit");

            MedicineDetails item = (MedicineDetails) tblMedicine.getSelectionModel().getSelectedItem();
            txtMedicineName.setText(item.getName());
            id=item.getId();

        });

        mnDelet.setOnAction((ActionEvent event) -> {

            MedicineDetails item = (MedicineDetails) tblMedicine.getSelectionModel().getSelectedItem();
            try {
                MedicineDAO.deleteMedicine(item.getId());
                showDataToTable(MedicineDAO.getMedicine());
                Stage stage = new Stage();
                Scene scne= new Scene(new VBox());
                stage.setScene(scne);
                ToastUtil.makeText(stage, item.getName() +"  "+ Message.MEDICINE_DELETE.get(), 2500, 500, 500,200);
                MappingDAO.deleteMappingByMedicine(item.getId());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        tblMedicine.setRowFactory( tv -> {
            TableRow<SuppliersDetails> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                btnSave.setText("Add");
                System.out.println("clicked.................");
            });
            return row ;
        });
        try {
           // cmbMedSup.getItems().addAll(SuppliersDAO.getSuppliers());
           showDataToTable(MedicineDAO.getMedicine());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }    
    
}
