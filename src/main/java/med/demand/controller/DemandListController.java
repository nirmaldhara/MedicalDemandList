/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package med.demand.controller;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import med.demand.dao.*;
import med.demand.enums.Message;
import med.demand.model.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import med.demand.util.AutoCompleteComboBoxListener;
import med.demand.util.ToastUtil;
import med.demand.util.Util;

/**
 * FXML Controller class
 *
 * @author ndhara
 */

public class DemandListController implements Initializable {

    private ObservableList<ObservableList<DemandListDetails>> data = null;
    List<DemandListDetails> demandList =new ArrayList<>();
    @FXML
    ComboBox<MedicineDetails> cmbDemandMedicine;
    @FXML
    ComboBox<SuppliersDetails> cmbDemandSupplier;
    @FXML
    ComboBox<Integer> cmbDemandQuantity;
    @FXML
    ComboBox cmdDemandListName;
    @FXML
    TableView tblDemandList;
    @FXML
    TableColumn<DemandListDetails,String> clmnDemLstMedicine;
    @FXML
    TableColumn<DemandListDetails,String> clmnDemLstSuppliers;
    @FXML
    TableColumn<DemandListDetails,Long> clmnDemLstQuantity;
    @FXML
    Label lblMedicine;
    @FXML
    Label lblSupplier;
    @FXML
    Label lblQuantity;
    @FXML
    Label lblDemandListFile;
    /**
     * Initializes the controller class.
     */
    @FXML
    private void saveDemandList(ActionEvent event) throws SQLException {
        long id =0;

        Stage stage = new Stage();
        Scene scne= new Scene(new VBox());
        stage.setScene(scne);

        if(!cmdDemandListName.getEditor().getText().equals("")) {
            id=DemandListDAO.addDemandListFile(cmdDemandListName.getEditor().getText(), 1);
            lblDemandListFile.setText("");
            if(demandList.size()==0) {
                ToastUtil.makeText(stage, "Nothing is the to add", 2500, 500, 500,500);

            }
            else
            {
                DemandListDAO.addDemandList(demandList, id, 1);
                ToastUtil.makeText(stage, cmdDemandListName.getEditor().getText() +"  "+ Message.DEMAND_LIST_SAVE.get(), 2500, 500, 500,200);

            }
        }
        else{
            lblDemandListFile.setText("Please provide the demand list file Name");
        }


    }
    @FXML
    private void addToDemandList(ActionEvent event) throws SQLException {
        boolean flag=true;
        boolean validFlag=true;
        DemandListDetails dd= new DemandListDetails();
        List<MedicineDetails> medLst=MedicineDAO.getMedicine().stream().filter(med->med.getName().equalsIgnoreCase(cmbDemandMedicine.getEditor().getText())).collect(Collectors.toList());
        List<SuppliersDetails> supLst=SuppliersDAO.getSuppliers().stream().filter(med->med.getName().equalsIgnoreCase(cmbDemandSupplier.getEditor().getText().substring(0,cmbDemandSupplier.getEditor().getText().indexOf("  --->")))).collect(Collectors.toList());


        if(medLst.size()==0){
            lblMedicine.setText("Please select medicine");
            validFlag=false;
        }
        else{
            lblMedicine.setText("");
            validFlag=true;
        }

        if(supLst.size()==0){
            lblSupplier.setText("Please select supplier");
            validFlag=false;
        }
        else{
            lblSupplier.setText("");
            validFlag=true;
        }


       try{
           Integer.parseInt(cmbDemandQuantity.getEditor().getText());
           validFlag=true;
           lblQuantity.setText("");
       }
       catch (NumberFormatException e){
           lblQuantity.setText("Please select valid Quantity");
           validFlag=false;
       }
        dd.setMedId(medLst.get(0).getId());
        dd.setMedName(medLst.get(0).getName());
        dd.setSupId(supLst.get(0).getId());
        dd.setSupName(supLst.get(0).getName());
        dd.setQuantity(Long.parseLong(cmbDemandQuantity.getEditor().getText()));
        for (DemandListDetails med:demandList) {
            if(med.getMedId()==dd.getMedId() && med.getSupId()==dd.getSupId()) {
                flag = false;
                break;
            }

        }
        if(flag && validFlag) {
            demandList.add(dd);
            showDataToTable(demandList);
        }
    }
    @FXML
    private void loadDemandList(ActionEvent event) throws SQLException {


        demandList=DemandListDAO.getDemandList(DemandListDAO.getFileId(cmdDemandListName.getEditor().getText()));

        showDataToTable(demandList);
    }

    private void showDataToTable(List<DemandListDetails> list) {
        List al = new ArrayList();
        al.addAll(list);
        data = FXCollections.observableArrayList(al);
        tblDemandList.getItems().setAll(data);
    }

    @FXML
    private void populateSuppliers(ActionEvent event) throws SQLException {
        cmbDemandSupplier.getItems().clear();
        cmbDemandSupplier.getItems().addAll(SuppliersDAO.getSuppliersForMedicine(cmbDemandMedicine.getEditor().getText()));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblSupplier.setText("");
        lblMedicine.setText("");
        lblQuantity.setText("");
        lblDemandListFile.setText("");
        MenuItem mnDelet = new MenuItem("Delete");
        ContextMenu menu = new ContextMenu();
        menu.getItems().add(mnDelet);
        tblDemandList.setContextMenu(menu);



        mnDelet.setOnAction((ActionEvent event) -> {

            DemandListDetails item = (DemandListDetails) tblDemandList.getSelectionModel().getSelectedItem();
            try {
                DemandListDAO.deleteDemandList(item.getId());
                demandList=DemandListDAO.getDemandList(DemandListDAO.getFileId(cmdDemandListName.getEditor().getText()));
                showDataToTable(demandList);
                Stage stage = new Stage();
                Scene scne= new Scene(new VBox());
                stage.setScene(scne);
                ToastUtil.makeText(stage, item.getSupName() +"  -  "+item.getMedName() +"  "+ Message.MEDICINE_DELETE.get(), 2500, 500, 500,200);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        new AutoCompleteComboBoxListener<>(cmdDemandListName);
        clmnDemLstMedicine.setCellValueFactory(new PropertyValueFactory<DemandListDetails, String>("medName"));
        clmnDemLstSuppliers.setCellValueFactory(new PropertyValueFactory<DemandListDetails, String>("supName"));
        clmnDemLstQuantity.setCellValueFactory(new PropertyValueFactory<DemandListDetails, Long>("quantity"));
        List<Integer> count= new ArrayList<>();
        cmbDemandSupplier.getItems().clear();
        new AutoCompleteComboBoxListener<>(cmbDemandMedicine);
        new AutoCompleteComboBoxListener<>(cmbDemandSupplier);
        new AutoCompleteComboBoxListener<>(cmbDemandQuantity);
        for (int i=1;i<=2000;i++)
        {
            count.add(i);
        }

        try {
            cmbDemandMedicine.getItems().addAll(MedicineDAO.getMedicine());
            cmbDemandQuantity.getItems().addAll(count);
            cmdDemandListName.getEditor().setText(Message.LIST_NAME.get()+"_"+ Util.currentDate());
            cmdDemandListName.getItems().addAll(DemandListFileDAO.getAllFiles());

                demandList=DemandListDAO.getDemandList(DemandListDAO.getFileId(cmdDemandListName.getEditor().getText()));
                showDataToTable(demandList);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
