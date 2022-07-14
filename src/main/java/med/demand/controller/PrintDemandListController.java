/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package med.demand.controller;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import med.demand.dao.*;
import med.demand.enums.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import med.demand.model.DemandListDetails;
import med.demand.model.DemandListFileDetails;
import med.demand.model.MedicineDetails;
import med.demand.model.SuppliersDetails;
import med.demand.util.AutoCompleteComboBoxListener;
import med.demand.util.PrintUtil;
import med.demand.util.ToastUtil;

/**
 *
 * @author ndhara
 */
public class PrintDemandListController implements Initializable {

    List<SuppliersDetails> medLst= null;
    ObservableList<DemandListFileDetails> items = null;
    @FXML
    ListView<DemandListFileDetails> lstDemandList;
    @FXML
    TableView tblDemandList;

    @FXML
    TableColumn<DemandListDetails,String> clmnDemLstMedicine;
    @FXML
    TableColumn<DemandListDetails,String> clmnDemLstSuppliers;
    @FXML
    TableColumn<DemandListDetails,Long> clmnDemLstQuantity;
    @FXML
    ComboBox<SuppliersDetails> cmbSuppliers;

    @FXML
    Button btnSave;

    private ObservableList<ObservableList<DemandListDetails>> data = null;
    private void showDataToTable(List<DemandListDetails> list) {
        List al = new ArrayList();
        al.addAll(list);
        data = FXCollections.observableArrayList(al);
        tblDemandList.getItems().setAll(data);
    }

    @FXML
private void addFilter(ActionEvent event) {

        try {
            medLst = SuppliersDAO.getSuppliers().stream().filter(sup->sup.getName().equalsIgnoreCase(cmbSuppliers.getEditor().getText())).collect(Collectors.toList());
            if(medLst!=null && medLst.size()>0  && data!=null && data.size()>0)
            showDataToTable(DemandListDAO.getDemandList(lstDemandList.getSelectionModel().getSelectedItem().getId(),medLst.get(0).getId()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


}


    @Override
    public void initialize(URL url, ResourceBundle rb) {


        MenuItem mnDelet = new MenuItem("Delete");
        ContextMenu menu = new ContextMenu();
        menu.getItems().add(mnDelet);
        lstDemandList.setContextMenu(menu);

        mnDelet.setOnAction((ActionEvent event) -> {

            DemandListFileDetails item = (DemandListFileDetails) lstDemandList.getSelectionModel().getSelectedItem();
            try {

                System.out.println("----------------"+item.getId());
                DemandListDAO.deleteDemandListByFile(item.getId());


                Stage stage = new Stage();
                Scene scne= new Scene(new VBox());
                stage.setScene(scne);
                ToastUtil.makeText(stage, item.getFileName()  + Message.MEDICINE_DELETE.get(), 2500, 500, 500,200);
                DemandListFileDAO.deleteDemandLisFile(item.getId());
                try {
                    items=null;
                    items=  FXCollections.observableArrayList(DemandListFileDAO.getAllFiles());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                lstDemandList.getItems().clear();
                tblDemandList.getItems().clear();
                lstDemandList.getItems().addAll(items);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });



        clmnDemLstMedicine.setCellValueFactory(new PropertyValueFactory<DemandListDetails, String>("medName"));
        clmnDemLstSuppliers.setCellValueFactory(new PropertyValueFactory<DemandListDetails, String>("supName"));
        clmnDemLstQuantity.setCellValueFactory(new PropertyValueFactory<DemandListDetails, Long>("quantity"));
        try {
            items=  FXCollections.observableArrayList(DemandListFileDAO.getAllFiles());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        lstDemandList.getItems().addAll(items);

        cmbSuppliers.getItems().clear();
        new AutoCompleteComboBoxListener<>(cmbSuppliers);
        try {
            cmbSuppliers.getItems().addAll(SuppliersDAO.getSuppliers());
          //  showDataToTable(DemandListDAO.getDemandList(lstDemandList.getSelectionModel().getSelectedItem().getId()));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
    @FXML
    private void selectDemandList(MouseEvent arg0) throws SQLException {
        showDataToTable(DemandListDAO.getDemandList(lstDemandList.getSelectionModel().getSelectedItem().getId()));

        System.out.println("clicked on " + lstDemandList.getSelectionModel().getSelectedItem().getId());
    }


    @FXML
    private void printDemandList(ActionEvent event) throws IOException, SQLException {

        Stage stage = new Stage();
        Scene scne= new Scene(new VBox());
        stage.setScene(scne);

        if(cmbSuppliers.getEditor().getText().equals("")) {
           if(lstDemandList.getSelectionModel().getSelectedItem()!=null) {
               if (PrintUtil.createDemandListFile(DemandListDAO.getDemandList(lstDemandList.getSelectionModel().getSelectedItem().getId()))) {
                   PrintUtil.print();

               }
           }else{
               ToastUtil.makeText(stage, "Please select a file", 2500, 500, 500,500);


           }

        }
        else{
            medLst = SuppliersDAO.getSuppliers().stream().filter(sup->sup.getName().equalsIgnoreCase(cmbSuppliers.getEditor().getText())).collect(Collectors.toList());
            if(medLst!=null && medLst.size()>0)
            if (PrintUtil.createDemandListFile(DemandListDAO.getDemandList(lstDemandList.getSelectionModel().getSelectedItem().getId(),medLst.get(0).getId()))) {
                PrintUtil.print();

            }
           else  if (PrintUtil.createDemandListFile(DemandListDAO.getDemandList(lstDemandList.getSelectionModel().getSelectedItem().getId(),0))) {
                PrintUtil.print();

            }
        }

    }

}
