/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package med.demand.controller;

import javafx.scene.image.Image;
import med.demand.dao.LoginDAO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author ndhara
 */
public class MainController extends Application implements Initializable {

    @FXML
    Button loginMenu;
    @FXML
    Button addSuppliers;
    
    @FXML
    Button medicine;
    
    @FXML
    Button demandList;
    @FXML
    Button settings;
    @FXML
    Button uploadData;
    @FXML
    Button btnMapping;
    @FXML
    Button logoutMenu;
    @FXML
    ImageView mainLogo;
    @FXML
    Label lblAddress;

    @FXML
    private void logout() {
        LoginDAO dao = new LoginDAO();
        dao.removeSession();
    }

    @FXML
    private void addSuppliers(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/med/fxml/supplier.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setFullScreen(false);
        stage.setTitle("Add Suppliers");
        stage.setScene(new Scene(root1));
        stage.getIcons().add(new Image("med/icons/suppliers.png"));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void openDemandList(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/med/fxml/demandList.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setFullScreen(false);
        stage.setTitle("Demand List");
        stage.setScene(new Scene(root1));
        stage.getIcons().add(new Image("med/icons/demand_list.png"));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void openSignUp(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/med/fxml/signup.fxml"));
        VBox root1 = (VBox) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setFullScreen(false);
        stage.setTitle("Sign Up");
        stage.setScene(new Scene(root1));
        stage.getIcons().add(new Image("med/icons/sharp_person_add.png"));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void openUploadData(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/med/fxml/uploadData.fxml"));
        VBox root1 = (VBox) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setFullScreen(false);
        stage.setTitle("Upload Data");
        stage.setScene(new Scene(root1));
        stage.getIcons().add(new Image("med/icons/upload.png"));
        stage.setResizable(false);
        stage.show();
    }


    @FXML
    private void openSettings(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/med/fxml/printDemandList.fxml"));
        VBox root1 = (VBox) fxmlLoader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setFullScreen(false);
        stage.setTitle("Settings");
        stage.setScene(new Scene(root1));
        stage.setResizable(false);
        stage.show();
    }
    @FXML
    private void openLogin(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/med/fxml/Login.fxml"));
        VBox root1 = (VBox) fxmlLoader.load();
        LoginController lController = fxmlLoader.getController();
        lController.eanbleButtons(addSuppliers, demandList, medicine,settings,btnMapping,uploadData);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setFullScreen(false);
        stage.setTitle("Login");
        stage.setScene(new Scene(root1));
        stage.getIcons().add(new Image("med/icons/sharp_person_black_24dp.png"));
        stage.setResizable(false);
        stage.show();
    }
    
    @FXML
    private void openMedicine(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/med/fxml/medicine.fxml"));
        VBox root1 = (VBox) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setFullScreen(false);
        stage.setTitle("Add Medicine");
        stage.setScene(new Scene(root1));
        stage.getIcons().add(new Image("med/icons/add_medinine.png"));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void addMapping(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/med/fxml/mapping.fxml"));
        VBox root1 = (VBox) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setFullScreen(false);
        stage.setTitle("Add Mapping");
        stage.setScene(new Scene(root1));
        stage.getIcons().add(new Image("med/icons/mapping.png"));
        stage.setResizable(false);
        stage.show();
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        javafx.geometry.Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        Double width=primaryScreenBounds.getWidth();
        Double height=primaryScreenBounds.getHeight();
        mainLogo.setLayoutX((width/2)-25);
        mainLogo.setLayoutY((height/2)-200);
        lblAddress.setLayoutX((width/2-100));
        lblAddress.setLayoutY(height/2);
        addSuppliers.setDisable(true);
        medicine.setDisable(true);
        demandList.setDisable(true);
        settings.setDisable(true);
        btnMapping.setDisable(true);
        uploadData.setDisable(true);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
